package uz.pdp.video.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.video.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    boolean existsByName(String name);
}
