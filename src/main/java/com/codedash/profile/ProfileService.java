package com.codedash.profile;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.codedash.exceptionhandlers.AlreadyExistsException;
import com.codedash.exceptionhandlers.ResourceNotFoundException;
import com.codedash.mapper.AppMapper;
import com.codedash.profile.dto.ProfileRequest;
import com.codedash.profile.dto.ProfileResponse;
import com.codedash.profile.dto.UpdateProfileRequest;
import com.codedash.user.User;
import com.codedash.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final StudentProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final AppMapper mapper;

    public void createProfile(UUID userId, ProfileRequest request) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (profileRepository.findByUser(user).isPresent()) {
            throw new AlreadyExistsException("Profile already exists");
        }
        if (profileRepository.existsByRegisterNumber(request.getRegisterNumber())) {
            throw new AlreadyExistsException("Register Number already exists");
        }

        StudentProfile profile = mapper.toEntity(request);
        profile.setUser(user);

        profileRepository.save(profile);
    }

    public void updateProfile(Long profileId, UpdateProfileRequest request) {

        StudentProfile profile = profileRepository.findById(profileId)
            .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        mapper.updateProfileFromDto(request, profile);

        profileRepository.save(profile);

    }

    public List<ProfileResponse> getAll() {
        return profileRepository.findAll()
            .stream()
            .map(mapper::toResponse)
            .toList();
    }


}