package org.fujitsu.training.codes.dao.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.model.data.Bid;
import org.fujitsu.training.codes.model.data.BidConfirm;
import org.fujitsu.training.codes.model.data.Product;
import org.springframework.stereotype.Repository;

@Repository
public class AdminBidDaoImpl {
	private static final Logger logger = LogManager.getLogger("admin-flow");

	private final SqlSessionFactory ssf;

	public AdminBidDaoImpl(SqlSessionFactory ssf) {
		this.ssf = ssf;
	}

	public List<Bid> getAllBidsForAdmin() {
		logger.info("Getting all bids for admin");
		try (SqlSession sess = ssf.openSession()) {
			logger.info("Returning all bids");
			return sess.selectList("org.fujitsu.training.codes.dao.BidDao.selectAllBidsForAdmin");
		} catch (Exception ex) {
			logger.error("Failed to load admin bids: {}", ex.getMessage(), ex);
			return Collections.emptyList();
		}
	}

	public void confirmWinningBid(Integer bidId) throws Exception {
		logger.info("Starting winning bid confirmation. bidId={}", bidId);

		SqlSession sess = ssf.openSession();
		try {
			Bid bid = sess.selectOne("org.fujitsu.training.codes.dao.BidDao.selectBidById", bidId);
			if (bid == null) {
				throw new IllegalArgumentException("Selected bid was not found.");
			}

			Product product = sess.selectOne("org.fujitsu.training.codes.dao.ProductDao.selectProductDetailById",
					bid.getProductId());

			if (product == null) {
				throw new IllegalArgumentException("Product for the selected bid was not found.");
			}

			if (product.getEndDate() == null || LocalDateTime.now().isBefore(product.getEndDate())) {
				throw new IllegalArgumentException("Winner can only be confirmed after the auction end date.");
			}

			Integer existingCount = sess.selectOne(
					"org.fujitsu.training.codes.dao.BidConfirmDao.countConfirmedByProductId", bid.getProductId());

			if (existingCount != null && existingCount > 0) {
				throw new IllegalArgumentException("This product already has a confirmed winner.");
			}

			BidConfirm confirm = new BidConfirm();
			confirm.setBidId(bid.getBidId());
			confirm.setWinnerUsername(bid.getBidderUsername());
			confirm.setConfirmedPrice(bid.getBidPrice());
			confirm.setConfirmedAt(LocalDateTime.now());

			Integer inserted = sess.insert("org.fujitsu.training.codes.dao.BidConfirmDao.insertBidConfirmation",
					confirm);

			if (inserted == null || inserted != 1) {
				throw new IllegalStateException("Failed to confirm winning bid.");
			}

			Integer closed = sess.update("org.fujitsu.training.codes.dao.ProductDao.closeProductById",
					bid.getProductId());

			if (closed == null || closed != 1) {
				throw new IllegalStateException("Failed to close product after confirmation.");
			}

			sess.commit();
			logger.info("Winning bid confirmation completed. bidId={}", bidId);
		} catch (Exception ex) {
			sess.rollback();
			logger.error("Winning bid confirmation failed. bidId={}: {}", bidId, ex.getMessage(), ex);
			throw ex;
		} finally {
			sess.close();
		}
	}
}
