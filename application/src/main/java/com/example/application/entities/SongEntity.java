package com.example.application.entities;

import com.example.application.models.MusicStyle;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@RedisHash("Song")
@NoArgsConstructor
@AllArgsConstructor
public class SongEntity implements Serializable {

    private String id;

    private String title;

    private Integer year;

    private MusicStyle style;

    @Builder.Default
    private List<ArtistEntity> artist = new ArrayList<>();
}
