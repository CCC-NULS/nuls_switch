package io.nuls.nulsswitch.job.jobs;

import io.nuls.nulsswitch.common.utils.DateUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <pre>
 * </pre>
 * 
 *
 */
@Component
public class TestJob implements Job {

    @Override
    public void execute(JobExecutionContext context){
        System.err.println("测试任务执 | " + DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN_19)
                + " | 定时统计人数：\" + userService.selectCount(null)");
    }

}
