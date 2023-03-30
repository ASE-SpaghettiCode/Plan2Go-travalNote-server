package ASESpaghettiCode.TravelNoteServer.Controller;

import ASESpaghettiCode.TravelNoteServer.Model.Note;
import ASESpaghettiCode.TravelNoteServer.Repository.NoteRepository;
import ASESpaghettiCode.TravelNoteServer.Service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class NoteController {

    private NoteService noteService;
    private NoteRepository noteRepository;

    NoteController(NoteRepository noteRepository, NoteService noteService){
        this.noteRepository = noteRepository;
        this.noteService = noteService;
    }

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

    @DeleteMapping("/users/{userId}/notes/{noteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable String noteId, @PathVariable String userId) {
        noteService.deleteNote(noteId, userId);
    }

    @RequestMapping("/notes/{noteId}")
    public String getNote(@PathVariable("noteId") String noteId){
        return "the note info page of note "+noteId;
    }


}
