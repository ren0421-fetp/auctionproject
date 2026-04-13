package org.fujitsu.training.codes.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.fujitsu.training.codes.model.data.PackagePurchaseRequest;
import org.fujitsu.training.codes.model.data.PackagePurchaseRequestInfo;

public interface PackagePurchaseRequestDao {

    @Insert("""
        insert into package_purchase_request (
            username,
            package_id,
            request_status,
            requested_at,
            remarks
        ) values (
            #{username},
            #{packageId},
            #{requestStatus},
            #{requestedAt},
            #{remarks}
        )
    """)
    @Options(useGeneratedKeys = true, keyProperty = "requestId", keyColumn = "request_id")
    int insertRequest(PackagePurchaseRequest request);

    @Select("""
        select count(*)
        from package_purchase_request
        where username = #{username}
          and request_status = 'PENDING'
    """)
    int countPendingRequestsByUsername(@Param("username") String username);

    @Select("""
        select
            request_id,
            username,
            package_id,
            request_status,
            requested_at,
            reviewed_at,
            reviewed_by,
            remarks
        from package_purchase_request
        where request_id = #{requestId}
    """)
    @Results({
        @Result(property = "requestId", column = "request_id"),
        @Result(property = "username", column = "username"),
        @Result(property = "packageId", column = "package_id"),
        @Result(property = "requestStatus", column = "request_status"),
        @Result(property = "requestedAt", column = "requested_at"),
        @Result(property = "reviewedAt", column = "reviewed_at"),
        @Result(property = "reviewedBy", column = "reviewed_by"),
        @Result(property = "remarks", column = "remarks")
    })
    PackagePurchaseRequest selectRequestById(@Param("requestId") Integer requestId);

    @Select("""
    	    select
    	        r.request_id,
    	        r.username,
    	        p.package_id,
    	        p.package_name,
    	        p.package_price,
    	        p.allowed_bid_count,
    	        p.photo_path,
    	        r.request_status,
    	        r.requested_at,
    	        r.reviewed_at,
    	        r.reviewed_by,
    	        r.remarks
    	    from package_purchase_request r
    	    join package_master p on p.package_id = r.package_id
    	    where r.request_status = 'PENDING'
    	    order by r.request_id desc
    	""")
    @Results({
        @Result(property = "requestId", column = "request_id"),
        @Result(property = "username", column = "username"),
        @Result(property = "packageId", column = "package_id"),
        @Result(property = "packageName", column = "package_name"),
        @Result(property = "packagePrice", column = "package_price"),
        @Result(property = "allowedBidCount", column = "allowed_bid_count"),
        @Result(property = "photoPath", column = "photo_path"),
        @Result(property = "requestStatus", column = "request_status"),
        @Result(property = "requestedAt", column = "requested_at"),
        @Result(property = "reviewedAt", column = "reviewed_at"),
        @Result(property = "reviewedBy", column = "reviewed_by"),
        @Result(property = "remarks", column = "remarks")
    })
    List<PackagePurchaseRequestInfo> selectAllRequestInfos();

    @Update("""
        update package_purchase_request
        set request_status = #{requestStatus},
            reviewed_at = #{reviewedAt},
            reviewed_by = #{reviewedBy},
            remarks = #{remarks}
        where request_id = #{requestId}
          and request_status = 'PENDING'
    """)
    int reviewRequest(@Param("requestId") Integer requestId,
            @Param("requestStatus") String requestStatus,
            @Param("reviewedAt") LocalDateTime reviewedAt,
            @Param("reviewedBy") String reviewedBy,
            @Param("remarks") String remarks);
}
