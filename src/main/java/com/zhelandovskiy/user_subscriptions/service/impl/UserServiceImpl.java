package com.zhelandovskiy.user_subscriptions.service.impl;

import com.zhelandovskiy.user_subscriptions.dto.SubscriptionCreateDto;
import com.zhelandovskiy.user_subscriptions.dto.UserCreateUpdateDto;
import com.zhelandovskiy.user_subscriptions.dto.UserDto;
import com.zhelandovskiy.user_subscriptions.dto.UserNewDto;
import com.zhelandovskiy.user_subscriptions.entity.SubscriptionEntity;
import com.zhelandovskiy.user_subscriptions.entity.UserEntity;
import com.zhelandovskiy.user_subscriptions.exception.AlreadyExistException;
import com.zhelandovskiy.user_subscriptions.exception.NotFoundException;
import com.zhelandovskiy.user_subscriptions.mapper.UserMapper;
import com.zhelandovskiy.user_subscriptions.repository.UserRepository;
import com.zhelandovskiy.user_subscriptions.service.SubscriptionService;
import com.zhelandovskiy.user_subscriptions.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SubscriptionService subscriptionService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Cacheable(value = "user_cache")
    public UserDto getById(Long id) {

        UserEntity entityById = getUserEntityById(id);
        log.info("User received, id: {}", entityById.getId());

        return userMapper.toDto(entityById);

    }

    @Override
    @Transactional
    public UserNewDto create(UserCreateUpdateDto dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            String message = String.format("User already exist, email: %s", dto.getEmail());
            log.error(message);
            throw new AlreadyExistException(message);
        }

        UserEntity userNewEntity = userMapper.toEntity(dto);
        UserEntity userSavedEntity = userRepository.save(userNewEntity);
        log.info("User created, id: {}", userSavedEntity.getId());

        return userMapper.toDtoNew(userSavedEntity);
    }

    @Override
    @Transactional
    @CacheEvict(value = "user_cache", allEntries = true)
    public UserDto update(Long id, UserCreateUpdateDto dto) {

        if (userExist(id)) {
            UserEntity userUpdateEntity = userMapper.toEntity(dto);
            userUpdateEntity.setId(id);
            UserEntity userSavedEntity = userRepository.save(userUpdateEntity);
            log.info("User updated, id: {}", userSavedEntity.getId());

            return userMapper.toDto(userSavedEntity);
        }

        return null;
    }

    @Override
    @Transactional
    @CacheEvict(value = "user_cache", allEntries = true)
    public void delete(Long id) {

        if (userExist(id)) {
            userRepository.deleteById(id);
            log.info("User deleted, id: {}", id);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "user_cache", allEntries = true)
    public UserDto addSubscription(Long id, SubscriptionCreateDto dto) {

        UserEntity entityById = getUserEntityById(id);

        if (subscriptionExist(entityById, dto.getCode())) {
            String message = String.format("User id: %s already has subscription code: %s ", id, dto.getCode());
            log.error(message);
            throw new AlreadyExistException(message);
        }

        SubscriptionEntity subscriptionEntity = subscriptionService.getOrInsert(dto);
        entityById.addSubscription(subscriptionEntity);

        UserEntity savedUserEntity = userRepository.save(entityById);
        log.info("Subscription code: {} added to user id: {}", dto.getCode(), id);

        return userMapper.toDto(savedUserEntity);
    }

    @Override
    @CacheEvict(value = "user_cache", allEntries = true)
    public UserDto removeSubscription(Long id, String code) {

        UserEntity entityById = getUserEntityById(id);

        if (!subscriptionExist(entityById, code)) {
            String message = String.format("User id: %s already has not subscription code: %s ", id, code);
            log.error(message);
            throw new NotFoundException(message);
        }

        SubscriptionEntity subscriptionEntityByCode = subscriptionService.getByCode(code);
        entityById.removeSubscription(subscriptionEntityByCode);
        userRepository.save(entityById);
        log.info("Subscription code:{} removed from user id: {}", code, id);

        return userMapper.toDto(entityById);
    }

    private boolean userExist(Long id) {
        getUserEntityById(id);

        return true;
    }

    private UserEntity getUserEntityById(Long id) {

        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            String message = String.format("User not found id: %s", id);
            log.error(message);

            throw new NotFoundException(message);
        }

        return optionalUser.get();
    }

    private boolean subscriptionExist(UserEntity userEntity, String code) {
        return userEntity.getSubscriptionList().stream()
                .anyMatch(subscription -> subscription.getCode().equals(code));
    }
}
