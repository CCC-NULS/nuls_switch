package io.nuls.nulsswitch.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.nuls.nulsswitch.constant.SwitchConstant;
import io.nuls.nulsswitch.entity.Order;
import io.nuls.nulsswitch.service.OrderService;
import io.nuls.nulsswitch.web.dto.QueryAllAsksRequestDto;
import io.nuls.nulsswitch.web.dto.order.QueryOrderReqDto;
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

import java.util.Arrays;


@RestController
@RequestMapping("/v1/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "获取可购买挂单", notes = "分页获取可购买挂单")
    @GetMapping("listOnBuy")
    public Wrapper<Page<Order>> listOnBuy(QueryOrderReqDto reqDto) {
        // 查询当前可购买的等待出售列表，排除自己发布的出售委托
        Page<Order> orderPage = new Page<>();
        Order order = new Order();
        order.setTxType(SwitchConstant.TX_TYPE_SELL);
        BeanUtils.copyProperties(reqDto, order);
        order.setAddress(null);
        orderPage.setCurrent(reqDto.getCurrent() == null ? 1 : reqDto.getCurrent());
        orderPage.setSize(reqDto.getSize() == null ? 10 : reqDto.getSize());
        EntityWrapper<Order> eWrapper = new EntityWrapper<>(order);
        if (reqDto.getStartQueryTime() != null) {
            eWrapper.gt("create_time", reqDto.getStartQueryTime());
        }
        if (reqDto.getEndQueryTime() != null) {
            eWrapper.lt("create_time", reqDto.getEndQueryTime());
        }
        eWrapper.in("status", Arrays.asList(SwitchConstant.TX_ORDER_STATUS_INIT, SwitchConstant.TX_ORDER_STATUS_PART));
        eWrapper.notIn("address", reqDto.getAddress());
        orderService.selectPage(orderPage, eWrapper);
        log.info("listOnBuy response:{}", JSON.toJSONString(WrapMapper.ok(orderPage)));
        return WrapMapper.ok(orderPage);
    }

    @ApiOperation(value = "获取可卖出挂单", notes = "分页获取可卖出挂单")
    @GetMapping("listOnSell")
    public Wrapper<Page<Order>> listOnSell(QueryOrderReqDto reqDto) {
        // 查询当前可出售的等待购买列表，排除自己发布的购买委托
        Page<Order> orderPage = new Page<>();
        Order order = new Order();
        order.setTxType(SwitchConstant.TX_TYPE_BUY);
        BeanUtils.copyProperties(reqDto, order);
        order.setAddress(null);
        orderPage.setCurrent(reqDto.getCurrent() == null ? 1 : reqDto.getCurrent());
        orderPage.setSize(reqDto.getSize() == null ? 10 : reqDto.getSize());
        EntityWrapper<Order> eWrapper = new EntityWrapper<>(order);
        if (reqDto.getStartQueryTime() != null) {
            eWrapper.gt("create_time", reqDto.getStartQueryTime());
        }
        if (reqDto.getEndQueryTime() != null) {
            eWrapper.lt("create_time", reqDto.getEndQueryTime());
        }
        eWrapper.in("status", Arrays.asList(SwitchConstant.TX_ORDER_STATUS_INIT, SwitchConstant.TX_ORDER_STATUS_PART));
        eWrapper.notIn("address", reqDto.getAddress());
        orderService.selectPage(orderPage, eWrapper);
        log.info("listOnSell response:{}", JSON.toJSONString(WrapMapper.ok(orderPage)));
        return WrapMapper.ok(orderPage);
    }


    @ApiOperation(value = "获取所有的买单", notes = "分页获取所有的买单")
    @GetMapping("queryAllAsksWithPage")
    public Wrapper<Page<Order>> queryAllAsksWithPage(QueryAllAsksRequestDto reqDto) {
        Page<Order> orderPage = new Page<>();
        Order order = new Order();
        order.setTxType(1);
        BeanUtils.copyProperties(reqDto, order);
        orderPage.setCurrent(reqDto.getCurrent() == null ? 1 : reqDto.getCurrent());
        orderPage.setSize(reqDto.getSize() == null ? 10 : reqDto.getSize());
        EntityWrapper<Order> eWrapper = new EntityWrapper<>(order);
        if (reqDto.getStartQueryTime() != null) {
            eWrapper.gt("create_time", reqDto.getStartQueryTime());
        }
        if (reqDto.getEndQueryTime() != null) {
            eWrapper.lt("create_time", reqDto.getEndQueryTime());
        }
        orderService.selectPage(orderPage, eWrapper);
        log.info("getAllAsksWithPage response:{}", JSON.toJSONString(WrapMapper.ok(orderPage)));
        return WrapMapper.ok(orderPage);
    }

    @ApiOperation(value = "获取所有的卖单", notes = "分页获取所有的卖单")
    @GetMapping("getAllBids")//买家单
    public Wrapper<Long> getAllBidsWithPage(@RequestBody Order param) {
        return null;
    }

    @ApiOperation(value = "用户挂单", notes = "用户挂单")
    @GetMapping("createOrder")
    public Wrapper<Long> createOrder(@RequestBody Order param) {
        return null;
    }

    @ApiOperation(value = "用户撤单", notes = "用户撤单")
    @GetMapping("cancelOrder")
    public Wrapper<Long> cancelOrder(@RequestBody Order param) {
        return null;
        //return WrapMapper.ok(unitService.create(param));
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
