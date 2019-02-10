package com.example.application.services.converter;

import com.example.application.entities.SongEntity;
import com.example.application.models.MusicStyle;
import com.example.application.models.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class SongConverter implements Converter<Song, SongEntity> {

    private final ConversionService converter;

    @Autowired
    public SongConverter(ConversionService converter) {
        this.converter = converter;
    }

    @Override
    public SongEntity convert(@Nullable Song song) {
        if(song == null) {
            return null;
        }

        return SongEntity.builder()
                .id(song.getId())
                .style(this.converter.convert(song.getMusicType(), MusicStyle.class))
                .title(song.getTitle())
                .year(song.getYear())
                .build();
    }
}
