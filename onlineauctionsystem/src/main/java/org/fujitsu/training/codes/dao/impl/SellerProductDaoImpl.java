package org.fujitsu.training.codes.dao.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.BidDao;
import org.fujitsu.training.codes.dao.CategoryDao;
import org.fujitsu.training.codes.model.data.Bid;
import org.fujitsu.training.codes.model.data.Category;
import org.fujitsu.training.codes.model.data.Product;
import org.fujitsu.training.codes.model.form.ProductForm;
import org.springframework.stereotype.Repository;

@Repository
public class SellerProductDaoImpl {
    private static final Logger logger = LogManager.getLogger("seller-flow");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private final CategoryDao categoryDao;
    private final BidDao bidDao;
    private final SqlSessionFactory ssf;

    public SellerProductDaoImpl(CategoryDao categoryDao, SqlSessionFactory ssf, BidDao bidDao) {
        this.categoryDao = categoryDao;
        this.ssf = ssf;
        this.bidDao = bidDao;
    }

    public List<Category> getCategories() {
    	logger.info("Getting categories");
        try {
        	logger.info("Returning categories");
            return categoryDao.selectAllCategories();
        } catch (Exception ex) {
            logger.error("Failed to load seller categories: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public List<Product> getSellerProducts(String sellerUsername) {
    	logger.info("Getting seller products");
        try (SqlSession sess = ssf.openSession()) {
        	logger.info("Returning seller products");
        	return sess.selectList("org.fujitsu.training.codes.dao.ProductDao.selectProductsBySeller", sellerUsername);
        } catch (Exception ex) {
            logger.error("Failed to load seller products for sellerUsername={}: {}", sellerUsername, ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public Product getSellerProduct(Integer productId, String sellerUsername) {
    	logger.info("Getting a product");
        try (SqlSession sess = ssf.openSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("productId", productId);
            params.put("sellerUsername", sellerUsername);
            logger.info("Returning a product");
            return sess.selectOne("org.fujitsu.training.codes.dao.ProductDao.selectProductByIdAndSeller", params);
        } catch (Exception ex) {
            logger.error("Failed to load seller product for productId={}, sellerUsername={}: {}",
                    productId, sellerUsername, ex.getMessage(), ex);
            return null;
        }
    }

    public Integer saveProduct(ProductForm form, String sellerUsername) throws Exception {
        logger.info("Starting seller product save. sellerUsername={}, productId={}",
                sellerUsername, form.getProductId());

        SqlSession sess = ssf.openSession();
        try {
            Integer categoryCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.CategoryDao.countCategoryById",
                    form.getCatId());

            if (categoryCount == null || categoryCount == 0) {
                throw new IllegalArgumentException("Selected category does not exist.");
            }

            Product product = mapFormToProduct(form, sellerUsername);

            if (form.getProductId() == null) {
                Integer inserted = sess.insert("org.fujitsu.training.codes.dao.ProductDao.insertProduct", product);
                if (inserted == null || inserted != 1) {
                    throw new IllegalStateException("Failed to add product.");
                }
            } else {
                Integer updated = sess.update("org.fujitsu.training.codes.dao.ProductDao.updateProduct", product);
                if (updated == null || updated != 1) {
                    throw new IllegalArgumentException("Product was not found or does not belong to the seller.");
                }
            }

            sess.commit();
            logger.info("Seller product save completed. sellerUsername={}, productId={}",
                    sellerUsername, product.getProductId());
            return product.getProductId();
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Seller product save failed. sellerUsername={}, productId={}: {}",
                    sellerUsername, form.getProductId(), ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

    public ProductForm toForm(Product product) {
        ProductForm form = new ProductForm();
        form.setProductId(product.getProductId());
        form.setCatId(product.getCatId());
        form.setProductName(product.getProductName());
        form.setDescription(product.getDescription());
        form.setMinBidPrice(product.getMinBidPrice());
        form.setStatus(product.getStatus());
        form.setCurrentPhotoPath(product.getPhotoPath());
        form.setPhotoPath(product.getPhotoPath());
        form.setStartDate(product.getStartDate().format(FORMATTER));
        form.setEndDate(product.getEndDate().format(FORMATTER));
        return form;
    }

    public void deleteSellerProduct(Integer productId, String sellerUsername) throws Exception {
        logger.info("Starting seller product delete. sellerUsername={}, productId={}",
                sellerUsername, productId);

        SqlSession sess = ssf.openSession();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("productId", productId);
            params.put("sellerUsername", sellerUsername);

            Product product = sess.selectOne(
                    "org.fujitsu.training.codes.dao.ProductDao.selectProductByIdAndSeller",
                    params);

            if (product == null) {
                throw new IllegalArgumentException("Product not found or does not belong to the seller.");
            }

            Integer bidCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.BidDao.countBidsByProductId",
                    productId);

            if (bidCount != null && bidCount > 0) {
                throw new IllegalArgumentException("Product cannot be deleted because it already has bids.");
            }

            Integer deleted = sess.delete(
                    "org.fujitsu.training.codes.dao.ProductDao.deleteProductByIdAndSeller",
                    params);

            if (deleted == null || deleted != 1) {
                throw new IllegalStateException("Failed to delete product.");
            }

            sess.commit();
            logger.info("Seller product delete completed. sellerUsername={}, productId={}",
                    sellerUsername, productId);
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Seller product delete failed. sellerUsername={}, productId={}: {}",
                    sellerUsername, productId, ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

    public List<Bid> getSellerBids(String sellerUsername) {
    	logger.info("Getting seller bids");
        try (SqlSession sess = ssf.openSession()) {
        	logger.info("Returning seller bids");
            return sess.selectList("org.fujitsu.training.codes.dao.BidDao.selectBidsBySeller", sellerUsername);
        } catch (Exception ex) {
            logger.error("Failed to load seller bids for sellerUsername={}: {}", sellerUsername, ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    private Product mapFormToProduct(ProductForm form, String sellerUsername) {
        Product product = new Product();
        product.setProductId(form.getProductId());
        product.setCatId(form.getCatId());
        product.setSellerUsername(sellerUsername);
        product.setProductName(form.getProductName());
        product.setDescription(form.getDescription());
        product.setMinBidPrice(form.getMinBidPrice());
        product.setStatus(form.getStatus());
        product.setPhotoPath(form.getPhotoPath());
        product.setStartDate(LocalDateTime.parse(form.getStartDate(), FORMATTER));
        product.setEndDate(LocalDateTime.parse(form.getEndDate(), FORMATTER));
        return product;
    }
}
