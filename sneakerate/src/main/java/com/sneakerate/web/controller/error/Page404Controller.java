package com.sneakerate.web.controller.error;

import com.sneakerate.web.controller.AbstractController;
import com.sneakerate.web.service.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@ControllerAdvice
public class Page404Controller extends AbstractController implements ErrorController {
    @Autowired
    private MailService mailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(Page404Controller.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e)
    {
        LOGGER.error("Exception {} occurred while following request: {}", e, request);

        notifyAboutProblem(request, e);
        return new ModelAndView("pages/page404");
    }

    @RequestMapping("/error")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String getErrorPage(Model model, HttpServletRequest request)
    {
        LOGGER.error("Unknown exception occurred while following request: {}", request);

        notifyAboutProblem(request, null);
        return "pages/page404";
    }

    private void notifyAboutProblem(HttpServletRequest request, Exception e) {
        LOGGER.info("Sending notification about problem...");

        incrementFailedQueryCount();

        StringBuilder builder = new StringBuilder();
        builder.append("Time: " + new Date());
        builder.append("\nURL: " + request.getRequestURL());
        if (e != null) {
            builder.append("\nException:\n");
            builder.append(e.getMessage());
        }

        mailService.send(builder.toString());

        LOGGER.info("Finished sending notification");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
