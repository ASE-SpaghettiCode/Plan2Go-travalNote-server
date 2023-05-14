package ASESpaghettiCode.TravelNoteServer.Controller;

import ASESpaghettiCode.TravelNoteServer.DTO.NoteDTO;
import ASESpaghettiCode.TravelNoteServer.Model.Note;
import ASESpaghettiCode.TravelNoteServer.Model.User;
import ASESpaghettiCode.TravelNoteServer.Repository.NoteRepository;
import ASESpaghettiCode.TravelNoteServer.Service.NoteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
public class NoteControllerTest {
    @MockBean
    private NoteService noteService;

    @MockBean
    private NoteRepository noteRepository;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

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
    void findAllNotesTest() throws Exception {
        List<Note> allNotes = List.of(note);

        given(noteService.findAllNotes()).willReturn(allNotes);

        MockHttpServletRequestBuilder getRequest = get("/notes")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].noteTitle", is(note.getNoteTitle())))
                .andExpect(jsonPath("$[0].authorId", is(note.getAuthorId())))
                .andExpect(jsonPath("$[0].coverImage", is(note.getCoverImage())))
                .andExpect(jsonPath("$[0].date", is(note.getDate())))
                .andExpect(jsonPath("$[0].duration", is(note.getDuration())))
                .andExpect(jsonPath("$[0].rating", is(note.getRating())))
                .andExpect(jsonPath("$[0].expense", is(note.getExpense())))
                .andExpect(jsonPath("$[0].numTravelers", is(note.getNumTravelers())))
                .andExpect(jsonPath("$[0].targetGroup", is(note.getTargetGroup())))
                .andExpect(jsonPath("$[0].destination", is(note.getDestination())))
                .andExpect(jsonPath("$[0].editorData", is(note.getEditorData())));
    }

    @Test
    void findNoteByIdTest() throws Exception {
        given(noteService.findNoteById(any(String.class))).willReturn(note);

        MockHttpServletRequestBuilder getRequest = get("/notes/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.noteTitle", is(note.getNoteTitle())))
                .andExpect(jsonPath("$.authorId", is(note.getAuthorId())))
                .andExpect(jsonPath("$.coverImage", is(note.getCoverImage())))
                .andExpect(jsonPath("$.date", is(note.getDate())))
                .andExpect(jsonPath("$.duration", is(note.getDuration())))
                .andExpect(jsonPath("$.rating", is(note.getRating())))
                .andExpect(jsonPath("$.expense", is(note.getExpense())))
                .andExpect(jsonPath("$.numTravelers", is(note.getNumTravelers())))
                .andExpect(jsonPath("$.targetGroup", is(note.getTargetGroup())))
                .andExpect(jsonPath("$.destination", is(note.getDestination())))
                .andExpect(jsonPath("$.editorData", is(note.getEditorData())));
    }

    @Test
    void findNotesByUser() throws Exception {
        given(noteService.findNotesByUserId(any(String.class))).willReturn((List.of(note)));

        MockHttpServletRequestBuilder getRequest = get("/users/1/notes")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].noteTitle", is(note.getNoteTitle())))
                .andExpect(jsonPath("$[0].authorId", is(note.getAuthorId())))
                .andExpect(jsonPath("$[0].coverImage", is(note.getCoverImage())))
                .andExpect(jsonPath("$[0].date", is(note.getDate())))
                .andExpect(jsonPath("$[0].duration", is(note.getDuration())))
                .andExpect(jsonPath("$[0].rating", is(note.getRating())))
                .andExpect(jsonPath("$[0].expense", is(note.getExpense())))
                .andExpect(jsonPath("$[0].numTravelers", is(note.getNumTravelers())))
                .andExpect(jsonPath("$[0].targetGroup", is(note.getTargetGroup())))
                .andExpect(jsonPath("$[0].destination", is(note.getDestination())))
                .andExpect(jsonPath("$[0].editorData", is(note.getEditorData())));
    }

    @Test
    void createNoteTest() throws Exception {
        note.setCreatedTime(null);
        given(noteService.createNote(any(Note.class))).willReturn(note);

        MockHttpServletRequestBuilder postRequest = post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(note));

        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.noteTitle", is(note.getNoteTitle())))
                .andExpect(jsonPath("$.authorId", is(note.getAuthorId())))
                .andExpect(jsonPath("$.coverImage", is(note.getCoverImage())))
                .andExpect(jsonPath("$.date", is(note.getDate())))
                .andExpect(jsonPath("$.duration", is(note.getDuration())))
                .andExpect(jsonPath("$.rating", is(note.getRating())))
                .andExpect(jsonPath("$.expense", is(note.getExpense())))
                .andExpect(jsonPath("$.numTravelers", is(note.getNumTravelers())))
                .andExpect(jsonPath("$.targetGroup", is(note.getTargetGroup())))
                .andExpect(jsonPath("$.destination", is(note.getDestination())))
                .andExpect(jsonPath("$.editorData", is(note.getEditorData())));
    }

    @Test
    void deleteNoteTest() throws Exception {
        doNothing().when(noteService).deleteNote(any(String.class), any(String.class));

        MockHttpServletRequestBuilder deleteRequest = delete("/users/1/delete/notes/1");

        mockMvc.perform(deleteRequest)
                .andExpect(status().isNoContent());
    }

    @Test
    void updateNoteTest() throws Exception {
        note.setCreatedTime(null);
        doNothing().when(noteService).updateNote(any(String.class), any(String.class), any(Note.class));

        MockHttpServletRequestBuilder putRequest = put("/users/1/editing/notes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(note));

        mockMvc.perform(putRequest)
                .andExpect(status().isOk());
    }

    @Test
    void userLikeNoteTest() throws Exception {
        doNothing().when(noteService).userLikesNote(any(String.class), any(String.class));

        MockHttpServletRequestBuilder postRequest = post("/users/1/likes/notes/1");

        mockMvc.perform(postRequest)
                .andExpect(status().isNoContent());
    }

    @Test
    void userUnlikeNoteTest() throws Exception {
        doNothing().when(noteService).userUnlikesNote(any(String.class), any(String.class));

        MockHttpServletRequestBuilder deleteRequest = delete("/users/1/likes/notes/1");

        mockMvc.perform(deleteRequest)
                .andExpect(status().isNoContent());
    }

    @Test
    void findFollowingNotesTest() throws Exception {
        User user1 = new User("username1","password1","1");
        user1.setUserId("id1");
        List<User> followingUsers = List.of(user1);
        ResponseEntity<List<User>> response = ResponseEntity.ok(followingUsers);
        NoteDTO noteDTO = new NoteDTO(note);

        given(restTemplate.exchange(any(String.class), any(), any(), eq(new ParameterizedTypeReference<List<User>>() {}))).willReturn(response);
        given(noteService.findNotesOfFollowees(any())).willReturn(List.of(note));
        given(noteService.addUsernameImagePathtotheNotelist(any())).willReturn(List.of(noteDTO));

        MockHttpServletRequestBuilder getRequest = get("/notes/following/1");

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
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
