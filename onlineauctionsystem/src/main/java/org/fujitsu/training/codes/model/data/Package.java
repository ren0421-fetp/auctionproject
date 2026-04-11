package org.fujitsu.training.codes.model.data;

import java.math.BigDecimal;

public class Package {
    private Integer packageId;
    private String packageName;
    private BigDecimal packagePrice;
    private Integer allowedBidCount;
    private String photoPath;

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
}
