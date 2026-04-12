package org.fujitsu.training.codes.model.data;

import java.math.BigDecimal;

public class UserPackageInfo {
    private Integer userPackageId;
    private String username;
    private Integer packageId;
    private String packageName;
    private BigDecimal packagePrice;
    private Integer allowedBidCount;
    private String photoPath;
    private Integer remainingBidCount;

    public Integer getUserPackageId() {
        return userPackageId;
    }

    public void setUserPackageId(Integer userPackageId) {
        this.userPackageId = userPackageId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public BigDecimal getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(BigDecimal packagePrice) {
        this.packagePrice = packagePrice;
    }

    public Integer getAllowedBidCount() {
        return allowedBidCount;
    }

    public void setAllowedBidCount(Integer allowedBidCount) {
        this.allowedBidCount = allowedBidCount;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Integer getRemainingBidCount() {
        return remainingBidCount;
    }

    public void setRemainingBidCount(Integer remainingBidCount) {
        this.remainingBidCount = remainingBidCount;
    }
}
