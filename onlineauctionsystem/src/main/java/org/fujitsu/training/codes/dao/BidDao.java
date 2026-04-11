package org.fujitsu.training.codes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.fujitsu.training.codes.model.data.Bid;

public interface BidDao {

    @Select("""
        select count(*)
        from bid_master
        where product_id = #{productId}
    """)
    int countBidsByProductId(@Param("productId") Integer productId);

    @Select("""
        select
            b.bid_id,
            b.product_id,
            b.bidder_username,
            b.bid_date,
            b.bid_price,
            p.product_name,
            p.min_bid_price,
            p.photo_path,
            p.seller_username
        from bid_master b
        join product_master p on p.product_id = b.product_id
        where p.seller_username = #{sellerUsername}
        order by b.bid_date desc, b.bid_id desc
    """)
    @Results({
        @Result(property = "bidId", column = "bid_id"),
        @Result(property = "productId", column = "product_id"),
        @Result(property = "bidderUsername", column = "bidder_username"),
        @Result(property = "bidDate", column = "bid_date"),
        @Result(property = "bidPrice", column = "bid_price"),
        @Result(property = "productName", column = "product_name"),
        @Result(property = "minBidPrice", column = "min_bid_price"),
        @Result(property = "productPhotoPath", column = "photo_path"),
        @Result(property = "sellerUsername", column = "seller_username")
    })
    List<Bid> selectBidsBySeller(@Param("sellerUsername") String sellerUsername);
}
