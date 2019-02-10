package com.example.application.configurations;

import com.example.application.services.converter.SongConverter;
import com.example.application.services.converter.StringToEnumConverterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebFluxConfigurer {

    private final SongConverter songConverter;
    private final StringToEnumConverterFactory stringToEnumConverterFactory;

    @Autowired
    public WebConfig(
            SongConverter songConverter,
            StringToEnumConverterFactory stringToEnumConverterFactory
    ) {
        super();
        this.songConverter = songConverter;
        this.stringToEnumConverterFactory = stringToEnumConverterFactory;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(songConverter);
        registry.addConverterFactory(stringToEnumConverterFactory);
    }
}
