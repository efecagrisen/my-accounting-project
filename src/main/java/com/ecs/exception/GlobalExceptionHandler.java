package com.ecs.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({Throwable.class, Exception.class, RuntimeException.class})
    public ModelAndView handleError(HttpServletRequest req, Exception exception) throws Exception {

        // Rethrow annotated exceptions or they will be processed here instead.
        if (AnnotationUtils.findAnnotation(exception.getClass(),
                ResponseStatus.class) != null)
            throw exception;

        ModelAndView mav = new ModelAndView();
        mav.addObject("url", req.getRequestURL());
        mav.addObject("timestamp", new Date().toString());
        mav.addObject("status", 500);
        mav.addObject("exception", exception);

        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler({
            ProductLowLimitAlertException.class
    })
    public ModelAndView handleLowLimitException(HttpServletRequest req, Exception exception) throws Exception {

        // Rethrow annotated exceptions or they will be processed here instead.
        if (AnnotationUtils.findAnnotation(exception.getClass(),
                ResponseStatus.class) != null)
            throw exception;

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("timestamp", new Date().toString());

        mav.setViewName("low-limit");
        return mav;
    }

}
