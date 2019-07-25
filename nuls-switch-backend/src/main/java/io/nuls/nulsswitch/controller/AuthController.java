package io.nuls.nulsswitch.controller;


import io.nuls.nulsswitch.constant.ResultCode;
import io.nuls.nulsswitch.service.AuthService;
import io.nuls.nulsswitch.web.dto.auth.GetTokenReqDto;
import io.nuls.nulsswitch.web.wrapper.WrapMapper;
import io.nuls.nulsswitch.web.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author JoeTao
 * createAt: 2018/9/17
 */

@RestController
@Api(description = "用户操作相关接口")
@RequestMapping("/v1/auth")
public class AuthController extends BaseController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    AuthService authService;

    @PostMapping(value = "/getToken")
    @ApiOperation(value = "获取Token", notes = "根据用户公钥和数字签名获取访问Token")
    public Wrapper<String> login(@RequestBody GetTokenReqDto getTokenReqDto) {
        Assert.notNull(getTokenReqDto, "请求不能为空");
        Assert.notNull(getTokenReqDto.getDataHex(), "签名原始数据不能为空");
        Assert.notNull(getTokenReqDto.getPublicKeyHex(), "用户公钥不能为空");
        Assert.notNull(getTokenReqDto.getSignatureHex(), "数字签名不能为空");
        String token = authService.getToken(getTokenReqDto.getDataHex(), getTokenReqDto.getPublicKeyHex(), getTokenReqDto.getSignatureHex());
        return WrapMapper.ok(token);
    }

    @GetMapping(value = "/logout")
    @ApiOperation(value = "退出时删除Token", notes = "退出时删除Token")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
    public Wrapper<Boolean> logout(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        if (token == null) {
            return WrapMapper.error(ResultCode.FORBIDDEN.getMsg());
        }
        authService.logout(token);
        return WrapMapper.ok(true);
    }

}
