package com.example.application.services.converter;

import com.example.application.entities.SongEntity;
import com.example.application.models.MusicStyle;
import com.example.application.models.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SongConverter implements Converter<Song, SongEntity> {

    private final Converter<String, MusicStyle> converter;

    @Autowired
    public SongConverter(StringToEnumConverterFactory factory) {
        this.converter = factory.getConverter(MusicStyle.class);
    }

    @Override
    public SongEntity convert(@Nullable Song song) {
        if(song == null) {
            return null;
        }

        return SongEntity.builder()
                .id(song.getId())
                .style(this.converter.convert(song.getMusicType()))
                .title(song.getTitle())
                .year(song.getYear())
                .build();
    }
}
