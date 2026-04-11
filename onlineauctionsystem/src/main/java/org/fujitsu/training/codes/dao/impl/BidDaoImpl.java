package org.fujitsu.training.codes.dao.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.model.data.Bid;
import org.fujitsu.training.codes.model.data.Category;
import org.fujitsu.training.codes.model.data.Product;
import org.fujitsu.training.codes.model.form.BidForm;
import org.springframework.stereotype.Repository;

@Repository
public class BidDaoImpl {
    private static final Logger logger = LogManager.getLogger(BidDaoImpl.class);
    private final SqlSessionFactory ssf;

    public BidDaoImpl(SqlSessionFactory ssf) {
        this.ssf = ssf;
    }

    public List<Product> getOpenProducts(String keyword, Integer catId,
            BigDecimal minPrice, BigDecimal maxPrice) {
        try (SqlSession sess = ssf.openSession()) {
            java.util.Map<String, Object> params = new java.util.HashMap<>();
            params.put("keyword", keyword);
            params.put("catId", catId);
            params.put("minPrice", minPrice);
            params.put("maxPrice", maxPrice);
            return sess.selectList("org.fujitsu.training.codes.dao.ProductDao.selectOpenProducts", params);
        }
    }

    public java.util.List<org.fujitsu.training.codes.model.data.Category> getCategories() {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.CategoryDao.selectAllCategories");
        }
    }



    public Product getProductDetail(Integer productId) {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectOne("org.fujitsu.training.codes.dao.ProductDao.selectProductDetailById", productId);
        }
    }

    public Integer getRemainingBidCount(String username) {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectOne("org.fujitsu.training.codes.dao.PackageUserDao.selectRemainingBidCountByUsername", username);
        }
    }

    public void placeBid(BidForm form, String bidderUsername) throws Exception {
        logger.info("Attempting to place bid for bidder {} on product {}", bidderUsername, form.getProductId());

        SqlSession sess = ssf.openSession();
        try {
            Product product = sess.selectOne("org.fujitsu.training.codes.dao.ProductDao.selectProductDetailById", form.getProductId());
            if (product == null) {
                throw new IllegalArgumentException("Product not found.");
            }

            if (!"open".equalsIgnoreCase(product.getStatus())) {
                throw new IllegalArgumentException("Product is not open for bidding.");
            }

            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(product.getStartDate()) || now.isAfter(product.getEndDate())) {
                throw new IllegalArgumentException("Bidding is not allowed outside the auction period.");
            }

            Integer remainingBidCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.PackageUserDao.selectRemainingBidCountByUsername",
                    bidderUsername);

            if (remainingBidCount == null || remainingBidCount <= 0) {
                throw new IllegalArgumentException("You do not have enough package bid balance.");
            }

            BigDecimal highestBid = sess.selectOne(
                    "org.fujitsu.training.codes.dao.BidDao.selectHighestBidByProductId",
                    form.getProductId());

            BigDecimal minimumRequired = product.getMinBidPrice();
            if (highestBid != null && highestBid.compareTo(minimumRequired) > 0) {
                minimumRequired = highestBid;
            }

            if (form.getBidPrice().compareTo(minimumRequired) <= 0) {
                throw new IllegalArgumentException("Bid must be higher than the current required amount.");
            }

            Bid bid = new Bid();
            bid.setProductId(form.getProductId());
            bid.setBidderUsername(bidderUsername);
            bid.setBidDate(now);
            bid.setBidPrice(form.getBidPrice());

            sess.insert("org.fujitsu.training.codes.dao.BidDao.insertBid", bid);

            int updated = sess.update("org.fujitsu.training.codes.dao.PackageUserDao.decrementBidCountByUsername",
                    bidderUsername);
            if (updated != 1) {
                throw new IllegalStateException("Failed to deduct package bid balance.");
            }

            sess.commit();
            logger.info("Bid placed successfully for bidder {} on product {}", bidderUsername, form.getProductId());
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Failed to place bid for bidder {} on product {}: {}", bidderUsername,
                    form.getProductId(), ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }
    
    public List<Bid> getBidderBids(String bidderUsername) {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.BidDao.selectBidsByBidder", bidderUsername);
        }
    }
    
    public Bid getBidderBid(Integer bidId, String bidderUsername) {
        try (SqlSession sess = ssf.openSession()) {
            java.util.Map<String, Object> params = new java.util.HashMap<>();
            params.put("bidId", bidId);
            params.put("bidderUsername", bidderUsername);
            return sess.selectOne("org.fujitsu.training.codes.dao.BidDao.selectBidByIdAndBidder", params);
        }
    }

    public BidForm toModifyForm(Bid bid) {
        BidForm form = new BidForm();
        form.setBidId(bid.getBidId());
        form.setProductId(bid.getProductId());
        form.setBidPrice(bid.getBidPrice());
        return form;
    }

    public void modifyBid(BidForm form, String bidderUsername) throws Exception {
        logger.info("Attempting to modify bid {} for bidder {}", form.getBidId(), bidderUsername);

        SqlSession sess = ssf.openSession();
        try {
            java.util.Map<String, Object> bidParams = new java.util.HashMap<>();
            bidParams.put("bidId", form.getBidId());
            bidParams.put("bidderUsername", bidderUsername);

            Bid existingBid = sess.selectOne(
                    "org.fujitsu.training.codes.dao.BidDao.selectBidByIdAndBidder",
                    bidParams);

            if (existingBid == null) {
                throw new IllegalArgumentException("Bid not found or does not belong to the bidder.");
            }

            Product product = sess.selectOne(
                    "org.fujitsu.training.codes.dao.ProductDao.selectProductDetailById",
                    existingBid.getProductId());

            if (product == null) {
                throw new IllegalArgumentException("Product not found.");
            }

            if (!"open".equalsIgnoreCase(product.getStatus())) {
                throw new IllegalArgumentException("Product is not open for bidding.");
            }

            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(product.getStartDate()) || now.isAfter(product.getEndDate())) {
                throw new IllegalArgumentException("Bidding is not allowed outside the auction period.");
            }

            Integer remainingBidCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.PackageUserDao.selectRemainingBidCountByUsername",
                    bidderUsername);

            if (remainingBidCount == null || remainingBidCount <= 0) {
                throw new IllegalArgumentException("You do not have enough package bid balance.");
            }

            BigDecimal highestBid = sess.selectOne(
                    "org.fujitsu.training.codes.dao.BidDao.selectHighestBidByProductId",
                    existingBid.getProductId());

            BigDecimal minimumRequired = product.getMinBidPrice();
            if (highestBid != null && highestBid.compareTo(minimumRequired) > 0) {
                minimumRequired = highestBid;
            }

            if (form.getBidPrice().compareTo(existingBid.getBidPrice()) <= 0) {
                throw new IllegalArgumentException("Modified bid must be higher than your previous bid.");
            }

            if (form.getBidPrice().compareTo(minimumRequired) <= 0) {
                throw new IllegalArgumentException("Modified bid must be higher than the current highest bid.");
            }

            Bid newBid = new Bid();
            newBid.setProductId(existingBid.getProductId());
            newBid.setBidderUsername(bidderUsername);
            newBid.setBidDate(now);
            newBid.setBidPrice(form.getBidPrice());

            sess.insert("org.fujitsu.training.codes.dao.BidDao.insertBid", newBid);

            int updated = sess.update(
                    "org.fujitsu.training.codes.dao.PackageUserDao.decrementBidCountByUsername",
                    bidderUsername);

            if (updated != 1) {
                throw new IllegalStateException("Failed to deduct package bid balance.");
            }

            sess.commit();
            logger.info("Bid {} modified successfully for bidder {}", form.getBidId(), bidderUsername);
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Failed to modify bid {} for bidder {}: {}", form.getBidId(), bidderUsername,
                    ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

}
