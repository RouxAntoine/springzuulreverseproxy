package com.example.application.hateosResources;

import com.example.application.controllers.sync.SongsSyncController;
import com.example.application.models.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class SyncSongsResourceAssembler implements ResourceAssembler<List<Song>, Resources<Resource<Song>>> {

    private final SyncSongResourceAssembler resourceAssembler;

    @Autowired
    public SyncSongsResourceAssembler(SyncSongResourceAssembler resourceAssembler) {
        this.resourceAssembler = resourceAssembler;
    }

    @Override
    public Resources<Resource<Song>> toResource(List<Song> songs) {
        return new Resources<>(
                songs.stream().map(resourceAssembler::toResource).collect(Collectors.toList()),
                linkTo(methodOn(SongsSyncController.class).listSongs()).withSelfRel()
        );
    }
}
