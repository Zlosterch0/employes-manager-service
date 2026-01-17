package com.DDimov.employee_service.entity;@Entity @Data
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // e.g., "ROLE_ADMIN", "ROLE_USER"
}