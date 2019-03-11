package com.example.OAuth2.configurations;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

//    private final AuthenticationManager authenticationManager;
//
//    @Autowired
//    public WebSecurityConfiguration(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .antMatcher("/**")
            .authorizeRequests()
                .antMatchers("/", "/login**", "/webjars/**", "/error**")
                .permitAll()
            .anyRequest()
                .authenticated()

            .and().logout().logoutSuccessUrl("/").permitAll()
            .and().csrf().disable();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("john").password(passwordEncoder().encode("123")).authorities("USER");
////        auth.parentAuthenticationManager(authenticationManager);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
