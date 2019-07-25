package io.nuls.nulsswitch.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import io.nuls.nulsswitch.constant.CommonErrorCode;
import io.nuls.nulsswitch.constant.SwitchConstant;
import io.nuls.nulsswitch.entity.Order;
import io.nuls.nulsswitch.entity.Trade;
import io.nuls.nulsswitch.service.OrderService;
import io.nuls.nulsswitch.service.TradeService;
import io.nuls.nulsswitch.util.BigDecimalUtils;
import io.nuls.nulsswitch.util.IdUtils;
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
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/v1/order")
@Slf4j
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private TradeService tradeService;

    @ApiOperation(value = "获取卖出挂单", notes = "分页获取卖出挂单")
    @GetMapping("listOnSell")
    public Wrapper<Page<Order>> listOnSell(QueryOrderReqDto orderReq) {
        // 查询当前卖出单，等待购买列表，排除自己发布的出售委托
        Page<Order> orderPage;
        try {
            // check parameters
            Preconditions.checkNotNull(orderReq.getAddress(), CommonErrorCode.PARAMETER_NULL);
            orderReq.setTxType(SwitchConstant.TX_TYPE_SELL);
            orderPage = orderService.queryCanTxOrderByPage(orderReq);
            log.info("listOnSell response:{}", JSON.toJSONString(WrapMapper.ok(orderPage)));
        } catch (NulsRuntimeException ex) {
            return WrapMapper.error(ex.getErrorCode());
        }
        return WrapMapper.ok(orderPage);
    }

    @ApiOperation(value = "获取买入挂单", notes = "分页获取买入挂单")
    @GetMapping("listOnBuy")
    public Wrapper<Page<Order>> listOnBuy(QueryOrderReqDto orderReq) {
        // 查询当前卖入单，等待卖出列表，排除自己发布的购买委托
        Page<Order> orderPage;
        try {
            // check parameters
            Preconditions.checkNotNull(orderReq.getAddress(), CommonErrorCode.PARAMETER_NULL);
            orderReq.setTxType(SwitchConstant.TX_TYPE_BUY);
            orderPage = orderService.queryCanTxOrderByPage(orderReq);
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
            // 订单ID生成
            order.setOrderId(IdUtils.getIncreaseIdByNanoTime());
            order.setStatus(SwitchConstant.TX_ORDER_STATUS_INIT);
            order.setTotalAmount(BigDecimalUtils.mul(new BigDecimal(order.getTotalNum()), order.getPrice()));
            orderService.insert(order);
        } catch (NulsRuntimeException ex) {
            return WrapMapper.error(ex.getErrorCode());
        }
        return WrapMapper.ok();
    }

    @ApiOperation(value = "用户撤单", notes = "用户撤单")
    @PostMapping("cancelOrder")
    public Wrapper<Long> cancelOrder(@RequestBody BaseReq<Order> orderReq) {
        try {
            String token = orderReq.getToken();
            Order order = orderReq.getParams();
            // check parameters
            Preconditions.checkNotNull(token, CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkNotNull(order, CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkNotNull(order.getOrderId(), CommonErrorCode.PARAMETER_NULL);

            // check auth
            checkAuth(token, null);
            order = orderService.selectById(order.getOrderId());
            // cancel order
            order.setStatus(SwitchConstant.TX_ORDER_STATUS_CANCEL);
            EntityWrapper<Order> eWrapper = new EntityWrapper<>();
            eWrapper.notIn("status", Arrays.asList(SwitchConstant.TX_ORDER_STATUS_DONE));
            orderService.updateById(order);
        } catch (NulsRuntimeException ex) {
            return WrapMapper.error(ex.getErrorCode());
        }
        return WrapMapper.ok();
    }

    @ApiOperation(value = "用户吃单", notes = "用户吃单")
    @PostMapping("tradingOrder")
    public Wrapper<Long> tradingOrder(@RequestBody BaseReq<Trade> orderReq) {
        try {
            String token = orderReq.getToken();
            Trade trade = orderReq.getParams();
            int txNum = trade.getTxNum();
            // check parameters
            Preconditions.checkNotNull(token, CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkNotNull(trade, CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkNotNull(trade.getAddress(), CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkNotNull(trade.getOrderId(), CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkNotNull(trade.getTxNum(), CommonErrorCode.PARAMETER_NULL);

            // check auth
            checkAuth(token, null);

            // check data
            Order order = orderService.selectById(trade.getOrderId());
            if (order == null) {
                log.error("the order does not exist,orderId:{}", trade.getOrderId());
                throw new NulsRuntimeException(CommonErrorCode.DATA_NOT_FOUND);
            }

            int remainNum = order.getTotalNum() - order.getTxNum();
            Preconditions.checkArgument(txNum > 0 && txNum <= remainNum, CommonErrorCode.PARAMETER_ERROR);

            // create order trade
            // 交易ID生成
            trade.setTxId(IdUtils.getIncreaseIdByNanoTime());
            trade.setStatus(SwitchConstant.TX_ORDER_STATUS_INIT);
            tradeService.insert(trade);
        } catch (NulsRuntimeException ex) {
            return WrapMapper.error(ex.getErrorCode());
        }
        return WrapMapper.ok();
    }

    @ApiOperation(value = "查询用户当前委托订单", notes = "查询用户当前委托订单")
    @GetMapping("queryMyCurrentOrder")
    public Wrapper<Long> queryMyCurrentOrder(QueryOrderReqDto orderReq) {
        // 查询用户当前委托订单，包含未交易、部分交易的订单
        Page<Order> orderPage;
        try {
            // check parameters
            Preconditions.checkNotNull(orderReq, CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkNotNull(orderReq.getAddress(), CommonErrorCode.PARAMETER_NULL);
            // 只查询可交易的订单
            orderReq.setCanTx(true);
            orderPage = orderService.queryOrderByPage(orderReq);
            log.info("queryMyCurrentOrder response:{}", JSON.toJSONString(WrapMapper.ok(orderPage)));
        } catch (NulsRuntimeException ex) {
            return WrapMapper.error(ex.getErrorCode());
        }
        return WrapMapper.ok();
    }

    @ApiOperation(value = "查询用户历史委托订单", notes = "查询用户历史委托订单")
    @GetMapping("queryMyHisOrder")
    public Wrapper<Long> queryMyHisOrder(QueryOrderReqDto orderReq) {
        // 查询用户历史委托订单，包含所有交易状态的订单
        Page<Order> orderPage;
        try {
            // check parameters
            Preconditions.checkNotNull(orderReq, CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkNotNull(orderReq.getAddress(), CommonErrorCode.PARAMETER_NULL);
            orderPage = orderService.queryOrderByPage(orderReq);
            log.info("queryMyHisOrder response:{}", JSON.toJSONString(WrapMapper.ok(orderPage)));
        } catch (NulsRuntimeException ex) {
            return WrapMapper.error(ex.getErrorCode());
        }
        return WrapMapper.ok();
    }

    @ApiOperation(value = "查询订单明细", notes = "查询订单明细")
    @GetMapping("getMyOrderDetail")
    public Wrapper<List<Trade>> getMyOrderDetail(@RequestBody Order orderReq) {
        List<Trade> tradeList = Lists.newArrayList();
        try {
            // check parameters
            Preconditions.checkNotNull(orderReq, CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkNotNull(orderReq.getOrderId(), CommonErrorCode.PARAMETER_NULL);

            // query order trade detail
            EntityWrapper<Trade> eWrapper = new EntityWrapper<>();
            eWrapper.in("order_id", orderReq.getOrderId());
            tradeList = tradeService.selectList(eWrapper);
            log.info("getMyOrderDetail response:{}", JSON.toJSONString(WrapMapper.ok(tradeList)));
        } catch (NulsRuntimeException ex) {
            return WrapMapper.error(ex.getErrorCode());
        }
        return WrapMapper.ok(tradeList);
    }

    @ApiOperation(value = "确认订单", notes = "确认订单")
    @GetMapping("confirmOrder")
    public Wrapper confirmOrder(@RequestBody Trade tradeReq) {
        try {
            // check parameters
            Preconditions.checkNotNull(tradeReq, CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkNotNull(tradeReq.getOrderId(), CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkNotNull(tradeReq.getTxNum(), CommonErrorCode.PARAMETER_NULL);
            Preconditions.checkArgument(tradeReq.getTxNum() > 0, CommonErrorCode.PARAMETER_ERROR);

            // check data
            Trade trade = tradeService.selectById(tradeReq.getTxId());
            if (trade == null) {
                log.error("the trade does not exist,txId:{}", tradeReq.getTxId());
                throw new NulsRuntimeException(CommonErrorCode.DATA_NOT_FOUND);
            }

            // assembled transfer transaction TODO 组装转账交易数据，调用NULS2.0底层

            // save trade
            trade.setStatus(SwitchConstant.TX_ORDER_STATUS_INIT);
            boolean update = tradeService.updateById(trade);
            log.info("confirmOrder response:{}", update);
        } catch (NulsRuntimeException ex) {
            return WrapMapper.error(ex.getErrorCode());
        }
        return WrapMapper.ok();
    }

}
