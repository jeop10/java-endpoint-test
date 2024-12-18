package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfig {

    private final ClientAuthenticationHelper authServiceHelper;

    public WebSecurityConfig(ClientAuthenticationHelper authServiceHelper) {
        this.authServiceHelper = authServiceHelper;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        //add the ApiKeyFilter to the security chain
        http.addFilterBefore(new ApiKeyFilter(authServiceHelper),
                AnonymousAuthenticationFilter.class);

        //configure the security chain to authenticate all endpoints
        //except the /error
        http.authorizeHttpRequests(requests ->
                requests.requestMatchers(new AntPathRequestMatcher("/error")).permitAll()
                        .anyRequest().permitAll()
        );

//        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());

        //since this is an API app, configure it to be stateless
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
