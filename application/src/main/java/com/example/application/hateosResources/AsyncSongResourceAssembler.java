package com.example.application.hateosResources;

import com.example.application.controllers.async.SongsAsyncController;
import com.example.application.models.Song;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@Component
public class AsyncSongResourceAssembler implements ReactiveRepresentationModelAssembler<Song, EntityModel<Song>> {

    @Override
    public Mono<EntityModel<Song>> toModel(Song song, ServerWebExchange exchange) {

        Mono<Link> getOneSongLink = linkToSongById(exchange, song);
        Mono<Link> getAllSongLink = linkToSongs(exchange);

        return getOneSongLink.zipWith(getAllSongLink)
                .map(objects -> toSongEntityModel(song, objects.getT1(), objects.getT2()));

    }

    private EntityModel<Song> toSongEntityModel(Song song, Link ...links) {
        return new EntityModel<>(song, links);
    }

    private Mono<Link> linkToSongById(ServerWebExchange exchange, Song song) {
        return linkTo(
                methodOn(SongsAsyncController.class)
                        .getSong(song.getId(), exchange)
        ).withSelfRel().toMono();
    }

    private Mono<Link> linkToSongs(ServerWebExchange exchange) {
        return linkTo(
                methodOn(SongsAsyncController.class)
                        .listSongs(exchange)
        ).withRel("songs").toMono();
    }

    @Override
    public Flux<EntityModel<Song>> toCollectionModel
            (Flux<? extends Song> entities, ServerWebExchange exchange) {

        return entities.map(o -> toModel(o, exchange))
                .flatMap(Mono::flux);
    }
}