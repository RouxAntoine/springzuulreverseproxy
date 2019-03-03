package com.example.application.controllers.async;

import com.example.application.models.MusicStyle;
import com.example.application.models.Song;
import com.example.application.services.SongService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@Log4j2
@AutoConfigureWebTestClient
@SpringBootTest
class SongsAsyncControllerTest {

    private final WebTestClient client;

    private List<Song> songs = new ArrayList<>();

    @MockBean
    private SongService songService;

    @Autowired
    public SongsAsyncControllerTest(WebTestClient client) {
        this.client = client;
    }

    @BeforeEach
    void init() {
        songs.add(
                Song.builder()
                        .id("14")
                        .musicType(MusicStyle.FRENCH_VARIETY.toString())
                        .title("Milord")
                        .year(1959)
                        // .artist("Édith Piaf")
                        .build()
        );
        songs.add(
                Song.builder()
                        .id("15")
                        .musicType(MusicStyle.FRENCH_VARIETY.toString())
                        .title("La ballade irlandaise (Un oranger)")
                        .year(1958)
                        // .artist("Bourvil")
                        .build()
        );
    }

    @Test
    @DisplayName("test get one song by id")
    void getSong() {
        log.info("get one song by id");

        Mockito
                .when(this.songService.getOne(any()))
                .thenReturn(Mono.just(songs.get(1)));

        this.client
                .get()
                .uri("/async/songs/1")
                .accept(MediaTypes.HAL_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaTypes.HAL_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isEqualTo(songs.get(1).getId())
                .jsonPath("$._links").exists();
    }

    @Test
    @DisplayName("test get all song without hateoas")
    void listSongsBasic() {
    }

    @Test
    @DisplayName("test get all song with hateoas")
    void listSongs() {
        log.info("get all song");

        Mockito
                .when(this.songService.getAll())
                .thenReturn(Flux.fromIterable(songs));

        this.client
                .get()
                .uri("/async/songs/hateoas")
                .accept(MediaTypes.HAL_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaTypes.HAL_JSON_UTF8)
                .expectBodyList(Song.class)
                .hasSize(2)
                .consumeWith(listEntityExchangeResult -> {
                    List<Song> s = listEntityExchangeResult.getResponseBody();

                    Assertions.assertNotNull(s);
                    Assertions.assertNotNull(s.get(0));
                    AssertionErrors.assertEquals("compare id", s.get(0).getId(), songs.get(0).getId());
//                    AssertionErrors.assertEquals("contain _links", ((Object)s.get(0))["_links"], songs.get(0).getId());

                })
                .contains(songs.get(0), songs.get(1));
    }

    @Test
    @DisplayName("test post new song object")
    void addSong() {
    }
}