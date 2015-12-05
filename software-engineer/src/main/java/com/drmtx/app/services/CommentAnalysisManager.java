package com.drmtx.app.services;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.drmtx.app.entities.Comment;
import com.drmtx.app.entities.WordFrequency;
import com.drmtx.app.repositories.CommentRepository;
import com.drmtx.app.util.JsonUtil;
import com.drmtx.app.util.StringUtil;

@Service
public class CommentAnalysisManager {

	private static final Logger LOGGER = Logger.getLogger(CommentAnalysisManager.class.getName());
	
	private static final String JSON_ATTR_TO_FETCH = "body";
	@Autowired
	private CommentRepository commentRepo;
	
	public int analyzeAndSaveComment(String commentUrl) {
		LOGGER.log(Level.INFO, "Retrieving the json to analyze");
		String comments = fetchJson(commentUrl);
		LOGGER.log(Level.INFO, "Body attribute of Json retrieved and now counting word frequency");
		List<WordFrequency> wordFrequency = countWords(comments);
		LOGGER.log(Level.SEVERE, "Saving Comments and words Frequency");
		return saveCommentAnalysis(commentUrl, wordFrequency);
	}

	@Transactional
	private int saveCommentAnalysis(String commentUrl, List<WordFrequency> wordFrequency) {
		Comment commObj = new Comment();
		commObj.setCommentUrl(commentUrl);
		commObj.setWordFrequency(wordFrequency);
		commentRepo.saveAndFlush(commObj);
		LOGGER.log(Level.INFO, "COmment saved with Id:" + commObj.getId());
		return commObj.getId();
	}
	
	private String fetchJson(String commentUrl) {
		RestTemplate template = new RestTemplate();
		String commentJson = template.getForObject(commentUrl, String.class);
		List<Object> jsonList = JsonUtil.parseJsonAsList(commentJson);
		StringBuilder sb = new StringBuilder();
		JsonUtil.getAllJsonAttrValuesList(jsonList, JSON_ATTR_TO_FETCH, sb);
		return sb.toString();
	}

	private List<WordFrequency> countWords(String comments) {
		// check if this will work or we need something else here
		Map<String, Integer> wordFrequency = new HashMap<String, Integer>();
		String[] words = comments.split("\\W+");
		for (String word : words) {
			String lowerCaseWord = word.toLowerCase().trim();
			if (wordFrequency.containsKey(lowerCaseWord)) {
				int val = wordFrequency.get(lowerCaseWord) + 1;
				wordFrequency.put(lowerCaseWord, val);
			} else {
				if(StringUtil.isValidWord(lowerCaseWord)) {
					wordFrequency.put(lowerCaseWord, 1);
				}
			}
		}

		List<WordFrequency> wordFreqLst = new ArrayList<WordFrequency>();

		for (String word : wordFrequency.keySet()) {
			WordFrequency wf = new WordFrequency();
			wf.setWord(word);
			wf.setFrequency(wordFrequency.get(word));
			wordFreqLst.add(wf);
		}
		return wordFreqLst;

	}

	public List<WordFrequency> getCommentsAnalysis(int id, int count) {
		Pageable page = new PageRequest(0, count);
		return commentRepo.findByCommentIdOrderByFrequencyDesc(id, page);
	}
	
	
}
