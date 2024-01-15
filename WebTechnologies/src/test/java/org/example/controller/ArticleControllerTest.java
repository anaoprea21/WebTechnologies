package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.helper.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.http.RequestEntity.put;
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
    private Article article2;
    private User user;
    private User user2;
    private CreateArticleDTO createDTO;

    @BeforeEach
    void setUp() throws Exception {
        user = new User("username", "email@email.com", "password", UserRole.REPORTER, "pseudo");

        user2 = new User("username2", "email2@email.com", "password2", UserRole.SEEKER, "e");
        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user2)))
                .andExpect(status().isOk());

        article = new Article();
        article.setText("text text text text");
        article.setTitle("title1");
        article.setAuthor("pseudo");
        article.setReviewing(false);

        article2 = new Article();
        article2.setText("text text text text");
        article2.setTitle("Title2");
        article2.setAuthor("pseudo");
        article2.setReviewing(true);

        createDTO = new CreateArticleDTO(user.getUsername(), article);
    }

    @Test
    void createArticleTest() throws Exception {
        mockMvc.perform(post("/api/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title1"));
    }

    @Test
    void createArticleTestUnauthorized() throws Exception {
        createDTO.setAuthorUsername(user2.getUsername());
        mockMvc.perform(post("/api/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAllArticles() throws Exception {
        mockMvc.perform(post("/api/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/article"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getArticleTest() throws Exception {
        String responseString = mockMvc.perform(post("/api/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Article art = objectMapper.readValue(responseString, Article.class);

        mockMvc.perform(get("/api/article/" + art.getTitle()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(art.getTitle()));
    }

    @Test
    void getArticlesByAuthorTest() throws Exception {
        String responseString = mockMvc.perform(post("/api/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Article art = objectMapper.readValue(responseString, Article.class);

        mockMvc.perform(get("/api/article/author/" + art.getAuthor()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getArticlesInReviewTest() throws Exception {
        String responseString = mockMvc.perform(post("/api/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Article art = objectMapper.readValue(responseString, Article.class);

        mockMvc.perform(get("/api/article/checkInReview/" + art.getTitle() + "/" + user.getUsername()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(art.getTitle()));
    }

    @Test
    void getArticlesInReviewTestUnauthorized() throws Exception {
        String responseString = mockMvc.perform(post("/api/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Article art = objectMapper.readValue(responseString, Article.class);

        mockMvc.perform(get("/api/article/checkInReview/" + art.getTitle() + "/" + user2.getUsername()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getArticlesByPopularityTest() throws Exception {
        String responseString = mockMvc.perform(post("/api/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Article art = objectMapper.readValue(responseString, Article.class);

        createDTO.setArticle(article2);

        mockMvc.perform(post("/api/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/article/" + art.getTitle()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/article/getArticlesByPopularity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void editArticleTest() throws Exception {
        String response = mockMvc.perform(post("/api/article                                                                                                                                ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Article art = objectMapper.readValue(response, Article.class);
        art.setText("new text");
        createDTO.setArticle(art);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/article/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("new text"));
    }
}
