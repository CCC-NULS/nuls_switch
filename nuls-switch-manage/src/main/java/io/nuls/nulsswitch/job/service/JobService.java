package io.nuls.nulsswitch.job.service;

import io.nuls.nulsswitch.job.domain.TaskDO;
import org.quartz.SchedulerException;

import io.nuls.nulsswitch.common.base.CoreService;


/**
 * <pre>
 * 定时任务
 * </pre>
 *
 */
public interface JobService extends CoreService<TaskDO> {
	
	void initSchedule() throws SchedulerException;

	void changeStatus(Long jobId, String cmd) throws SchedulerException;

	void updateCron(Long jobId) throws SchedulerException;

	void runNowOnce(Long jobId)  throws SchedulerException ;
}
