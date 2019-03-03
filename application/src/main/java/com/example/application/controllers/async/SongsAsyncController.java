package com.example.application.controllers.async;

import com.example.application.hateosResources.AsyncSongResourceAssembler;
import com.example.application.models.Song;
import com.example.application.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;

/**
 * asynchronous controller
 */
@RestController
@RequestMapping("/async")
public class SongsAsyncController {

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
    public Flux<Song> listSongsBasic() {
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
