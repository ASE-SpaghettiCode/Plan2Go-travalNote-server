package ASESpaghettiCode.TravelNoteServer.Service;

import ASESpaghettiCode.TravelNoteServer.Model.Note;
import ASESpaghettiCode.TravelNoteServer.Repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public void save(Note note){
        noteRepository.save(note);
    }

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public Note createNote(Note newNote){
        return noteRepository.save(newNote);
    }

    public void deleteNote(String noteId, String userId) {
        Optional<Note> targetNote = noteRepository.findById(noteId);
        if (targetNote.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note is not found!");
        } else if (!Objects.equals(userId, targetNote.get().getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot delete this note!");
        }
        else {
            noteRepository.delete(noteRepository.findById(noteId).get());
        }
    }

    public List<Note> findNotesById(String userId) {
        List<Note> listOfNotes = new ArrayList<>();
        for (Note note : noteRepository.findAll()) {
            if (Objects.equals(note.getNoteId(), userId)){
                listOfNotes.add(note);
            }
        }
        return listOfNotes;
    }
}
