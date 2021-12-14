package com.example.coachwithme.security;

import com.example.coachwithme.model.user.UserRole;
import com.example.coachwithme.security.filters.CustomAuthenticationFilter;
import com.example.coachwithme.security.filters.CustomAuthorizationFilter;
import com.example.coachwithme.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String JWT_SECRET = "SuperDuperSecretSentence!";

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/users/login");

        http.csrf().disable();
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //http.authorizeRequests().anyRequest().permitAll();
        http.authorizeRequests().antMatchers(GET, "/users/login/**").permitAll();
        http.authorizeRequests().antMatchers(POST, "/users").permitAll();
        http.authorizeRequests().antMatchers(POST, "/users/**").permitAll();
        http.authorizeRequests().antMatchers(GET, "/findACoach").permitAll();

        http.authorizeRequests().antMatchers(GET, "/users/check/**").permitAll();
        http.authorizeRequests().antMatchers(POST, "/coaches/becomecoach").permitAll();
        http.authorizeRequests().antMatchers(PUT, "/coaches/**").permitAll();
        http.authorizeRequests().antMatchers(GET, "/admin/**").hasAuthority(UserRole.ADMIN.getRoleName());
        http.authorizeRequests().antMatchers(POST, "/admin/**").hasAuthority(UserRole.ADMIN.getRoleName());
        //http.authorizeRequests().antMatchers(GET, "/coach/**").hasAnyAuthority(UserRole.COACH.getRoleName());
        //http.authorizeRequests().antMatchers(POST, "/users/save/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/v3/api-docs/**",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/webjars/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CorsFilter corsFilter() {

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setMaxAge(8000L);
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedOrigin("http://localhost:4200");
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("GET");
        corsConfig.addAllowedMethod("POST");
        corsConfig.addAllowedMethod("PUT");
        corsConfig.addAllowedMethod("PATCH");
        corsConfig.addAllowedMethod("DELETE");


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsFilter(source);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
