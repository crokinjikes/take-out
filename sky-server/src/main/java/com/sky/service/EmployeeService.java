package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {


    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * register employee
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * page query
     * @param pqd
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO pqd);

    /**
     * start or stop employee account
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * getById
     * @param id
     * @return Employee
     */
    Employee getById(Long id);

    /**
     * update employee
     * @param employeeDTO
     * @return Employee
     */
    Employee update(EmployeeDTO employeeDTO);
}
