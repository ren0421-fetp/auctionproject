package org.fujitsu.training.codes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.fujitsu.training.codes.model.data.Bid;
import org.fujitsu.training.codes.model.data.ConfirmBidReportRow;
import org.fujitsu.training.codes.model.data.ProductReportRow;
import org.fujitsu.training.codes.model.data.UserReportRow;

public interface ReportDao {

	@Select("""
			    select
			        username,
			        first_name,
			        last_name,
			        email,
			        user_type,
			        is_locked
			    from user_master
			    order by username
			""")
	@Results({ @Result(property = "username", column = "username"),
			@Result(property = "firstName", column = "first_name"),
			@Result(property = "lastName", column = "last_name"), @Result(property = "email", column = "email"),
			@Result(property = "userType", column = "user_type"),
			@Result(property = "isLocked", column = "is_locked") })
	List<UserReportRow> selectUserRegistrationReport();

	@Select("""
			    select
			        p.product_id,
			        p.product_name,
			        p.seller_username,
			        c.cat_name,
			        p.min_bid_price,
			        p.status,
			        p.start_date,
			        p.end_date
			    from product_master p
			    join category_master c on c.cat_id = p.cat_id
			    order by p.product_id desc
			""")
	@Results({ @Result(property = "productId", column = "product_id"),
			@Result(property = "productName", column = "product_name"),
			@Result(property = "sellerUsername", column = "seller_username"),
			@Result(property = "categoryName", column = "cat_name"),
			@Result(property = "minBidPrice", column = "min_bid_price"),
			@Result(property = "status", column = "status"), @Result(property = "startDate", column = "start_date"),
			@Result(property = "endDate", column = "end_date") })
	List<ProductReportRow> selectAuctionItemReport();

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
			    order by b.bid_id desc
			""")
	@Results({ @Result(property = "bidId", column = "bid_id"), @Result(property = "productId", column = "product_id"),
			@Result(property = "bidderUsername", column = "bidder_username"),
			@Result(property = "bidDate", column = "bid_date"), @Result(property = "bidPrice", column = "bid_price"),
			@Result(property = "productName", column = "product_name"),
			@Result(property = "minBidPrice", column = "min_bid_price"),
			@Result(property = "productPhotoPath", column = "photo_path"),
			@Result(property = "sellerUsername", column = "seller_username") })
	List<Bid> selectBidReport();

	@Select("""
			    select
			        bc.confirm_bid_id,
			        bc.bid_id,
			        b.product_id,
			        p.product_name,
			        bc.winner_username,
			        bc.confirmed_price,
			        bc.confirmed_at
			    from bidconfirm_master bc
			    join bid_master b on b.bid_id = bc.bid_id
			    join product_master p on p.product_id = b.product_id
			    order by bc.confirm_bid_id desc
			""")
	@Results({ @Result(property = "confirmBidId", column = "confirm_bid_id"),
			@Result(property = "bidId", column = "bid_id"), @Result(property = "productId", column = "product_id"),
			@Result(property = "productName", column = "product_name"),
			@Result(property = "winnerUsername", column = "winner_username"),
			@Result(property = "confirmedPrice", column = "confirmed_price"),
			@Result(property = "confirmedAt", column = "confirmed_at") })
	List<ConfirmBidReportRow> selectConfirmBidReport();
}
