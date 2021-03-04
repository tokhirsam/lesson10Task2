package uz.pdp.video.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.video.entity.Address;
import uz.pdp.video.entity.University;
import uz.pdp.video.payload.UniversityDto;
import uz.pdp.video.repository.AddressRepository;
import uz.pdp.video.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class UniversityController {
    @Autowired
    UniversityRepository universityRepository;
    @Autowired
    AddressRepository addressRepository;


    @RequestMapping(value = "/university", method = RequestMethod.GET)
    public List<University> getUniversities(){
        return universityRepository.findAll();
    }


    @RequestMapping(value = "/university", method = RequestMethod.POST)
    public String addUniversity(@RequestBody UniversityDto universityDto){
        Address address = new Address();
        address.setCity(universityDto.getCity());
        address.setDistrict(universityDto.getDistrict());
        address.setStreet(universityDto.getStreet());
        Address savedAddress = addressRepository.save(address);
        University university = new University();
        university.setName(universityDto.getName());
        university.setAddress(savedAddress);
        universityRepository.save(university);
        return "University added";


    }
    @RequestMapping(value = "/university/{id}", method = RequestMethod.PUT)
    public String editUniversity(@PathVariable Integer id, @RequestBody UniversityDto universityDto){
        Optional<University> universityOptional = universityRepository.findById(id);
        if (universityOptional.isPresent()){
            University university = universityOptional.get();
            university.setName(universityDto.getName());
            Address address = university.getAddress();
            address.setCity(universityDto.getCity());
            address.setDistrict(universityDto.getDistrict());
            address.setStreet(universityDto.getStreet());
            addressRepository.save(address);
            return "University edited";
        }
        return "University not found";
    }
    @RequestMapping(value = "/university/{id}", method = RequestMethod.DELETE)
    public String DeleteUniversity(@PathVariable Integer id){
        universityRepository.deleteById(id);
        return "University Deleted";
    }

}
