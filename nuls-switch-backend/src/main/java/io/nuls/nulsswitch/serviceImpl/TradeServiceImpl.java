package io.nuls.nulsswitch.serviceImpl;

import io.nuls.nulsswitch.entity.Trade;
import io.nuls.nulsswitch.mapper.TradeMapper;
import io.nuls.nulsswitch.service.TradeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 挂单交易明细，一个挂单可以对应多个明细记录 服务实现类
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
@Service
public class TradeServiceImpl extends ServiceImpl<TradeMapper, Trade> implements TradeService {

}
