package com.legendme.missions_svc.shared.dto;

public record Error(
        int status,
        String message
) {
}
