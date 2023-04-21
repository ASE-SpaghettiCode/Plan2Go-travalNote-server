package ASESpaghettiCode.TravelNoteServer.Controller;

import ASESpaghettiCode.TravelNoteServer.DTO.CommentPostDTO;
import ASESpaghettiCode.TravelNoteServer.Model.Comment;
import ASESpaghettiCode.TravelNoteServer.Service.CommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @MockBean
    private CommentService commentService;

    @Autowired
    private MockMvc mockMvc;

    Comment comment = new Comment("authorId","authorName","imageLink","targetNoteId","commentText");

    @Test
    void getCommentsByNoteIdTest() throws Exception {
        List<Comment> comments = List.of(comment);

        given(commentService.findCommentsByNoteId(any(String.class))).willReturn(comments);

        MockHttpServletRequestBuilder getRequest = get("/notes/1/comments")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].commentAuthorId", is(comment.getCommentAuthorId())))
                .andExpect(jsonPath("$[0].commentAuthorName", is(comment.getCommentAuthorName())))
                .andExpect(jsonPath("$[0].commentAuthorImage", is(comment.getCommentAuthorImage())))
                .andExpect(jsonPath("$[0].targetNoteId", is(comment.getTargetNoteId())))
                .andExpect(jsonPath("$[0].commentText", is(comment.getCommentText())));
    }

    @Test
    void createCommentForAGivenNoteTest() throws Exception {
        CommentPostDTO commentPostDTO = new CommentPostDTO();

        given(commentService.createComment(any(String.class),any(CommentPostDTO.class))).willReturn(comment);

        MockHttpServletRequestBuilder postRequest = post("/notes/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(commentPostDTO));

        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.commentAuthorId", is(comment.getCommentAuthorId())))
                .andExpect(jsonPath("$.commentAuthorName", is(comment.getCommentAuthorName())))
                .andExpect(jsonPath("$.commentAuthorImage", is(comment.getCommentAuthorImage())))
                .andExpect(jsonPath("$.targetNoteId", is(comment.getTargetNoteId())))
                .andExpect(jsonPath("$.commentText", is(comment.getCommentText())));
    }

    @Test
    void deleteCommentTest() throws Exception {
        doNothing().when(commentService).deleteComment(any(String.class),any(String.class));

        MockHttpServletRequestBuilder deleteRequest = delete("/users/1/comments/1");

        mockMvc.perform(deleteRequest)
                .andExpect(status().isNoContent());
    }

    @Test
    void updateCommentTest() throws Exception {
        CommentPostDTO commentPostDTO = new CommentPostDTO();

        doNothing().when(commentService).updateComment(any(String.class),any(String.class),any(CommentPostDTO.class));

        MockHttpServletRequestBuilder putRequest = put("/users/1/comments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(commentPostDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isOk());
    }

    private String asJsonString(final Object o) {
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e));
        }
    }
}
