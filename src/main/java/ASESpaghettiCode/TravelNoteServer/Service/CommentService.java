package ASESpaghettiCode.TravelNoteServer.Service;

import ASESpaghettiCode.TravelNoteServer.Controller.RestTemplateErrorHandler;
import ASESpaghettiCode.TravelNoteServer.DTO.CommentPostDTO;
import ASESpaghettiCode.TravelNoteServer.Model.Comment;
import ASESpaghettiCode.TravelNoteServer.Model.Note;
import ASESpaghettiCode.TravelNoteServer.Model.User;
import ASESpaghettiCode.TravelNoteServer.Repository.CommentRepository;
import ASESpaghettiCode.TravelNoteServer.Repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentService {

    @Value("${UserServerLocation}")
    private String UserServerLocation;

    @Autowired
    private RestTemplate restTemplate = new RestTemplateBuilder()
            .errorHandler(new RestTemplateErrorHandler())
            .build();

    private final CommentRepository commentRepository;
    private final NoteRepository noteRepository;

    @Autowired
    public CommentService(@Qualifier("commentRepository")CommentRepository commentRepository,@Qualifier("noteRepository")NoteRepository noteRepository) {
        this.commentRepository = commentRepository;
        this.noteRepository = noteRepository;
    }

    public Comment createComment(String targetNoteId, CommentPostDTO commentPostDTO) {
        String authorId = commentPostDTO.getCommentAuthorId();
        User user = restTemplate.getForObject(UserServerLocation + "/users/" + authorId, User.class);
        Comment newComment = new Comment(authorId,user.getUsername(),user.getImageLink(),targetNoteId,commentPostDTO.getCommentText());
        // add comment Id to comment list of note
        Optional<Note> targetNote =  noteRepository.findById(targetNoteId);
        if (targetNote.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The travel note is not found!");
        }
        commentRepository.save(newComment);
        targetNote.get().addComment(newComment.getCommentId());
        noteRepository.save(targetNote.get());
        return commentRepository.save(newComment);
    }

    public List<Comment> findCommentsByNoteId(String noteId) {
        Optional<Note> targetNote = noteRepository.findById(noteId);
        if (targetNote.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note is not found!");
        }
        List<String> commentIdList = targetNote.get().getCommentList();
//        List<Comment> commentList = new ArrayList<>();
//        for (String id : commentIdList){
//            Optional<Comment> comment = commentRepository.findById(id);
//            comment.ifPresent(commentList::add);
//        }
        return commentIdList.stream()
                .map(commentRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public void deleteComment(String commentId, String userId) {
        Optional<Comment> targetComment = commentRepository.findById(commentId);
        if (targetComment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment is not found!");
        } else if (!Objects.equals(userId, targetComment.get().getCommentAuthorId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "unauthorized delete!");
        }
        else {
            // delete Comment in commentList of the note
            Optional<Note> targetNote = noteRepository.findById(targetComment.get().getTargetNoteId());
            if (targetNote.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note is not found!");
            }else{
                targetNote.get().removeComment(commentId);
                noteRepository.save(targetNote.get());
            }
            commentRepository.delete(targetComment.get());
        }
    }

    public void updateComment(String userId, String commentId, CommentPostDTO commentPostDTO) {
        Optional<Comment> targetComment = commentRepository.findById(commentId);
        if (targetComment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment is not found!");
        } else if (!Objects.equals(userId, targetComment.get().getCommentAuthorId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized edit!");
        }
        else {
            targetComment.get().setCommentText(commentPostDTO.getCommentText());
            commentRepository.save(targetComment.get());
        }
    }
}
