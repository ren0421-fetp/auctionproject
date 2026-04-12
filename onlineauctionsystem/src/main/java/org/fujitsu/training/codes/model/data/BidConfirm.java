package org.fujitsu.training.codes.model.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BidConfirm {
    private Integer confirmBidId;
    private Integer bidId;
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
