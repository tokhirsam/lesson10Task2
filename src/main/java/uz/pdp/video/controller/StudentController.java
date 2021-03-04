package uz.pdp.video.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.video.entity.Address;
import uz.pdp.video.entity.Group;
import uz.pdp.video.entity.Student;
import uz.pdp.video.entity.Subject;
import uz.pdp.video.payload.StudentDto;
import uz.pdp.video.repository.AddressRepository;
import uz.pdp.video.repository.GroupRepository;
import uz.pdp.video.repository.StudentRepository;
import uz.pdp.video.repository.SubjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    SubjectRepository subjectRepository;
    //1. Vazirlik

    @GetMapping("/forMinistry")
    public Page<Student> getListByPageForMinistry(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findAll(pageable);
    }

    //2. Universitet
    @GetMapping("/forUniversity/{universityId}")
    public Page<Student> getListByPageForUniversity(@PathVariable Integer universityId, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findAllByGroup_Faculty_UniversityId(universityId, pageable);
    }

    //3. dekanat
    @GetMapping("/forFaculty/{facultyId}")
    public Page<Student> getListByPageForFaculty(@PathVariable Integer facultyId, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findAllByGroup_FacultyId(facultyId, pageable);
    }

    //4. group owner
    @GetMapping("/forGroup/{groupId}")
    public Page<Student> getListByPageForGroup(@PathVariable Integer groupId, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findAllByGroupId(groupId, pageable);
    }

    @PostMapping
    public String addStudent(@RequestBody StudentDto studentDto) {
        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        Optional<Address> optionalAddress = addressRepository.findById(studentDto.getAddressId());
        if (!optionalAddress.isPresent()) return "Address ID not found!";
        student.setAddress(optionalAddress.get());
        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if (!optionalGroup.isPresent()) return "Group ID not found!";
        student.setGroup(optionalGroup.get());

        List<Integer> dtoSubjectsId = studentDto.getSubjectsId();
        List<Subject> subjectList = new ArrayList<>();
        for (Integer i : dtoSubjectsId) {
            Optional<Subject> subjectOptional = subjectRepository.findById(i);
            if (!subjectOptional.isPresent()) return "Subject not found";
            subjectList.add(subjectOptional.get());
        }
        student.setSubjectList(subjectList);
        studentRepository.save(student);
        return "Student added successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        studentRepository.deleteById(id);
        return "Student deleted";
    }

    @PutMapping("/{id}")
    public String editStudent(@PathVariable Integer id, @RequestBody StudentDto studentDto) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (!optionalStudent.isPresent()) return "Student not found";
        Student student = optionalStudent.get();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        Optional<Address> optionalAddress = addressRepository.findById(studentDto.getAddressId());
        if (!optionalAddress.isPresent()) return "Address ID not found!";
        student.setAddress(optionalAddress.get());
        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if (!optionalGroup.isPresent()) return "Group ID not found!";
        student.setGroup(optionalGroup.get());

        List<Integer> dtoSubjectsId = studentDto.getSubjectsId();
        List<Subject> subjectList = new ArrayList<>();
        for (Integer i : dtoSubjectsId) {
            Optional<Subject> subjectOptional = subjectRepository.findById(i);
            if (!subjectOptional.isPresent()) return "Subject not found";
            subjectList.add(subjectOptional.get());
        }
        student.setSubjectList(subjectList);
        studentRepository.save(student);
        return "Student edited successfully";
    }

}
