package com.example.application.services;

import com.example.application.repositories.SongRepository;
import com.example.application.entities.SongEntity;
import com.example.application.models.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

/**
 * song service interaction with {@link SongRepository}
 */
@Service
public class SongService {

    private final SongRepository songRepository;
    private final ConversionService converter;

    @Autowired
    public SongService(SongRepository songRepository, ConversionService converter) {
        this.songRepository = songRepository;
        this.converter = converter;
    }

    /**
     * create and persist {@link SongEntity}
     * @param song
     * @return
     */
    public Mono<Song> create(@NotNull Song song) {
        SongEntity s = this.converter.convert(song, SongEntity.class);

        if(s == null) {
            throw new RuntimeException("songConvertFailed");
        }

        // convert Mono<SongEntity> to Mono<Song>
        return this.songRepository.save(s)
                .map(songEntity -> this.converter.convert(songEntity, Song.class));
    }

    /**
     * get one song by his id
     * @param id
     * @return
     */
    public Mono<Song> getOne(String id) {
        return this.songRepository.findById(id)
                .map(songEntity -> this.converter.convert(songEntity, Song.class));
    }

    /**
     * get all songs
     * @return
     */
    public Flux<Song> getAll() {
        return this.songRepository.findAll()
                .map(songEntity -> this.converter.convert(songEntity, Song.class));
    }
}
