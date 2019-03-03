package com.example.application.configurations;

import com.example.application.services.converter.SongConverter;
import com.example.application.services.converter.SongEntityConverter;
import com.example.application.services.converter.StringToEnumConverterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebConfig implements WebFluxConfigurer {

    private final StringToEnumConverterFactory stringToEnumConverterFactory;
    private final SongConverter songConverter;
    private final SongEntityConverter songEntityConverter;

    @Autowired
    public WebConfig(StringToEnumConverterFactory stringToEnumConverterFactory, SongConverter songConverter, SongEntityConverter songEntityConverter) {
        this.stringToEnumConverterFactory = stringToEnumConverterFactory;
        this.songConverter = songConverter;
        this.songEntityConverter = songEntityConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(songEntityConverter);
        registry.addConverter(songConverter);
        registry.addConverterFactory(stringToEnumConverterFactory);
    }
}
