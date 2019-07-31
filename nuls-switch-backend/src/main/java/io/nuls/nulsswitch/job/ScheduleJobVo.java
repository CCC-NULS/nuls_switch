package io.nuls.nulsswitch.job;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
public class ScheduleJobVo {

    public enum JobStatus {
        NORMAL("NORMAL", "正常"),
        PAUSED("PAUSED", "暂停"),
        COMPLETE("COMPLETE", "触发完成"),
        BLOCKED("BLOCKED", "阻塞"),
        NONE("NONE", "停止"),
        ERROR("ERROR", "错误");
        private String value;
        private String text;

        private JobStatus(String value, String text) {
            this.value = value;
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    //任务名称
    private String jobName;
    //任务所属组的名称
    private String jobGroup;
    //任务触发器名称
    private String triggerName;
    //任务触发器所属组的名称
    private String triggerGroup;
    //任务的状态
    private JobStatus jobStatus;
    //任务运行时间表达式
    private String cronExpression;
    //任务描述
    private String description;
    //任务实际开始执行时间
    private Date fireTime;
    //任务下次执行时间
    private Date nextFireTime;
    //任务计划执行时间
    private Date scheduledFireTime;
    //任务是否支持并发
    private Boolean concurrent;
    //任务是否持久化
    private Boolean persist;
    //目标类
    private String targetClass;
    //目标方法
    private String targetMethod;
    //目标方法参数
    private String jobData;
    //misfire策略
    private Integer misfireInstruction;
    //开始时间
    private Date startTime;
    //结束时间
    private Date endTime;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public String getJobStatusText() {
        return jobStatus.getText();
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getFireTime() {
        return fireTime;
    }

    public void setFireTime(Date fireTime) {
        this.fireTime = fireTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getScheduledFireTime() {
        return scheduledFireTime;
    }

    public void setScheduledFireTime(Date scheduledFireTime) {
        this.scheduledFireTime = scheduledFireTime;
    }

    public String getJobData() {
        return jobData;
    }

    public void setJobData(String jobData) {
        this.jobData = jobData;
    }

    public Boolean getConcurrent() {
        return concurrent;
    }

    public void setConcurrent(Boolean concurrent) {
        this.concurrent = concurrent;
    }

    public void setPersist(Boolean persist) {
        this.persist = persist;
    }

    public Integer getMisfireInstruction() {
        return misfireInstruction;
    }

    public void setMisfireInstruction(Integer misfireInstruction) {
        this.misfireInstruction = misfireInstruction;
    }

    public Boolean getPersist() {
        return persist;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }

    /**
     * <p>Title: toString</p>
     * <p>Description: </p>
     *
     * @return
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "ScheduleJobVo [concurrent=" + concurrent + ", cronExpression="
                + cronExpression + ", description=" + description
                + ", endTime=" + endTime + ", fireTime=" + fireTime
                + ", jobData=" + jobData
                + ", jobGroup=" + jobGroup + ", jobName=" + jobName
                + ", jobStatus=" + jobStatus + ", misfireInstruction="
                + misfireInstruction + ", nextFireTime=" + nextFireTime
                + ", persist=" + persist + ", scheduledFireTime="
                + scheduledFireTime + ", startTime=" + startTime
                + ", targetClass=" + targetClass + ", targetMethod="
                + targetMethod + ", triggerGroup=" + triggerGroup
                + ", triggerName=" + triggerName + "]";
    }

}
