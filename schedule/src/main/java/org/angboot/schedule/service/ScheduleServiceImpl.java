/*
 * Copyright (c) 2019, AngBoot Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to AngBoot Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

package org.angboot.schedule.service;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service("scheduleService")
@org.apache.dubbo.config.annotation.Service
public class ScheduleServiceImpl implements ScheduleService {

   @PostConstruct
   private void init() throws SchedulerException {
      scheduler = StdSchedulerFactory.getDefaultScheduler();
      LOGGER.info("Schedule Inited.");
      startServer();
   }

   @Override
   public void startServer() throws SchedulerException {
      scheduler.start();
      LOGGER.info("Schedule Started.");
   }

   @Override
   public void stopServer() throws SchedulerException {
      shutdown(true);
      LOGGER.info("Schedule will Shutdown.");
   }

   /**
    * @param waitForJobsToComplete wait current running jobs to completed if true.
    */
   public void shutdown(boolean waitForJobsToComplete) throws SchedulerException {
      if(!scheduler.isShutdown()) {
         scheduler.shutdown(waitForJobsToComplete);
      }

      LOGGER.warn("Schedule server is shutdown.");
   }

   public void addJob(JobDetail job) throws SchedulerException {
      addJob(job, false);
   }

   public void addJob(JobDetail job, boolean replace) throws SchedulerException {
      if(scheduler.isStarted()) {
         scheduler.addJob(job, replace);
      }
      else {
         LOGGER.warn("Schedule server is not running.");
      }
   }

   /**
    * 立即执行一个 Job
    */
   public void executeJob(JobKey jobKey) throws SchedulerException {
      executeJob(jobKey, null);
   }

   public void executeJob(JobKey jobKey, JobDataMap data) throws SchedulerException {
      if(scheduler.isStarted()) {
         scheduler.triggerJob(jobKey, data);
      }
      else {
         LOGGER.warn("Schedule server is not running.");
      }
   }

   public void scheduleJob(Trigger trigger) throws SchedulerException {
      scheduleJob(null, trigger);
   }

   public void scheduleJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
      assert trigger != null;

      if(scheduler.isStarted()) {
         if(jobDetail == null) {
            if(LOGGER.isDebugEnabled()) {
               if(scheduler.getJobDetail(trigger.getJobKey()) == null) {
                  LOGGER.debug("The jobKey of trigger setting miss or error. jobKey is " + trigger.getJobKey());
               }
            }

            scheduler.scheduleJob(trigger);
         }
         else {
            scheduler.scheduleJob(jobDetail, trigger);
         }
      }
      else {
         LOGGER.warn("Schedule server is not running.");
      }
   }

   private Scheduler scheduler;

   private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleServiceImpl.class);
}
