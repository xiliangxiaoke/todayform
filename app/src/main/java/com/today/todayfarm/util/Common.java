package com.today.todayfarm.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
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



    public static byte[] readStream(String imagepath) throws Exception {
        FileInputStream fs = new FileInputStream(imagepath);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while (-1 != (len = fs.read(buffer))) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        fs.close();
        return outStream.toByteArray();
    }

    // 二进制转字符串
    public static String byte2hex(byte[] b)
    {
        StringBuffer sb = new StringBuffer();
        String tmp = "";
        for (int i = 0; i < b.length; i++) {
            tmp = Integer.toHexString(b[i] & 0XFF);
            if (tmp.length() == 1){
                sb.append("0" + tmp);
            }else{
                sb.append(tmp);
            }

        }
        return sb.toString();
    }

}
