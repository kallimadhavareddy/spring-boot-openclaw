package com.kalli.tech.openclaw.job;


import com.kalli.tech.openclaw.service.OpenClawService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HelloWorldScheduler {

    public final OpenClawService  openClawService;

    @Autowired
    public HelloWorldScheduler(OpenClawService openClawService) {
        this.openClawService = openClawService;
    }

    // Cron: "0 */2 * * * *" -> every 2 minutes at 0 seconds
    //@Scheduled(cron = "0 */2 * * * *")
    public void sayHelloEveryTwoMinutes() {
        String COMMAND ="openclaw message send --target 120363404739904487@g.us --message \"Hello from openclaw\"";
        String s = openClawService.runCommand(COMMAND);
        log.info("Command Successfully Executed: " + s);

    }
}

