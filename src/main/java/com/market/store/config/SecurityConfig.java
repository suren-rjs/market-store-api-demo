package com.market.store.config;

import com.market.store.config.filter.JwtAuthenticationFilter;
import com.market.store.config.utils.UnauthorisedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@SuppressWarnings({"unused", "deprecation"})
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String[] PUBLIC_END_POINTS = {"/auth/**", "/v3/**", "/help/**", "/swagger-ui/**", "/notification/**", "/", "/public/**"};
    private final String[] MANAGER_END_POINTS = {"/products/**", "/productCategories"};
    private final String[] ADMIN_END_POINTS = {"/admin/**"};
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UnauthorisedEntryPoint unauthorisedEntryPoint;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests().antMatchers(ADMIN_END_POINTS).hasAnyRole("ADMIN").antMatchers(MANAGER_END_POINTS).hasAnyRole("ADMIN", "MANAGER").antMatchers(PUBLIC_END_POINTS).permitAll().anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(unauthorisedEntryPoint).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
