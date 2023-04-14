package ASESpaghettiCode.TravelNoteServer.DTO;

import lombok.Data;
import ASESpaghettiCode.TravelNoteServer.Model.Note;


import java.util.ArrayList;
import java.util.List;

@Data
public class NoteDTO {

    private Note note;

    private String authorName;

    private String imagePath;

    public NoteDTO(Note note) {
        this.note = note;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}


