package io.nuls.nulsswitch.mapper;

import io.nuls.nulsswitch.entity.Trade;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 挂单交易明细，一个挂单可以对应多个明细记录 Mapper 接口
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
public interface TradeMapper extends BaseMapper<Trade> {

}
