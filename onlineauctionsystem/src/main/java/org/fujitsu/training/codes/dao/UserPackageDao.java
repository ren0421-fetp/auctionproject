package org.fujitsu.training.codes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.fujitsu.training.codes.model.data.UserPackageInfo;

public interface UserPackageDao {

    @Insert("""
        insert into user_package_master (
            username,
            package_id
        ) values (
            #{username},
            #{packageId}
        )
    """)
    int insertUserPackage(@Param("username") String username,
            @Param("packageId") Integer packageId);

    @Select("""
        select
            up.user_package_id,
            up.username,
            p.package_id,
            p.package_name,
            p.package_price,
            p.allowed_bid_count,
            p.photo_path,
            pu.total_package_bid as remaining_bid_count
        from user_package_master up
        join package_master p on p.package_id = up.package_id
        left join package_user_master pu on pu.username = up.username
        order by up.user_package_id desc
    """)
    @Results({
        @Result(property = "userPackageId", column = "user_package_id"),
        @Result(property = "username", column = "username"),
        @Result(property = "packageId", column = "package_id"),
        @Result(property = "packageName", column = "package_name"),
        @Result(property = "packagePrice", column = "package_price"),
        @Result(property = "allowedBidCount", column = "allowed_bid_count"),
        @Result(property = "photoPath", column = "photo_path"),
        @Result(property = "remainingBidCount", column = "remaining_bid_count")
    })
    List<UserPackageInfo> selectAllUserPackageInfos();

    @Select("""
        select
            up.user_package_id,
            up.username,
            p.package_id,
            p.package_name,
            p.package_price,
            p.allowed_bid_count,
            p.photo_path,
            pu.total_package_bid as remaining_bid_count
        from user_package_master up
        join package_master p on p.package_id = up.package_id
        left join package_user_master pu on pu.username = up.username
        where up.user_package_id = #{userPackageId}
    """)
    @Results({
        @Result(property = "userPackageId", column = "user_package_id"),
        @Result(property = "username", column = "username"),
        @Result(property = "packageId", column = "package_id"),
        @Result(property = "packageName", column = "package_name"),
        @Result(property = "packagePrice", column = "package_price"),
        @Result(property = "allowedBidCount", column = "allowed_bid_count"),
        @Result(property = "photoPath", column = "photo_path"),
        @Result(property = "remainingBidCount", column = "remaining_bid_count")
    })
    UserPackageInfo selectUserPackageInfoById(@Param("userPackageId") Integer userPackageId);
}
