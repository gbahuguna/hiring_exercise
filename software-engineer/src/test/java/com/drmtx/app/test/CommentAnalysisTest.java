package com.drmtx.app.test;

import org.junit.Before;

////import org.springframework.beans.factory.annotation.Autowired;
////import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
////import org.springframework.test.context.TestContext;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
////import org.springframework.test.web.servlet.setup.MockMvcBuilders;
////import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.hamcrest.Matchers.*;
//import static org.hamcrest.Matchers.is;
//import static org.mockito.Mockito.*;
////import com.drmtx.app.api.CommentAnalysisController;
////import com.drmtx.app.services.CommentAnalysisManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.drmtx.app.api.CommentAnalysisController;
import com.drmtx.app.services.CommentAnalysisManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@WebAppConfiguration
public class CommentAnalysisTest {
	 
	    private MockMvc mockMvc;
	    private static final String COMMENT_URL = "/frequency/new?commentUrl=https://www.reddit.com/r/java/comments/3v93h6/java_magazine_novdec_2014/.json";
	    @Mock
	    private CommentAnalysisManager manager;
	    
	    @Autowired
	    private CommentAnalysisController commentAnalysis;
	    
	    @Before
	    public void setup() {
	    	this.mockMvc = MockMvcBuilders.standaloneSetup(commentAnalysis).build();
	    }
	    @Test
	    public void saveCommentNewValid() throws Exception {
	    	this.mockMvc.perform(get(COMMENT_URL))
	    	         .andExpect(status().is2xxSuccessful())
	    	         .andExpect(content().contentType("text/plain"));
	    	        
	    }
	    @Test
	    public void getFrequencyValid() throws Exception {
	    	this.mockMvc.perform(get("/frequency/1?count=2"))
	    	         .andExpect(status().is2xxSuccessful());
	    	        
	    }
	    
	    
	    
	    
}
