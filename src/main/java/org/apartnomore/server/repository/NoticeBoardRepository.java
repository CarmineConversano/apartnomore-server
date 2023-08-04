package org.apartnomore.server.repository;

import org.apartnomore.server.models.NoticeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {
    Page<NoticeBoard> findAllByCommunityId(Long communityId, Pageable pageable);

}
