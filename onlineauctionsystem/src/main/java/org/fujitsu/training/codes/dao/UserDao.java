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
        
        @Update("""
                update user_master
                set first_name = #{firstName},
                    last_name = #{lastName},
                    gender = #{gender},
                    address = #{address},
                    country_id = #{countryId},
                    state_id = #{stateId},
                    city_id = #{cityId},
                    email = #{email},
                    contact_no = #{contactNo},
                    photo_path = #{photoPath}
                where username = #{username}
                  and user_type = 'seller'
            """)
            int updateSellerProfile(User user);

            @Update("""
                update user_master
                set password_hash = #{passwordHash}
                where username = #{username}
                  and user_type = 'seller'
            """)
            int updateSellerPassword(@Param("username") String username,
                    @Param("passwordHash") String passwordHash);
            
            @Select("""
                    select
                        username,
                        first_name,
                        last_name,
                        email,
                        user_type
                    from user_master
                    where user_type = #{userType}
                    order by username
                """)
                @Results({
                    @Result(property = "username", column = "username"),
                    @Result(property = "firstName", column = "first_name"),
                    @Result(property = "lastName", column = "last_name"),
                    @Result(property = "email", column = "email"),
                    @Result(property = "userType", column = "user_type")
                })
                java.util.List<User> selectUsersByType(@Param("userType") String userType);
            
            @Select("""
                    <script>
                    select
                        u.username,
                        u.first_name,
                        u.last_name,
                        u.gender,
                        u.address,
                        u.country_id,
                        u.state_id,
                        u.city_id,
                        c.city_name,
                        u.email,
                        u.contact_no,
                        u.photo_path,
                        u.user_type,
                        u.is_locked,
                        u.failed_login_attempts
                    from user_master u
                    left join city_master c on c.city_id = u.city_id
                    <if test="userType != null and userType != ''">
                        where u.user_type = #{userType}
                    </if>
                    order by u.username
                    </script>
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
                    @Result(property = "cityName", column = "city_name"),
                    @Result(property = "email", column = "email"),
                    @Result(property = "contactNo", column = "contact_no"),
                    @Result(property = "photoPath", column = "photo_path"),
                    @Result(property = "userType", column = "user_type"),
                    @Result(property = "isLocked", column = "is_locked"),
                    @Result(property = "failedLoginAttempts", column = "failed_login_attempts")
                })
                java.util.List<User> selectAllUsers(@Param("userType") String userType);

                @Update("""
                    update user_master
                    set is_locked = false,
                        failed_login_attempts = 0
                    where username = #{username}
                """)
                int unlockUser(@Param("username") String username);

}
