package kovteba.onlineshopapi.enums;

import java.util.Arrays;
import java.util.List;

public enum ProductType {

    PC("PC"),
    LAPTOP("LAPTOP"),
    CAMERA("CAMERA");

    private String productTypeValue;

    ProductType(String roleValue) {
        this.productTypeValue = productTypeValue;
    }

    public String getProductTypeValue() {
        return productTypeValue;
    }

    public static List<ProductType> getListProductTypeValue(){
        return Arrays.asList(ProductType.values());
    }

    public static ProductType findProductType(String productTypeString){
        ProductType productType = null;
        return productType = getListProductTypeValue()
                .stream()
                .filter(s -> s.getProductTypeValue().equals(productTypeString))
                .findAny()
                .get();
    }



}
