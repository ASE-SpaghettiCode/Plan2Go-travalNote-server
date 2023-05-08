package ASESpaghettiCode.TravelNoteServer.Service;

import ASESpaghettiCode.TravelNoteServer.DTO.CommentPostDTO;
import ASESpaghettiCode.TravelNoteServer.Model.Comment;
import ASESpaghettiCode.TravelNoteServer.Model.Note;
import ASESpaghettiCode.TravelNoteServer.Repository.CommentRepository;
import ASESpaghettiCode.TravelNoteServer.Repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CommentServiceTest {

    private final CommentRepository commentRepository = mock(CommentRepository.class);
    private final NoteRepository noteRepository = mock(NoteRepository.class);
    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final CommentService commentService = new CommentService(commentRepository, noteRepository);

    Comment comment = new Comment("authorId", "authorName", "imageLink", "targetNoteId", "commentText");
    CommentPostDTO commentPostDTO = new CommentPostDTO();
    Note note;

    @BeforeEach
    void createComment() {
        commentPostDTO.setCommentText("commentText");
        commentPostDTO.setCommentAuthorId("1");

        Map<String, Object> editorData = new HashMap<>();
        editorData.put("time", 1681651243808L);

        Map<String, Object> block = new HashMap<>();
        block.put("id", "c1nqUL8OAu");
        block.put("type", "header");

        Map<String, Object> data = new HashMap<>();
        data.put("text", "Shop now!");
        data.put("level", 1);

        block.put("data", data);

        List<Map<String, Object>> blocks = new ArrayList<>();
        blocks.add(block);

        editorData.put("blocks", blocks);
        editorData.put("version", "2.26.5");

        Double[] coordinates = new Double[]{1.0, 1.0};
        Object obj = editorData;
        note = new Note("title1", "authorId1", "imageLink", "11.11.2022", 10, 5.0, 100, 3, "targetGroup1", "destination1", coordinates, obj);
    }

    @Test
    void createCommentTest() {
        ReflectionTestUtils.setField(commentService, "UserServerLocation", "http://localhost:8081");
        note.setCommentList(new ArrayList<>());

        when(noteRepository.findById(any(String.class))).thenReturn(java.util.Optional.ofNullable(note));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        Comment createComment = commentService.createComment("1", commentPostDTO);
        assertEquals(comment, createComment);
    }

    @Test
    void findCommentsByNoteIdTest_Success() {
        note.setCommentList(List.of("comment"));
        when(noteRepository.findById(any(String.class))).thenReturn(java.util.Optional.ofNullable(note));
        when(commentRepository.findById(any(String.class))).thenReturn(java.util.Optional.ofNullable(comment));

        List<Comment> comments = commentService.findCommentsByNoteId("1");

        assertEquals(List.of(comment),comments);
    }

    @Test
    void findCommentsByNoteIdTest_Fail(){
        when(noteRepository.findById(any(String.class))).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,() -> commentService.findCommentsByNoteId("1"));
    }

    @Test
    void deleteCommentTest_Success() {
        List<String> commentList = new ArrayList<>();
        commentList.add("1");
        note.setCommentList(commentList);
        when(commentRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(comment));
        when(noteRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(note));

        commentService.deleteComment("1","authorId");

        verify(noteRepository,times(1)).save(any(Note.class));
        verify(commentRepository,times(1)).delete(any(Comment.class));
    }

    @Test
    void deleteCommentTest_Fail_NoComment() {
        when(commentRepository.findById(any(String.class))).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> commentService.deleteComment("1","1"));
    }

    @Test
    void deleteCommentTest_Fail_Unauthorized() {
        when(commentRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(comment));

        assertThrows(ResponseStatusException.class, () -> commentService.deleteComment("1","1"));
    }

    @Test
    void deleteCommentTest_Fail_NoNote() {
        when(commentRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(comment));
        when(noteRepository.findById(any(String.class))).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> commentService.deleteComment("1","authorId"));
    }

    @Test
    void updateComment_Success() {
        when(commentRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(comment));

        commentService.updateComment("authorId","1",commentPostDTO);

        verify(commentRepository,times(1)).save(any(Comment.class));
    }

    @Test
    void updateComment_Fail_NoComment() {
        when(commentRepository.findById(any(String.class))).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> commentService.updateComment("1","1",commentPostDTO));
    }

    @Test
    void updateComment_Fail_Unauthorized() {
        when(commentRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(comment));

        assertThrows(ResponseStatusException.class, () -> commentService.updateComment("1","1",commentPostDTO));
    }
}
