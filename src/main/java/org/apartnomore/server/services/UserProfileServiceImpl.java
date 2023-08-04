package org.apartnomore.server.services;

import org.apartnomore.server.models.UserProfile;
import org.apartnomore.server.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public UserProfile save(UserProfile userProfile) {
        return this.userProfileRepository.save(userProfile);
    }
}
