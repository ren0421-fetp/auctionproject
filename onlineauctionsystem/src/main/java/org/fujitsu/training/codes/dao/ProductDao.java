package org.fujitsu.training.codes.dao;

import java.util.List;

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
    @Results({
        @Result(property = "productId", column = "product_id"),
        @Result(property = "catId", column = "cat_id"),
        @Result(property = "sellerUsername", column = "seller_username"),
        @Result(property = "productName", column = "product_name"),
        @Result(property = "description", column = "description"),
        @Result(property = "minBidPrice", column = "min_bid_price"),
        @Result(property = "status", column = "status"),
        @Result(property = "photoPath", column = "photo_path"),
        @Result(property = "startDate", column = "start_date"),
        @Result(property = "endDate", column = "end_date"),
        @Result(property = "categoryName", column = "cat_name")
    })
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
    @Results({
        @Result(property = "productId", column = "product_id"),
        @Result(property = "catId", column = "cat_id"),
        @Result(property = "sellerUsername", column = "seller_username"),
        @Result(property = "productName", column = "product_name"),
        @Result(property = "description", column = "description"),
        @Result(property = "minBidPrice", column = "min_bid_price"),
        @Result(property = "status", column = "status"),
        @Result(property = "photoPath", column = "photo_path"),
        @Result(property = "startDate", column = "start_date"),
        @Result(property = "endDate", column = "end_date"),
        @Result(property = "categoryName", column = "cat_name")
    })
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
}
