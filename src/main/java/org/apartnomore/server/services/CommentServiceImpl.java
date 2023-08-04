package org.apartnomore.server.services;

import org.apartnomore.server.exceptions.NoticeNotFoundException;
import org.apartnomore.server.exceptions.UnauthorizedException;
import org.apartnomore.server.models.*;
import org.apartnomore.server.payload.request.CreateComment;
import org.apartnomore.server.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {

    private final NoticeServiceImpl noticeService;
    private final UserCommunitiesServiceImpl userCommunitiesService;
    private final UserCommunityPermissionEntityServiceImpl userCommunityPermissionEntityService;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(NoticeServiceImpl noticeService, UserCommunitiesServiceImpl userCommunitiesService, UserCommunityPermissionEntityServiceImpl userCommunityPermissionEntityService, CommentRepository commentRepository) {
        this.noticeService = noticeService;
        this.userCommunitiesService = userCommunitiesService;
        this.userCommunityPermissionEntityService = userCommunityPermissionEntityService;
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment createComment(Long noticeId, User loggedUser, CreateComment createComment) throws UnauthorizedException, NoticeNotFoundException {
        Notice notice = this.noticeService.findById(noticeId);
        if (canWriteNotice(loggedUser, notice.getNoticeBoard().getCommunity())) {
            Comment comment = new Comment();
            comment.setNotice(notice);
            comment.setUser(loggedUser);
            comment.setText(createComment.getText());
            return this.commentRepository.save(comment);
        } else {
            throw new UnauthorizedException("Non hai i permessi per commentare!");
        }
    }

    private boolean canWriteNotice(User user, Community community) {
        UserCommunities userCommunity = this.userCommunitiesService.findByUserAndCommunity(user, community);

        Set<UserCommunityPermissionsEntity> userPermissions = this.userCommunityPermissionEntityService.findByUserCommunityPair(userCommunity);
        return userPermissions.stream().filter(permission ->
                EPermission.USE_CREATOR == permission.getPermission().getName() ||
                        EPermission.USE_WRITE == permission.getPermission().getName()
        ).findFirst().orElse(null) != null;
    }

    private boolean canReadNotice(User user, Community community) {
        UserCommunities userCommunity = this.userCommunitiesService.findByUserAndCommunity(user, community);
        Set<UserCommunityPermissionsEntity> userPermissions = this.userCommunityPermissionEntityService.findByUserCommunityPair(userCommunity);
        return userPermissions.stream().filter(permission ->
                EPermission.USE_CREATOR == permission.getPermission().getName() ||
                        EPermission.USE_READ == permission.getPermission().getName()
        ).findFirst().orElse(null) != null;
    }


    public Page<Comment> findAllByNotice(Long noticeId, User loggedUser, Pageable pageable) throws UnauthorizedException, NoticeNotFoundException {
        Notice notice = this.noticeService.findById(noticeId);
        if (canReadNotice(loggedUser, notice.getNoticeBoard().getCommunity())) {
            return this.commentRepository.findAllByNotice(notice, pageable);
        } else {
            throw new UnauthorizedException("Cannot view comments");
        }
    }
}
