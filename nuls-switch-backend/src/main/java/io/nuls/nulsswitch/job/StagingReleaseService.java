package io.nuls.nulsswitch.job;

import io.nuls.nulsswitch.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 */
@Service
@Slf4j
public class StagingReleaseService extends QuartzJobBean {
    // private String jobData;
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String targetClass = null;
        Iterator<java.util.Map.Entry<String, Object>> enit = context.getJobDetail().getJobDataMap().entrySet().iterator();
        JobMethodParam jobMethodParam = null;
        while (enit.hasNext()) {
            Entry<String, Object> en = enit.next();
            if ("targetClass".equals(en.getKey())) {
                targetClass = en.getValue().toString();
            } else if ("jobData".equals(en.getKey())) {
                jobMethodParam = buildJobMethodParam(en.getValue().toString());
            }
        }
        log.info("开始执行定时任务：targetClass=" + targetClass + ",参数=" + jobMethodParam);
        if (!StringUtils.isEmpty(targetClass)) {
            Object targetObject = SpringContextUtils.getBean(targetClass);
            if (targetObject instanceof ITimerJobber) {
                ITimerJobber timerJobber = (ITimerJobber) targetObject;
                timerJobber.execute(jobMethodParam);
            }
        } else {
            log.warn("没有可执行的对象和方法，targetClass=" + targetClass + ",参数=" + jobMethodParam);
        }

    }

    /**
     */
    private JobMethodParam buildJobMethodParam(String jobData) {
        JobMethodParam jobMethodParam = null;
        if (!StringUtils.isEmpty(jobData)) {
            jobMethodParam = JobMethodParam.create();
            String[] params = jobData.split("&");
            for (String paramString : params) {
                String[] param = paramString.split("=");
                if (!StringUtils.isEmpty(param) && param.length > 1) {
                    jobMethodParam.put(param[0], param[1]);
                }
            }
        }
        return jobMethodParam;
    }
}
