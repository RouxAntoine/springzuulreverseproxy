package com.example.application.hateosResources;

import com.example.application.controllers.sync.SongsSyncController;
import com.example.application.models.Song;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.reactive.SimpleReactiveRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class SyncSongResourceAssembler implements RepresentationModelAssembler<Song, EntityModel<Song>>,
        SimpleReactiveRepresentationModelAssembler<Song> {

    @Override
    public EntityModel<Song> toModel(Song song) {
        Link getOneSongLink = linkTo(methodOn(SongsSyncController.class).getSong(song.getId())).withSelfRel();
        Link getAllSongLink = linkTo(methodOn(SongsSyncController.class).listSongs()).withRel("songs");

        return new EntityModel<>(song, getOneSongLink,getAllSongLink);
    }

    @Override
    public CollectionModel<EntityModel<Song>> toCollectionModel(Iterable<? extends Song> songs) {

        return new CollectionModel<>(

                StreamSupport.stream(songs.spliterator(), false)
                        .map(this::toModel)
                        .collect(Collectors.toList()),

                linkTo(methodOn(SongsSyncController.class).listSongs()).withSelfRel()
        );
    }
}