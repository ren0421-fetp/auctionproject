package org.fujitsu.training.codes.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.fujitsu.training.codes.model.data.BidConfirm;

public interface BidConfirmDao {

	@Select("""
			    select count(*)
			    from bidconfirm_master bc
			    join bid_master b on b.bid_id = bc.bid_id
			    where b.product_id = #{productId}
			""")
	int countConfirmedByProductId(@Param("productId") Integer productId);

	@Insert("""
			    insert into bidconfirm_master (
			        bid_id,
			        winner_username,
			        confirmed_price,
			        confirmed_at
			    ) values (
			        #{bidId},
			        #{winnerUsername},
			        #{confirmedPrice},
			        #{confirmedAt}
			    )
			""")
	@Options(useGeneratedKeys = true, keyProperty = "confirmBidId", keyColumn = "confirm_bid_id")
	int insertBidConfirmation(BidConfirm bidConfirm);
}
