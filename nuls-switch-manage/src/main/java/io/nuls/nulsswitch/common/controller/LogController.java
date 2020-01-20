package io.nuls.nulsswitch.common.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.nuls.nulsswitch.common.annotation.Log;
import io.nuls.nulsswitch.common.base.BaseController;
import io.nuls.nulsswitch.common.domain.LogDO;
import io.nuls.nulsswitch.common.service.LogService;
import io.nuls.nulsswitch.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <pre>
 * 日志
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@RequestMapping("/common/log")
@Controller
public class LogController extends BaseController {
    @Autowired
    LogService logService;
    String prefix = "common/log";
    
    @GetMapping()
    String log() {
        return prefix + "/log";
    }
    
    @ResponseBody
    @GetMapping("/list")
    public Result<Page<LogDO>> list(LogDO logDTO) {
    	EntityWrapper<LogDO> wrapper = logService.convertToEntityWrapper("username", logDTO.getUsername());
    	wrapper.eq(logDTO.getUserId()!=null, "userId", logDTO.getUserId());
    	wrapper.like("operation", logDTO.getOperation());
        Page<LogDO> page = logService.selectPage(getPage(LogDO.class), wrapper);
        return success(page);
    }
    
    @Log("删除系统日志")
    @ResponseBody
    @PostMapping("/remove")
    Result<String> remove(Long id) {
        logService.deleteById(id);
        return success();
    }
    
    @Log("批量删除系统日志")
    @ResponseBody
    @PostMapping("/batchRemove")
    Result<String> batchRemove(@RequestParam("ids[]") Long[] ids) {
        logService.deleteBatchIds(Arrays.asList(ids));
        return Result.fail();
    }
}
