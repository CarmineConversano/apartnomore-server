package org.apartnomore.server.services;

import org.apartnomore.server.exceptions.CommunityNotFoundException;
import org.apartnomore.server.exceptions.NoticeBoardNotFoundException;
import org.apartnomore.server.exceptions.UnauthorizedException;
import org.apartnomore.server.models.NoticeBoard;
import org.apartnomore.server.models.User;
import org.apartnomore.server.payload.request.CreateNoticeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeBoardService {
    Page<NoticeBoard> findAllByCommunity(Long communityId, User loggedUser, Pageable pageable) throws UnauthorizedException;

    NoticeBoard createNoticeBoard(Long communityId, User user, CreateNoticeBoard createNoticeBoard) throws UnauthorizedException, CommunityNotFoundException;

    NoticeBoard findById(Long boardId) throws NoticeBoardNotFoundException;
}
