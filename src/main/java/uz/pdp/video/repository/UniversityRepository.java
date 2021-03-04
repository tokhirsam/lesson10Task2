package uz.pdp.video.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.video.entity.University;
@Repository
public interface UniversityRepository extends JpaRepository<University,Integer> {

}
