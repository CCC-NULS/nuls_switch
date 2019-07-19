package io.nuls.nulsswitch.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import io.nuls.nulsswitch.constant.CommonErrorCode;
import io.nuls.nulsswitch.constant.SwitchConstant;
import io.nuls.nulsswitch.entity.Order;
import io.nuls.nulsswitch.service.OrderService;
import io.nuls.nulsswitch.util.BigDecimalUtils;
import io.nuls.nulsswitch.util.Preconditions;
import io.nuls.nulsswitch.web.dto.BaseReq;
import io.nuls.nulsswitch.web.dto.order.QueryOrderReqDto;
import io.nuls.nulsswitch.web.exception.NulsRuntimeException;
import io.nuls.nulsswitch.web.wrapper.WrapMapper;
import io.nuls.nulsswitch.web.wrapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequestMapping("/v1/order")
@Slf4j
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "获取卖出挂单", notes = "分页获取卖出挂单")
    @GetMapping("listOnSell")
    public Wrapper<Page<Order>> listOnSell(QueryOrderReqDto reqDto) {
        // 查询当前卖出单，等待购买列表，排除自己发布的出售委托
        Page<Order> orderPage;
        try {
            // check parameters
            Preconditions.checkNotNull(reqDto.getAddress(), CommonErrorCode.PARAMETER_NULL);
            reqDto.setTxType(SwitchConstant.TX_TYPE_SELL);
            orderPage = orderService.queryOrderByPage(reqDto);
            log.info("listOnSell response:{}", JSON.toJSONString(WrapMapper.ok(orderPage)));
        } catch (NulsRuntimeException ex) {
            return WrapMapper.error(ex.getErrorCode());
        }
        return WrapMapper.ok(orderPage);
    }

    @ApiOperation(value = "获取买入挂单", notes = "分页获取买入挂单")
    @GetMapping("listOnBuy")
    public Wrapper<Page<Order>> listOnBuy(QueryOrderReqDto reqDto) {
        // 查询当前卖入单，等待卖出列表，排除自己发布的购买委托
        Page<Order> orderPage;
        try {
            // check parameters
            Preconditions.checkNotNull(reqDto.getAddress(), CommonErrorCode.PARAMETER_NULL);
            reqDto.setTxType(SwitchConstant.TX_TYPE_BUY);
            orderPage = orderService.queryOrderByPage(reqDto);
            log.info("listOnBuy response:{}", JSON.toJSONString(WrapMapper.ok(orderPage)));
        } catch (NulsRuntimeException ex) {
            return WrapMapper.error(ex.getErrorCode());
        }
        return WrapMapper.ok(orderPage);
    }

    @ApiOperation(value = "用户挂单", notes = "用户挂单")
    @PostMapping("createOrder")
    public Wrapper<Long> createOrder(@RequestBody BaseReq<Order> orderReq) {
        //用户Token、交易对、单价、数量
        try {
            String token = orderReq.getToken();
            Order order = orderReq.getParams();
            // check parameters
            Preconditions.checkNotNull(token, CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkNotNull(order, CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkNotNull(order.getTxType(), CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkNotNull(order.getAddress(), CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkNotNull(order.getFromTokenId(), CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkNotNull(order.getToTokenId(), CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkArgument(order.getPrice() != null && order.getPrice().doubleValue() > 0, CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkArgument(order.getTotalNum() != null && order.getTotalNum() > 0, CommonErrorCode.PARAMETER_NULL);

            // check auth
            checkAuth(token, order.getAddress());

            // check balance

            // save order
            // TODO 订单ID生成规则
            order.setOrderId(System.currentTimeMillis() + "");
            order.setStatus(SwitchConstant.TX_ORDER_STATUS_INIT);
            order.setTotalAmount(BigDecimalUtils.mul(new BigDecimal(order.getTotalNum()), order.getPrice()));
            orderService.insert(order);
        } catch (NulsRuntimeException ex) {
            return WrapMapper.error(ex.getErrorCode());
        }
        return WrapMapper.ok();
    }

    @ApiOperation(value = "用户撤单", notes = "用户撤单")
    @GetMapping("cancelOrder")
    public Wrapper<Long> cancelOrder(@RequestBody BaseReq<Order> orderReq) {
        try {
            String token = orderReq.getToken();
            Order order = orderReq.getParams();
            // check parameters
            Preconditions.checkNotNull(token, CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkNotNull(order, CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkNotNull(order.getAddress(), CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkNotNull(order.getOrderId(), CommonErrorCode.PARAMETER_NULL);

            // check auth
            checkAuth(token, order.getAddress());

            // cancel order
            order.setStatus(SwitchConstant.TX_ORDER_STATUS_CANCEL);
            orderService.updateById(order);
        } catch (NulsRuntimeException ex) {
            return WrapMapper.error(ex.getErrorCode());
        }
        return WrapMapper.ok();
    }

    @ApiOperation(value = "用户吃单", notes = "用户吃单")
    @GetMapping("tradingOrder")
    public Wrapper<Long> tradingOrder(@RequestBody Order param) {
        return null;
    }

    @ApiOperation(value = "确认订单", notes = "确认订单")
    @GetMapping("confirmOrder")
    public Wrapper<Long> confirmOrder(@RequestBody Order param) {
        return null;
    }

    @ApiOperation(value = "查询用户委托历史", notes = "查询用户委托历史")
    @GetMapping("getMyOrder")
    public Wrapper<Long> getMyOrder(@RequestBody Order param) {
        return null;
    }

    @ApiOperation(value = "查询订单明细", notes = "查询订单明细")
    @GetMapping("getMyOrderDetail")
    public Wrapper<Long> getMyOrderDetail(@RequestBody Order param) {
        return null;
    }

}
