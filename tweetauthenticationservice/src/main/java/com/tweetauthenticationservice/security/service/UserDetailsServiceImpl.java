package com.tweetauthenticationservice.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tweetauthenticationservice.client.UpdateServiceClient;
import com.tweetauthenticationservice.payload.LoginResponse;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * This class is used for load User Credential
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UpdateServiceClient updateServiceClient;

	public static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	public UserDetailsServiceImpl(final UpdateServiceClient updateServiceClient) {
		this.updateServiceClient = updateServiceClient;
	}

	/**
	 * This method is used to load userCredentials from update-service database
	 */
	@Override
	public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("{}, Information: Calling update service to load userCredentials ",
					this.getClass().getSimpleName());
		}
		LoginResponse login = updateServiceClient.login(userName);
		return new User(login.getLoginId(), login.getPassword(), Arrays.stream(login.getRole().split(","))
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
	}
}
