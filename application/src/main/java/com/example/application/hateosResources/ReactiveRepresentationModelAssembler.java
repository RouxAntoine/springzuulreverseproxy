package com.example.application.hateosResources;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveRepresentationModelAssembler<T, D extends RepresentationModel<D>> {

	/**
	 * convert an entity to {@link RepresentationModelAssembler}
	 *
	 * @param entity
	 * @param exchange
	 * @return
	 */
	Mono<D> toModel(T entity, ServerWebExchange exchange);


	/**
	 * convert a {@link Flux} of entity to {@link Flux} of {@link RepresentationModelAssembler}
	 * @param entities
	 * @param exchange
	 * @return
	 */
	Flux<D> toCollectionModel(Flux<? extends T> entities, ServerWebExchange exchange);
}
