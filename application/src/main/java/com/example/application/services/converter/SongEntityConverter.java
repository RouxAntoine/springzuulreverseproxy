package com.example.application.services.converter;

import com.example.application.entities.SongEntity;
import com.example.application.models.Song;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class SongEntityConverter implements Converter<SongEntity, Song> {

    @Override
    public Song convert(@Nullable SongEntity source) {
        if(source == null) {
            return null;
        }

        return Song.builder()
                .id(source.getId())
                .musicType(source.getStyle().name())
                .title(source.getTitle())
                .year(source.getYear())
                .build();
    }
}
