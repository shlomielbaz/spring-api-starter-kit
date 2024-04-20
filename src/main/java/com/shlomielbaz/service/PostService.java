package com.shlomielbaz.service;

import com.shlomielbaz.entity.Post;
import com.shlomielbaz.interfaces.ICrusService;
import com.shlomielbaz.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService implements ICrusService<Post> {
    @Autowired
    private PostRepository postRepository;

    @Override
    public Post create(Post entity) {
        return postRepository.save(entity);
    }

    @Override
    public Post getById(Long id) {
        Optional<Post> optionalUser = postRepository.findById(id);
        return optionalUser.get();
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Override
    public Post update(Post entity) {
        Post existingPost = postRepository.findById(entity.getId()).get();
        existingPost.setBody(entity.getBody());
        existingPost.setTitle(entity.getTitle());
        return postRepository.save(existingPost);
    }

    @Override
    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
