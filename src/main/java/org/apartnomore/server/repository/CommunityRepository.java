package org.apartnomore.server.repository;

import org.apartnomore.server.models.Community;
import org.apartnomore.server.payload.response.CommunityLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    Optional<Community> findCommunityByName(String name);

    //    @Query("SELECT c, p\n" +
//            "FROM Community c\n" +
//            "    INNER JOIN UserCommunityPermissionsEntity p\n" +
//            "        ON c = p.community\n" +
//            "    INNER JOIN" +
//            "    (\n" +
//            "        SELECT community_id, MAX(permission_id) maxPermission\n" +
//            "        FROM user_community_permissions\n" +
//            "        GROUP BY community_id\n" +
//            "    ) b ON p.community_id = b.community_id AND\n" +
//            "            p.permission_id = maxPermission\n" +
//            "where c.is_public = true;")
    @Query("SELECT u from Community u where (u.isPublic = true) AND (u.description LIKE %:query% or u.name LIKE %:query% or u.accessLink LIKE %:query% or u.location LIKE %:query%)")
    Page<Community> findPublics(Pageable pageable, @Param("query") String query);

    @Query(value = "SELECT c.id, access_link as accessLink, is_public as isPublic, description, location, lat, lng, name, i.path as imagePath, 6371 * 2 * ASIN(SQRT(POWER(SIN((:startLat - c.lat) * pi() / 180 / 2), 2) + COS(:startLat * pi() / 180) * COS(c.lat * pi() / 180) * POWER(SIN((:startLng - c.lng) * pi() / 180 / 2), 2))) as distance FROM community c join images i on i.id = c.image_id where c.is_public = true and (c.lng between :lon1 and :lon2 and c.lat between :lat1 and :lat2)", nativeQuery = true)
    Page<CommunityLocation> findAllPublicsNearLocation(Pageable pageable, @Param("lon1") double lon1, @Param("lon2") double lon2,
                                                       @Param("lat1") double lat1, @Param("lat2") double lat2,
                                                       @Param("startLat") double startLat, @Param("startLng") double startLng);

    Community findByAccessLink(String accessLink);

    Optional<Community> findCommunityByAccessLink(String accessLink);
}
