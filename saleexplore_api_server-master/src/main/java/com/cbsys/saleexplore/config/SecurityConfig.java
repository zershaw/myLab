package com.cbsys.saleexplore.config;
/**
 * Spring security configuration for the API
 */

import com.cbsys.saleexplore.security.FailureAuthenticationEntryPoint;
import com.cbsys.saleexplore.security.JWTAccessFilter;
import com.cbsys.saleexplore.security.JWTTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * spring global bean
     */
    @Autowired
    public JWTTokenProvider jwtTokenProvider;

    /**
     * Global bean of the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    /**
     * The URLs under api/** are protected except for api/public
     * other URLs are permitted all by default
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors()
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .csrf()
                    .disable()
                .formLogin()
                    .disable()
                .httpBasic()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(new FailureAuthenticationEntryPoint())
                    .and()
                .authorizeRequests()
                    .antMatchers(ConstantConfig.URL_PREFIX_API_PUBLIC + "**")
                        .permitAll() // must use ** here
                    .antMatchers(ConstantConfig.URL_PREFIX_API + "**")
                        .authenticated();


        /**
         * add a filter bean which will managed by spring. this bean will automatically called in the spring security
         * chain. The apis starting with "/api/" requests will be intercept by this filter
         */
        http.antMatcher(ConstantConfig.URL_PREFIX_API + "**").addFilterBefore(new JWTAccessFilter(jwtTokenProvider), BasicAuthenticationFilter.class);
    }
}
