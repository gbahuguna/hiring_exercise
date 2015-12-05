package com.drmtx.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="WordFrequency")
public class WordFrequency {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

//	@Column(name="comment_id")
//	private long commentId;
//	
	 @JsonIgnore
	@ManyToOne
	@JoinColumn(name="comment_id", insertable=false, updatable=false)
	private  Comment comment;
	
	
	private String word;
	
	private int frequency;
	
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	
	public Comment getComment() {
		return comment;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public String getWord() {
		return word;
	}
	
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	public int getFrequency() {
		return frequency;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{").append("word").append(":").append(word);
		sb.append(",").append("count").append(":").append(frequency).append("}");
		return sb.toString();
	}
	
	
}
