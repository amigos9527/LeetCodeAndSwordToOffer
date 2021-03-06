package SwordToOfferSolution._67_StringToInt;

/*
 * 把字符串转换成整数
 *
 * 题目描述：
 * 请你写一个函数StrToInt，实现把字符串转换成整数这个功能。不能使用 atoi 或者其他类似的库函数。
 *
 * 思路：
 * 该题需要考虑的特殊情况比较多，例如：数据溢出、空串、只有正负号、有无正负号、非法输入等。
 * 0. 尽量避免使用库函数；
 * 1. 需要去掉前导空格；
 * 2. 需要判断第 1 个字符为 + 和 - 的情况，因此，可以设计一个变量 sign，初始化的时候为 1，如果遇到 - ，将 sign 修正为 -1；
 * 3. 判断是否是数字，可以使用字符的 ASCII 码数值进行比较，即 0 <= c <= '9'；
 * 4. 在遇到第 1 个不是数字的字符的情况下，就得退出循环；
 * 5. 如果转换以后的数字超过了 int 类型的范围，需要截取。这里需要将结果 res 变量设计为 long 类型，
 *    注意：由于输入的字符串转换以后也有可能超过 long 类型，因此需要在循环内部就判断是否越界，
 *    只要越界就退出循环，这样也可以减少不必要的计算；
 * 6. 因为涉及下标访问，因此全程需要考虑数组下标是否越界的情况。
 */
public class Solution {
    public static int myAtoi(String str) {
        int len = str.length();

        // 去掉前导空格
        // index 会来到第一个不是空格的字符
        int index = 0;
        while (index < len) {
            if (str.charAt(index) != ' ') {
                break;
            }
            index++;
        }

        // 如果 str 都是空格，则返回 0
        if (index == len) {
            return 0;
        }

        // 能走到这里，说明 index 所指的就是 str 中第一个不是空格的字符
        // 第 1 个字符如果是符号，则判断合法性，并记录正负
        int sign = 1;
        char firstChar = str.charAt(index);
        if (firstChar == '+') {
            index++;
            sign = 1;
        } else if (firstChar == '-') {
            index++;
            sign = -1;
        }

        // 能够来到这里说明，已经越过空格和正负号了
        int res = 0;
        while (index < len) {
            char curChar = str.charAt(index);
            // 合法性判断
            if (curChar < '0' || curChar > '9') {
                break;
            }

            // 环境只能存储 32 位大小的有符号整数，因此，需要提前判断乘以 10 以后是否越界
            if (res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && (curChar - '0') > Integer.MAX_VALUE % 10)) {
                return Integer.MAX_VALUE;
            }
            if (res < Integer.MIN_VALUE / 10 || (res == Integer.MIN_VALUE / 10 && (curChar - '0') > -(Integer.MIN_VALUE % 10))) {
                return Integer.MIN_VALUE;
            }

            // 每一步都把符号位乘进去
            // 字符 0 的十进制数是 48
            // 字符 1 的十进制数是 49
            // 字符 2 的十进制数是 50
            res = res * 10 + sign * (curChar - '0');
            index++;
        }
        return res;
    }

    public static int strToInt(String str) {
        if (str == null || str.trim().length() == 0) {
            return 0;
        }

        str = str.trim();
        // 判断是否是负数
        boolean isNegative = str.charAt(0) == '-';
        int res = 0;
        for (int i = 0; i < str.length(); i++) {
            // 取每一个字符逐一判断
            char c = str.charAt(i);
            // 如果第一个字符含有正号或负号
            if (i == 0 && (c == '+' || c == '-')) {
                continue;
            }
            // 非法输入
            if (c < '0' || c > '9') {
                return 0;
            }
            // 将 字符数字 转化成 数字
            res = res * 10 + (c - '0');
        }
        return isNegative ? -res : res;
    }

    public static void main(String[] args) {
        System.out.println(myAtoi("   42"));
        System.out.println(myAtoi("   -42"));
        System.out.println(myAtoi(" 42"));
        System.out.println(myAtoi("4193 with words"));
        System.out.println(myAtoi("words and 987"));
    }
}