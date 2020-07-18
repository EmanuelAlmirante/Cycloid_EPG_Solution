package epg.controller;

import epg.error.BusinessError;
import epg.error.ResourceNotFoundError;
import epg.error.RestError;
import epg.error.TechnicalError;
import epg.exception.BusinessException;
import epg.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RestError handleBusinessRuleValidationError(
            HttpServletRequest request, HttpServletResponse response, Exception exception) {

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        if (exception instanceof BusinessException) {
            BusinessException businessException = (BusinessException) exception;
            return new BusinessError(businessException.getMessageKey(), businessException.getArguments());
        } else {
            return new TechnicalError(exception);
        }
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResourceNotFoundError handle(ResourceNotFoundException ex) {
        return new ResourceNotFoundError(ex.getMessageKey(), ex.getArguments());
    }
}
