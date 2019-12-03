package cn.wwq.algorithm;

/**
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 *
 * 示例 1:
 * 输入: 121
 * 输出: true
 *
 * 示例 2:
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 *
 * 示例 3:
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 *
 * 进阶:你能不将整数转为字符串来解决这个问题吗？
 */
public class algDemo3 {
    public static void main(String[] args) {
        System.out.println(isPalindrome1(1234));
    }

    /**
     * 时间复杂度为O(n)  空间复杂度O(n)
     * @param x
     * @return
     */
    private static boolean isPalindrome(int x){
        if (x < 0){
            return false;
        }
        String str = String.valueOf(x);
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (str.charAt(i) != str.charAt(len - i - 1)){
                return false;
            }
        }
        return true;
    }

    /**
     * 时间复杂度为O(1)  空间复杂度O(1)
     * @param x
     * @return
     */
    private static boolean isPalindrome1(int x){
        if (x < 0){
            return false;
        }
        int temp = x;
        long res = 0;
        while (x != 0){
            res = res * 10 + x % 10;
            x /= 10;
        }
        return temp == res;
    }
}
