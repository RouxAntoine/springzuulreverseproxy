package com.example.application.controllers.async;

import com.example.application.models.MusicStyle;
import com.example.application.models.Song;
import com.example.application.services.SongService;
import com.example.application.services.utils.StreamUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

/**
 * test async {@link org.springframework.web.bind.annotation.RestController}
 * with {@link Flux} and {@link Mono} return
 */
@Log4j2
@AutoConfigureWebTestClient
@SpringBootTest
class SongsAsyncControllerTest {

    private final WebTestClient client;

    private List<Song> songs = new ArrayList<>();

    private ObjectMapper mapper;

    @MockBean
    private SongService songService;

    @Autowired
    SongsAsyncControllerTest(WebTestClient client) {
        this.client = client;
    }

    @BeforeEach
    void init() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

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
        log.info("get all song");

        Mockito
                .when(this.songService.getAll())
                .thenReturn(Flux.fromIterable(songs));

        this.client
                .get()
                .uri("/async/songs")
                .accept(MediaTypes.HAL_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaTypes.HAL_JSON_UTF8)
                .expectBodyList(Song.class)
                .hasSize(2)
                .value(this::compareListEntityModelSong)
                .contains(songs.get(0), songs.get(1));

    }

    @Test
    @DisplayName("test get all song with hateoas")
    void listSongs() {
        log.info("get all song hateoas");

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
                .expectBodyList(Object.class)
                .hasSize(2)
                .value(this::compareListEntityModelObject);
    }

    /**
     * compare {@link List<Object>} to expected {@value songs}
     * @param listToCompare
     */
    private void compareListEntityModelObject(List<Object> listToCompare) {
        Assertions.assertNotNull(listToCompare);

        StreamUtils
                .zip(listToCompare.stream(), songs.stream())
                .peek(o -> {
                    Song tested = mapper.convertValue(o.getFirst(), Song.class);
                    compareSong(o.getSecond(), tested);
                })
                .forEach(o -> compareEntityModelSong(o.getFirst()));
    }

    /**
     * compare {@link List<Song>} to expected {@value songs}
     * @param listToCompare
     */
    private void compareListEntityModelSong(List<Song> listToCompare) {
        Assertions.assertNotNull(listToCompare);

        StreamUtils
                .zip(listToCompare.stream(), songs.stream())
                .forEach(o -> {
                    Song tested = mapper.convertValue(o.getFirst(), Song.class);
                    compareSong(o.getSecond(), tested);
                });
    }

    /**
     * compare Object (json) to Song POJO
     * @param toCompare
     */
    private void compareEntityModelSong(Object toCompare) {
        AssertionErrors.assertTrue("object instance of Map", toCompare instanceof Map);
        Map m = (Map) toCompare;

        AssertionErrors.assertTrue("_links instance of Map", m.get("_links") instanceof Map);
        Map links = (Map)m.get("_links");
        AssertionErrors.assertEquals("contain _links size 2", links.size(), 2);
    }

    /**
     * compare two Song
     * @param expected
     * @param m
     */
    private void compareSong(Song expected, Song m) {
        AssertionErrors.assertEquals("compare id", m.getId(), expected.getId());
        AssertionErrors.assertEquals("compare year", m.getYear(), expected.getYear());
        AssertionErrors.assertEquals("compare title", m.getTitle(), expected.getTitle());
    }
}