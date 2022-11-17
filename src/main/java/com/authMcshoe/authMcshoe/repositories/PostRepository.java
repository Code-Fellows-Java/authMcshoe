package com.authMcshoe.authMcshoe.repositories;

import com.authMcshoe.authMcshoe.models.PostUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostUser, Long> {

}
