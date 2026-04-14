package org.fujitsu.training.codes.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PackageUserDao {

	@Select("""
			    select total_package_bid
			    from package_user_master
			    where username = #{username}
			""")
	Integer selectRemainingBidCountByUsername(@Param("username") String username);

	@Update("""
			    update package_user_master
			    set total_package_bid = total_package_bid - 1
			    where username = #{username}
			      and total_package_bid > 0
			""")
	int decrementBidCountByUsername(@Param("username") String username);

	@Select("""
			    select count(*)
			    from package_user_master
			    where username = #{username}
			""")
	int countBalanceRowsByUsername(@Param("username") String username);

	@Insert("""
			    insert into package_user_master (
			        username,
			        total_package_bid
			    ) values (
			        #{username},
			        #{totalPackageBid}
			    )
			""")
	int insertBidBalance(@Param("username") String username, @Param("totalPackageBid") Integer totalPackageBid);

	@Update("""
			    update package_user_master
			    set total_package_bid = total_package_bid + #{additionalBidCount}
			    where username = #{username}
			""")
	int addBidCountByUsername(@Param("username") String username,
			@Param("additionalBidCount") Integer additionalBidCount);

}
