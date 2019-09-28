package com.example.OAuth2.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableOAuth2Client
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final OAuth2ClientContext oauth2ClientContext;

    @Autowired
    public WebSecurityConfiguration(OAuth2ClientContext oauth2ClientContext) {
        this.oauth2ClientContext = oauth2ClientContext;
    }

//    private final AuthenticationManager authenticationManager;
//
//    @Autowired
//    public WebSecurityConfiguration(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
            .authorizeRequests()
                .antMatchers("/", "/login**", "/webjars/**", "/error**").permitAll()
                .anyRequest().authenticated()
            .and().exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
            .and()
                .logout().logoutSuccessUrl("/").permitAll()
            .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
                .csrf().disable()
        ;
    }

    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();

        // facebook filter
        OAuth2ClientAuthenticationProcessingFilter facebookFilter = createFilter(facebook(), "/login/facebook");
        filters.add(facebookFilter);

        // github filter
        OAuth2ClientAuthenticationProcessingFilter githubFilter = createFilter(github(), "/login/github");
        filters.add(githubFilter);

//        // local filter
//        OAuth2ClientAuthenticationProcessingFilter local = createFilter(local(), "/login/local");
//        filters.add(local);

        filter.setFilters(filters);
        return filter;
    }

    /**
     * create oauth2 filter for each client application
     * @param resources
     * @param path
     * @return
     */
    private OAuth2ClientAuthenticationProcessingFilter createFilter(AggregatedClientResources resources, String path) {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);

        OAuth2RestTemplate template = new OAuth2RestTemplate(resources.getClient(), oauth2ClientContext);
        filter.setRestTemplate(template);

        UserInfoTokenServices tokenServices = new UserInfoTokenServices(resources.getResource().getUserInfoUri(), resources.getClient().getClientId());
        tokenServices.setRestTemplate(template);

        filter.setTokenServices(tokenServices);
        return filter;
    }

    /**
     * authorization code resource and resource server for facebook
     * @return
     */
    @Bean
    @ConfigurationProperties("facebook")
    public AggregatedClientResources facebook() {
        return new AggregatedClientResources();
    }

    /**
     * authorization code resource and resource server for github
     * @return
     */
    @Bean
    @ConfigurationProperties("github")
    public AggregatedClientResources github() {
        return new AggregatedClientResources();
    }

//    /**
//     * redirect filter to oauth facebook login page
//     * @param filter
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
//        FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<>();
//        registration.setFilter(filter);
//        registration.setOrder(-100);
//        return registration;
//    }

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
