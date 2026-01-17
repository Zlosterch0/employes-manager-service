package com.DDimov.employee_service.entity;@Entity @Data
public class Project { 
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; 
    private String title; 
}