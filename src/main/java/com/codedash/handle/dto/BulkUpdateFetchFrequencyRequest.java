package com.codedash.handle.dto;

import java.util.List;
import java.util.UUID;

import com.codedash.handle.FetchFrequency;
import com.codedash.handle.Platform;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BulkUpdateFetchFrequencyRequest {

    @NotEmpty
    private List<UUID> userIds;

    @NotNull
    private Platform platform;

    @NotNull
    private FetchFrequency fetchFrequency;
}