package org.fujitsu.training.codes.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.fujitsu.training.codes.model.data.Product;

public interface ProductDao {

	@Insert("""
			    insert into product_master (
			        cat_id,
			        seller_username,
			        product_name,
			        description,
			        min_bid_price,
			        status,
			        photo_path,
			        start_date,
			        end_date
			    ) values (
			        #{catId},
			        #{sellerUsername},
			        #{productName},
			        #{description},
			        #{minBidPrice},
			        #{status},
			        #{photoPath},
			        #{startDate},
			        #{endDate}
			    )
			""")
	@Options(useGeneratedKeys = true, keyProperty = "productId", keyColumn = "product_id")
	int insertProduct(Product product);

	@Select("""
			    select
			        p.product_id,
			        p.cat_id,
			        p.seller_username,
			        p.product_name,
			        p.description,
			        p.min_bid_price,
			        p.status,
			        p.photo_path,
			        p.start_date,
			        p.end_date,
			        c.cat_name
			    from product_master p
			    join category_master c on c.cat_id = p.cat_id
			    where p.seller_username = #{sellerUsername}
			    order by p.product_id desc
			""")
	@Results({ @Result(property = "productId", column = "product_id"), @Result(property = "catId", column = "cat_id"),
			@Result(property = "sellerUsername", column = "seller_username"),
			@Result(property = "productName", column = "product_name"),
			@Result(property = "description", column = "description"),
			@Result(property = "minBidPrice", column = "min_bid_price"),
			@Result(property = "status", column = "status"), @Result(property = "photoPath", column = "photo_path"),
			@Result(property = "startDate", column = "start_date"), @Result(property = "endDate", column = "end_date"),
			@Result(property = "categoryName", column = "cat_name") })
	List<Product> selectProductsBySeller(@Param("sellerUsername") String sellerUsername);

	@Select("""
			    select
			        p.product_id,
			        p.cat_id,
			        p.seller_username,
			        p.product_name,
			        p.description,
			        p.min_bid_price,
			        p.status,
			        p.photo_path,
			        p.start_date,
			        p.end_date,
			        c.cat_name
			    from product_master p
			    join category_master c on c.cat_id = p.cat_id
			    where p.product_id = #{productId}
			      and p.seller_username = #{sellerUsername}
			""")
	@Results({ @Result(property = "productId", column = "product_id"), @Result(property = "catId", column = "cat_id"),
			@Result(property = "sellerUsername", column = "seller_username"),
			@Result(property = "productName", column = "product_name"),
			@Result(property = "description", column = "description"),
			@Result(property = "minBidPrice", column = "min_bid_price"),
			@Result(property = "status", column = "status"), @Result(property = "photoPath", column = "photo_path"),
			@Result(property = "startDate", column = "start_date"), @Result(property = "endDate", column = "end_date"),
			@Result(property = "categoryName", column = "cat_name") })
	Product selectProductByIdAndSeller(@Param("productId") Integer productId,
			@Param("sellerUsername") String sellerUsername);

	@Update("""
			    <script>
			    update product_master
			    set cat_id = #{catId},
			        product_name = #{productName},
			        description = #{description},
			        min_bid_price = #{minBidPrice},
			        status = #{status},
			        start_date = #{startDate},
			        end_date = #{endDate}
			        <if test="photoPath != null and photoPath != ''">
			            , photo_path = #{photoPath}
			        </if>
			    where product_id = #{productId}
			      and seller_username = #{sellerUsername}
			    </script>
			""")
	int updateProduct(Product product);

	@Delete("""
			    delete from product_master
			    where product_id = #{productId}
			      and seller_username = #{sellerUsername}
			""")
	int deleteProductByIdAndSeller(@Param("productId") Integer productId,
			@Param("sellerUsername") String sellerUsername);

