package app.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {


    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(Exception.class)
    public ModelAndView handle400Errors(Exception ex) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/error");
        mv.addObject("exception", ex.getMessage());
        log.info("error mesaji "+ex.getMessage());
        return mv;
    }


    @ExceptionHandler(TaskException.class)
    public ModelAndView handle500Errors(Exception ex) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/error_task");
        mv.addObject("exception", ex.getMessage());
        return mv;
    }

}
