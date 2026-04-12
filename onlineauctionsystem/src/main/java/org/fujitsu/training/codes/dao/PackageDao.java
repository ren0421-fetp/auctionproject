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

    @Insert("""
        insert into package_master (
            package_name,
            package_price,
            allowed_bid_count,
            photo_path
        ) values (
            #{packageName},
            #{packagePrice},
            #{allowedBidCount},
            #{photoPath}
        )
    """)
    @Options(useGeneratedKeys = true, keyProperty = "packageId", keyColumn = "package_id")
    int insertPackage(Package pkg);

    @Update("""
        update package_master
        set package_name = #{packageName},
            package_price = #{packagePrice},
            allowed_bid_count = #{allowedBidCount},
            photo_path = #{photoPath}
        where package_id = #{packageId}
    """)
    int updatePackage(Package pkg);

    @Delete("""
        delete from package_master
        where package_id = #{packageId}
    """)
    int deletePackageById(@Param("packageId") Integer packageId);

    @Select("""
            <script>
            select count(*)
            from package_master
            where lower(package_name) = lower(#{packageName})
            <if test="packageId != null">
                and package_id &lt;&gt; #{packageId}
            </if>
            </script>
        """)
        int countPackageByName(@Param("packageName") String packageName,
                @Param("packageId") Integer packageId);


    @Select("""
        select count(*)
        from user_package_master
        where package_id = #{packageId}
    """)
    int countUsersUsingPackage(@Param("packageId") Integer packageId);
}
