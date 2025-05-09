package com.zhelandovskiy.user_subscriptions.service.impl;

import com.zhelandovskiy.user_subscriptions.dto.SubscriptionCreateDto;
import com.zhelandovskiy.user_subscriptions.dto.SubscriptionTopDto;
import com.zhelandovskiy.user_subscriptions.entity.SubscriptionEntity;
import com.zhelandovskiy.user_subscriptions.exception.NotFoundException;
import com.zhelandovskiy.user_subscriptions.mapper.SubscriptionMapper;
import com.zhelandovskiy.user_subscriptions.repository.SubscriptionRepository;
import com.zhelandovskiy.user_subscriptions.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    public List<SubscriptionTopDto> getTop() {
        return subscriptionRepository.getTopSubscription();
    }

    @Override
    @Transactional
    public SubscriptionEntity getOrInsert(SubscriptionCreateDto dto) {

        Optional<SubscriptionEntity> subscription = subscriptionRepository.findByCode(dto.getCode());

        if (subscription.isPresent()) {
            log.info("Subscription exist code: {}", dto.getCode());
            return subscription.get();
        } else {
            SubscriptionEntity subscriptionNewEntity = subscriptionMapper.toEntity(dto);
            SubscriptionEntity subscriptionSavedEntity = subscriptionRepository.save(subscriptionNewEntity);
            log.info("Subscription not exist created new, id: {}, code: {}", subscriptionSavedEntity.getId(), subscriptionSavedEntity.getCode());

            return subscriptionSavedEntity;
        }
    }

    @Override
    public SubscriptionEntity getByCode(String code) {
        Optional<SubscriptionEntity> optionalSubscriptionEntity = subscriptionRepository.findByCode(code);
        if (optionalSubscriptionEntity.isEmpty()) {
            String message = String.format("Subscription code: %s not found", code);
            log.error(message);
            throw new NotFoundException(message);
        } else
            return optionalSubscriptionEntity.get();
    }
}
