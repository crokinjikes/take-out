package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "api of employee")
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
    @ApiOperation(value = "login of employee")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
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
    @ApiOperation(value = "logout of employee")
    public Result<String> logout() {
        return Result.success();
    }

    @PostMapping
    @ApiOperation(value = "register employee")
    public Result<Object> registerEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工[{}]", employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("page query")
    public Result<PageResult> pageQuery(EmployeePageQueryDTO pqd) {
        log.info("employee query: {}", pqd);
        PageResult pr = employeeService.pageQuery(pqd);
        return Result.success(pr);
    }
    @PostMapping("status/{status}")
    @ApiOperation("to start or stop employee's account")
    public Result startOrStop(@PathVariable("status") Integer status, Long id) {
        log.info("start or stop employee's account {}, {}", status, id);
        employeeService.startOrStop(status, id);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("getById")
    public Result<Employee> getById(@PathVariable Long id) {
        log.info("get by id {}", id);
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }
    @PutMapping()
    @ApiOperation("set employee info")
    public Result update(@RequestBody EmployeeDTO employeeDTO) {
        log.info("update employee info {}", employeeDTO);
        Employee employee = employeeService.update(employeeDTO);
        return Result.success(employee);
    }
}
