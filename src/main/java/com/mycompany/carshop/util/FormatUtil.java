package com.mycompany.carshop.util;

import java.text.DecimalFormat;

public class FormatUtil {
    
    public static String formatCurrency(double amount) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(amount);
    }
}
