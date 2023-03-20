package ASESpaghettiCode.TravelNoteServer.Repository;

import ASESpaghettiCode.TravelNoteServer.Model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note,String> {
    List<Note> findAllById(String userId);
}
