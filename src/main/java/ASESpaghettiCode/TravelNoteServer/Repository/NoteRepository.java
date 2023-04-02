package ASESpaghettiCode.TravelNoteServer.Repository;

import ASESpaghettiCode.TravelNoteServer.Model.Note;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
//    public List<Note> findAll(Sort sort);
    public Optional<Note> findById(String noteId);

    @Query("{'authorId': {$in: ?0}}")
    public List<Note>findByUserIdListInOrderByCreatedDateAsc(List<String> followingUserId, Sort sort);
}
