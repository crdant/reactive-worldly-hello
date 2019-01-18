package io.pivotal.pa.newengland.demo.reactiveworldlyhello

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity


@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  fun permitAllSecurity(http: ServerHttpSecurity): SecurityWebFilterChain =
    http
      .csrf().disable()
      .authorizeExchange().pathMatchers("/greeting/**", "/greetings").permitAll()
      .and()
      .build()

}
