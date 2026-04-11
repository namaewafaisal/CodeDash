package com.codedash.institution;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codedash.institution.dto.InstitutionRegisterRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/institution")
@RequiredArgsConstructor
public class InstitutionController {
    private final InstitutionService institutionService;

    @PostMapping("/register")
    public ResponseEntity<String> registerInstitution(@Valid @RequestBody InstitutionRegisterRequest request){
        institutionService.registerInstitution(request);
        return ResponseEntity.ok("Registered");
    }

    @PatchMapping("/master/institutions/{id}")
    public ResponseEntity<String> handleInstitution(
            @PathVariable Long id,
            @RequestBody Map<String,InstitutionStatus> statusRequest) {

        institutionService.handleInstitution(id, statusRequest.get("status"));
        return ResponseEntity.ok("Done");
    }

    @GetMapping("/master/institutions")
    public List<Institution> all(){
        return institutionService.all();
    }
}
