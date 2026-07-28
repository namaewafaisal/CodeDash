package com.codedash.fetch;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/debug")
@RequiredArgsConstructor
@Tag(
    name = "Debug",
    description = "Internal debugging and maintenance endpoints"
)
@SecurityRequirement(name = "Bearer Auth")
public class DebugController {

    private final FetchSchedulerService fetchSchedulerService;

    @PostMapping("/sync")
    @PreAuthorize("hasRole('MASTER')")
    @Operation(
        summary = "Trigger manual sync",
        description = "Manually triggers synchronization of coding platform data."
    )
    public String sync() {

        fetchSchedulerService.syncLeetcodeHandles();

        return "Sync triggered";
    }
}