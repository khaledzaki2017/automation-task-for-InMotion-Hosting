package com.get.inmotion.helpers;

import java.math.BigDecimal;

public class CommonHelper {

    public static BigDecimal normalizePrice(String priceText) {
        // Remove everything except numbers and dot
        String normalized = priceText.replaceAll("[^0-9.]", "");
        return new BigDecimal(normalized);
    }

}
