package org.apartnomore.server.services;

import org.apartnomore.server.exceptions.NoticeBoardNotFoundException;
import org.apartnomore.server.exceptions.UnauthorizedException;
import org.apartnomore.server.models.Notice;
import org.apartnomore.server.models.User;
import org.apartnomore.server.payload.request.CreateNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeService {
    Page<Notice> findAllByBoard(Long boardId, User loggedUser, Pageable pageable) throws UnauthorizedException, NoticeBoardNotFoundException;

    Notice createNotice(Long boardId, User loggedUser, CreateNotice createNotice) throws NoticeBoardNotFoundException, UnauthorizedException;
}
