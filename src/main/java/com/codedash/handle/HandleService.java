package com.codedash.handle;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HandleService {
    private final HandleRepository handleRepository;    
}
