package com.sneakerate.web.controller.status;

import com.sneakerate.web.service.status.StatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class StatusController {
    @Autowired
    private StatusService statusService;

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusController.class);

    @RequestMapping(value = "status", method = RequestMethod.GET)
    public String getStatus()
    {
        LOGGER.info("Started processing request...");

        StringBuilder status = new StringBuilder("Status:<br>");
        status.append("<br>Build version: " + statusService.getBuildVersion());
        status.append("<br>Query count: " + statusService.getQueryCount());
        status.append("<br>Failed query count: " + statusService.getFailedQueryCount());
        status.append("<br>Start time: " + statusService.getStartTime());
        status.append("<br>Running time: " + statusService.getRunningTime());
        status.append("<br>Last crawling time: " + statusService.getLastCrawlingTime());

        LOGGER.info("Finished processing request");
        return status.toString();
    }
}
