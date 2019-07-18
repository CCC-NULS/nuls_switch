package io.nuls.nulsswitch.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import io.nuls.nulsswitch.entity.Token;
import io.nuls.nulsswitch.entity.TokenPair;
import io.nuls.nulsswitch.service.TokenPairService;
import io.nuls.nulsswitch.service.TokenService;
import io.nuls.nulsswitch.web.dto.token.TokenDto;
import io.nuls.nulsswitch.web.dto.token.TokenPairDto;
import io.nuls.nulsswitch.web.vo.token.TokenPairVO;
import io.nuls.nulsswitch.web.wrapper.WrapMapper;
import io.nuls.nulsswitch.web.wrapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private TokenPairService tokenPairService;

    @ApiOperation(value = "查询支持的代币列表", notes = "查询支持的代币列表")
    @GetMapping("list")
    public Wrapper<List<TokenPairDto>> getTokenList() {
        List<TokenPairDto> tokenPairDtos = Lists.newArrayList();
        // 查询所有token
        List<Token> tokenList = tokenService.selectList(null);
        // 查询交易对
        Map<Integer, List<TokenPairVO>> tokenPairMap = Optional.ofNullable(tokenPairService.queryTokenPairList()).orElse(Collections.emptyList()).stream().collect(Collectors.groupingBy(TokenPair::getFromTokenId));
        tokenList.forEach(token -> {
            // 封装交易对
            TokenPairDto tokenPairDto = new TokenPairDto();
            BeanUtils.copyProperties(token, tokenPairDto);
            List<TokenPairVO> tokenPairVOS = tokenPairMap.get(token.getTokenId());
            List<TokenDto> tokenDtos = Optional.ofNullable(tokenPairVOS).orElse(Collections.emptyList()).stream().map(pairVO -> new TokenDto(pairVO.getToTokenId(), pairVO.getTokenSymbol(), pairVO.getTokenName())).collect(Collectors.toList());
            tokenPairDto.setSwitchTokenList(tokenDtos);
            tokenPairDtos.add(tokenPairDto);
        });
        log.info("getTokenList response:{}", JSON.toJSONString(WrapMapper.ok(tokenPairDtos)));
        return WrapMapper.ok(tokenPairDtos);
    }


}
