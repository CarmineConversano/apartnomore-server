package org.apartnomore.server.repository;

import org.apartnomore.server.models.Notice;
import org.apartnomore.server.models.NoticeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Page<Notice> findAllByNoticeBoard(NoticeBoard noticeBoard, Pageable pageable);

}
