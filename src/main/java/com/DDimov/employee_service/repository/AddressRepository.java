package com.DDimov.employee_service.repository;

import com.DDimov.employee_service.entity.Address;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
  Optional<Address> findByStreetAndStreetNumber(String street, String streetNumber);
}
