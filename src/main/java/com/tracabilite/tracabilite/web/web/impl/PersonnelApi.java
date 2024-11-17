package com.tracabilite.tracabilite.web.web.impl;

import com.tracabilite.tracabilite.persistence.dao.UserRepository;
import com.tracabilite.tracabilite.persistence.model.User;
import com.tracabilite.tracabilite.service.IMachineService;
import com.tracabilite.tracabilite.service.IMatiereService;
import com.tracabilite.tracabilite.service.impl.UserDetailsImpl;
import com.tracabilite.tracabilite.web.dto.JwtResponse;
import com.tracabilite.tracabilite.web.dto.LoginRequest;
import com.tracabilite.tracabilite.web.dto.PersonnelDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/personnel")
public class PersonnelApi {
    @Autowired
    UserRepository userRepository;
    @Autowired
    IMachineService machineService;
    @GetMapping("/personnels")
    public ResponseEntity<?> getAllPersonnel() {
        List<User> user = userRepository.findAll();
        List<PersonnelDto> personnelDtos = new ArrayList<>();
        user.forEach(user1 -> {
            PersonnelDto personnelDto = new PersonnelDto();
            personnelDto.setId(user1.getId());
            personnelDto.setEmail(user1.getEmail());
            personnelDto.setRole(user1.getRole());
            personnelDto.setFullName(user1.getFullName());
            personnelDto.setMachine(machineService.findById(user1.getMachine()).get().getName());
            personnelDto.setNumero(user1.getNumero());
            personnelDtos.add(personnelDto);
        });
        return ResponseEntity.ok(personnelDtos);
    }

}
