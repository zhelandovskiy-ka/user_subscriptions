package com.zhelandovskiy.user_subscriptions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class UserSubscriptionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserSubscriptionsApplication.class, args);
	}

}
