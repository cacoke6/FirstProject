package com.example.basic.Member.repositoty;

import com.example.basic.Member.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepositoy extends JpaRepository<Comment, Integer> {
}
