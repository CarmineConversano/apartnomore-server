package org.apartnomore.server.repository;

import org.apartnomore.server.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByUserId(Long userId);
}
