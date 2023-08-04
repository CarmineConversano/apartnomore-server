package org.apartnomore.server.controllers;

import org.apartnomore.server.exceptions.UnauthorizedException;
import org.apartnomore.server.models.NoticeBoard;
import org.apartnomore.server.models.User;
import org.apartnomore.server.payload.request.CreateNoticeBoard;
import org.apartnomore.server.payload.response.MessageResponse;
import org.apartnomore.server.services.NoticeBoardServiceImpl;
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
@RequestMapping("/api/noticeboard")
public class NoticeBoardController {

    private final NoticeBoardServiceImpl noticeBoardService;
    private final UserServiceImpl userService;

    public NoticeBoardController(NoticeBoardServiceImpl noticeBoardService, UserServiceImpl userService) {
        this.noticeBoardService = noticeBoardService;
        this.userService = userService;
    }

    @GetMapping("/{communityId}")
    public Page<NoticeBoard> findAllByCommunity(@PathVariable Long communityId, Pageable pageable, Principal principal) {
        try {
            User loggedUser = userService.findByLogin(principal.getName());
            return this.noticeBoardService.findAllByCommunity(communityId, loggedUser, pageable);
        } catch (UnauthorizedException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        } catch (Exception exception) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
        }
    }

    @PostMapping("/{communityId}/create")
    public ResponseEntity<?> createBoard(@PathVariable Long communityId, @Valid @RequestBody CreateNoticeBoard createNoticeBoard, Principal principal) {
        try {
            User loggedUser = userService.findByLogin(principal.getName());
            NoticeBoard noticeBoard = this.noticeBoardService.createNoticeBoard(communityId, loggedUser, createNoticeBoard);
            return ResponseEntity.ok(noticeBoard);
        } catch (Exception exception) {
            return ResponseEntity
                    .status(500)
                    .body(new MessageResponse("Error: ".concat(exception.getMessage())));
        }
    }
}
