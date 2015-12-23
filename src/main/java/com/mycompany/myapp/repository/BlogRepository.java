package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Blog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Blog entity.
 */
public interface BlogRepository extends JpaRepository<Blog,Long> {

}
