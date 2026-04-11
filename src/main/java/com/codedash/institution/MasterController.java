package com.codedash.institution;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/master")
@RequiredArgsConstructor
public class MasterController {

    private final InstitutionService institutionService;

    @GetMapping("/institutions")
    public List<Institution> getPending() {
        return institutionService.getAll();
    }

    @PostMapping("/institutions/{id}/approve")
    public ResponseEntity<String> approve(@PathVariable Long id) {
        institutionService.approve(id);
        return ResponseEntity.ok("Institution approved");
    }

    @PostMapping("/institutions/{id}/reject")
    public ResponseEntity<String> reject(@PathVariable Long id) {
        institutionService.reject(id);
        return ResponseEntity.ok("Institution rejected");
    }
}