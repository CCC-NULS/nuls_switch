package io.nuls.nulsswitch.demo.service.impl;

import io.nuls.nulsswitch.demo.service.DemoService;
import org.springframework.stereotype.Service;

import io.nuls.nulsswitch.demo.dao.DemoDao;
import io.nuls.nulsswitch.demo.domain.DemoDO;
import io.nuls.nulsswitch.common.base.CoreServiceImpl;

/**
 * 
 * <pre>
 * 基础表
 * </pre>

 */
@Service
public class DemoServiceImpl extends CoreServiceImpl<DemoDao, DemoDO> implements DemoService {

}
