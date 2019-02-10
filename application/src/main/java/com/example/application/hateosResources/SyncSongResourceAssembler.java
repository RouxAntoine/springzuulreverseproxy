package com.example.application.hateosResources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.example.application.controllers.sync.SongsSyncController;
import com.example.application.models.Song;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class SyncSongResourceAssembler implements ResourceAssembler<Song, Resource<Song>> {

    @Override
    public Resource<Song> toResource(Song song) {
        return new Resource<>(song,
                linkTo(methodOn(SongsSyncController.class).getSong(song.getId())).withSelfRel(),
                linkTo(methodOn(SongsSyncController.class).listSongs()).withRel("songs"));
    }
}