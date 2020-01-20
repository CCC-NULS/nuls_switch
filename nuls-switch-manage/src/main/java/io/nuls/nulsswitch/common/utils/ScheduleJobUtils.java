package io.nuls.nulsswitch.common.utils;

import io.nuls.nulsswitch.job.domain.ScheduleJobDO;
import io.nuls.nulsswitch.job.domain.TaskDO;

public class ScheduleJobUtils {
	public static ScheduleJobDO entityToData(TaskDO scheduleJobEntity) {
		ScheduleJobDO scheduleJob = new ScheduleJobDO();
		scheduleJob.setBeanClass(scheduleJobEntity.getBeanClass());
		scheduleJob.setCronExpression(scheduleJobEntity.getCronExpression());
		scheduleJob.setDescription(scheduleJobEntity.getDescription());
		scheduleJob.setIsConcurrent(scheduleJobEntity.getIsConcurrent());
		scheduleJob.setJobName(scheduleJobEntity.getJobName());
		scheduleJob.setJobGroup(scheduleJobEntity.getJobGroup());
		scheduleJob.setJobStatus(scheduleJobEntity.getJobStatus());
		scheduleJob.setMethodName(scheduleJobEntity.getMethodName());
		scheduleJob.setSpringBean(scheduleJobEntity.getSpringBean());
		return scheduleJob;
	}
}