package com.jt.quartz;


import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.OrderMapper;
import com.jt.pojo.Order;


//准备订单定时任务
@Component
public class OrderQuartz extends QuartzJobBean{

	@Autowired
	private OrderMapper orderMapper;  //操作Mapper接口
	
	/**
	 * 当定时任务到了指定的时间,则执行该方法.
	 * 需要将超过30分钟的订单设定关闭.
	 * 
	 * 问题: 如何判断订单超时????
	 * 公式:  now() - created > 30分钟   变形
	 * 		  created < now() - 30
	 * sql:
	 * 		update tb_order set status = 6,updated = date()
	 * 		where status = 1 and created < now() - 30分钟
	 */
	@Override
	@Transactional
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		//日历对象  格林威治时间
		Calendar calendar = Calendar.getInstance(); //获取现在的时间
		calendar.add(Calendar.MINUTE, -30);
		Order order = new Order();
		order.setStatus(6).setUpdated(new Date());
		UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("status",1)
					 .lt("created", calendar.getTime());
		orderMapper.update(order, updateWrapper);
		System.out.println("订单任务执行成功!!!!!");
	}
}
