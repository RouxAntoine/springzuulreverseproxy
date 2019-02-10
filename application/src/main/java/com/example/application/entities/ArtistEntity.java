package com.example.application.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Artist")
@Getter @Setter @Builder
public class ArtistEntity implements Serializable {

    private String name;
    private String surname;
    private String nickname;

}
