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

//    List<Note> findAll(Sort sort);

    Optional<Note> findById(String noteId);

    @Query("{'authorId': {$in: ?0}}")
    List<Note>findByUserIdListInOrderByCreatedDateAsc(List<String> followingUserId, Sort sort);
}
