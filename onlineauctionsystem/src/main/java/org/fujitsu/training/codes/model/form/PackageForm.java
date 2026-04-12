package org.fujitsu.training.codes.model.form;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

public class PackageForm {
    private Integer packageId;
    private String packageName;
    private BigDecimal packagePrice;
    private Integer allowedBidCount;
    private String photoPath;
    private String currentPhotoPath;
    private MultipartFile photoFile;

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

    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }

    public void setCurrentPhotoPath(String currentPhotoPath) {
        this.currentPhotoPath = currentPhotoPath;
    }

    public MultipartFile getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(MultipartFile photoFile) {
        this.photoFile = photoFile;
    }
}
