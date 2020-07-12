/*
 * Zenlayer.com Inc.
 * Copyright (c) 2014-2019 All Rights Reserved.
 */
package cloud.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author CQY13 MD5加密工具类
 */
public class MD5Utils {

    /**
     * 获取MD5加密
     *
     * @param pwd
     *            需要加密的字符串
     * @return String字符串 加密后的字符串
     */
    public static String getPwd(String pwd) {
        try {
            // 创建加密对象
            MessageDigest digest = MessageDigest.getInstance("md5");
            try {
                digest.update(pwd.getBytes("UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 调用加密对象的方法，加密的动作已经完成
            byte[] bs = digest.digest();
            // 接下来，我们要对加密后的结果，进行优化，按照mysql的优化思路走
            // mysql的优化思路：
            // 第一步，将数据全部转换成正数：
            String hexString = "";
            for (byte b : bs) {
                // 第一步，将数据全部转换成正数：
                // 解释：为什么采用b&255
                /*
                 * b:它本来是一个byte类型的数据(1个字节) 255：是一个int类型的数据(4个字节)
                 * byte类型的数据与int类型的数据进行运算，会自动类型提升为int类型 eg: b: 1001 1100(原始数据)
                 * 运算时： b: 0000 0000 0000 0000 0000 0000 1001 1100 255: 0000
                 * 0000 0000 0000 0000 0000 1111 1111 结果：0000 0000 0000 0000
                 * 0000 0000 1001 1100 此时的temp是一个int类型的整数
                 */
                int temp = b & 255;
                // 第二步，将所有的数据转换成16进制的形式
                // 注意：转换的时候注意if正数>=0&&<16，那么如果使用Integer.toHexString()，可能会造成缺少位数
                // 因此，需要对temp进行判断
                if (temp < 16 && temp >= 0) {
                    // 手动补上一个“0”
                    hexString = hexString + "0" + Integer.toHexString(temp);
                } else {
                    hexString = hexString + Integer.toHexString(temp);
                }
            }
            return hexString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        //String str = "appKey=9DPLzTPw4CiAyU2&brandNo=BD0000000000000000001342302282&color=green&description
        // =备注改掉了&drivingLicenseExpiredDate=2017-9-07&drivingLicenseNo=999000&engineNo=A44B55C66784&gateWayNo=GW
        // -000000000000000001105690416&insuranceNo=234234236556&makerNo=MK0000000000000000000419798732&modelNo=MD
        // -000000000000000000493024978&no=VC0000000000000000002001275815&plateLicenseNo=沪H99999&registerDate=2013-1-1&seriesNo=SR
        // -000000000000000000379205132&serviceLife=50&sim=324324234&tboxModelNo=BM-000000000000000000987715153&timestamp=1505109486863
        // &vin=234234324234234&key=Ht703YLPkvWYDeV";
        //String str = "appKey=9DPLzTPw4CiAyU2&isCurrentData=true&list=[{\"acquisitionTime\":1502782069859,\"batteryVoltage\":220,
        // \"engineSpeed\":3000,\"fuel\":95,\"latitude\":29.99733971065012,\"longitude\":106.00373706546354,\"totalDistance\":9000000,
        // \"vehBatSOC\":67,\"vehicleSpeed\":100,\"vin\":\"23423432423423411\"}]&timestamp=1507702792443&key=Ht703YLPkvWYDeV";
        String str
                = "appKey=9DPLzTPw4CiAyU2&isCurrentData=true&list=[{\"acquisitionTime\":1502782069859,\"batteryVoltage\":220.0,"
                + "\"engineSpeed\":3000.0,\"fuel\":95.0,\"latitude\":29.99733971065012,\"longitude\":106.00373706546354,"
                + "\"totalDistance\":9000000.0,\"vehBatSOC\":67.0,\"vehicleSpeed\":100.0,"
                + "\"vin\":\"23423432423423411\"}]&timestamp=1507711203494&key=Ht703YLPkvWYDeV";
        String sign = MD5Utils.getPwd(str).toUpperCase();
        System.out.println(sign);
        System.out.println("EB4161CB13D9771D1236779F49734E0A");
    }

}
