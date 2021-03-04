package uz.pdp.video.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.video.entity.Subject;
import uz.pdp.video.repository.SubjectRepository;

import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {
    @Autowired
    SubjectRepository subjectRepository;

    @RequestMapping(method = RequestMethod.POST)
    public String addSubject(@RequestBody Subject subject){
        boolean exist = subjectRepository.existsByName(subject.getName());
        if (exist) return "The subject already exists in database";
        subjectRepository.save(subject);
        return "Subject added";

    }
    @GetMapping
    public List<Subject> getSubjects(){
       return subjectRepository.findAll();
    }
}
