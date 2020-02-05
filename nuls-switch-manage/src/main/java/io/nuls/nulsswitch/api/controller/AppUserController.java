package io.nuls.nulsswitch.api.controller;

import io.nuls.nulsswitch.api.pojo.dto.UserLoginDTO;
import io.nuls.nulsswitch.api.pojo.dto.UserLogoutDTO;
import io.nuls.nulsswitch.api.pojo.vo.TokenVO;
import io.nuls.nulsswitch.api.service.AppUserService;
import io.nuls.nulsswitch.common.annotation.Log;
import io.nuls.nulsswitch.common.utils.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 *  基于jwt实现的API测试类
 * </pre>
 * 
 *
 */
@RestController
@RequestMapping("/api/user/")
public class AppUserController {
    @Autowired
    private AppUserService userService;

    @PostMapping("login")
    @Log("登录-用户名、密码")
    @ApiOperation("登录-用户名、密码")
    public Result<TokenVO> login(@RequestBody final UserLoginDTO loginDTO) {
        TokenVO token = userService.getToken(new UsernamePasswordToken(loginDTO.getUname(), loginDTO.getPasswd()));
        return Result.ok(token);
    }

    @PostMapping("refresh")
    @Log("api测试-刷新token")
    @ApiOperation("api测试-刷新token")
    public Result<TokenVO> refresh(@RequestParam String uname, @RequestBody final String refreshToken) {
    	TokenVO token = userService.refreshToken(uname, refreshToken);
    	return Result.ok(token);
    }
    
    @PostMapping("logout")
    @Log("api测试-注销token")
    @ApiOperation("api测试-注销token")
    public Result<String> logout(@RequestBody UserLogoutDTO dto) {
    	userService.logoutToken(dto.getToken(), dto.getRefreshToken());
    	return Result.ok();
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    @Log("api测试-需要认证才能访问")
    @ApiOperation("api测试-需要认证才能访问")
    @ApiImplicitParams({ @ApiImplicitParam(name = "Authorization", value = "Authorization", paramType = "header") })
    public Result<String> requireAuth() {
        return Result.build(200, "认证通过", null);
    }

    @GetMapping("/require_role")
    @RequiresRoles("apiRole")
    @Log("api测试-需要api角色才能访问")
    @ApiOperation("api测试-需要api角色才能访问")
    @ApiImplicitParams({ @ApiImplicitParam(name = "Authorization", value = "Authorization", paramType = "header") })
    public Result<String> requireRole() {
        return Result.build(200, "用户有role角色权限", null);
    }

    @GetMapping("/require_permission")
    @RequiresPermissions("api:user:update")
    @Log("api测试-需要api:user:update权限才能访问")
    @ApiOperation("api测试-需要api:user:update权限才能访问")
    @ApiImplicitParams({ @ApiImplicitParam(name = "Authorization", value = "Authorization", paramType = "header") })
    public Result<String> requirePermission() {
        return Result.build(200, "用户有api:user:update权限", null);
    }

}
