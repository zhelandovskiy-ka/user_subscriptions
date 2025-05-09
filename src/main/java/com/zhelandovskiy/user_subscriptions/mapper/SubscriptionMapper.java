package com.zhelandovskiy.user_subscriptions.mapper;

import com.zhelandovskiy.user_subscriptions.dto.SubscriptionCreateDto;
import com.zhelandovskiy.user_subscriptions.dto.SubscriptionDto;
import com.zhelandovskiy.user_subscriptions.entity.SubscriptionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionDto toDto(SubscriptionEntity entity);

    SubscriptionEntity toEntity(SubscriptionCreateDto dto);
}
