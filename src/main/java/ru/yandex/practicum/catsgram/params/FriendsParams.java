package ru.yandex.practicum.catsgram.params;

import lombok.Data;

import java.util.List;

@Data
public class FriendsParams {

    private String sort;
    private int size;
    private List<String> friends;

}
