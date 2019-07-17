package io.nuls.nulsswitch.serviceImpl;

import io.nuls.nulsswitch.entity.Order;
import io.nuls.nulsswitch.mapper.OrderMapper;
import io.nuls.nulsswitch.service.OrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