	@Select("""
			    <script>
			    select
			        p.product_id,
			        p.cat_id,
			        p.seller_username,
			        p.product_name,
			        p.description,
			        p.min_bid_price,
			        p.status,
			        p.photo_path,
			        p.start_date,
			        p.end_date,
			        c.cat_name,
			        max(b.bid_price) as current_highest_bid,
			        case
			            when current_timestamp &lt; p.start_date then 'Upcoming'
			            else 'Active'
			        end as auction_phase
			    from product_master p
			    join category_master c on c.cat_id = p.cat_id
			    left join bid_master b on b.product_id = p.product_id
			    where p.status = 'open'
			      and p.end_date &gt;= current_timestamp
			      <if test="keyword != null and keyword != ''">
			          and lower(p.product_name) like lower(concat('%', #{keyword}, '%'))
			      </if>
			      <if test="catId != null">
			          and p.cat_id = #{catId}
			      </if>
			      <if test="minPrice != null">
			          and p.min_bid_price &gt;= #{minPrice}
			      </if>
			      <if test="maxPrice != null">
			          and p.min_bid_price &lt;= #{maxPrice}
			      </if>
			    group by
			        p.product_id,
			        p.cat_id,
			        p.seller_username,
			        p.product_name,
			        p.description,
			        p.min_bid_price,
			        p.status,
			        p.photo_path,
			        p.start_date,
			        p.end_date,
			        c.cat_name
			    order by
			        p.start_date asc,
			        p.product_id desc
			    </script>
			""")
	@Results({ @Result(property = "productId", column = "product_id"), @Result(property = "catId", column = "cat_id"),
			@Result(property = "sellerUsername", column = "seller_username"),
			@Result(property = "productName", column = "product_name"),
			@Result(property = "description", column = "description"),
			@Result(property = "minBidPrice", column = "min_bid_price"),
			@Result(property = "status", column = "status"), @Result(property = "photoPath", column = "photo_path"),
			@Result(property = "startDate", column = "start_date"), @Result(property = "endDate", column = "end_date"),
			@Result(property = "categoryName", column = "cat_name"),
			@Result(property = "currentHighestBid", column = "current_highest_bid"),
			@Result(property = "auctionPhase", column = "auction_phase") })
	List<Product> selectOpenProducts(@Param("keyword") String keyword, @Param("catId") Integer catId,
			@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

	@Select("""
			    select
			        p.product_id,
			        p.cat_id,
			        p.seller_username,
			        p.product_name,
			        p.description,
			        p.min_bid_price,
			        p.status,
			        p.photo_path,
			        p.start_date,
			        p.end_date,
			        c.cat_name,
			        max(b.bid_price) as current_highest_bid
			    from product_master p
			    join category_master c on c.cat_id = p.cat_id
			    left join bid_master b on b.product_id = p.product_id
			    where p.product_id = #{productId}
			    group by
			        p.product_id,
			        p.cat_id,
			        p.seller_username,
			        p.product_name,
			        p.description,
			        p.min_bid_price,
			        p.status,
			        p.photo_path,
			        p.start_date,
			        p.end_date,
			        c.cat_name
			""")
	@Results({ @Result(property = "productId", column = "product_id"), @Result(property = "catId", column = "cat_id"),
			@Result(property = "sellerUsername", column = "seller_username"),
			@Result(property = "productName", column = "product_name"),
			@Result(property = "description", column = "description"),
			@Result(property = "minBidPrice", column = "min_bid_price"),
			@Result(property = "status", column = "status"), @Result(property = "photoPath", column = "photo_path"),
			@Result(property = "startDate", column = "start_date"), @Result(property = "endDate", column = "end_date"),
			@Result(property = "categoryName", column = "cat_name"),
			@Result(property = "currentHighestBid", column = "current_highest_bid") })
	Product selectProductDetailById(@Param("productId") Integer productId);

	@Update("""
			    update product_master
			    set status = 'closed'
			    where product_id = #{productId}
			""")
	int closeProductById(@Param("productId") Integer productId);

	@Select("""
			    <script>
			    select
			        p.product_id,
			        p.cat_id,
			        p.seller_username,
			        p.product_name,
			        p.description,
			        p.min_bid_price,
			        p.status,
			        p.photo_path,
			        p.start_date,
			        p.end_date,
			        c.cat_name,
			        max(b.bid_price) as current_highest_bid
			    from product_master p
			    join category_master c on c.cat_id = p.cat_id
			    left join bid_master b on b.product_id = p.product_id
			    where 1 = 1
			    <if test="status != null and status != ''">
			        and p.status = #{status}
			    </if>
			    <if test="sellerUsername != null and sellerUsername != ''">
			        and p.seller_username = #{sellerUsername}
			    </if>
			    group by
			        p.product_id,
			        p.cat_id,
			        p.seller_username,
			        p.product_name,
			        p.description,
			        p.min_bid_price,
			        p.status,
			        p.photo_path,
			        p.start_date,
			        p.end_date,
			        c.cat_name
			    order by p.product_id desc
			    </script>
			""")
	@Results({ @Result(property = "productId", column = "product_id"), @Result(property = "catId", column = "cat_id"),
			@Result(property = "sellerUsername", column = "seller_username"),
			@Result(property = "productName", column = "product_name"),
			@Result(property = "description", column = "description"),
			@Result(property = "minBidPrice", column = "min_bid_price"),
			@Result(property = "status", column = "status"), @Result(property = "photoPath", column = "photo_path"),
			@Result(property = "startDate", column = "start_date"), @Result(property = "endDate", column = "end_date"),
			@Result(property = "categoryName", column = "cat_name"),
			@Result(property = "currentHighestBid", column = "current_highest_bid") })
	List<Product> selectAllProductsForAdmin(@Param("status") String status,
			@Param("sellerUsername") String sellerUsername);

	@Select("""
			    select
			        p.product_id,
			        p.cat_id,
			        p.seller_username,
			        p.product_name,
			        p.description,
			        p.min_bid_price,
			        p.status,
			        p.photo_path,
			        p.start_date,
			        p.end_date,
			        c.cat_name
			    from product_master p
			    join category_master c on c.cat_id = p.cat_id
			    where p.product_id = #{productId}
			""")
	@Results({ @Result(property = "productId", column = "product_id"), @Result(property = "catId", column = "cat_id"),
			@Result(property = "sellerUsername", column = "seller_username"),
			@Result(property = "productName", column = "product_name"),
			@Result(property = "description", column = "description"),
			@Result(property = "minBidPrice", column = "min_bid_price"),
			@Result(property = "status", column = "status"), @Result(property = "photoPath", column = "photo_path"),
			@Result(property = "startDate", column = "start_date"), @Result(property = "endDate", column = "end_date"),
			@Result(property = "categoryName", column = "cat_name") })
	Product selectProductByIdForAdmin(@Param("productId") Integer productId);

	@Update("""
			    <script>
			    update product_master
			    set cat_id = #{catId},
			        seller_username = #{sellerUsername},
			        product_name = #{productName},
			        description = #{description},
			        min_bid_price = #{minBidPrice},
			        status = #{status},
			        start_date = #{startDate},
			        end_date = #{endDate}
			        <if test="photoPath != null and photoPath != ''">
			            , photo_path = #{photoPath}
			        </if>
			    where product_id = #{productId}
			    </script>
			""")
	int updateProductForAdmin(Product product);

	@Delete("""
			    delete from product_master
			    where product_id = #{productId}
			""")
	int deleteProductById(@Param("productId") Integer productId);

}
