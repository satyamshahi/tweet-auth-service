
package com.tweetauthenticationservice.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tweetauthenticationservice.payload.LoginRequest;
import com.tweetauthenticationservice.payload.ValidationResponse;
import com.tweetauthenticationservice.security.jwt.JwtUtils;

/**
 * 
 */
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
	/**
	 * 
	 */
	@Autowired
	public transient MockMvc mockMvc;

	/**
	 * 
	 */
	@MockBean
	private transient JwtUtils jwtUtils;
	
	/**
	 * @throws Exception
	 * 
	 * This Test is use for Authentication Testing
	 */
	/**
	 * @throws Exception
	 */
	
	private static final ObjectMapper MAPPER = new ObjectMapper()
		    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
		    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
		    .registerModule(new JavaTimeModule());

		  public static String requestBody(Object request) {
		    try {
		      return MAPPER.writeValueAsString(request);
		    } catch (JsonProcessingException e) {
		      throw new RuntimeException(e);
		    }
		  }

		  public static <T> T parseResponse(MvcResult result, Class<T> responseClass) {
		    try {
		      String contentAsString = result.getResponse().getContentAsString();
		      return MAPPER.readValue(contentAsString, responseClass);
		    } catch (IOException e) {
		      throw new RuntimeException(e);
		    }
		  }
	
	
	@Test
	 void testAuthenticateUser() throws Exception {

		final ObjectMapper mapper = new ObjectMapper();

		final LoginRequest loginRequest = new LoginRequest();
		loginRequest.setPassword("12345");
		loginRequest.setLoginId("satyam1");

		// Java object to JSON string
		final String jsonString = mapper.writeValueAsString(loginRequest);

		final MvcResult result = mockMvc
				.perform(post("/api/auth/signin").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andReturn();

		assertEquals("{\"token\":null,\"loginId\":\"satyam1\",\"roles\":[\"ROLE_USER\"]}",
				result.getResponse().getContentAsString());
	}
	
	
	
	
	/**
	 * @throws Exception
	 * 
	 * Testing AuthenticateUser Rest Point When UserName And Password Is Blank
	 */
	@Test
	 void testAuthenticateUserRestPointWhenUserNameAndPasswordIsBlank() throws Exception {

		final ObjectMapper mapper = new ObjectMapper();

		final LoginRequest loginRequest = new LoginRequest();
			loginRequest.setPassword("");
			loginRequest.setLoginId("");

		// Java object to JSON string
		final String jsonString = mapper.writeValueAsString(loginRequest);

		 mockMvc.perform(post("/api/auth/signin").contentType(MediaType.APPLICATION_JSON)
						.content(jsonString)).andExpect(status().isBadRequest());
	}
	
	

	/**
	 * @throws Exception
	 * 
	 * This test is use for Validation testing user having Valid token
	 */
	@Test
	 void testValidateAndReturnUser() throws Exception {
		when(jwtUtils.getUserNameFromJwtToken(Mockito.anyString())).thenReturn("satyam");
		ValidationResponse validationResponse = new ValidationResponse();
		validationResponse.setMessage("Validated Successfully....");
		validationResponse.setUserId("satyam");
		final ResponseEntity<ValidationResponse> authorize=new ResponseEntity<>(validationResponse,HttpStatus.OK);
		
		when(jwtUtils.validateJwtToken(Mockito.anyString())).thenReturn(authorize);

		final MvcResult result = mockMvc.perform(post("/api/auth/validate").header("Authorization", "Bearer " + "satyam")
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		ValidationResponse response = parseResponse(result,ValidationResponse.class);
		assertEquals("Validated Successfully....", response.getMessage());
	}


/**
 * @throws Exception
 * 
 * This test is use for Validation testing user having not Valid token
 */
@Test
 void testValidateAndReturnUserHavingNotValidToken() throws Exception {

	when(jwtUtils.getUserNameFromJwtToken(Mockito.anyString())).thenReturn("satyam");
	ValidationResponse validationResponse = new ValidationResponse();
	validationResponse.setMessage("JWT Token is Not Valid.*");
	
	
	final ResponseEntity<ValidationResponse> authorize=new ResponseEntity<>(validationResponse,HttpStatus.UNAUTHORIZED);
	
	when(jwtUtils.validateJwtToken(Mockito.anyString())).thenReturn(authorize);

	 mockMvc.perform(post("/api/auth/validate").header("Authorization", "Bearer " + "satyam")
			.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
}

}
