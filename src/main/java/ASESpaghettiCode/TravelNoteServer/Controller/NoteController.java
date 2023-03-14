package ASESpaghettiCode.TravelNoteServer.Controller;

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

    @RequestMapping("/user/{notesId}")
    public String getNote(@PathVariable("notesId") String notesId){
        return "The info page of Note" + notesId ;
    }
}
