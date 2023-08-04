package org.apartnomore.server.repository;

import org.apartnomore.server.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
