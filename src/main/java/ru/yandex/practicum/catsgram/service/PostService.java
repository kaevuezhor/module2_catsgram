package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.PostNotFoundException;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final UserService userService;
    private final List<Post> posts = new ArrayList<>();

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll(
            String sort,
            int size,
            int from
    ) {
        return posts.stream().
                sorted((p0, p1) -> {
                    int comp = p0.getCreationDate().compareTo(p1.getCreationDate());
                    if (sort.equals("desc")) {
                        comp *= -1;
                    }
                    return comp;
                }).
                skip(from).
                limit(size).
                collect(Collectors.toList());
    }

    public List<Post> findAllByUserEmail(String email, Integer size, String sort) {
        return posts.stream().
                filter(p -> email.equals(p.getAuthor())).
                sorted(
                    (p0, p1) -> {
                    int comp = p0.getCreationDate().compareTo(p1.getCreationDate());
                    if (sort.equals("desc")) {
                        comp = -1 * comp;
                    }
                    return comp;
                    }).
                limit(size).
                collect(Collectors.toList());
    }

    public Post create(Post post) {
        if (userService.findUser(post.getAuthor()) == null) {
            throw new UserNotFoundException(String.format(
                    "Пользователь %s не найден",
                    post.getAuthor()));
        }
        Post uploadingPost = new Post(
                posts.size(),
                post.getAuthor(),
                post.getDescription(),
                post.getPhotoUrl()
        );
        posts.add(uploadingPost);
        return uploadingPost;
    }

    public Post findById(int postId) {
        return posts.stream()
                .filter(x -> x.getId() == postId)
                .findFirst()
                .orElseThrow(() -> new PostNotFoundException(String.format("Пост № %d не найден", postId)));
    }
}