package io.nuls.nulsswitch.job;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.nuls.core.basic.Result;
import io.nuls.core.model.DateUtils;
import io.nuls.nulsswitch.constant.SwitchConstant;
import io.nuls.nulsswitch.entity.Order;
import io.nuls.nulsswitch.entity.Trade;
import io.nuls.nulsswitch.service.OrderService;
import io.nuls.nulsswitch.service.TradeService;
import io.nuls.nulsswitch.util.StringUtils;
import io.nuls.v2.util.NulsSDKTool;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 交易状态同步定时任务
 */
@Component("updateTradeStatusJobTimer")
@Lazy(false)
@Slf4j
public class UpdateTradeStatusJobTimer implements ITimerJobber, InitializingBean {

    private static boolean isRunning = false;

    @Autowired
    ScheduleJobManager scheduleJobManager;

    @Autowired
    TradeService tradeService;

    @Autowired
    OrderService orderService;

    /**
     * 定时任务调度策略
     * 如果数据库中已经存在，则以数据库为准
     */
    String cron = "0 */1 * * * ?";

    String jobName = "交易状态同步定时任务";


    @Override
    public void execute(JobMethodParam param) {
        log.info("Job:{} 开始执行！", jobName);
        //仅允许单线程执行任务：如果上次任务执行未结束，本次调度将直接返回
        if (isRunning) {
            log.warn("Job:{} 正在执行中，本次调度将不执行！", jobName);
            return;
        } else {
            isRunning = true;
        }
        try {
            //查询出需要确认的交易
            Page<Trade> tradePage = new Page<>();
            tradePage.setCurrent(1).setSize(100);
            Trade example = new Trade();
            example.setStatus(1);
            EntityWrapper<Trade> entityWrapper = new EntityWrapper<>(example);
            Page<Trade> page = tradeService.selectPage(tradePage, entityWrapper);
            if (page == null || page.getRecords() == null) {
                log.info("have no not confirm trade record");
            }
            //循环，到区块链中查询交易状态
            for (Trade trade : page.getRecords()) {
                if (StringUtils.isNotBlank(trade.getTxHash())) {
                    // 广播时间超过一天，且不存在的交易，修改状态为交易失败
                    boolean notSameDay = DateUtils.getDate(new Date()) != DateUtils.getDate(trade.getUpdateTime());
                    // 从主网查询交易hash
                    Result result = NulsSDKTool.getTx(trade.getTxHash());
                    log.info("NulsSDKTool getTx, txHash: {}, resp: {}", trade.getTxHash(), result.toString());
                    if (result != null && result.getData() != null) {
                        Map map = (Map) result.getData();
                        Integer status = (Integer) map.get("status");
                        //如果为已经确定，则更新状态为成功
                        if (status == 1) {
                            trade.setStatus(SwitchConstant.TX_TRADE_STATUS_CONFIRMED);
                            trade.setUpdateTime(new Date());
                            tradeService.updateById(trade);

                            // 修改订单交易数量、状态
                            Order order = orderService.selectById(trade.getOrderId());
                            if (order != null) {
                                // 修改交易数量（原token、目标token）
                                Long txNum = trade.getTxNum() + order.getTxNum();
                                Long toNum = trade.getToNum() + (order.getToNum() != null ? order.getToNum() : 0);
                                order.setStatus(SwitchConstant.TX_ORDER_STATUS_PART);
                                order.setTxNum(txNum);
                                order.setToNum(toNum);
                                // 如果交易数等于挂单总量，则修改订单状态为完成交易
                                if (Objects.equals(txNum, order.getTotalNum())) {
                                    order.setStatus(SwitchConstant.TX_ORDER_STATUS_DONE);
                                }
                                order.setUpdateTime(new Date());
                                orderService.updateById(order);
                            }
                        } else {
                            // 交易确认失败，该交易不存在，有可能存在前端刚广播出去，主网还未收到该交易，此时定时任务去查询该交易查询不到记录
                            if (StringUtils.isNotBlank(result.getMsg()) || notSameDay) {
                                trade.setStatus(SwitchConstant.TX_TRADE_STATUS_FAIL);
                                trade.setMsg(result.getMsg());
                                tradeService.updateById(trade);
                            }
                        }
                    } else {
                        // 交易确认失败，该交易不存在，有可能存在前端刚广播出去，主网还未收到该交易，此时定时任务去查询该交易查询不到记录
                        if (StringUtils.isNotBlank(result.getMsg()) || notSameDay) {
                            trade.setStatus(SwitchConstant.TX_TRADE_STATUS_FAIL);
                            trade.setMsg(result.getMsg());
                            tradeService.updateById(trade);
                        }
                    }
                }
            }
            log.info("Job:{} 执行结束！", jobName);
        } catch (Exception e) {
            log.error("Job:" + jobName + " 执行出错！", e);
        } finally {
            isRunning = false;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("init task:{}", jobName);
        ScheduleJobVo job = new ScheduleJobVo();
        job.setJobName(jobName);
        job.setJobGroup(job.getJobName() + "Group");
        job.setPersist(true);
        job.setJobData("Hello");
        JobDetail JobDetail = scheduleJobManager.getJob(job);
        if (JobDetail != null) {
            log.info("the task is already exist：{}", jobName);
            return;
        }
        job.setTargetClass("updateTradeStatusJobTimer");
        job.setStartTime(new Date());
        job.setCronExpression(cron);
        job.setDescription("update trade status");
        job.setMisfireInstruction(2);
        job.setPersist(true);
        //job.setJobData("orderId=" + order.getId());
        try {
            scheduleJobManager.addJob(job);
        } catch (Exception e) {
            log.error("create task failed", e);
        }
    }
}
