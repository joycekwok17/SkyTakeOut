package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Employee page query DTO (Data Transfer Object)
 */
@Data
public class EmployeePageQueryDTO implements Serializable {

    // employee name
    private String name;

    // page number
    private int page;

    // page size
    private int pageSize;

}
