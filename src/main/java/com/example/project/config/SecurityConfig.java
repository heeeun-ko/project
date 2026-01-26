package com.example.project.config;

import com.example.project.domain.auth.handler.OAuth2LoginSucessHandler;
import com.example.project.domain.auth.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final OAuth2LoginSucessHandler oAuth2LoginSucessHandler;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/login/**", "/oauth2/**").permitAll()  // 허용 URL 설정
          .anyRequest().authenticated()  // 그 외 모든 요청은 인증 필요
        )
        .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화 (API 서버의 경우)
        .formLogin(form -> form.disable())  // 폼 로그인 비활성화
        .httpBasic(basic -> basic.disable())  // HTTP Basic 인증 비활성화
        .oauth2Login(oauth -> oauth
            .successHandler(oAuth2LoginSucessHandler)
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
    ;

    return http.build();
  }
}