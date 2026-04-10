package org.fujitsu.training.codes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.fujitsu.training.codes.model.data.Country;

public interface CountryDao {

    @Select("""
        select country_id, country_name
        from country_master
        order by country_name
    """)
    @Results({
        @Result(property = "countryId", column = "country_id"),
        @Result(property = "countryName", column = "country_name")
    })
    List<Country> selectAllCountries();
}
