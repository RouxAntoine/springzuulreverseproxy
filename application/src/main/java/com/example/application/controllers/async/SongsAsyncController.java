package com.example.application.controllers.async;

import com.example.application.hateosResources.AsyncSongResourceAssembler;
import com.example.application.models.Song;
import com.example.application.services.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.logging.Level;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;

/**
 * asynchronous controller
 */
@RestController
@RequestMapping("/async")
public class SongsAsyncController {

    private static final Logger log = LoggerFactory.getLogger(SongsAsyncController.class);

    private final SongService songService;
    private final AsyncSongResourceAssembler resourceAssembler;

    @Autowired
    public SongsAsyncController(SongService songService, AsyncSongResourceAssembler resourceAssembler) {
        this.songService = songService;
        this.resourceAssembler = resourceAssembler;
    }

    @GetMapping("/songs/{id}")
    public Mono<EntityModel<Song>> getSong(@PathVariable String id, ServerWebExchange exchange) {
        return this.songService.getOne(id)
                .flatMap(songMono -> resourceAssembler.toModel(songMono, exchange));
    }

    @GetMapping("/songs")
    public Flux<Song> listSongsBasic(Mono<Principal> principal) {
        principal.log(log.getName(), Level.FINEST);
        return this.songService.getAll();
    }

    @GetMapping("/songs/hateoas")
    public Flux<EntityModel<Song>> listSongs(ServerWebExchange exchange) {
        return resourceAssembler.toCollectionModel(this.songService.getAll(), exchange);
    }

    @ResponseBody
    @PostMapping("/songs")
    public Mono<EntityModel<Song>> addSong(@RequestBody Song song, ServerWebExchange exchange) {
        return this.songService.create(song)
                .flatMap(savedSong -> resourceAssembler.toModel(savedSong, exchange));
    }
}
