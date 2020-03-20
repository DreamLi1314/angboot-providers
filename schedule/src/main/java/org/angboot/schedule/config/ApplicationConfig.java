package org.angboot.schedule.config;

import org.angboot.schedule.service.ScheduleService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.angboot.schedule")
public class ApplicationConfig implements InitializingBean {

   @Autowired
   public ApplicationConfig(ScheduleService scheduleService) {
      this.scheduleService = scheduleService;
   }

   /**
    * 在 IOC 容器初始化完成后启动 Schedule 实例
    */
   @Override
   public void afterPropertiesSet() throws Exception {
      scheduleService.startServer();
   }

   private final ScheduleService scheduleService;
}
