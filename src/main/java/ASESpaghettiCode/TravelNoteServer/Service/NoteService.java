package ASESpaghettiCode.TravelNoteServer.Service;

import ASESpaghettiCode.TravelNoteServer.Model.Note;
import ASESpaghettiCode.TravelNoteServer.Model.User;
import ASESpaghettiCode.TravelNoteServer.Repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NoteService {



    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void save(Note note){
        noteRepository.save(note);
    }

    public List<Note> findAllNotes() {
        return this.noteRepository.findAll();
    }


    public List<Note> findNotesOfFollowees(List<String> followingUserId) {
        Optional<List<Note>> sortedList = Optional.ofNullable(noteRepository.findByUserIdListInOrderByCreatedDateAsc(followingUserId, Sort.by(Sort.Direction.ASC, "createdTime")));
        if (sortedList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User haven't follow anyone!");
        }
        return sortedList.get();
    }

    public Note createNote(Note newNote){
        List<String> initialLikedUsers = new ArrayList<>();
        newNote.setLikedUsers(initialLikedUsers);
        return noteRepository.save(newNote);
    }

    public void deleteNote(String noteId, String userId) {
        Optional<Note> targetNote = noteRepository.findById(noteId);
        if (targetNote.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note is not found!");
        } else if (!Objects.equals(userId, targetNote.get().getAuthorId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot delete this note!");
        }
        else {
            noteRepository.delete(noteRepository.findById(noteId).get());
        }
    }

    public List<Note> findNotesByUserId(String userId) {
        List<Note> listOfNotes = new ArrayList<>();
        for (Note note : noteRepository.findAll()) {
            if (Objects.equals(note.getAuthorId(), userId)){
                listOfNotes.add(note);
            }
        }
        return listOfNotes;
    }
    public Note findNoteById(String noteId) {
        Optional<Note> targetNote = noteRepository.findById(noteId);
        if (targetNote.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note is not found!");
        }
        return targetNote.get();
    }

    public void updateNote(String noteId, String userId, Note note) {
        Optional<Note> targetNote = noteRepository.findById(noteId);
        if (targetNote.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note is not found!");
        } else if (!Objects.equals(userId, targetNote.get().getAuthorId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot edit this note!");
        }
        else {
            targetNote.get().setNoteTitle(note.getNoteTitle());
            targetNote.get().setCoordinates(note.getCoordinates());
            targetNote.get().setCoverImage(note.getCoverImage());
            targetNote.get().setDate(note.getDate());
            targetNote.get().setDuration(note.getDuration());
            targetNote.get().setDestination(note.getDestination());
            targetNote.get().setExpense(note.getExpense());
            targetNote.get().setNumTravelers(note.getNumTravelers());
            targetNote.get().setRating(note.getRating());
            targetNote.get().setTargetGroup(note.getTargetGroup());
            targetNote.get().setEditorData(note.getEditorData());
            noteRepository.save(targetNote.get());
        }
    }

    public void userLikesNote(String userId, String noteId) {
        Optional<Note> targetNote = noteRepository.findById(noteId);
        if (targetNote.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The travel note is not found!");
        }
        targetNote.get().addLikedUsers(userId);
        noteRepository.save(targetNote.get());
    }

    public void userUnlikesNote(String userId, String noteId) {
        Optional<Note> targetNote = noteRepository.findById(noteId);
        if (targetNote.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The travel note is not found!");
        }
        if (!targetNote.get().getLikedUsers().contains(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The user didn't like this travel note");
        }
        targetNote.get().removeLikedUsers(userId);
        noteRepository.save(targetNote.get());
    }
}
