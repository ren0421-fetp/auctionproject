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
import org.fujitsu.training.codes.model.data.Category;

public interface CategoryDao {

	@Select("""
			    select cat_id, cat_name
			    from category_master
			    order by cat_name
			""")
	@Results({ @Result(property = "catId", column = "cat_id"), @Result(property = "catName", column = "cat_name") })
	List<Category> selectAllCategories();

	@Select("""
			    select count(*)
			    from category_master
			    where cat_id = #{catId}
			""")
	int countCategoryById(Integer catId);

	@Select("""
			    select cat_id, cat_name
			    from category_master
			    where cat_id = #{catId}
			""")
	@Results({ @Result(property = "catId", column = "cat_id"), @Result(property = "catName", column = "cat_name") })
	Category selectCategoryById(@Param("catId") Integer catId);

	@Insert("""
			    insert into category_master (
			        cat_name
			    ) values (
			        #{catName}
			    )
			""")
	@Options(useGeneratedKeys = true, keyProperty = "catId", keyColumn = "cat_id")
	int insertCategory(Category category);

	@Update("""
			    update category_master
			    set cat_name = #{catName}
			    where cat_id = #{catId}
			""")
	int updateCategory(Category category);

	@Delete("""
			    delete from category_master
			    where cat_id = #{catId}
			""")
	int deleteCategoryById(@Param("catId") Integer catId);

	@Select("""
			    select count(*)
			    from product_master
			    where cat_id = #{catId}
			""")
	int countProductsUsingCategory(@Param("catId") Integer catId);

	@Select("""
			    <script>
			    select count(*)
			    from category_master
			    where lower(cat_name) = lower(#{catName})
			    <if test="catId != null">
			        and cat_id &lt;&gt; #{catId}
			    </if>
			    </script>
			""")
	int countCategoryByName(@Param("catName") String catName, @Param("catId") Integer catId);

}
