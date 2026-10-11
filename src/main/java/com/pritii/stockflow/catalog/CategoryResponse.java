package com.pritii.stockflow.catalog;

import java.time.LocalDateTime;

public record CategoryResponse(Long id, String name, LocalDateTime createdAt) {

    static CategoryResponse from(Category c) {
        return new CategoryResponse(c.getId(), c.getName(), c.getCreatedAt());
    }
}