package io.nuls.nulsswitch.service;

import com.baomidou.mybatisplus.plugins.Page;
import io.nuls.nulsswitch.entity.Order;
import com.baomidou.mybatisplus.service.IService;
import io.nuls.nulsswitch.web.dto.order.QueryOrderReqDto;
import io.nuls.nulsswitch.web.vo.token.TokenPairVO;

import java.util.List;

/**
 * <p>
 * 用户交易委托表：包括当前委托、历史委托 服务类
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
public interface OrderService extends IService<Order> {

    Page<Order> queryOrderByPage(QueryOrderReqDto reqDto);
}
