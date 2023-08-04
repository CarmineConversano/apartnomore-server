package org.apartnomore.server.services;

import org.apartnomore.server.models.Community;
import org.apartnomore.server.models.User;
import org.apartnomore.server.models.UserCommunities;
import org.apartnomore.server.payload.response.MemberDetail;
import org.apartnomore.server.repository.UserCommunitiesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserCommunitiesServiceImpl {
    private final UserCommunitiesRepository userCommunitiesRepository;

    public UserCommunitiesServiceImpl(UserCommunitiesRepository userCommunitiesRepository) {
        this.userCommunitiesRepository = userCommunitiesRepository;
    }

    public UserCommunities addUserToCommunity(UserCommunities userCommunities) {
        return this.userCommunitiesRepository.save(userCommunities);
    }

    public Page<Community> findByUser(User user, Pageable pageable, String query) {
        return this.userCommunitiesRepository.findAllByUser(user, pageable, query);
    }

    public UserCommunities findByUserAndCommunity(User user, Community community) {
        return this.userCommunitiesRepository.findByUserAndCommunity(user, community);
    }

    public UserCommunities findByUserIdAndCommunity(User user, Community community) {
        return this.userCommunitiesRepository.findByUserIdAndCommunity(user, community);
    }

    public UserCommunities findByUserAndCommunityId(User user, Long communityId) {
        return this.userCommunitiesRepository.findByUserAndCommunityId(user, communityId);
    }

    public Page<MemberDetail> findAllByCommunity(Long communityId, Pageable pageable) {
        return this.userCommunitiesRepository.findAllWithCommunityIdComplete(communityId, pageable);
    }

    public UserCommunities findByUserIdAndCommunityId(Long memberId, Long communityId) {
        return this.userCommunitiesRepository.findByUserIdAndCommunityId(memberId, communityId);

    }
}
