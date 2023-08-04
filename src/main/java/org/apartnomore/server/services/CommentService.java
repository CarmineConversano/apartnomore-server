package org.apartnomore.server.services;

import org.apartnomore.server.exceptions.NoticeBoardNotFoundException;
import org.apartnomore.server.exceptions.NoticeNotFoundException;
import org.apartnomore.server.exceptions.UnauthorizedException;
import org.apartnomore.server.models.Comment;
import org.apartnomore.server.models.User;
import org.apartnomore.server.payload.request.CreateComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Page<Comment> findAllByNotice(Long noticeId, User loggedUser, Pageable pageable) throws UnauthorizedException, NoticeBoardNotFoundException, NoticeNotFoundException;

    Comment createComment(Long noticeId, User loggedUser, CreateComment createComment) throws NoticeBoardNotFoundException, UnauthorizedException, NoticeNotFoundException;
}
