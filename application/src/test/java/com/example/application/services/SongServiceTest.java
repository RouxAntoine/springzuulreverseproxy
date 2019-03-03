package com.example.application.services;

import com.example.application.entities.SongEntity;
import com.example.application.models.MusicStyle;
import com.example.application.models.Song;
import com.example.application.repositories.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.core.convert.ConversionService;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class SongServiceTest {

    private List<SongEntity> songs = new ArrayList<>();

    @InjectMocks
    private SongService songService;

    @Mock
    private SongRepository songRepository;

    @Mock
    private ConversionService converter;

    @BeforeEach
    void init() {
        songs.add(
                SongEntity.builder()
                        .id("14")
                        .style(MusicStyle.FRENCH_VARIETY)
                        .title("Milord")
                        .year(1959)
                        // .artist("Édith Piaf")
                        .build()
        );
        songs.add(
                SongEntity.builder()
                        .id("15")
                        .style(MusicStyle.FRENCH_VARIETY)
                        .title("La ballade irlandaise (Un oranger)")
                        .year(1958)
                        // .artist("Bourvil")
                        .build()
        );
    }

    @Test
    void create() {
        Mockito
                .when(this.songRepository.findAll())
                .thenReturn(Flux.fromIterable(songs));

        Mockito.when(this.converter.convert(any(), any()))
                .thenCallRealMethod();

        StepVerifier
                .create(this.songService.getAll())
                .expectComplete()
                .verify();
    }

    @Test
    void getOne() {
    }

    @Test
    void getAll() {
    }
}