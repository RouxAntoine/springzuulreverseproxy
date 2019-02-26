package com.example.application.controllers.async;

import com.example.application.hateosResources.AsyncSongResourceAssembler;
import com.example.application.models.Song;
import com.example.application.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<Resource<Song>> getSong(@PathVariable String id, ServerWebExchange exchange) {
        return this.songService.getOne(id)
                .flatMap(songMono -> resourceAssembler.toResource(songMono, exchange));
    }

    @GetMapping("/songs")
    public Flux<Song> listSongsBasic() {
        return this.songService.getAll();
    }

    @GetMapping("/songs/hateos")
    public Flux<Resource<Song>> listSongs(ServerWebExchange exchange) {
        return this.songService.getAll()
                .flatMap(song -> resourceAssembler.toResource(song, exchange));
    }

    @ResponseBody
    @PostMapping("/songs")
    public Mono<Resource<Song>> addSong(@RequestBody Song song, ServerWebExchange exchange) {
        return this.songService.create(song)
                .flatMap(savedSong -> resourceAssembler.toResource(savedSong, exchange));
    }
}
