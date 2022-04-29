package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.params.FriendsParams;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostFeedController {

    private final PostService postService;

    @Autowired
    public PostFeedController(PostService postService){
        this.postService = postService;
    }

    @PostMapping("/feed/friends")
    public List<Post> findFriends(@RequestBody String request) {
        ObjectMapper objectMapper = new ObjectMapper();
        FriendsParams friendsParams;
        try {
            String requestParams = objectMapper.readValue(request, String.class);
            friendsParams = objectMapper.readValue(requestParams, FriendsParams.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (friendsParams == null) {
            throw new RuntimeException();
        }

        List<Post> result = new ArrayList<>();
        for (String friend : friendsParams.getFriends()) {
            result.addAll(postService.findAllByUserEmail(friend, friendsParams.getSize(), friendsParams.getSort()));
        }

        return result;
    }

}
