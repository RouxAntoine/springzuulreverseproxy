//package com.example.OAuth2.configurations;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//
///**
// * resource server configuration
// */
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//    private static final String RESOURCE_ID = "my_rest_api";
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.resourceId(RESOURCE_ID).stateless(false);
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.
//                anonymous().disable()
//                .requestMatchers().antMatchers("/user/**")
//                .and().authorizeRequests()
//                .antMatchers("/user/**").access("hasRole('USER')")
//                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
//    }
//
////    private final DefaultTokenServices tokenServices;
////
////    @Autowired
////    public ResourceServerConfiguration(DefaultTokenServices tokenServices) {
////        this.tokenServices = tokenServices;
////    }
////
////    @Override
////    public void configure(ResourceServerSecurityConfigurer config) {
////        config.tokenServices(tokenServices);
////    }
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new InMemoryTokenStore();
////        return new JwtTokenStore(accessTokenConverter());
//    }
//
////    @Bean
////    public JwtAccessTokenConverter accessTokenConverter() {
////        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
////        converter.setSigningKey("123");
////        return converter;
////    }
//
////    @Bean
////    @Primary
////    public DefaultTokenServices tokenServices() {
////        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
////        defaultTokenServices.setTokenStore(tokenStore);
////        return defaultTokenServices;
////    }
//}
