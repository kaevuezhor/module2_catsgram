package ru.yandex.practicum.catsgram.model;

import lombok.Data;

import java.time.Instant;

@Data
public class Post {
    private final int id;
    private final String author; // автор
    private final Instant creationDate = Instant.now(); // дата создания
    private String description; // описание
    private String photoUrl; // url-адрес фотографии

    public Post (int id, String author, String description, String photoUrl) {
        this.id = id;
        this.author = author;
        this.description = description;
        this.photoUrl = photoUrl;
    }
}