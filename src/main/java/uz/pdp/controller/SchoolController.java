package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Address;
import uz.pdp.entity.School;
import uz.pdp.payload.SchoolDto;
import uz.pdp.repository.AddressRepository;
import uz.pdp.repository.SchoolRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class SchoolController {

    @Autowired
    SchoolRepository schoolRepository;
    @Autowired
    AddressRepository addressRepository;

    // Read
    @RequestMapping(value = "/school", method = RequestMethod.GET)
    public List<School> getSchools(){
        List<School> schoolList = schoolRepository.findAll();
        return schoolList;
    }

    @RequestMapping(value = "/school/{id}", method = RequestMethod.GET)
    public School getPhone(@PathVariable Integer id) {
        Optional<School> optionalSchool = schoolRepository.findById(id);
        if (optionalSchool.isPresent()) {
            School school = optionalSchool.get();
            return school;
        }
        return new School();
    }

    //create
    @RequestMapping(value = "/school", method = RequestMethod.POST)
    public String addSchool(@RequestBody SchoolDto schoolDto){

        Address address = new Address();
        address.setCity(schoolDto.getCity());
        address.setDistrict(schoolDto.getDistrict());
        address.setStreet(schoolDto.getStreet());

        Address address1 = addressRepository.save(address);

        School school = new School();
        school.setName(schoolDto.getName());
        school.setAddress(address1);

        schoolRepository.save(school);
        return "added";
    }

    @RequestMapping(value = "/school/{id}", method = RequestMethod.DELETE)
    public String deletePhone(@PathVariable Integer id) {
        schoolRepository.deleteById(id);
        return "deleted";
    }

    @RequestMapping(value = "/school/{id}", method = RequestMethod.PUT)
    public String editStudent(@PathVariable Integer id, @RequestBody School school) {
        Optional<School> optionalSchool = schoolRepository.findById(id);
        if (optionalSchool.isPresent()) {
            School editingSchool = optionalSchool.get();
            editingSchool.setName(school.getName());

            Address address = new Address();
            Address editingSchoolAddress = editingSchool.getAddress();

            address.setCity(editingSchoolAddress.getCity());
            address.setDistrict(editingSchoolAddress.getDistrict());
            address.setStreet(editingSchoolAddress.getStreet());

            editingSchool.setAddress(address);
            schoolRepository.save(editingSchool);
            return "edited";
        }
        return "not found";
    }
}
