package org.apartnomore.server.controllers;

import org.apartnomore.server.exceptions.UnauthorizedException;
import org.apartnomore.server.models.Notice;
import org.apartnomore.server.models.User;
import org.apartnomore.server.payload.request.CreateNotice;
import org.apartnomore.server.payload.response.MessageResponse;
import org.apartnomore.server.services.NoticeServiceImpl;
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
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeServiceImpl noticeService;
    private final UserServiceImpl userService;

    public NoticeController(NoticeServiceImpl noticeService, UserServiceImpl userService) {
        this.noticeService = noticeService;
        this.userService = userService;
    }

    @GetMapping("/{boardId}")
    public Page<Notice> findAllByBoard(@PathVariable Long boardId, Pageable pageable, Principal principal) {
        try {
            User loggedUser = userService.findByLogin(principal.getName());
            return this.noticeService.findAllByBoard(boardId, loggedUser, pageable);
        } catch (UnauthorizedException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        } catch (Exception exception) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
        }
    }

    @PostMapping("/{boardId}/create")
    public ResponseEntity<?> create(@PathVariable Long boardId, @Valid @RequestBody CreateNotice createNotice, Principal principal) {
        try {
            User loggedUser = userService.findByLogin(principal.getName());
            Notice notice = this.noticeService.createNotice(boardId, loggedUser, createNotice);
            return ResponseEntity.ok(notice);
        } catch (Exception exception) {
            return ResponseEntity
                    .status(500)
                    .body(new MessageResponse("Error: ".concat(exception.getMessage())));
        }
    }
}
