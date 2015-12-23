package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.BlogService;
import com.mycompany.myapp.domain.Blog;
import com.mycompany.myapp.repository.BlogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Blog.
 */
@Service
@Transactional
public class BlogServiceImpl implements BlogService{

    private final Logger log = LoggerFactory.getLogger(BlogServiceImpl.class);
    
    @Inject
    private BlogRepository blogRepository;
    
    /**
     * Save a blog.
     * @return the persisted entity
     */
    public Blog save(Blog blog) {
        log.debug("Request to save Blog : {}", blog);
        Blog result = blogRepository.save(blog);
        return result;
    }

    /**
     *  get all the blogs.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Blog> findAll(Pageable pageable) {
        log.debug("Request to get all Blogs");
        Page<Blog> result = blogRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one blog by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Blog findOne(Long id) {
        log.debug("Request to get Blog : {}", id);
        Blog blog = blogRepository.findOne(id);
        return blog;
    }

    /**
     *  delete the  blog by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Blog : {}", id);
        blogRepository.delete(id);
    }
}
