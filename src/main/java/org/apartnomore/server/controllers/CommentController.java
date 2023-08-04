package org.apartnomore.server.controllers;

import org.apartnomore.server.exceptions.UnauthorizedException;
import org.apartnomore.server.models.Comment;
import org.apartnomore.server.models.User;
import org.apartnomore.server.payload.request.CreateComment;
import org.apartnomore.server.payload.response.MessageResponse;
import org.apartnomore.server.services.CommentServiceImpl;
import org.apartnomore.server.services.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentServiceImpl commentService;
    private final UserServiceImpl userService;

    public CommentController(CommentServiceImpl commentService, UserServiceImpl userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping("/{noticeId}")
    public Page<Comment> findAllByNotice(@PathVariable Long noticeId, Pageable pageable, Principal principal) {
        try {
            User loggedUser = userService.findByLogin(principal.getName());
            return this.commentService.findAllByNotice(noticeId, loggedUser, pageable);
        } catch (UnauthorizedException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        } catch (Exception exception) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
        }
    }

    @PostMapping("/{noticeId}/create")
    public ResponseEntity<?> create(@PathVariable Long noticeId, @Valid @RequestBody CreateComment createComment, Principal principal) {
        try {
            User loggedUser = userService.findByLogin(principal.getName());
            Comment comment = this.commentService.createComment(noticeId, loggedUser, createComment);
            return ResponseEntity.ok(comment);
        } catch (Exception exception) {
            return ResponseEntity
                    .status(500)
                    .body(new MessageResponse("Error: ".concat(exception.getMessage())));
        }
    }
}
