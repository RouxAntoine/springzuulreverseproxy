package com.example.application.repositories;

import com.example.application.entities.SongEntity;
import com.example.application.models.Song;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;

import javax.validation.constraints.NotNull;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Repository
public class SongRepository implements ReactiveCrudRepository<SongEntity, String> {

    private final ReactiveRedisOperations<String, SongEntity> songOps;

    public SongRepository(ReactiveRedisOperations<String, SongEntity> songOps) {
        this.songOps = songOps;
    }

    @Override
    public <S extends SongEntity> Mono<S> save(@NotNull S entity) {
        Mono<Boolean> success = this.songOps.opsForValue().set(entity.getId(), entity);
        return success
                .map(aBoolean -> aBoolean? entity : null);
    }

    @Override
    public <S extends SongEntity> Flux<S> saveAll(Iterable<S> entities) {

        Stream<Mono<S>> s = StreamSupport.stream(entities.spliterator(), false)
                .map(this::save);

        return Flux.fromStream(s)
                .flatMap(Mono::flux);
    }

    @Override
    public <S extends SongEntity> Flux<S> saveAll(Publisher<S> entityStream) {
        return Flux.from(entityStream)
                .map(this::save)
                .flatMap(sMono -> sMono);
    }

    @Override
    public Mono<SongEntity> findById(String s) {
        return this.songOps.opsForValue().get(s);
    }

    @Override
    public Mono<SongEntity> findById(Publisher<String> id) {
        Mono<String> idM = Mono.from(id);
        return idM
                .map(this::findById)
                .flatMap(songEntityMono -> songEntityMono);
    }

    @Override
    public Mono<Boolean> existsById(String s) {
        return this.songOps.hasKey(s);
    }

    @Override
    public Mono<Boolean> existsById(Publisher<String> id) {
        return Mono.from(id)
                .map(this.songOps::hasKey)
                .flatMap(booleanMono -> booleanMono);
    }

    @Override
    public Flux<SongEntity> findAll() {
//        this.songOps.opsForValue();
        this.songOps.opsForHash().get("id", "Song");
        return null;
    }

    @Override
    public Flux<SongEntity> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public Flux<SongEntity> findAllById(Publisher<String> idStream) {
        return null;
    }

    @Override
    public Mono<Long> count() {
        return null;
    }

    @Override
    public Mono<Void> deleteById(String s) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Publisher<String> id) {
        return null;
    }

    @Override
    public Mono<Void> delete(SongEntity entity) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends SongEntity> entities) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends SongEntity> entityStream) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll() {
        return null;
    }
}
