package com.zhelandovskiy.user_subscriptions.service;

import com.zhelandovskiy.user_subscriptions.dto.SubscriptionCreateDto;
import com.zhelandovskiy.user_subscriptions.dto.UserCreateUpdateDto;
import com.zhelandovskiy.user_subscriptions.dto.UserDto;
import com.zhelandovskiy.user_subscriptions.dto.UserNewDto;
import com.zhelandovskiy.user_subscriptions.exception.AlreadyExistException;
import com.zhelandovskiy.user_subscriptions.exception.NotFoundException;

/**
 * Service interface for managing users and their subscription relationships.
 * Provides CRUD operations for users and methods to manage user-subscription associations.
 */
public interface UserService {

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the unique ID of the user
     * @return the {@link UserDto} representing the user
     * @throws NotFoundException if no user exists with the given ID
     */
    UserDto getById(Long id);

    /**
     * Creates a new user with the provided details.
     *
     * @param dto the {@link UserCreateUpdateDto} containing user details
     * @return the {@link UserNewDto} representing the newly created user
     * @throws AlreadyExistException if a user with the same email already exists
     */
    UserNewDto create(UserCreateUpdateDto dto);

    /**
     * Updates an existing user's details.
     *
     * @param id the ID of the user to update
     * @param dto the {@link UserCreateUpdateDto} containing updated user details
     * @return the updated {@link UserDto}
     * @throws NotFoundException if no user exists with the given ID
     */
    UserDto update(Long id, UserCreateUpdateDto dto);

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @throws NotFoundException if no user exists with the given ID
     */
    void delete(Long id);

    /**
     * Adds a subscription to a user. Creates the subscription if it doesn't exist.
     *
     * @param id the ID of the user
     * @param dto the {@link SubscriptionCreateDto} containing subscription details
     * @return the updated {@link UserDto} with the new subscription
     * @throws NotFoundException if no user exists with the given ID
     * @throws AlreadyExistException if user already has subscription
     */
    UserDto addSubscription(Long id, SubscriptionCreateDto dto);

    /**
     * Removes a subscription from a user by subscription code.
     *
     * @param id the ID of the user
     * @param code the unique code of the subscription to remove
     * @return the updated {@link UserDto} without the removed subscription
     * @throws NotFoundException if no user exists with the given ID
     *         or if the user doesn't have the specified subscription
     */
    UserDto removeSubscription(Long id, String code);
}
