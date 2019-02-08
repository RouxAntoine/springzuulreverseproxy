package com.example.proxy;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomFilter extends GenericFilterBean {
    private static CustomFilter ourInstance = new CustomFilter();

    public static CustomFilter getInstance() {
        return ourInstance;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(request, response);
    }

}
