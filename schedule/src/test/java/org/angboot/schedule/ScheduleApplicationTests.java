package org.angboot.schedule;

import org.angboot.schedule.config.ApplicationConfig;
import org.angboot.schedule.service.ScheduleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(ApplicationConfig.class)
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {ApplicationConfig.class})
public class ScheduleApplicationTests {

   @Test
   public void testQuartz() throws Exception {
      Assertions.assertNotNull(scheduleService, "init schedule instance failed.");

      scheduleService.stopServer();
   }

   @Autowired
   private ScheduleService scheduleService;
}
