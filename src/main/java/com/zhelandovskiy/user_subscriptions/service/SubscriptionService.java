package com.zhelandovskiy.user_subscriptions.service;

import com.zhelandovskiy.user_subscriptions.dto.SubscriptionCreateDto;
import com.zhelandovskiy.user_subscriptions.dto.SubscriptionTopDto;
import com.zhelandovskiy.user_subscriptions.entity.SubscriptionEntity;
import com.zhelandovskiy.user_subscriptions.exception.NotFoundException;

import java.util.List;

/**
 * Service interface for managing subscriptions and related operations.
 * Provides methods for retrieving top subscriptions, creating or fetching subscriptions,
 * and finding subscriptions by their unique code.
 */
public interface SubscriptionService {

    /**
     * Retrieves the top most popular subscriptions based on subscriber count.
     *
     * @return a list of {@link SubscriptionTopDto} objects representing the top subscriptions,
     *         ordered by subscriber count in descending order
     */
    List<SubscriptionTopDto> getTop();

    /**
     * Either retrieves an existing subscription or creates a new one if it doesn't exist.
     *
     * @param dto the {@link SubscriptionCreateDto} containing subscription details
     * @return the existing or newly created {@link SubscriptionEntity}
     */
    SubscriptionEntity getOrInsert(SubscriptionCreateDto dto);

    /**
     * Retrieves a subscription by its unique code.
     *
     * @param code the unique code identifying the subscription
     * @return the {@link SubscriptionEntity} with the specified code
     * @throws NotFoundException if no subscription exists with the given code
     */
    SubscriptionEntity getByCode(String code);
}