package ASESpaghettiCode.TravelNoteServer.Repository;

import ASESpaghettiCode.TravelNoteServer.Model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
//    public List<Note> findAll();
    public Optional<Note> findById(String noteId);
}
