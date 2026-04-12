package org.fujitsu.training.codes.model.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ConfirmBidReportRow {
    private Integer confirmBidId;
    private Integer bidId;
    private Integer productId;
    private String productName;
    private String winnerUsername;
    private BigDecimal confirmedPrice;
    private LocalDateTime confirmedAt;

    public Integer getConfirmBidId() {
        return confirmBidId;
    }

    public void setConfirmBidId(Integer confirmBidId) {
        this.confirmBidId = confirmBidId;
    }

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getWinnerUsername() {
        return winnerUsername;
    }

    public void setWinnerUsername(String winnerUsername) {
        this.winnerUsername = winnerUsername;
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
}
