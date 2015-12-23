package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Blog;
import com.mycompany.myapp.repository.BlogRepository;
import com.mycompany.myapp.service.BlogService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the BlogResource REST controller.
 *
 * @see BlogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BlogResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_BODY = "AAAAA";
    private static final String UPDATED_BODY = "BBBBB";

    private static final LocalDate DEFAULT_PUBLISHON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PUBLISHON = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private BlogRepository blogRepository;

    @Inject
    private BlogService blogService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBlogMockMvc;

    private Blog blog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BlogResource blogResource = new BlogResource();
        ReflectionTestUtils.setField(blogResource, "blogService", blogService);
        this.restBlogMockMvc = MockMvcBuilders.standaloneSetup(blogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        blog = new Blog();
        blog.setTitle(DEFAULT_TITLE);
        blog.setBody(DEFAULT_BODY);
        blog.setPublishon(DEFAULT_PUBLISHON);
        blog.setCreated(DEFAULT_CREATED);
    }

    @Test
    @Transactional
    public void createBlog() throws Exception {
        int databaseSizeBeforeCreate = blogRepository.findAll().size();

        // Create the Blog

        restBlogMockMvc.perform(post("/api/blogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(blog)))
                .andExpect(status().isCreated());

        // Validate the Blog in the database
        List<Blog> blogs = blogRepository.findAll();
        assertThat(blogs).hasSize(databaseSizeBeforeCreate + 1);
        Blog testBlog = blogs.get(blogs.size() - 1);
        assertThat(testBlog.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBlog.getBody()).isEqualTo(DEFAULT_BODY);
        assertThat(testBlog.getPublishon()).isEqualTo(DEFAULT_PUBLISHON);
        assertThat(testBlog.getCreated()).isEqualTo(DEFAULT_CREATED);
    }

    @Test
    @Transactional
    public void getAllBlogs() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogs
        restBlogMockMvc.perform(get("/api/blogs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(blog.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY.toString())))
                .andExpect(jsonPath("$.[*].publishon").value(hasItem(DEFAULT_PUBLISHON.toString())))
                .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())));
    }

    @Test
    @Transactional
    public void getBlog() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get the blog
        restBlogMockMvc.perform(get("/api/blogs/{id}", blog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(blog.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.body").value(DEFAULT_BODY.toString()))
            .andExpect(jsonPath("$.publishon").value(DEFAULT_PUBLISHON.toString()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBlog() throws Exception {
        // Get the blog
        restBlogMockMvc.perform(get("/api/blogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBlog() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

		int databaseSizeBeforeUpdate = blogRepository.findAll().size();

        // Update the blog
        blog.setTitle(UPDATED_TITLE);
        blog.setBody(UPDATED_BODY);
        blog.setPublishon(UPDATED_PUBLISHON);
        blog.setCreated(UPDATED_CREATED);

        restBlogMockMvc.perform(put("/api/blogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(blog)))
                .andExpect(status().isOk());

        // Validate the Blog in the database
        List<Blog> blogs = blogRepository.findAll();
        assertThat(blogs).hasSize(databaseSizeBeforeUpdate);
        Blog testBlog = blogs.get(blogs.size() - 1);
        assertThat(testBlog.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBlog.getBody()).isEqualTo(UPDATED_BODY);
        assertThat(testBlog.getPublishon()).isEqualTo(UPDATED_PUBLISHON);
        assertThat(testBlog.getCreated()).isEqualTo(UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void deleteBlog() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

		int databaseSizeBeforeDelete = blogRepository.findAll().size();

        // Get the blog
        restBlogMockMvc.perform(delete("/api/blogs/{id}", blog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Blog> blogs = blogRepository.findAll();
        assertThat(blogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
