package ASESpaghettiCode.TravelNoteServer.Controller;

import ASESpaghettiCode.TravelNoteServer.DTO.NoteDTO;
import ASESpaghettiCode.TravelNoteServer.Model.Note;
import ASESpaghettiCode.TravelNoteServer.Model.User;
import ASESpaghettiCode.TravelNoteServer.Repository.NoteRepository;
import ASESpaghettiCode.TravelNoteServer.Service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class NoteController {

    private NoteService noteService;
    private NoteRepository noteRepository;

    NoteController(NoteRepository noteRepository, NoteService noteService){
        this.noteRepository = noteRepository;
        this.noteService = noteService;
    }

    @Value("${UserServerLocation}")
    private String UserServerLocation;

    @Autowired
    private RestTemplate restTemplate = new RestTemplateBuilder()
            .errorHandler(new RestTemplateErrorHandler())
            .build();


    @GetMapping("/notes")
    @ResponseStatus(HttpStatus.OK)
    public List<Note> findAllNotes(){
        return noteService.findAllNotes();
    }

    @GetMapping("/notes/{noteId}")
    @ResponseStatus(HttpStatus.OK)
    public Note findNoteById(@PathVariable String noteId) {
        return noteService.findNoteById(noteId);
    }

    @GetMapping("users/{userId}/notes")
    @ResponseStatus(HttpStatus.OK)
    public List<Note> findNotesByUser(@PathVariable String userId) {
        return noteService.findNotesByUserId(userId);
    }

    @PostMapping("/notes")
    @ResponseStatus(HttpStatus.CREATED)
    public Note createNote(@RequestBody Note newNote) {
        return noteService.createNote(newNote);
    }

    @DeleteMapping("/users/{userId}/delete/notes/{noteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable String noteId, @PathVariable String userId) {
        noteService.deleteNote(noteId, userId);
    }

    @PutMapping("users/{userId}/editing/notes/{noteId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateNote(@PathVariable String noteId, @PathVariable String userId, @RequestBody Note note) {
        noteService.updateNote(noteId, userId, note);
    }

    @RequestMapping("/notes/{noteId}")
    public String getNote(@PathVariable("noteId") String noteId){
        return "the note info page of note "+noteId;
    }

    @PostMapping("users/{userId}/likes/notes/{noteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void userLikesNote(@PathVariable String userId, @PathVariable String noteId) {
        noteService.userLikesNote(userId, noteId);
    }

    @DeleteMapping("users/{userId}/likes/notes/{noteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void userUnlikesNote(@PathVariable String userId, @PathVariable String noteId) {
        noteService.userUnlikesNote(userId, noteId);
    }

    @GetMapping("/notes/following/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<NoteDTO> findFollowingNotes(@PathVariable String userId) {
        // get all the authorId that a user is following
        List<String> followingUserId = restTemplate.getForObject(UserServerLocation + "/users/" + userId + "/followings", List.class);
        // find all notes with the followingUserId
        List<Note> noteList = noteService.findNotesOfFollowees(followingUserId);
        return noteService.addUsernameImagePathtotheNotelist(noteList);
    }

}
