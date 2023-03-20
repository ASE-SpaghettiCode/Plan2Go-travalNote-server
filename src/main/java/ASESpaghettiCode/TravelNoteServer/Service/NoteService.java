package ASESpaghettiCode.TravelNoteServer.Service;

import ASESpaghettiCode.TravelNoteServer.Model.Note;
import ASESpaghettiCode.TravelNoteServer.Repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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
        if (targetNote.isEmpty() || userId != targetNote.get().getUserId()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            noteRepository.delete(noteRepository.findById(noteId).get());
        }
    }
}
