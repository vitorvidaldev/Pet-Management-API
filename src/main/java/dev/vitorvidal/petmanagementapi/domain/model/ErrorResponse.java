package dev.vitorvidal.petmanagementapi.domain.model;

public record ErrorResponse(String httpStatus, String errorMessage) {
}
