package com.example.application.configurations;

import com.example.application.entities.SongEntity;
import com.example.application.models.MusicStyle;
import com.example.application.repositories.SongRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * init data into database
 */
@Configuration
public class SpringCommandLineRunner {

    /**
     * Use Spring to inject a {@link SongRepository} that can then load data. Since this will run
     * only after the app is operational, the database will be up.
     *
     * @param repository
     */
    @Bean
    CommandLineRunner init(SongRepository repository) {
        return args -> {
            repository.save(
                    SongEntity.builder()
                            .id("14")
                            .style(MusicStyle.FRENCH_VARIETY)
                            .title("Milord")
                            .year(1959)
//                            .artist("Édith Piaf")
                            .build()
            );

            repository.save(
                    SongEntity.builder()
                            .id("15")
                            .style(MusicStyle.FRENCH_VARIETY)
                            .title("La ballade irlandaise (Un oranger)")
                            .year(1958)
//                            .artist("Bourvil")
                            .build()
            );
        };
    }
}
