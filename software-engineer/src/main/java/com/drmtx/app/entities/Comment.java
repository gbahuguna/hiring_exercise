package com.drmtx.app.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Comments")
public class Comment {
@Id
@Column(name="id", nullable=false)
@GeneratedValue(strategy = GenerationType.AUTO)
private int id;

@Column(name="comment_url", length=6000)
private String commentUrl;

@OneToMany(cascade=CascadeType.ALL)
private List<WordFrequency> wordFrequency;

public void setCommentUrl(String url) {
	commentUrl = url;
}

public String getCommentUrl() {
	return commentUrl;
}

public void setWordFrequency(List<WordFrequency> words) {
	wordFrequency = words;
}

public List<WordFrequency> getWordFrequency() {
	return wordFrequency;
}

public int getId() {
	return id;
}
}
