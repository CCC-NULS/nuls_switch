package io.nuls.nulsswitch.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import io.nuls.nulsswitch.entity.Order;
import io.nuls.nulsswitch.web.dto.order.QueryOrderReqDto;
import io.nuls.nulsswitch.web.dto.order.QueryOrderResDto;

/**
 * <p>
 * 用户交易委托表：包括当前委托、历史委托 服务类
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
public interface OrderService extends IService<Order> {

    /**
     * 查询当前可交易的委托，包含未交易、部分交易的挂单
     * @param reqDto
     * @return
     */
    public Page<Order> queryCanTxOrderByPage(QueryOrderReqDto reqDto);

    /**
     * 查询所有委托，包含未交易、部分交易、完成交易、取消交易的挂单
     *
     * @param reqDto
     * @return
     */
    Page<QueryOrderResDto> queryOrderByPage(QueryOrderReqDto reqDto);
}
