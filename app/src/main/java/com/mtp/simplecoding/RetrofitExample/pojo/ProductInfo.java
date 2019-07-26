
package com.mtp.simplecoding.RetrofitExample.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductInfo {

    @SerializedName("Product deal transaction_details")
    @Expose
    private List<ProductDealTransactionDetail> productDealTransactionDetails = null;

    public List<ProductDealTransactionDetail> getProductDealTransactionDetails() {
        return productDealTransactionDetails;
    }

    public void setProductDealTransactionDetails(List<ProductDealTransactionDetail> productDealTransactionDetails) {
        this.productDealTransactionDetails = productDealTransactionDetails;
    }

}
