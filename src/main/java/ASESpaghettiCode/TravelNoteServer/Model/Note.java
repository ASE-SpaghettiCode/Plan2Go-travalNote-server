package ASESpaghettiCode.TravelNoteServer.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mongodb.lang.Nullable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Note {
    @Id
    private String  noteId;
    private String title;
    private String userId;
    private String content;
    private Double latitude;
    private Double longitude;
    private List<String> picURLs;

    public Note(String title, String userId, String content, Double latitude, Double longitude, List<String> picURLs) {
        this.title = title;
        this.userId = userId;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.picURLs = picURLs;
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

    public List<String> getPicURLs() {
        return picURLs;
    }

    public void setPicURLs(List<String> urls) {
        this.picURLs = urls;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
