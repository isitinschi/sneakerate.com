package com.sneakerate.web.service.status;

import com.sneakerate.web.crawler.AmazonCrawler;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {
    @Autowired
    private AmazonCrawler amazonCrawler;

    private DateTime startTime;
    public static String buildVersion = "N/A";
    private long queryCount;
    private long failedQueryCount;

    private PeriodFormatter periodFormatter;
    private DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusService.class);

    public StatusService() {
        LOGGER.info("Initialization...");

        startTime = new DateTime();
        queryCount = 0;
        failedQueryCount = 0;

        periodFormatter = new PeriodFormatterBuilder()
                .appendDays()
                .appendSuffix(" days ")
                .appendHours()
                .appendSuffix(" hours ")
                .appendMinutes()
                .appendSuffix(" minutes ")
                .appendSeconds()
                .appendSuffix(" seconds ")
                .toFormatter();

        LOGGER.info("Initialization finished");
    }

    public void incrementQueryCount() {
        ++queryCount;
    }

    public void incrementFailedQueryCount() {
        ++failedQueryCount;
    }

    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
    }

    public String getBuildVersion() {
        return buildVersion;
    }

    public long getQueryCount() {
        return queryCount;
    }

    public long getFailedQueryCount() {
        return failedQueryCount;
    }

    public String getStartTime() {
        return dtf.print(startTime);
    }

    public String getRunningTime() {
        DateTime now = new DateTime();
        Duration duration = new Duration(startTime, now);
        return periodFormatter.print(duration.toPeriod());
    }

    public String getLastCrawlingTime() {
        if (amazonCrawler.getLastUpdated() == null) {
            return "Unknown";
        } else {
            return String.valueOf(amazonCrawler.getLastUpdated());
        }
    }
}
