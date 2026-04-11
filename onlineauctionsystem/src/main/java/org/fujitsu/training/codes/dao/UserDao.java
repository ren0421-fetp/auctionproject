package org.fujitsu.training.codes.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.fujitsu.training.codes.model.data.User;

public interface UserDao {

    @Select("""
        select
            username,
            first_name,
            last_name,
            gender,
            address,
            country_id,
            state_id,
            city_id,
            email,
            contact_no,
            photo_path,
            password_hash,
            user_type,
            is_locked,
            failed_login_attempts
        from user_master
        where username = #{username}
    """)
    @Results({
        @Result(property = "username", column = "username"),
        @Result(property = "firstName", column = "first_name"),
        @Result(property = "lastName", column = "last_name"),
        @Result(property = "gender", column = "gender"),
        @Result(property = "address", column = "address"),
        @Result(property = "countryId", column = "country_id"),
        @Result(property = "stateId", column = "state_id"),
        @Result(property = "cityId", column = "city_id"),
        @Result(property = "email", column = "email"),
        @Result(property = "contactNo", column = "contact_no"),
        @Result(property = "photoPath", column = "photo_path"),
        @Result(property = "passwordHash", column = "password_hash"),
        @Result(property = "userType", column = "user_type"),
        @Result(property = "isLocked", column = "is_locked"),
        @Result(property = "failedLoginAttempts", column = "failed_login_attempts")
    })
    User selectByUsername(@Param("username") String username);

    @Insert("""
        insert into user_master (
            username, first_name, last_name, gender, address,
            country_id, state_id, city_id, email, contact_no,
            photo_path, password_hash, user_type, is_locked, failed_login_attempts
        ) values (
            #{username}, #{firstName}, #{lastName}, #{gender}, #{address},
            #{countryId}, #{stateId}, #{cityId}, #{email}, #{contactNo},
            #{photoPath}, #{passwordHash}, #{userType}, #{isLocked}, #{failedLoginAttempts}
        )
    """)
    boolean insertUser(User user);
    
    @Update("""
            update user_master
            set failed_login_attempts = #{failedLoginAttempts}
            where username = #{username}
        """)
        int updateFailedLoginAttempts(@Param("username") String username,
                @Param("failedLoginAttempts") int failedLoginAttempts);

        @Update("""
            update user_master
            set is_locked = true,
                failed_login_attempts = 3
            where username = #{username}
        """)
        int lockUser(@Param("username") String username);

        @Update("""
            update user_master
            set failed_login_attempts = 0
            where username = #{username}
        """)
        int resetFailedLoginAttempts(@Param("username") String username);
}
