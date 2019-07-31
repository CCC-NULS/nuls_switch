package io.nuls.nulsswitch.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.nuls.nulsswitch.job.ScheduleJobVo.JobStatus;

import java.util.*;

/**
 */
@Service("scheduleJobManagerImp")
@Slf4j
public class ScheduleJobManagerImp implements ScheduleJobManager {
    @Autowired(required = false)
    @Qualifier("quartzSchedulerDB")
    private Scheduler quartzSchedulerDB;

    @Autowired(required = false)
    @Qualifier("quartzSchedulerRAM")
    private Scheduler quartzSchedulerRAM;


    @Override
    public List<ScheduleJobVo> getAllJobs() throws SchedulerException {
        log.debug("开始查询所有job");
        List<ScheduleJobVo> jobVoList = new ArrayList<ScheduleJobVo>();
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        /**
         * 持久型job
         */
        if (quartzSchedulerDB != null) {
            Set<JobKey> jobKeys = quartzSchedulerDB.getJobKeys(matcher);
            for (JobKey jobKey : jobKeys) {
                ScheduleJobVo jobVo = new ScheduleJobVo();
                jobVo.setJobName(jobKey.getName());
                jobVo.setJobGroup(jobKey.getGroup());
                JobDetail jobDetail = quartzSchedulerDB.getJobDetail(jobKey);
                jobVo.setTargetClass(jobDetail.getJobDataMap().getString("targetClass"));
                jobVo.setTargetMethod(jobDetail.getJobDataMap().getString("targetMethod"));
                jobVo.setJobData(jobDetail.getJobDataMap().getString("jobData"));
                jobVo.setDescription(jobDetail.getDescription());
                jobVo.setConcurrent(!jobDetail.isConcurrentExectionDisallowed());
                List<? extends Trigger> triggers = quartzSchedulerDB.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Trigger.TriggerState triggerState = quartzSchedulerDB.getTriggerState(trigger.getKey());
                    for (ScheduleJobVo.JobStatus jobStatus : ScheduleJobVo.JobStatus.values()) {
                        if (triggerState.name().equals(jobStatus.getValue())) {
                            jobVo.setJobStatus(jobStatus);
                            break;
                        }
                    }
                    jobVo.setTriggerName(trigger.getKey().getName());
                    jobVo.setTriggerGroup(trigger.getKey().getGroup());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        jobVo.setMisfireInstruction(cronTrigger.getMisfireInstruction());
                        jobVo.setCronExpression(cronTrigger.getCronExpression());
                    }
                    jobVo.setStartTime(trigger.getStartTime());
                    jobVo.setEndTime(trigger.getEndTime());
                }
                if (jobVo.getJobStatus() == null) {
                    jobVo.setJobStatus(ScheduleJobVo.JobStatus.NONE);
                }
                jobVo.setPersist(true);
                jobVoList.add(jobVo);
            }
        }
        /**
         * 内存型job
         */
        if (quartzSchedulerRAM != null) {
            Set<JobKey> jobKeysRAM = quartzSchedulerRAM.getJobKeys(matcher);
            for (JobKey jobKey : jobKeysRAM) {
                ScheduleJobVo jobVo = new ScheduleJobVo();
                jobVo.setJobName(jobKey.getName());
                jobVo.setJobGroup(jobKey.getGroup());
                JobDetail jobDetail = quartzSchedulerRAM.getJobDetail(jobKey);
                jobVo.setTargetClass(jobDetail.getJobDataMap().getString("targetClass"));
                jobVo.setTargetMethod(jobDetail.getJobDataMap().getString("targetMethod"));
                jobVo.setJobData(jobDetail.getJobDataMap().getString("jobData"));
                jobVo.setDescription(jobDetail.getDescription());
                jobVo.setConcurrent(!jobDetail.isConcurrentExectionDisallowed());
                List<? extends Trigger> triggers = quartzSchedulerRAM.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Trigger.TriggerState triggerState = quartzSchedulerRAM.getTriggerState(trigger.getKey());
                    for (ScheduleJobVo.JobStatus jobStatus : ScheduleJobVo.JobStatus.values()) {
                        if (triggerState.name().equals(jobStatus.getValue())) {
                            jobVo.setJobStatus(jobStatus);
                            break;
                        }
                    }
                    jobVo.setTriggerName(trigger.getKey().getName());
                    jobVo.setTriggerGroup(trigger.getKey().getGroup());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        jobVo.setCronExpression(cronTrigger.getCronExpression());
                    }
                    jobVo.setStartTime(trigger.getStartTime());
                    jobVo.setEndTime(trigger.getEndTime());
                }
                jobVo.setPersist(false);
                jobVoList.add(jobVo);
            }
        }
        return jobVoList;
    }

    /**
     * <p>Title: getRunningJobs</p>
     * <p>Description: </p>
     *
     * @return
     * @throws SchedulerException
     */
    @Override
    public List<ScheduleJobVo> getRunningJobs() throws SchedulerException {
        log.debug("开始查询所有执行中的job");
        List<ScheduleJobVo> jobVoList = new ArrayList<ScheduleJobVo>();
        /**
         * 持久型job
         */
        if (quartzSchedulerDB != null) {
            List<JobExecutionContext> executingJobs = quartzSchedulerDB.getCurrentlyExecutingJobs();
            for (JobExecutionContext executingJob : executingJobs) {
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                ScheduleJobVo jobVo = new ScheduleJobVo();
                jobVo.setJobName(jobKey.getName());
                jobVo.setJobGroup(jobKey.getGroup());
                jobVo.setTargetClass(jobDetail.getJobDataMap().getString("targetClass"));
                jobVo.setTargetMethod(jobDetail.getJobDataMap().getString("targetMethod"));
                jobVo.setJobData(jobDetail.getJobDataMap().getString("jobData"));
                jobVo.setDescription(jobDetail.getDescription());
                Trigger.TriggerState triggerState = quartzSchedulerDB.getTriggerState(trigger.getKey());
                for (JobStatus jobStatus : JobStatus.values()) {
                    if (triggerState.name().equals(jobStatus.getValue())) {
                        jobVo.setJobStatus(jobStatus);
                        break;
                    }
                }
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    jobVo.setCronExpression(cronTrigger.getCronExpression());
                }
                jobVo.setFireTime(executingJob.getFireTime());
                jobVo.setNextFireTime(executingJob.getNextFireTime());
                jobVo.setScheduledFireTime(executingJob.getScheduledFireTime());
                jobVoList.add(jobVo);
            }
        }
        /**
         * 内存型job
         */
        if (quartzSchedulerRAM != null) {
            List<JobExecutionContext> executingJobsRAM = quartzSchedulerRAM.getCurrentlyExecutingJobs();
            for (JobExecutionContext executingJob : executingJobsRAM) {
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                ScheduleJobVo jobVo = new ScheduleJobVo();
                jobVo.setJobName(jobKey.getName());
                jobVo.setJobGroup(jobKey.getGroup());
                jobVo.setTargetClass(jobDetail.getJobDataMap().getString("targetClass"));
                jobVo.setTargetMethod(jobDetail.getJobDataMap().getString("targetMethod"));
                jobVo.setJobData(jobDetail.getJobDataMap().getString("jobData"));
                jobVo.setDescription(jobDetail.getDescription());
                Trigger.TriggerState triggerState = quartzSchedulerRAM.getTriggerState(trigger.getKey());
                for (JobStatus jobStatus : JobStatus.values()) {
                    if (triggerState.name().equals(jobStatus.getValue())) {
                        jobVo.setJobStatus(jobStatus);
                        break;
                    }
                }
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    jobVo.setCronExpression(cronTrigger.getCronExpression());
                }
                jobVo.setFireTime(executingJob.getFireTime());
                jobVo.setNextFireTime(executingJob.getNextFireTime());
                jobVo.setScheduledFireTime(executingJob.getScheduledFireTime());
                jobVoList.add(jobVo);
            }
        }
        return jobVoList;
    }

    /**
     * <p>Title: pauseJob</p>
     * <p>Description: </p>
     *
     * @param jobVo
     * @throws SchedulerException
     */
    @Override
    @Transactional
    public void pauseJob(ScheduleJobVo jobVo) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobVo.getJobName(), jobVo.getJobGroup());
        if (jobVo.getPersist()) {
            quartzSchedulerDB.pauseJob(jobKey);
        } else {
            quartzSchedulerRAM.pauseJob(jobKey);
        }
    }

    /**
     * <p>Title: resumeJob</p>
     * <p>Description: </p>
     *
     * @param jobVo
     * @throws SchedulerException
     */
    @Override
    @Transactional
    public void resumeJob(ScheduleJobVo jobVo) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobVo.getJobName(), jobVo.getJobGroup());
        if (jobVo.getPersist()) {
            quartzSchedulerDB.resumeJob(jobKey);
        } else {
            quartzSchedulerRAM.resumeJob(jobKey);
        }
    }

    /**
     * <p>Title: triggerJob</p>
     * <p>Description: </p>
     *
     * @param jobVo
     * @throws SchedulerException
     */
    @Override
    public void triggerJob(ScheduleJobVo jobVo) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobVo.getJobName(), jobVo.getJobGroup());
        if (jobVo.getPersist()) {
            quartzSchedulerDB.triggerJob(jobKey);
        } else {
            quartzSchedulerRAM.triggerJob(jobKey);
        }
    }

    /**
     * <p>Title: rescheduleJob</p>
     * <p>Description: </p>
     *
     * @param jobVo
     * @throws SchedulerException
     */
    @Override
    @Transactional
    public void rescheduleJob(ScheduleJobVo jobVo) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobVo.getTriggerName(), jobVo.getTriggerGroup());
        if (jobVo.getPersist()) {
            CronTrigger trigger = (CronTrigger) quartzSchedulerDB.getTrigger(triggerKey);
            if (trigger != null) {
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobVo.getCronExpression());
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
                quartzSchedulerDB.rescheduleJob(triggerKey, trigger);
            }
        } else {
            CronTrigger trigger = (CronTrigger) quartzSchedulerRAM.getTrigger(triggerKey);
            if (trigger != null) {
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobVo.getCronExpression());
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
                quartzSchedulerRAM.rescheduleJob(triggerKey, trigger);
            }
        }
    }

    @Override
    @Transactional
    public void deleteJob(ScheduleJobVo jobVo) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobVo.getJobName(), jobVo.getJobGroup());
        if (jobVo.getPersist()) {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobVo.getJobName() + "Trigger", jobVo.getJobName() + "Group");
            quartzSchedulerDB.pauseTrigger(triggerKey);  //停止触发器
            quartzSchedulerDB.unscheduleJob(triggerKey); //移除触发器
            quartzSchedulerDB.deleteJob(jobKey);  //删除任务
        } else {
            quartzSchedulerRAM.deleteJob(jobKey);
        }
    }

    @Override
    public JobDetail getJob(ScheduleJobVo jobVo) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobVo.getJobName(), jobVo.getJobName() + "Group");
        if (jobVo.getPersist() == null) {
            JobDetail jobDetail = null;
            if (quartzSchedulerDB != null) {
                jobDetail = quartzSchedulerDB.getJobDetail(jobKey);
            }
            if (jobDetail == null && quartzSchedulerRAM != null) {
                jobDetail = quartzSchedulerRAM.getJobDetail(jobKey);
            }
            return jobDetail;
        } else if (jobVo.getPersist()) {
            if (quartzSchedulerDB != null) {
                return quartzSchedulerDB.getJobDetail(jobKey);
            } else {
                return null;
            }
        } else {
            if (quartzSchedulerRAM != null) {
                return quartzSchedulerRAM.getJobDetail(jobKey);
            } else {
                return null;
            }
        }
    }

    @Override
    @Transactional
    public void addJob(ScheduleJobVo jobVo) throws SchedulerException, ClassNotFoundException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobVo.getJobName() + "Trigger", jobVo.getJobName() + "Group");
        if (jobVo.getPersist()) {
            if (quartzSchedulerDB != null) {
                CronTrigger trigger = (CronTrigger) quartzSchedulerDB.getTrigger(triggerKey);
                if (null == trigger) {
                    JobDetail jobDetail = JobBuilder.newJob(StagingReleaseService.class).withIdentity(jobVo.getJobName(), jobVo.getJobName() + "Group").storeDurably(true)
                            .withDescription(jobVo.getDescription()).build();
                    JobDataMap jobDataMap = jobDetail.getJobDataMap();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("targetClass", jobVo.getTargetClass());
                    map.put("targetMethod", jobVo.getTargetMethod());
                    map.put("jobData", jobVo.getJobData());// a=123&b=123
                    jobDataMap.putAll(map);
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobVo.getCronExpression());
                    if (jobVo.getMisfireInstruction() != null) {
                        if (jobVo.getMisfireInstruction() == 2) {
                            scheduleBuilder.withMisfireHandlingInstructionDoNothing();
                        } else if (jobVo.getMisfireInstruction() == 1) {
                            scheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
                        } else if (jobVo.getMisfireInstruction() == -1) {
                            scheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
                        }
                    }
                    TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder);
                    if (jobVo.getStartTime() != null) {
                        triggerBuilder.startAt(jobVo.getStartTime());
                    }
                    if (jobVo.getEndTime() != null) {
                        triggerBuilder.endAt(jobVo.getEndTime());
                    }
                    trigger = triggerBuilder.build();
                    quartzSchedulerDB.scheduleJob(jobDetail, trigger);
                } else {
                    throw new SchedulerException("job名称已存在!");
                }
            } else {
                // throw new SchedulerException("未配置持久型调度器(quartzSchedulerDB)!");
            }
        } else {
            if (quartzSchedulerRAM != null) {
                CronTrigger trigger = (CronTrigger) quartzSchedulerRAM.getTrigger(triggerKey);
                if (null == trigger) {
                    JobDetail jobDetail = JobBuilder.newJob(StagingReleaseService.class).withIdentity(jobVo.getJobName(), jobVo.getJobName() + "Group")
                            .withDescription(jobVo.getDescription()).build();
                    JobDataMap jobDataMap = jobDetail.getJobDataMap();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("targetClass", jobVo.getTargetClass());
                    map.put("targetMethod", jobVo.getTargetMethod());
                    map.put("jobData", jobVo.getJobData());
                    jobDataMap.putAll(map);
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobVo.getCronExpression());
                    if (jobVo.getMisfireInstruction() == 2) {
                        scheduleBuilder.withMisfireHandlingInstructionDoNothing();
                    } else if (jobVo.getMisfireInstruction() == 1) {
                        scheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
                    } else if (jobVo.getMisfireInstruction() == -1) {
                        scheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
                    }
                    TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder);
                    if (jobVo.getStartTime() != null) {
                        triggerBuilder.startAt(jobVo.getStartTime());
                    }
                    if (jobVo.getEndTime() != null) {
                        triggerBuilder.endAt(jobVo.getEndTime());
                    }
                    trigger = triggerBuilder.build();
                    quartzSchedulerRAM.scheduleJob(jobDetail, trigger);
                } else {
                    throw new SchedulerException("job名称已存在!");
                }
            } else {
                throw new SchedulerException("未配置内存型调度器(quartzSchedulerRAM)!");
            }
        }

    }

    /**
     * 获取Spring上下文
     *
     * @param scheduler
     * @return
     * @throws SchedulerException
     */
    @SuppressWarnings("unused")
    private ApplicationContext getApplicationContext(Scheduler scheduler) throws SchedulerException {
        ApplicationContext applicationContext = null;
        applicationContext = (ApplicationContext) scheduler.getContext().get("applicationContextKey");
        if (applicationContext == null) {
            throw new SchedulerException("Scheduler配置中属性applicationContextSchedulerContextKey的值不等于applicationContextKey");
        }
        return applicationContext;
    }
}
