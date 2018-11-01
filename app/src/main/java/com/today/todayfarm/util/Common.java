package com.today.todayfarm.util;

import java.text.DecimalFormat;

public class Common {

    /**
     * 将面积转换为亩显示
     * @param area_mi
     * @return
     */
    public static String getAreaStr(String area_mi) {
        double fieldarea_mu =0;
        try {
            fieldarea_mu = Double.parseDouble(area_mi)/666.666;

        } catch (Exception e) {

        }
        DecimalFormat df = new DecimalFormat("#.00");

        return df.format(fieldarea_mu);
    }

}
