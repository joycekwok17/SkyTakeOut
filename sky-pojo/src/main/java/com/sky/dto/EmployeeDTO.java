package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * EmployeeDTO for Employee entity
 * <p> EmployeeDTO is used to transfer data between front-end and back-end
 * <p> EmployeeDTO is used to hide sensitive information
 * <p> EmployeeDTO is used to hide unnecessary information
 * 当前端提交的数据和实体类中对应的属性不一致时，可以使用DTO来进行数据传输
 */
@Data
public class EmployeeDTO implements Serializable {

    private Long id;

    private String username;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;

}
