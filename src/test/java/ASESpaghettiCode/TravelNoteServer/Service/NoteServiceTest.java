package ASESpaghettiCode.TravelNoteServer.Service;

import ASESpaghettiCode.TravelNoteServer.Model.Comment;
import ASESpaghettiCode.TravelNoteServer.Model.Note;
import ASESpaghettiCode.TravelNoteServer.Repository.CommentRepository;
import ASESpaghettiCode.TravelNoteServer.Repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.net.URISyntaxException;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class NoteServiceTest {
    private final NoteRepository noteRepository = mock(NoteRepository.class);
    private final CommentRepository commentRepository = mock(CommentRepository.class);

    private final NoteService noteService = new NoteService(noteRepository, commentRepository);

    Comment comment = new Comment("authorId", "authorName", "imageLink", "targetNoteId", "commentText");
    Note note;

    @BeforeEach
    void createNote() {
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
    void findAllNotesTest() {
        when(noteRepository.findAll()).thenReturn(List.of(note));

        assertEquals(List.of(note), noteRepository.findAll());
    }

    @Test
    void createNoteTest() {
        List<String> initialLikedUsers = new ArrayList<>();
        List<String> initialCommentList = new ArrayList<>();
        note.setCommentList(initialCommentList);
        note.setLikedUsers(initialLikedUsers);

        when(noteRepository.save(any(Note.class))).thenReturn(note);

        assertEquals(note, noteService.createNote(note));
    }

    @Test
    void deleteNoteTest_Success() {
        List<String> initialCommentList = new ArrayList<>();
        initialCommentList.add("1");
        note.setCommentList(initialCommentList);

        when(noteRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(note));
        when(commentRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(comment));

        noteService.deleteNote("1", "authorId1");
        verify(commentRepository, times(1)).delete(any(Comment.class));
        verify(noteRepository, times(1)).delete(any(Note.class));
    }

    @Test
    void deleteNoteTest_Fail_NoNote() {
        when(noteRepository.findById(any(String.class))).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> noteService.deleteNote("1", "1"));
    }

    @Test
    void deleteNoteTest_Fail_Unauthorized() {
        when(noteRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(note));

        assertThrows(ResponseStatusException.class, () -> noteService.deleteNote("1", "1"));
    }

    @Test
    void findNotesByUserIdTest() {
        when(noteRepository.findAll()).thenReturn(List.of(note));

        assertEquals(List.of(note), noteService.findNotesByUserId("authorId1"));
    }

    @Test
    void findNotesByIdTest_Success() {
        when(noteRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(note));

        assertEquals(note, noteService.findNoteById("authorId1"));
    }

    @Test
    void findNotesByIdTest_Fail() {
        when(noteRepository.findById(any(String.class))).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> noteService.findNoteById("1"));
    }

    @Test
    void updateNoteTest_Success() {
        when(noteRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(note));

        noteService.updateNote("1", "authorId1", note);

        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void updateNoteTest_Fail_NoNote() {
        when(noteRepository.findById(any(String.class))).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> noteService.updateNote("1", "1", note));
    }

    @Test
    void updateNoteTest_Fail_Unauthorized() {
        when(noteRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(note));

        assertThrows(ResponseStatusException.class, () -> noteService.updateNote("1", "1", note));
    }

//    @Test
//    void userLikesNoteTest() {
//        List<String> initialLikedUsers = new ArrayList<>();
//        note.setLikedUsers(initialLikedUsers);
//        ReflectionTestUtils.setField(noteService, "UserServerLocation", "http://localhost:8081");
//        when(noteRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(note));
//
//        noteService.userLikesNote("1", "1");
//
//        verify(noteRepository, times(1)).save(any(Note.class));
//    }

//    @Test
//    void userUnlikesNoteTest_Success() {
//        List<String> initialLikedUsers = new ArrayList<>();
//        initialLikedUsers.add("1");
//        note.setLikedUsers(initialLikedUsers);
//
//        when(noteRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(note));
//
//        noteService.userUnlikesNote("1", "1");
//
//        verify(noteRepository, times(1)).save(any(Note.class));
//    }

    @Test
    void userUnlikesNoteTest_Fail() {
        List<String> initialLikedUsers = new ArrayList<>();
        note.setLikedUsers(initialLikedUsers);

        when(noteRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(note));

        assertThrows(ResponseStatusException.class, () -> noteService.userUnlikesNote("1", "1"));
    }

    @Test
    void findNotesOfFolloweesTest_Success() {
        when(noteRepository.findByUserIdListInOrderByCreatedDateAsc(any(), any())).thenReturn(List.of(note));

        assertEquals(List.of(note), noteService.findNotesOfFollowees(List.of("1")));
    }

    @Test
    void findNotesOfFolloweesTest_Fail() {
        when(noteRepository.findByUserIdListInOrderByCreatedDateAsc(any(), any())).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> noteService.findNotesOfFollowees(List.of("1")));
    }

    @Test
    void addUsernameImagePathtotheNotelistTest_Fail() {
        ReflectionTestUtils.setField(noteService, "UserServerLocation", "http://localhost:8081");

        assertThrows(RuntimeException.class,() -> noteService.addUsernameImagePathtotheNotelist(List.of(note)));
    }
}

