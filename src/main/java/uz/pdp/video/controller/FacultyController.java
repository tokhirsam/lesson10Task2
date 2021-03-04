package uz.pdp.video.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.video.entity.Faculty;
import uz.pdp.video.entity.University;
import uz.pdp.video.payload.FacultyDto;
import uz.pdp.video.repository.FacultyRepository;
import uz.pdp.video.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    UniversityRepository universityRepository;

    @PostMapping
    public String addFaculty(@RequestBody FacultyDto facultyDto) {
        boolean exists = facultyRepository.existsByNameAndUniversityId(facultyDto.getName(), facultyDto.getUniversityId());
        if (exists) return "The university already has such faculty";
        Faculty faculty = new Faculty();
        faculty.setName(facultyDto.getName());
        Optional<University> university = universityRepository.findById(facultyDto.getUniversityId());
        if (!university.isPresent()) return "University not found";
        faculty.setUniversity(university.get());
        facultyRepository.save(faculty);
        return "Faculty added";

    }

    @GetMapping("/byUniversityId/{universityId}")
    public List<Faculty> getFacultiesByUniversityId(@PathVariable Integer universityId){
        return facultyRepository.findAllByUniversityId(universityId);
    }
    @DeleteMapping("/{id}")
    public String deleteFaculty(@PathVariable Integer id){
        try{
            facultyRepository.deleteById(id);
            return "Faculty deleted";
        }catch (Exception e){
            return "Faculty not deleted. Error: "+e.getMessage();
        }


    }
    @PutMapping("/{id}")
    public String editFaculty(@PathVariable Integer id, @RequestBody FacultyDto facultyDto){
        Optional<Faculty> facultyOptional = facultyRepository.findById(id);
        if (facultyOptional.isPresent()){
            Faculty faculty = facultyOptional.get();
            faculty.setName(facultyDto.getName());
            Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getUniversityId());
            if (!optionalUniversity.isPresent()) return "University not found!";
            faculty.setUniversity(optionalUniversity.get());
            facultyRepository.save(faculty);
            return "Faculty edited successfully";
        }
        return "Faculty not found!";
    }

}
