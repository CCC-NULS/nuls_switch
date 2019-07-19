package io.nuls.nulsswitch.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.nuls.nulsswitch.constant.SwitchConstant;
import io.nuls.nulsswitch.entity.Order;
import io.nuls.nulsswitch.mapper.OrderMapper;
import io.nuls.nulsswitch.service.OrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuls.nulsswitch.web.dto.order.QueryOrderReqDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * <p>
 * 用户交易委托表：包括当前委托、历史委托 服务实现类
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public Page<Order> queryOrderByPage(QueryOrderReqDto reqDto) {
        Page<Order> orderPage = new Page<>();
        Order order = new Order();

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
        return this.selectPage(orderPage, eWrapper);
    }
}
