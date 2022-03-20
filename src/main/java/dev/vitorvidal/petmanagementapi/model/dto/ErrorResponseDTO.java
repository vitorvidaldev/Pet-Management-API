package dev.vitorvidal.petmanagementapi.model.dto;

public record ErrorResponseDTO(String httpStatus, String errorMessage) {
}
