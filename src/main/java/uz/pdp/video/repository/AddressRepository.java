package uz.pdp.video.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.video.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
