package com.tweetauthenticationservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class is used to configure the Spring Security
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private RestAuthenticationEntryPoint restAuthPoint;

	/**
	 * swagger UI
	 */
	private static final String[] AUTH_WHITELIST = {

			"/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**"

	};

	private final UserDetailsService userDetails;

	@Autowired
	public WebSecurityConfig(@Qualifier("userDetailsServiceImpl") final UserDetailsService userDetails) {
		super();
		this.userDetails = userDetails;
	}

	/**
	 * This configure Override method is used for loading user data from MongoDb.
	 */
	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetails).passwordEncoder(bCryptPasswordEncoder());
	}

	/**
	 * This configure Override method is used for configure the spring default
	 * security.
	 */
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/api/v1.0/tweets/**").permitAll().antMatchers("/v2/api-docs/**").permitAll()
				.antMatchers(AUTH_WHITELIST).permitAll().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(restAuthPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.headers().frameOptions().disable();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * This method is used for creating object of PasswordEncoder, that help in
	 * encoding the encrypted password.
	 */
	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
