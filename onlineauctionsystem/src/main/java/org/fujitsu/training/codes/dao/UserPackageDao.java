package org.fujitsu.training.codes.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

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
}
