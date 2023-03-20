package ASESpaghettiCode.TravelNoteServer.Repository;

import ASESpaghettiCode.TravelNoteServer.Model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends MongoRepository<Note,String> {
}
