package ASESpaghettiCode.TravelNoteServer.Controller;

import ASESpaghettiCode.TravelNoteServer.Model.Note;
import ASESpaghettiCode.TravelNoteServer.Service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/notes")
    @ResponseStatus(HttpStatus.OK)
    public List<Note> findAll(){
        return noteService.findAll();

    }

    @PostMapping("notes")
    @ResponseStatus(HttpStatus.CREATED)
    public Note createNote(@RequestBody Note newNote) {
        return noteService.createNote(newNote);
    }

    @RequestMapping("/notes/{noteId}")
    public String getNote(@PathVariable("noteId") String noteId){
        return "the note info page of note "+noteId;
    }


}
