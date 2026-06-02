package com.codedash.fetch;

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
    public String sync() {

        fetchSchedulerService.syncLeetcodeHandles();

        return "Sync triggered";
    }
}