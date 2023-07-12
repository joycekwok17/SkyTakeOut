package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee") // request url: /admin/employee + /xxx
@Slf4j
@Api(tags = "Employee Related Interface")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("Employee login interface")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("Employee login：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        // login success, create token using jwt
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId()); // put employee id into claims map to create token
        String token = JwtUtil.createJWT( // create token using jwt library
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("Employee logout interface")
    public Result<String> logout() {
        return Result.success();
    }


    /**
     * add new employee
     * @param employeeDTO
     * @return
     */
    @PostMapping
    @ApiOperation("Add New employee interface")
    public Result<String> save(@RequestBody EmployeeDTO employeeDTO){ //request body is json format data
        log.info("add new employee：{}",employeeDTO); // {} is a placeholder for the first parameter after the string
        employeeService.save(employeeDTO);
        return Result.success();
    }

    /**
     * page query employee list interface (pagination) (page query)
     * @param employeePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("Query employee by page interface")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("分页查询：{}",employeePageQueryDTO);
        PageResult pageResult = employeeService.page(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用员工账号")
    public Result<String>startOrStop(@PathVariable Integer status,Long id){
        log.info("启用或禁用员员工账户：{},{}",status,id);
        employeeService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * 根据id查询员工
     * 回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询员工")
    public Result<Employee> getById(@PathVariable Long id){
        return Result.success(employeeService.getById(id));
    }

    /**
     * 编辑员工信息
     * @param employeeDTO
     * @return
     */
    @PutMapping
    @ApiOperation("编辑员工信息")
    public Result<Employee> update(@RequestBody EmployeeDTO employeeDTO){
        log.info("编辑员工信息{}",employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }

    @PutMapping("/editPassword")
    @ApiOperation("编辑员工信息")
    public Result<Employee> editPassword(@RequestBody PasswordEditDTO passwordEditDTO){
        log.info("editPassword{}",passwordEditDTO);
        employeeService.editPassword(passwordEditDTO);
        return Result.success();
    }
}
