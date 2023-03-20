package ASESpaghettiCode.TravelNoteServer.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Note {
    @Id
    private String noteId;
    private String noteTitle;
    private String authorId;
    private String coverImage;
    private String date;
    private int duration;
    private double rating;
    private int expense;
    private int numTravelers;
    private String targetGroup;
    private String destination;
    private double[] coordinates;
    private String editorData;


    public Note(String noteTitle, String authorId, String coverImage, String date, int duration, double rating, int expense, int numTravelers, String targetGroup, String destination, double[] coordinates, String editorData) {
        this.noteTitle = noteTitle;
        this.authorId = authorId;
        this.coverImage = coverImage;
        this.date = date;
        this.duration = duration;
        this.rating = rating;
        this.expense = expense;
        this.numTravelers = numTravelers;
        this.targetGroup = targetGroup;
        this.destination = destination;
        this.coordinates = coordinates;
        this.editorData = editorData;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle(){
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle){
        this.noteTitle = noteTitle;
    }

    public String getAuthorId(){
        return authorId;
    }

    public void setAuthorId(String authorId){
        this.authorId = authorId;
    }

    public String getCoverImage(){
        return coverImage;
    }

    public void setCoverImage(String coverImage){
        this.coverImage = coverImage;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public int getDuration(){
        return duration;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }

    public double getRating(){
        return rating;
    }

    public void setRating(double rating){
        this.rating = rating;
    }

    public int getExpense(){
        return expense;
    }

    public void setExpense(int expense){
        this.expense = expense;
    }

    public int getNumTravelers(){
        return numTravelers;
    }

    public void setNumTravelers(int numTravelers){
        this.numTravelers = numTravelers;
    }

    public String getTargetGroup(){
        return targetGroup;
    }

    public void setTargetGroup(String targetGroup){
        this.targetGroup = targetGroup;
    }

    public String getDestination(){
        return destination;
    }

    public void setDestination(String destination){
        this.destination = destination;
    }

    public double[] getCoordinates(){
        return coordinates;
    }

    public void setCoordinates(double[] coordinates){
        this.coordinates = coordinates;
    }

    public String getEditorData(){
        return editorData;
    }

    public void setEditorData(String editorData){
        this.editorData = editorData;
    }
}
