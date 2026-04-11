package org.fujitsu.training.codes.model.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Product {
    private Integer productId;
    private Integer catId;
    private String sellerUsername;
    private String productName;
    private String description;
    private BigDecimal minBidPrice;
    private String status;
    private String photoPath;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String categoryName;
    private BigDecimal currentHighestBid;
    private String auctionPhase;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMinBidPrice() {
        return minBidPrice;
    }

    public void setMinBidPrice(BigDecimal minBidPrice) {
        this.minBidPrice = minBidPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

	public BigDecimal getCurrentHighestBid() {
		return currentHighestBid;
	}

	public void setCurrentHighestBid(BigDecimal currentHighestBid) {
		this.currentHighestBid = currentHighestBid;
	}

	public String getAuctionPhase() {
		return auctionPhase;
	}

	public void setAuctionPhase(String auctionPhase) {
		this.auctionPhase = auctionPhase;
	}
}
