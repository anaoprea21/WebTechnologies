package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.helper.Article;
import org.example.helper.User;
import org.example.helper.UserList;
import org.example.helper.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Article article;
    private User user;

    @BeforeEach
    void setUp() throws Exception {
        user = new User("username", "email@email.com", "password", UserRole.REPORTER, "pseudo");

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        article = new Article();
        article.setText("text text text text");
        article.setTitle("Title");
        article.setAuthor("pseudo");
        article.setReviewer("pseudo");
    }

    @Test
    void createArticleTest() throws Exception {
        mockMvc.perform(post("/api/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title"));
    }

    @Test
    void getAllArticles()throws Exception {
        mockMvc.perform(post("/api/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/article"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getArticleTest() throws Exception {
        String responseString = mockMvc.perform(post("/api/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Article art = objectMapper.readValue(responseString, Article.class);

        mockMvc.perform(get("/api/article/" + art.getAuthor()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(art.getTitle()));
    }
}
