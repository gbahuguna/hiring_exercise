package com.drmtx.app.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.drmtx.app.entities.WordFrequency;
import com.drmtx.app.exception.CommentAnalysisException;
import com.drmtx.app.services.CommentAnalysisManager;

@RestController
@RequestMapping("/frequency")
public class CommentAnalysisController {

	private static final Logger LOGGER = Logger.getLogger(CommentAnalysisController.class.getName());
	private static final String URL_EXTENSION = ".json";
	@Autowired
	private CommentAnalysisManager commentManager;
	
	@RequestMapping(value="/new", method=RequestMethod.GET, produces = "text/plain")
	public String analyzeComment(String commentUrl) throws CommentAnalysisException {
		LOGGER.log(Level.INFO, "Comment Url is:" + commentUrl);
		// Validate the url to ensure the url is not malformed and ends with a json 
		validateUrl(commentUrl);
		int id = commentManager.analyzeAndSaveComment(commentUrl);		
		return id + "";
	}
	
	@RequestMapping(value="/{id}", produces = "application/json")
	public List<WordFrequency> getCommentWordFrequency(@PathVariable("id") int id, int count) throws CommentAnalysisException {
		if(id <= 0 || count <0) {
			throw new CommentAnalysisException("The comment id or count is incorrect");
		}
		LOGGER.log(Level.INFO, "The comment Id:" + id + ", count to be returned:" + count);
		return commentManager.getCommentsAnalysis(id, count);
		
	}
	
	private void validateUrl(String commentUrl) throws CommentAnalysisException {
		URL url;
		try {
			url = new URL(commentUrl);
		} catch (MalformedURLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new CommentAnalysisException("Incorrect or malformed Url");
		}
		if(!url.toString().endsWith(URL_EXTENSION)) {
			LOGGER.log(Level.SEVERE, "Url Extension is invalid. It doesn't end with .json");
			throw new CommentAnalysisException("The url is invalid. It should end with .json");
		}
	}
}
