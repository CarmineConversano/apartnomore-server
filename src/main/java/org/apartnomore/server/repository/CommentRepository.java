package org.apartnomore.server.repository;

import org.apartnomore.server.models.Comment;
import org.apartnomore.server.models.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByNotice(Notice notice, Pageable pageable);
}
