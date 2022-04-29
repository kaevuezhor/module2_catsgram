package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;
import java.util.Optional;

@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<Post> findAll(
            @RequestParam(value = "sort", defaultValue = "desc", required = false) String sort,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page
    ) {
        if (!sort.equals("asc") || sort.equals("desc")) {
            throw new IllegalArgumentException();
        }
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException();
        }

        int from = page * size;
        return postService.findAll(sort, size, from);
    }

    @GetMapping("/post/{postId}")
    public Post findById(@PathVariable int postId) {
        return postService.findById(postId);
    }

    @PostMapping(value = "/post")
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }
}