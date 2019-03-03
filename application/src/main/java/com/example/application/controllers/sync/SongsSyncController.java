package com.example.application.controllers.sync;

import com.example.application.hateosResources.SyncSongResourceAssembler;
import com.example.application.models.Song;
import com.example.application.services.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

    @Autowired
    public SongsSyncController(SongService songService, SyncSongResourceAssembler resourceAssembler) {
        this.songRepository = songService;
        this.resourceAssembler = resourceAssembler;
    }

    @PostMapping(value = "/songs")
    public EntityModel<Song> addSong(@RequestBody Song song) {
        Mono<Song> savedSongWait = this.songRepository.create(song);

        Song saved = savedSongWait.block();
        log.info("Song " + saved.getId() + " create with success");

        return this.resourceAssembler.toModel(song);
    }

    @GetMapping("/songs/{id}")
    public EntityModel<Song> getSong(@PathVariable String id) {
        final Mono<Song> songWait = this.songRepository.getOne(id);
        Song song = songWait.block();

        if(song == null) {
            throw new RuntimeException("songNotFound");
        }
        return resourceAssembler.toModel(song);
    }

    @GetMapping("/songs")
    public CollectionModel<EntityModel<Song>> listSongs() {
        List<Song> songs = new ArrayList<>();

        final Flux<Song> songsWait = this.songRepository.getAll();
        songsWait
                .map(songs::add)
                .blockLast();
        return this.resourceAssembler.toCollectionModel(songs);
    }
}
