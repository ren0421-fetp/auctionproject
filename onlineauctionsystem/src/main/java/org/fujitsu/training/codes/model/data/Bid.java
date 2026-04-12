package org.fujitsu.training.codes.model.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bid {
    private Integer bidId;
    private Integer productId;
    private String bidderUsername;
    private LocalDateTime bidDate;
    private BigDecimal bidPrice;
    private String productName;
    private BigDecimal minBidPrice;
    private String productPhotoPath;
    private String sellerUsername;
    private Boolean productConfirmed;
    private String confirmedWinnerUsername;
    private BigDecimal confirmedPrice;
    private LocalDateTime confirmedAt;
    private LocalDateTime productStartDate;
    private LocalDateTime productEndDate;
    private String auctionState;



    public Integer getBidId() {
        return bidId;
    }

    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getBidderUsername() {
        return bidderUsername;
    }

    public void setBidderUsername(String bidderUsername) {
        this.bidderUsername = bidderUsername;
    }

    public LocalDateTime getBidDate() {
        return bidDate;
    }

    public void setBidDate(LocalDateTime bidDate) {
        this.bidDate = bidDate;
    }

    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(BigDecimal bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getMinBidPrice() {
        return minBidPrice;
    }

    public void setMinBidPrice(BigDecimal minBidPrice) {
        this.minBidPrice = minBidPrice;
    }

    public String getProductPhotoPath() {
        return productPhotoPath;
    }

    public void setProductPhotoPath(String productPhotoPath) {
        this.productPhotoPath = productPhotoPath;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

	public Boolean getProductConfirmed() {
		return productConfirmed;
	}

	public void setProductConfirmed(Boolean productConfirmed) {
		this.productConfirmed = productConfirmed;
	}

	public String getConfirmedWinnerUsername() {
		return confirmedWinnerUsername;
	}

	public void setConfirmedWinnerUsername(String confirmedWinnerUsername) {
		this.confirmedWinnerUsername = confirmedWinnerUsername;
	}

	public BigDecimal getConfirmedPrice() {
		return confirmedPrice;
	}

	public void setConfirmedPrice(BigDecimal confirmedPrice) {
		this.confirmedPrice = confirmedPrice;
	}

	public LocalDateTime getConfirmedAt() {
		return confirmedAt;
	}

	public void setConfirmedAt(LocalDateTime confirmedAt) {
		this.confirmedAt = confirmedAt;
	}

	public LocalDateTime getProductStartDate() {
		return productStartDate;
	}

	public void setProductStartDate(LocalDateTime productStartDate) {
		this.productStartDate = productStartDate;
	}

	public LocalDateTime getProductEndDate() {
		return productEndDate;
	}

	public void setProductEndDate(LocalDateTime productEndDate) {
		this.productEndDate = productEndDate;
	}

	public String getAuctionState() {
		return auctionState;
	}

	public void setAuctionState(String auctionState) {
		this.auctionState = auctionState;
	}
    
    
}
