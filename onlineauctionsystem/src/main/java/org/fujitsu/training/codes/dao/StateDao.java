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
import org.fujitsu.training.codes.model.data.State;

public interface StateDao {

	@Select("""
			    select state_id, country_id, state_name
			    from state_master
			    order by state_name
			""")
	@Results({ @Result(property = "stateId", column = "state_id"),
			@Result(property = "countryId", column = "country_id"),
			@Result(property = "stateName", column = "state_name") })
	List<State> selectAllStates();

	@Select("""
			    select state_id, country_id, state_name
			    from state_master
			    where state_id = #{stateId}
			""")
	@Results({ @Result(property = "stateId", column = "state_id"),
			@Result(property = "countryId", column = "country_id"),
			@Result(property = "stateName", column = "state_name") })
	State selectStateById(@Param("stateId") Integer stateId);

	@Insert("""
			    insert into state_master (country_id, state_name)
			    values (#{countryId}, #{stateName})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "stateId", keyColumn = "state_id")
	int insertState(State state);

	@Update("""
			    update state_master
			    set country_id = #{countryId},
			        state_name = #{stateName}
			    where state_id = #{stateId}
			""")
	int updateState(State state);

	@Delete("""
			    delete from state_master
			    where state_id = #{stateId}
			""")
	int deleteStateById(@Param("stateId") Integer stateId);

	@Select("""
			    select count(*)
			    from state_master
			    where state_id = #{stateId}
			      and country_id = #{countryId}
			""")
	int countStateByCountry(@Param("stateId") Integer stateId, @Param("countryId") Integer countryId);

	@Select("""
			    select count(*)
			    from city_master
			    where state_id = #{stateId}
			""")
	int countCitiesByStateId(@Param("stateId") Integer stateId);

	@Select("""
			    select count(*)
			    from user_master
			    where state_id = #{stateId}
			""")
	int countUsersByStateId(@Param("stateId") Integer stateId);

	@Select("""
			    <script>
			    select count(*)
			    from state_master
			    where lower(state_name) = lower(#{stateName})
			      and country_id = #{countryId}
			    <if test="stateId != null">
			        and state_id &lt;&gt; #{stateId}
			    </if>
			    </script>
			""")
	int countStateByName(@Param("stateName") String stateName, @Param("countryId") Integer countryId,
			@Param("stateId") Integer stateId);
}
