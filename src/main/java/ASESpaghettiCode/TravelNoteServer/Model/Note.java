package ASESpaghettiCode.TravelNoteServer.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Note {
    @Id
    private String  noteId;
    private String title;
    private String content;
    private Double latitude;
    private Double longitude;

    public Note(String title, String content, Double latitude, Double longitude) {
        this.title = title;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
