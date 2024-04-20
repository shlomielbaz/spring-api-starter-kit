package com.shlomielbaz.repository;


import com.shlomielbaz.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> { }

