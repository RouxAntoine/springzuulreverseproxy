package com.example.OAuth2.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

/**
 * authorization server configuration
 */
@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfiguration { //extends AuthorizationServerConfigurerAdapter {

////    private final AuthenticationManager authenticationManager;
////
////    @Autowired
////    public AuthorizationServerConfiguration(AuthenticationManager authenticationManager) {
////        this.authenticationManager = authenticationManager;
////    }
//
////    @Override
////    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
////        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
////
////        tokenEnhancerChain.setTokenEnhancers(
////                Arrays.asList(tokenEnhancer(), accessTokenConverter())
////        );
//
////        endpoints
////                .authenticationManager(authenticationManager)
////                .allowedTokenEndpointRequestMethods();
////                .tokenStore(tokenStore())
////                .tokenEnhancer(tokenEnhancerChain)
////                .tokenEnhancer(tokenEnhancer())
////                .accessTokenConverter(accessTokenConverter())
////        ;
////    }
//
////    @Bean
////    public TokenEnhancer tokenEnhancer() {
////        return new CustomTokenEnhancer();
////    }
//
////    /**
////     * jwt token storage
////     * @return
////     */
////    @Bean
////    public TokenStore tokenStore() {
////        return new JwtTokenStore(accessTokenConverter());
////    }
////
////    /**
////     * translate jwt token to oauth2 authentification form
////     * @return
////     */
////    @Bean
////    public JwtAccessTokenConverter accessTokenConverter() {
////        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
////        converter.setSigningKey("123");
////        return converter;
////    }
////
////    /**
////     * jwt token service
////     * @return
////     */
////    @Bean
////    @Primary
////    public DefaultTokenServices tokenServices() {
////        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
////        defaultTokenServices.setTokenStore(tokenStore());
////        defaultTokenServices.setSupportRefreshToken(true);
////        return defaultTokenServices;
////    }
//
//    /**
//     * configure Oauth2 client
//     * @param clients
//     * @throws Exception
//     */
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("client-app").secret("client-secret")
//                .authorizedGrantTypes("authorization_code", "refresh_token", "password")
//                .scopes("openid");
//    }
//
////    @Override
////    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
////        security.checkTokenAccess("isAuthenticated()");
////    }

}
