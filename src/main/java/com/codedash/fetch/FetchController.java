package com.codedash.fetch;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codedash.stats.HandleStats;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fetch")
@RequiredArgsConstructor
public class FetchController {

    private final FetchService fetchService;
    private final LeetcodeProfileService leetcodeProfileService;

    @PreAuthorize("permitAll()")
    @GetMapping("/github/{username}")
    public Object fetchGithub(@PathVariable String username) {
        return fetchService.fetchGithub(username);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/leetcode/{username}")
    public HandleStats fetchLeetcode(@PathVariable String username){
        
        return leetcodeProfileService.fetchProfile(username);
    }
}