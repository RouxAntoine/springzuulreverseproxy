package com.example.application.entities;

import com.example.application.models.MusicStyle;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@RedisHash("Song")
public class SongEntity implements Serializable {

    private String id;

    private String title;

    private Integer year;

    private MusicStyle style;

    @Builder.Default
    private List<ArtistEntity> artist = new ArrayList<>();
}
