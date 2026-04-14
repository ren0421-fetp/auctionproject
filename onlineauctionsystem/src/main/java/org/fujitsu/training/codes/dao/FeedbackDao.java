package org.fujitsu.training.codes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.fujitsu.training.codes.model.data.Feedback;

public interface FeedbackDao {

	@Insert("""
			    insert into feedback_master (
			        firstname,
			        email,
			        contact,
			        subject,
			        msg
			    ) values (
			        #{firstName},
			        #{email},
			        #{contact},
			        #{subject},
			        #{msg}
			    )
			""")
	@Options(useGeneratedKeys = true, keyProperty = "feedbackId", keyColumn = "f_id")
	int insertFeedback(Feedback feedback);

	@Select("""
			    select
			        f_id,
			        firstname,
			        email,
			        contact,
			        subject,
			        msg
			    from feedback_master
			    order by f_id desc
			""")
	@Results({ @Result(property = "feedbackId", column = "f_id"), @Result(property = "firstName", column = "firstname"),
			@Result(property = "email", column = "email"), @Result(property = "contact", column = "contact"),
			@Result(property = "subject", column = "subject"), @Result(property = "msg", column = "msg") })
	List<Feedback> selectAllFeedback();
}
