package com.fpt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
//        http.authorizeRequests().anyRequest().authenticated()
//            .and().logout(l -> l.logoutSuccessUrl("/").permitAll());
//        http.authorizeRequests(l -> l.anyRequest().authenticated()).oauth2Login();
//        http.authorizeExchange()
//            .anyExchange()
//            .authenticated()
//            .and()
//            .oauth2Login();
//        http.authorizeRequests()
//            .antMatchers("/api/**").authenticated()
//            .antMatchers("/").permitAll();
        http.authorizeRequests(l -> l
                    .antMatchers("/api/**").authenticated()
                    .antMatchers("/**").permitAll())
            .logout().logoutSuccessUrl("/api/security/logout").and()
            .oauth2Login();
//        http.authorizeHttpRequests(c -> c.anyRequest().permitAll());
//        http.sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.oauth2Login();
        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/actuator/health");
//    }
}
