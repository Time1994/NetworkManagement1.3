package com.eroadcar.networkmanagement.utils;

/**
 * Convert an IPv4address to an integer
 *
 * created by guoyongzheng @ 2018.8.21
 */
public class IpV4Converter {

    /**
     * Convert an IPv4address to an integer
     * @param ip
     * @return int
     */
    public long convert(String ip) {

        if (ip == null || ip.isEmpty()) {
            throw new IllegalArgumentException("Empty input!");
        }

        // 32位非负整数应当用long类型
        long result = 0;

        // 以下为中间结果和状态
        int curr = 0;
        boolean acceptDigit = true;
        boolean acceptDot = false;
        int dotCount = 0;

        // 遍历字符串, only once
        for (char c : ip.toCharArray()) {

            if (Character.isDigit(c)) {
                // 数字+空格后不能接数字
                if (!acceptDigit) {
                    reportErrorAndExit(ip);
                }

                // 十进制数字
                curr = curr * 10 + Character.getNumericValue(c);

                // 每段数字不能超过255
                if (curr > 255) {
                    reportErrorAndExit(ip);
                } else {

                    acceptDot = true;

                    // 如果本段为0，那么只能包含一个数字
                    if (curr == 0) {
                        acceptDigit = false;
                    } else {
                        acceptDigit = true;
                    }
                }

            } else if (c == '.') {
                dotCount++;

                // 句点必须跟在数字或(数字+空格)之后，最多只出现3个句点，每段数字不能超过255
                if (!acceptDot || dotCount > 3 || curr > 255) {
                    reportErrorAndExit(ip);
                }

                // 前值左移8位，加现值
                result = (result << 8) + curr;
                curr = 0;

                acceptDigit = true;
                acceptDot = false; // 句点后不能再紧接句点或结束

            } else if (Character.isSpaceChar(c)) {

                // 任何字符后都可以接空格

                // 数字+空格后不能接数字，只能接空格或句点
                if (curr > 0) {
                    acceptDigit = false;
                }

            } else {
                reportErrorAndExit(ip);
            }
        }

        // 后处理
        if (!acceptDot || dotCount != 3) {
            reportErrorAndExit(ip);
        } else {
            result = (result << 8) + curr;
        }

        return result;
    }

    /**
     * 报告错误并退出
     * @param ip
     */
    private void reportErrorAndExit(String ip) {
        throw new IllegalArgumentException("Invalid input: " + ip);
    }

}
