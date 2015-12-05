package com.drmtx.app.api;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.drmtx.app.exception.CommentAnalysisException;

@Controller
public class CommentAnalysisErrorController implements ErrorController{

    private static final String ERROR_PATH = "/error";
    private static final Logger LOGGER = Logger.getLogger(CommentAnalysisErrorController.class.getName());
	

	    /**
	     * Supports other formats like JSON, XML
	     * @param request
	     * @return
	     * @throws CommentAnalysisException 
	     */
	    @RequestMapping(value = ERROR_PATH , produces = "text/plain")
	    public ResponseEntity<String> error(HttpServletRequest request)  {
	    	LOGGER.log(Level.INFO,"Handling error requests");
	    	HttpStatus status = getStatus(request);
	    	return new ResponseEntity<String>("Invalid Request. Kindly check the request.", status);
	    }

	    /**
	     * Returns the path of the error page.
	     *
	     * @return the error path
	     */
	    @Override
	    public String getErrorPath() {
	        return ERROR_PATH;
	    }

	    private HttpStatus getStatus(HttpServletRequest request) {
	        Integer statusCode = (Integer) request
	                .getAttribute("javax.servlet.error.status_code");
	        if (statusCode != null) {
	            try {
	                return HttpStatus.valueOf(statusCode);
	            }
	            catch (Exception ex) {
	            }
	        }
	        return HttpStatus.INTERNAL_SERVER_ERROR;
	    }
}
