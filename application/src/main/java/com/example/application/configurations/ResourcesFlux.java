package com.example.application.configurations;

import org.springframework.hateoas.Resources;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;

public class ResourcesFlux<T> extends Flux<T> {

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {

    }
}
