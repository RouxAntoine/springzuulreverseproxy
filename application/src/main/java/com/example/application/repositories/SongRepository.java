package com.example.application.repositories;

import com.example.application.entities.SongEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Crud interface for interact with song
 */
public interface SongRepository extends ReactiveCrudRepository<SongEntity, String> {


}
