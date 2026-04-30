package com.codedash.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.codedash.handle.StudentHandle;
import com.codedash.handle.dto.HandleRequest;
import com.codedash.handle.dto.HandleResponse;
import com.codedash.profile.StudentProfile;
import com.codedash.profile.dto.ProfileRequest;
import com.codedash.profile.dto.ProfileResponse;
import com.codedash.profile.dto.UpdateProfileRequest;
import com.codedash.registration.PendingInstitution;

/**
 * Global MapStruct mapper for the entire project.
 * All entity <-> DTO conversions go here.
 *
 * HOW TO ADD A NEW MAPPING:
 * ─────────────────────────
 * 1. Simple conversion (same field names, same types):
 *    Just declare the method — MapStruct figures it out automatically.
 *    Example:
 *        SomeResponse toResponse(SomeEntity entity);
 *        SomeEntity toEntity(SomeRequest request);
 *
 * 2. Field names differ between source and target:
 *    Use @Mapping to tell MapStruct which field maps to which.
 *    Example:
 *        @Mapping(target = "fullName", source = "name")
 *        SomeResponse toResponse(SomeEntity entity);
 *
 * 3. Ignore a field entirely (never copy it):
 *    Example:
 *        @Mapping(target = "password", ignore = true)
 *        UserResponse toResponse(User user);
 *
 * 4. PATCH update (copy only non-null fields onto existing entity):
 *    Use @BeanMapping with IGNORE strategy + @MappingTarget.
 *    Example:
 *        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
 *        void updateFromDto(SomeUpdateRequest dto, @MappingTarget SomeEntity entity);
 *
 * 5. Nested objects (e.g. entity has a child entity, response has a child DTO):
 *    MapStruct calls the matching toResponse method automatically if it exists.
 *    If field names differ, use @Mapping(target = "childDto", source = "childEntity").
 *    If you want to skip the nested object entirely:
 *        @Mapping(target = "handle", ignore = true)
 *
 * 6. Field requires a custom expression (computed value):
 *    Example — combine first and last name:
 *        @Mapping(target = "fullName", expression = "java(entity.getFirst() + ' ' + entity.getLast())")
 *        SomeResponse toResponse(SomeEntity entity);
 *
 * RULES TO ALWAYS FOLLOW:
 * ───────────────────────
 * - Never map passwords into response DTOs — always ignore them
 * - Never let entities leak out — always return DTOs from controllers
 * - For PATCH methods, always use IGNORE strategy so null fields don't overwrite existing data
 * - For nested objects managed manually in service (like handles), always add ignore = true
 *   on the parent mapper so MapStruct doesn't touch them
 */
@Mapper(componentModel = "spring")
public interface AppMapper {

    // ─── StudentProfile ───────────────────────────────────────────────────────

    @Mapping(target = "user", ignore = true)
    StudentProfile toEntity(ProfileRequest request);

    ProfileResponse toResponse(StudentProfile profile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "handles", ignore = true)   // handles managed manually in service

    @Mapping(target = "user", ignore = true)
    void updateProfileFromDto(UpdateProfileRequest dto, @MappingTarget StudentProfile entity);


    // ─── StudentHandle ────────────────────────────────────────────────────────

    StudentHandle toEntity(HandleRequest request);

    HandleResponse toResponse(StudentHandle handle);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateHandleFromDto(HandleRequest dto, @MappingTarget StudentHandle entity);


    // ─── Institution ──────────────────────────────────────────────────────────

    // PendingInstitutionResponse toResponse(PendingInstitution pending);

}