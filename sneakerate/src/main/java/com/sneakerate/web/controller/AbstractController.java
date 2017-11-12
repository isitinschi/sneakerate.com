package com.sneakerate.web.controller;

import com.sneakerate.web.service.status.StatusService;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractController {
    @Autowired
    private StatusService statusService;

    protected void incrementQueryCount() {
        statusService.incrementQueryCount();
    }

    protected void incrementFailedQueryCount() {
        statusService.incrementFailedQueryCount();
    }
}
