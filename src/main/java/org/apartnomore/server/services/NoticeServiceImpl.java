package org.apartnomore.server.services;

import org.apartnomore.server.exceptions.NoticeBoardNotFoundException;
import org.apartnomore.server.exceptions.NoticeNotFoundException;
import org.apartnomore.server.exceptions.UnauthorizedException;
import org.apartnomore.server.models.*;
import org.apartnomore.server.payload.request.CreateNotice;
import org.apartnomore.server.repository.NoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeBoardServiceImpl noticeBoardService;
    private final UserCommunitiesServiceImpl userCommunitiesService;
    private final UserCommunityPermissionEntityServiceImpl userCommunityPermissionEntityService;
    private final NoticeRepository noticeRepository;

    public NoticeServiceImpl(NoticeBoardServiceImpl noticeBoardService, UserCommunitiesServiceImpl userCommunitiesService, UserCommunityPermissionEntityServiceImpl userCommunityPermissionEntityService, NoticeRepository noticeRepository) {
        this.noticeBoardService = noticeBoardService;
        this.userCommunitiesService = userCommunitiesService;
        this.userCommunityPermissionEntityService = userCommunityPermissionEntityService;
        this.noticeRepository = noticeRepository;
    }

    private boolean canWriteNotice(User user, Community community) {
        UserCommunities userCommunity = this.userCommunitiesService.findByUserAndCommunity(user, community);

        Set<UserCommunityPermissionsEntity> userPermissions = this.userCommunityPermissionEntityService.findByUserCommunityPair(userCommunity);
        return userPermissions.stream().filter(permission ->
                EPermission.USE_CREATOR == permission.getPermission().getName() ||
                        EPermission.USE_WRITE == permission.getPermission().getName()
        ).findFirst().orElse(null) != null;
    }

    public Notice findById(Long noticeId) throws NoticeNotFoundException {
        Optional<Notice> notice = this.noticeRepository.findById(noticeId);
        if (notice.isEmpty()) {
            throw new NoticeNotFoundException("Notice not found with provided id");
        } else {
            return notice.get();
        }
    }

    private boolean canReadNotice(User user, Community community) {
        UserCommunities userCommunity = this.userCommunitiesService.findByUserAndCommunity(user, community);
        Set<UserCommunityPermissionsEntity> userPermissions = this.userCommunityPermissionEntityService.findByUserCommunityPair(userCommunity);
        return userPermissions.stream().filter(permission ->
                EPermission.USE_CREATOR == permission.getPermission().getName() ||
                        EPermission.USE_READ == permission.getPermission().getName()
        ).findFirst().orElse(null) != null;
    }


    public Page<Notice> findAllByBoard(Long boardId, User loggedUser, Pageable pageable) throws UnauthorizedException, NoticeBoardNotFoundException {
        NoticeBoard noticeBoard = this.noticeBoardService.findById(boardId);
        if (canReadNotice(loggedUser, noticeBoard.getCommunity())) {
            return this.noticeRepository.findAllByNoticeBoard(noticeBoard, pageable);
        } else {
            throw new UnauthorizedException("Cannot view notices");
        }
    }


    @Override
    public Notice createNotice(Long boardId, User loggedUser, CreateNotice createNotice) throws NoticeBoardNotFoundException, UnauthorizedException {
        NoticeBoard noticeBoard = this.noticeBoardService.findById(boardId);
        if (canWriteNotice(loggedUser, noticeBoard.getCommunity())) {
            Notice notice = new Notice();
            notice.setNoticeBoard(noticeBoard);
            notice.setDescription(createNotice.getDescription());
            notice.setTitle(createNotice.getTitle());
            notice.setUserWriter(loggedUser);
            return this.noticeRepository.save(notice);
        } else {
            throw new UnauthorizedException("Non hai i permessi per scrivere!");
        }
    }
}
