package com.zhelandovskiy.user_subscriptions.repository;

import com.zhelandovskiy.user_subscriptions.dto.SubscriptionTopDto;
import com.zhelandovskiy.user_subscriptions.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

    @Query(value = """
        SELECT s.name as subscription, 
               COUNT(us.subscription_id) as subscribersCount
        FROM users_subscriptions us
        LEFT JOIN subscriptions s ON us.subscription_id=s.id
        GROUP BY s.name
        ORDER BY subscribersCount DESC
        LIMIT 3
        """, nativeQuery = true)
    List<SubscriptionTopDto> getTopSubscription();

    Optional<SubscriptionEntity> findByCode(String code);
}
