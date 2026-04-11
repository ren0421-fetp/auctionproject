package org.fujitsu.training.codes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.fujitsu.training.codes.model.data.Category;

public interface CategoryDao {

    @Select("""
        select cat_id, cat_name
        from category_master
        order by cat_name
    """)
    @Results({
        @Result(property = "catId", column = "cat_id"),
        @Result(property = "catName", column = "cat_name")
    })
    List<Category> selectAllCategories();

    @Select("""
        select count(*)
        from category_master
        where cat_id = #{catId}
    """)
    int countCategoryById(Integer catId);
}
