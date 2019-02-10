package com.example.application.controllers.sync;

import com.example.application.hateosResources.SyncSongResourceAssembler;
import com.example.application.hateosResources.SyncSongsResourceAssembler;
import com.example.application.models.Song;
import com.example.application.services.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * synchronous controller
 */
@Slf4j
@RestController
@RequestMapping("/sync")
public class SongsSyncController {

    private final SongService songRepository;

    private final SyncSongResourceAssembler resourceAssembler;
    private final SyncSongsResourceAssembler resourcesAssembler;

    @Autowired
    public SongsSyncController(SongService songService, SyncSongResourceAssembler resourceAssembler,
                               SyncSongsResourceAssembler resourcesAssembler) {
        this.songRepository = songService;
        this.resourceAssembler = resourceAssembler;
        this.resourcesAssembler = resourcesAssembler;
    }

    @ResponseBody
    @PostMapping("/songs")
    public Resource<Song> addSong(@RequestBody Song song) {
        Mono<Song> savedSongWait = this.songRepository.create(song);

        Song saved = savedSongWait.block();
        log.info("Song " + saved.getId() + "create with success");

        return this.resourceAssembler.toResource(song);
    }

    @GetMapping("/songs/{id}")
    public Resource<Song> getSong(@PathVariable String id) {
        final Mono<Song> songWait = this.songRepository.getOne(id);
        Song song = songWait.block();

        return resourceAssembler.toResource(song);
    }

    @GetMapping("/songs")
    public Resources<Resource<Song>> listSongs() {
        List<Song> songs = new ArrayList<>();

        final Flux<Song> songsWait = this.songRepository.getAll();
        songsWait
                .map(songs::add)
                .blockLast();
        return this.resourcesAssembler.toResource(songs);
    }
}
