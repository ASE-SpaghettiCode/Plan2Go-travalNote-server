package ASESpaghettiCode.TravelNoteServer.Controller;

import ASESpaghettiCode.TravelNoteServer.DTO.CommentPostDTO;
import ASESpaghettiCode.TravelNoteServer.Model.Comment;
import ASESpaghettiCode.TravelNoteServer.Model.Note;
import ASESpaghettiCode.TravelNoteServer.Repository.CommentRepository;
import ASESpaghettiCode.TravelNoteServer.Repository.NoteRepository;
import ASESpaghettiCode.TravelNoteServer.Service.CommentService;
import ASESpaghettiCode.TravelNoteServer.Service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {
    private CommentService commentService;

    CommentController(CommentService commentService){
        this.commentService = commentService;
    }


    @GetMapping("/notes/{noteId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<Comment> getCommentsByNoteId(@PathVariable String noteId){
        return commentService.findCommentsByNoteId(noteId);
    }

    @PostMapping("/notes/{noteId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createCommentForAGivenNote(@PathVariable String noteId, @RequestBody CommentPostDTO commentPostDTO){
        return commentService.createComment(noteId,commentPostDTO);
    }

    @DeleteMapping("/users/{userId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable String userId, @PathVariable String commentId) {
        commentService.deleteComment(commentId, userId);
    }

    @PutMapping("/users/{userId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateComment(@PathVariable String userId, @PathVariable String commentId, @RequestBody CommentPostDTO commentPostDTO) {
        commentService.updateComment(userId, commentId, commentPostDTO);
    }
}
