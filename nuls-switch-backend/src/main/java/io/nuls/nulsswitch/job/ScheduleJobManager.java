package io.nuls.nulsswitch.job;

import java.util.List;

import org.quartz.JobDetail;
import org.quartz.SchedulerException;


public interface ScheduleJobManager {
	
	/**计划任务处理类**/
	static String HANDL_CLASS ="com.oao.oao_coreservice.jobcenter.service.StagingReleaseService";

	List<ScheduleJobVo> getAllJobs() throws SchedulerException;

	List<ScheduleJobVo> getRunningJobs() throws SchedulerException;

	void pauseJob(ScheduleJobVo jobVo) throws SchedulerException;

	void resumeJob(ScheduleJobVo jobVo) throws SchedulerException;

	void triggerJob(ScheduleJobVo jobVo) throws SchedulerException;

	void rescheduleJob(ScheduleJobVo jobVo) throws SchedulerException;

	void deleteJob(ScheduleJobVo jobVo) throws SchedulerException;

	JobDetail getJob(ScheduleJobVo jobVo) throws SchedulerException;

	void addJob(ScheduleJobVo jobVo) throws SchedulerException, ClassNotFoundException;
}
