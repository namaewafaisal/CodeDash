package com.codedash.fetch;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/debug")
@RequiredArgsConstructor
public class DebugController {

    private final FetchSchedulerService fetchSchedulerService;

    @PostMapping("/sync")
    @PreAuthorize("hasRole('MASTER')")
    public String sync() {

        fetchSchedulerService.syncLeetcodeHandles();

        return "Sync triggered";
    }
}