package com.example.application.controllers.async;

import com.example.application.hateosResources.SyncSongResourceAssembler;
import com.example.application.hateosResources.SyncSongsResourceAssembler;
import com.example.application.models.Song;
import com.example.application.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * asynchronous controller
 */
@RestController
@RequestMapping("/async")
public class SongsAsyncController {

    private final SongService songService;
    private final SyncSongResourceAssembler resourceAssembler;

    @Autowired
    public SongsAsyncController(SongService songService, SyncSongResourceAssembler resourceAssembler) {
        this.songService = songService;
        this.resourceAssembler = resourceAssembler;
    }

    @GetMapping("/songs/{id}")
    public Mono<Resource<Song>> getSong(@PathVariable String id) {
        return this.songService.getOne(id).map(resourceAssembler::toResource);
    }

    @GetMapping("/songs")
    public Flux<Resource<Song>> listSongs() {
        return this.songService.getAll().map(resourceAssembler::toResource);
    }

    @ResponseBody
    @PostMapping("/songs")
    public Mono<Resource<Song>> addSong(@RequestBody Song song) {
        return this.songService.create(song).map(resourceAssembler::toResource);
    }
}
