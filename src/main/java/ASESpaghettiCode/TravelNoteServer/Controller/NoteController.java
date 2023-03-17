package ASESpaghettiCode.TravelNoteServer.Controller;

import ASESpaghettiCode.TravelNoteServer.Model.Note;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class NoteController {

    @RequestMapping()
    public String get(){
        return "The note page is running";
    }

    @RequestMapping("/notes/{noteId}")
    public Note getNote(@PathVariable("noteId") String noteId){
        Note note = new Note(Integer.valueOf(noteId),"title of note "+noteId);
        return note ;
    }


}
