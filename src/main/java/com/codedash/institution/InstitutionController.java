package com.codedash.institution;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/institution")
@RequiredArgsConstructor
public class InstitutionController {
    private final InstitutionService institutionService;
}
