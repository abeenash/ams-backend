package com.ams.academicmanagementsystem.exception;

import java.time.Instant;

public record ErrorResponse(String message, Instant timestamp) {
}
