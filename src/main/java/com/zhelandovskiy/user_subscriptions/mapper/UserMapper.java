package com.zhelandovskiy.user_subscriptions.mapper;

import com.zhelandovskiy.user_subscriptions.dto.UserDto;
import com.zhelandovskiy.user_subscriptions.dto.UserCreateUpdateDto;
import com.zhelandovskiy.user_subscriptions.dto.UserNewDto;
import com.zhelandovskiy.user_subscriptions.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(UserEntity entity);

    UserNewDto toDtoNew(UserEntity entity);

    List<UserDto> toDtoList(List<UserEntity> entity);

    UserEntity toEntity(UserCreateUpdateDto dto);
}
