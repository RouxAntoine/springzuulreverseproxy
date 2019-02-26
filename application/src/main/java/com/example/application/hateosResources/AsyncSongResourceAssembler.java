package com.example.application.hateosResources;

import com.example.application.controllers.async.SongsAsyncController;
import com.example.application.controllers.sync.SongsSyncController;
import com.example.application.models.Song;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.reactive.SimpleReactiveResourceAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class AsyncSongResourceAssembler implements SimpleReactiveResourceAssembler<Song> {

    @Override
    public void addLinks(Resource<Song> resource, ServerWebExchange exchange) {

        resource.add(
            linkTo(methodOn(SongsAsyncController.class).getSong(resource.getContent().getId(), exchange)).withSelfRel(),
            linkTo(methodOn(SongsAsyncController.class).listSongs(exchange)).withRel("songs")
        );
    }

    @Override
    public void addLinks(Resources<Resource<Song>> resources, ServerWebExchange exchange) {
        resources.add(
                linkTo(methodOn(SongsAsyncController.class).listSongs(exchange)).withSelfRel()
        );
    }
}