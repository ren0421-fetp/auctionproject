package org.fujitsu.training.codes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.fujitsu.training.codes.model.data.City;

public interface CityDao {

    @Select("""
        select city_id, state_id, city_name
        from city_master
        order by city_name
    """)
    @Results({
        @Result(property = "cityId", column = "city_id"),
        @Result(property = "stateId", column = "state_id"),
        @Result(property = "cityName", column = "city_name")
    })
    List<City> selectAllCities();

    @Select("""
        select count(*)
        from city_master
        where city_id = #{cityId}
          and state_id = #{stateId}
    """)
    int countCityByState(@Param("cityId") Integer cityId, @Param("stateId") Integer stateId);
}
