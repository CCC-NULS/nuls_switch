package io.nuls.nulsswitch.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.nuls.nulsswitch.entity.Order;
import io.nuls.nulsswitch.entity.Token;
import io.nuls.nulsswitch.service.OrderService;
import io.nuls.nulsswitch.service.TokenService;
import io.nuls.nulsswitch.web.dto.QueryAllAsksRequestDto;
import io.nuls.nulsswitch.web.wrapper.WrapMapper;
import io.nuls.nulsswitch.web.wrapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 支持的代币
 * </p>
 *
 * @author qinyifeng
 * @since 2019-07-17
 */
@RestController
@RequestMapping("/v1/token")
@Slf4j
public class ToeknController {

    @Autowired
    private TokenService tokenService;

    @ApiOperation(value="查询支持的代币列表", notes = "查询支持的代币列表")
    @GetMapping("list")
    public Wrapper<List<Token>> getTokenList(){
        List<Token> list=tokenService.selectList(null);
        log.info("getTokenList response:{}",JSON.toJSONString(WrapMapper.ok(list)));
        return WrapMapper.ok(list);
    }


}
