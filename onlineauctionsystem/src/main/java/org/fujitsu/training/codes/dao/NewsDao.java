package org.fujitsu.training.codes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.fujitsu.training.codes.model.data.News;

public interface NewsDao {

	@Select("""
			    select news_id, news_title, news_content
			    from news_master
			    order by news_id desc
			""")
	@Results({ @Result(property = "newsId", column = "news_id"), @Result(property = "newsTitle", column = "news_title"),
			@Result(property = "newsContent", column = "news_content") })
	List<News> selectAllNews();

	@Select("""
			    select news_id, news_title, news_content
			    from news_master
			    where news_id = #{newsId}
			""")
	@Results({ @Result(property = "newsId", column = "news_id"), @Result(property = "newsTitle", column = "news_title"),
			@Result(property = "newsContent", column = "news_content") })
	News selectNewsById(@Param("newsId") Integer newsId);

	@Insert("""
			    insert into news_master (
			        news_title,
			        news_content
			    ) values (
			        #{newsTitle},
			        #{newsContent}
			    )
			""")
	@Options(useGeneratedKeys = true, keyProperty = "newsId", keyColumn = "news_id")
	int insertNews(News news);

	@Update("""
			    update news_master
			    set news_title = #{newsTitle},
			        news_content = #{newsContent}
			    where news_id = #{newsId}
			""")
	int updateNews(News news);

	@Delete("""
			    delete from news_master
			    where news_id = #{newsId}
			""")
	int deleteNewsById(@Param("newsId") Integer newsId);

	@Select("""
			    <script>
			    select count(*)
			    from news_master
			    where lower(news_title) = lower(#{newsTitle})
			    <if test="newsId != null">
			        and news_id &lt;&gt; #{newsId}
			    </if>
			    </script>
			""")
	int countNewsByTitle(@Param("newsTitle") String newsTitle, @Param("newsId") Integer newsId);
}
