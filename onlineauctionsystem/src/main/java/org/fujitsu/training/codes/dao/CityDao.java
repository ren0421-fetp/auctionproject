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
import org.fujitsu.training.codes.model.data.City;

public interface CityDao {

	@Select("""
			    select city_id, state_id, city_name
			    from city_master
			    order by city_name
			""")
	@Results({ @Result(property = "cityId", column = "city_id"), @Result(property = "stateId", column = "state_id"),
			@Result(property = "cityName", column = "city_name") })
	List<City> selectAllCities();

	@Select("""
			    select city_id, state_id, city_name
			    from city_master
			    where city_id = #{cityId}
			""")
	@Results({ @Result(property = "cityId", column = "city_id"), @Result(property = "stateId", column = "state_id"),
			@Result(property = "cityName", column = "city_name") })
	City selectCityById(@Param("cityId") Integer cityId);

	@Insert("""
			    insert into city_master (state_id, city_name)
			    values (#{stateId}, #{cityName})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "cityId", keyColumn = "city_id")
	int insertCity(City city);

	@Update("""
			    update city_master
			    set state_id = #{stateId},
			        city_name = #{cityName}
			    where city_id = #{cityId}
			""")
	int updateCity(City city);

	@Delete("""
			    delete from city_master
			    where city_id = #{cityId}
			""")
	int deleteCityById(@Param("cityId") Integer cityId);

	@Select("""
			    select count(*)
			    from city_master
			    where city_id = #{cityId}
			      and state_id = #{stateId}
			""")
	int countCityByState(@Param("cityId") Integer cityId, @Param("stateId") Integer stateId);

	@Select("""
			    select count(*)
			    from user_master
			    where city_id = #{cityId}
			""")
	int countUsersByCityId(@Param("cityId") Integer cityId);

	@Select("""
			    <script>
			    select count(*)
			    from city_master
			    where lower(city_name) = lower(#{cityName})
			      and state_id = #{stateId}
			    <if test="cityId != null">
			        and city_id &lt;&gt; #{cityId}
			    </if>
			    </script>
			""")
	int countCityByName(@Param("cityName") String cityName, @Param("stateId") Integer stateId,
			@Param("cityId") Integer cityId);
}
