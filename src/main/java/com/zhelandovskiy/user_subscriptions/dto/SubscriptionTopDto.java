package com.zhelandovskiy.user_subscriptions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscriptionTopDto {
    private String subscription;
    private Long subscribersCount;
}
