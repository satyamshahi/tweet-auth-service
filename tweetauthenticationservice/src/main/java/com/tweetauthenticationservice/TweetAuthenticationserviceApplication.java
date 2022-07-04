package com.tweetauthenticationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Authentication Microservice's main spring boot application class
 *
 */
@SpringBootApplication
@EnableFeignClients
public class TweetAuthenticationserviceApplication {

	public static void main(final String[] args) {

		SpringApplication.run(TweetAuthenticationserviceApplication.class, args);

	}

}
