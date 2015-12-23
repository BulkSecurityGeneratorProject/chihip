package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Blog.
 */
public interface BlogService {

    /**
     * Save a blog.
     * @return the persisted entity
     */
    public Blog save(Blog blog);

    /**
     *  get all the blogs.
     *  @return the list of entities
     */
    public Page<Blog> findAll(Pageable pageable);

    /**
     *  get the "id" blog.
     *  @return the entity
     */
    public Blog findOne(Long id);

    /**
     *  delete the "id" blog.
     */
    public void delete(Long id);
}
