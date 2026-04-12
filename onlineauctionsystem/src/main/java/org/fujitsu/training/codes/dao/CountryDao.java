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

    @Select("""
        select country_id, country_name
        from country_master
        where country_id = #{countryId}
    """)
    @Results({
        @Result(property = "countryId", column = "country_id"),
        @Result(property = "countryName", column = "country_name")
    })
    Country selectCountryById(@Param("countryId") Integer countryId);

    @Insert("""
        insert into country_master (country_name)
        values (#{countryName})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "countryId", keyColumn = "country_id")
    int insertCountry(Country country);

    @Update("""
        update country_master
        set country_name = #{countryName}
        where country_id = #{countryId}
    """)
    int updateCountry(Country country);

    @Delete("""
        delete from country_master
        where country_id = #{countryId}
    """)
    int deleteCountryById(@Param("countryId") Integer countryId);

    @Select("""
        select count(*)
        from state_master
        where country_id = #{countryId}
    """)
    int countStatesByCountryId(@Param("countryId") Integer countryId);

    @Select("""
        select count(*)
        from user_master
        where country_id = #{countryId}
    """)
    int countUsersByCountryId(@Param("countryId") Integer countryId);

    @Select("""
        <script>
        select count(*)
        from country_master
        where lower(country_name) = lower(#{countryName})
        <if test="countryId != null">
            and country_id &lt;&gt; #{countryId}
        </if>
        </script>
    """)
    int countCountryByName(@Param("countryName") String countryName,
            @Param("countryId") Integer countryId);
}
