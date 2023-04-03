package ASESpaghettiCode.TravelNoteServer.Repository;

import ASESpaghettiCode.TravelNoteServer.Model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends MongoRepository<Comment,String> {

}
