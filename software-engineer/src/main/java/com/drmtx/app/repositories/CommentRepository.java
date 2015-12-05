package com.drmtx.app.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.drmtx.app.entities.Comment;
import com.drmtx.app.entities.WordFrequency;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	 @Query("select wf FROM Comment comm join comm.wordFrequency wf WHERE comm.id = :commentId ORDER BY wf.frequency DESC")
		public List<WordFrequency> findByCommentIdOrderByFrequencyDesc(@Param("commentId")int commentId, Pageable page);
}
