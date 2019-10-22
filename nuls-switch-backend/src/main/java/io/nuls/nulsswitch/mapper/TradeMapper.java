package io.nuls.nulsswitch.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import io.nuls.nulsswitch.entity.Trade;
import io.nuls.nulsswitch.web.dto.order.QueryTradeReqDto;
import io.nuls.nulsswitch.web.vo.trade.TradeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 挂单交易明细，一个挂单可以对应多个明细记录 Mapper 接口
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
public interface TradeMapper extends BaseMapper<Trade> {

    //Page<TradeVO> queryTradeByPage2(Page<TradeVO> page, @Param("ew") EntityWrapper<Trade> entityWrapper);

    List<TradeVO> queryTradeByPage(Pagination page, QueryTradeReqDto queryTrade);

    /**
     * 取消订单交易
     *
     * @param orderId 订单ID
     * @param txId    交易ID
     */
    void cancelOrderTrade(@Param("orderId") String orderId, @Param("txId") String txId);

    /**
     * 查询该地址+代币当前最新的未确认交易
     *
     * @param address 地址
     * @param tokenId 代币ID
     * @return
     */
    TradeVO queryLastTradeByToken(@Param("address") String address, @Param("tokenId") Integer tokenId);
}
