package ASESpaghettiCode.TravelNoteServer.Model;

public class Note {
    private Integer noteId;
    private String title;

    public Note(Integer noteId, String title) {
        this.noteId = noteId;
        this.title = title;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
