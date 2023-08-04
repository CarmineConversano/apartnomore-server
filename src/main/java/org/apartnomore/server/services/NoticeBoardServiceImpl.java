package org.apartnomore.server.services;

import org.apartnomore.server.exceptions.CommunityNotFoundException;
import org.apartnomore.server.exceptions.NoticeBoardNotFoundException;
import org.apartnomore.server.exceptions.UnauthorizedException;
import org.apartnomore.server.models.*;
import org.apartnomore.server.payload.request.CreateNoticeBoard;
import org.apartnomore.server.repository.NoticeBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class NoticeBoardServiceImpl implements NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;
    private final UserCommunitiesServiceImpl userCommunitiesService;
    private final UserCommunityPermissionEntityServiceImpl userCommunityPermissionEntityService;
    private final CommunityServiceImpl communityService;

    public NoticeBoardServiceImpl(NoticeBoardRepository noticeBoardRepository, UserCommunitiesServiceImpl userCommunitiesService, UserCommunityPermissionEntityServiceImpl userCommunityPermissionEntityService, CommunityServiceImpl communityService) {
        this.noticeBoardRepository = noticeBoardRepository;
        this.userCommunitiesService = userCommunitiesService;
        this.userCommunityPermissionEntityService = userCommunityPermissionEntityService;
        this.communityService = communityService;
    }


    private Set<UserCommunityPermissionsEntity> findPermissions(User user, Long communityId) {
        UserCommunities userCommunity = this.userCommunitiesService.findByUserAndCommunityId(user, communityId);

        return this.userCommunityPermissionEntityService.findByUserCommunityPair(userCommunity);
    }


    private boolean canViewBoards(User user, Long communityId) {
        UserCommunities userCommunity = this.userCommunitiesService.findByUserAndCommunityId(user, communityId);

        Set<UserCommunityPermissionsEntity> userPermissions = this.userCommunityPermissionEntityService.findByUserCommunityPair(userCommunity);
        return userPermissions.stream().filter(permission ->
                EPermission.USE_CREATOR == permission.getPermission().getName() ||
                        EPermission.USE_READ == permission.getPermission().getName()
        ).findFirst().orElse(null) != null;
    }


    private boolean canCreateBoard(User user, Long communityId) {
        UserCommunities userCommunity = this.userCommunitiesService.findByUserAndCommunityId(user, communityId);

        Set<UserCommunityPermissionsEntity> userPermissions = this.userCommunityPermissionEntityService.findByUserCommunityPair(userCommunity);
        return userPermissions.stream().filter(permission ->
                EPermission.USE_CREATOR == permission.getPermission().getName() ||
                        EPermission.USE_READ == permission.getPermission().getName()
        ).findFirst().orElse(null) != null;
    }

    @Override
    public Page<NoticeBoard> findAllByCommunity(Long communityId, User loggedUser, Pageable pageable) throws UnauthorizedException {
        if (canViewBoards(loggedUser, communityId)) {
            return this.noticeBoardRepository.findAllByCommunityId(communityId, pageable);
        } else {
            throw new UnauthorizedException("Cannot view boards");
        }
    }

    @Override
    public NoticeBoard createNoticeBoard(Long communityId, User user, CreateNoticeBoard createNoticeBoard) throws UnauthorizedException, CommunityNotFoundException {
        if (canCreateBoard(user, communityId)) {
            NoticeBoard noticeBoard = new NoticeBoard();
            noticeBoard.setCommunity(this.communityService.findById(communityId));
            noticeBoard.setName(createNoticeBoard.getName());
            return this.noticeBoardRepository.save(noticeBoard);
        } else {
            throw new UnauthorizedException("Cannot view boards");
        }
    }

    @Override
    public NoticeBoard findById(Long boardId) throws NoticeBoardNotFoundException {
        Optional<NoticeBoard> notice = this.noticeBoardRepository.findById(boardId);
        if (notice.isEmpty()) {
            throw new NoticeBoardNotFoundException("Notice not found with provided id");
        } else {
            return notice.get();
        }
    }
}
