package com.example.testing.controller.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
    private String name;
}