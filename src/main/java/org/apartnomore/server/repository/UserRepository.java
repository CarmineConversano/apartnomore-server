package org.apartnomore.server.repository;

import org.apartnomore.server.models.User;
import org.apartnomore.server.payload.response.PrivateUserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(value = "SELECT u.id, u.username, u.email, up.name,  up.surname, up.avatar_url as avatarUrl, up.phone_number as phoneNumber from users u join user_profile up on u.id = up.user_id join user_roles ur on u.id = ur.user_id where u.username = :username",
            nativeQuery = true)
    Optional<PrivateUserData> findPrivateDataByUsername(@Param("username") String username);
}
