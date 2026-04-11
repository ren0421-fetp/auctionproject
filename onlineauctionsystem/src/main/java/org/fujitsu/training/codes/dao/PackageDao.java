package org.fujitsu.training.codes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.fujitsu.training.codes.model.data.Package;

public interface PackageDao {

    @Select("""
        select
            package_id,
            package_name,
            package_price,
            allowed_bid_count,
            photo_path
        from package_master
        order by package_id
    """)
    @Results({
        @Result(property = "packageId", column = "package_id"),
        @Result(property = "packageName", column = "package_name"),
        @Result(property = "packagePrice", column = "package_price"),
        @Result(property = "allowedBidCount", column = "allowed_bid_count"),
        @Result(property = "photoPath", column = "photo_path")
    })
    List<Package> selectAllPackages();

    @Select("""
        select
            package_id,
            package_name,
            package_price,
            allowed_bid_count,
            photo_path
        from package_master
        where package_id = #{packageId}
    """)
    @Results({
        @Result(property = "packageId", column = "package_id"),
        @Result(property = "packageName", column = "package_name"),
        @Result(property = "packagePrice", column = "package_price"),
        @Result(property = "allowedBidCount", column = "allowed_bid_count"),
        @Result(property = "photoPath", column = "photo_path")
    })
    Package selectPackageById(@Param("packageId") Integer packageId);
}
