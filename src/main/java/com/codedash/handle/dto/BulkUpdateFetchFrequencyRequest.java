package com.codedash.handle.dto;

import java.util.List;
import java.util.UUID;

import com.codedash.handle.FetchFrequency;
import com.codedash.handle.Platform;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Bulk fetch frequency update request")
public class BulkUpdateFetchFrequencyRequest {

    @Schema(
        description = "IDs of users whose fetch frequency should be updated"
    )
    @NotEmpty
    private List<UUID> userIds;

    @Schema(
        description = "Coding platform",
        example = "LEETCODE"
    )
    @NotNull
    private Platform platform;

    @Schema(
        description = "New fetch frequency",
        example = "DAILY"
    )
    @NotNull
    private FetchFrequency fetchFrequency;
}