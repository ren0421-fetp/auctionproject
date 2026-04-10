package org.fujitsu.training.codes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.fujitsu.training.codes.model.data.State;

public interface StateDao {

    @Select("""
        select state_id, country_id, state_name
        from state_master
        order by state_name
    """)
    @Results({
        @Result(property = "stateId", column = "state_id"),
        @Result(property = "countryId", column = "country_id"),
        @Result(property = "stateName", column = "state_name")
    })
    List<State> selectAllStates();

    @Select("""
        select count(*)
        from state_master
        where state_id = #{stateId}
          and country_id = #{countryId}
    """)
    int countStateByCountry(@Param("stateId") Integer stateId, @Param("countryId") Integer countryId);
}
