/**
 * 
 */
package com.tweetauthenticationservice.security.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import com.tweetauthenticationservice.client.UpdateServiceClient;
import com.tweetauthenticationservice.payload.LoginResponse;
import com.tweetauthenticationservice.security.service.UserDetailsServiceImpl;
//import com.tweetauthenticationservice.security.service.com;


/**
 * UserDetailsServiceImpl Testing Class
 *
 */
class UserDetailsServiceImplTest {

	/**
	 * 
	 */
	@Mock
	public transient UpdateServiceClient updateServiceClient;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Test method for
	 * {@link com.authenticationmicroservice.security.service
	 * .UserDetailsServiceImpl#loadUserByUsername(java.lang.String)}.
	 */
	@Test
	 void testLoadUserByUsernameWhenuserPresentInRepository() {
		final UserDetailsServiceImpl udsi = new UserDetailsServiceImpl(updateServiceClient);
		LoginResponse loginResponse =new LoginResponse();
		loginResponse.setLoginId("user1");
		loginResponse.setPassword("12345");
		loginResponse.setRole("USER");
		when(updateServiceClient.login("user1")).thenReturn(loginResponse);
		final UserDetails result = udsi.loadUserByUsername("user1");
		assertNotNull(result);
		verify(updateServiceClient).login("user1");
	}

	/**
	 * Test method for
	 * {@link com.authenticationmicroservice.security.service
	 * .UserDetailsServiceImpl#loadUserByUsername(java.lang.String)}.
	 */
	@Test
	 void testLoadUserByUsernameWhenuserIsPresentInRepository() throws Exception {
		final UserDetailsServiceImpl udsi = new UserDetailsServiceImpl(updateServiceClient);
		LoginResponse loginResponse =new LoginResponse();
		loginResponse.setLoginId("user1");
		loginResponse.setPassword("12345");
		loginResponse.setRole("USER");
		when(updateServiceClient.login("user1")).thenReturn(loginResponse);
		assertNotNull(udsi.loadUserByUsername("user1"));

	}

}
