package io.nuls.nulsswitch.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.nuls.nulsswitch.entity.TokenPair;
import io.nuls.nulsswitch.web.vo.token.TokenPairVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 代币兑换交易对 Mapper 接口
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
public interface TokenPairMapper extends BaseMapper<TokenPair> {

    /**
     * 查询交易对列表
     * @return
     */
    List<TokenPairVO> queryTokenPairList();

}
