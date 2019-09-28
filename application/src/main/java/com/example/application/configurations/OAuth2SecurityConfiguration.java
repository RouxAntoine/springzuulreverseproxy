package com.example.application.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebFluxSecurity
public class OAuth2SecurityConfiguration {

    @Bean
    public WebClient webClient(ReactiveClientRegistrationRepository clientRegistrationRepository,
                               ServerOAuth2AuthorizedClientRepository authorizedClientRepository) {

        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(
                        clientRegistrationRepository,
                        authorizedClientRepository);

        // (optional) explicitly opt into using the oauth2Login to provide an access token implicitly
        oauth.setDefaultOAuth2AuthorizedClient(true);
        return WebClient.builder()
                .filter(oauth)
                .build();
    }

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
        http.oauth2Client();

        return http.build();
    }

}
