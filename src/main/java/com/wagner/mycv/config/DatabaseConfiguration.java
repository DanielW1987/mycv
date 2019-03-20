package com.wagner.mycv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class DatabaseConfiguration {

  @Bean
  public AuditorAware<String> auditorAware() {
    // todo: mit SpringSecurity sollte man den Namen des angemeldeten Users via SecurityContextHolder ermitteln
    return () -> Optional.of("Administrator");
  }

}