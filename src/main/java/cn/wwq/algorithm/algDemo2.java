package cn.wwq.algorithm;

/**
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 *
 * 示例 1:
 * 输入: 123
 * 输出: 321
 *
 * 示例 2:
 * 输入: -123
 * 输出: -321
 *
 * 示例 3:
 * 输入: 120
 * 输出: 21
 *
 * 注意:假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
 */
public class algDemo2 {
    public static void main(String[] args) {
        System.out.println(reverse(123));
    }

    /**
     * 时间复杂度为O(lgx)  空间复杂度O(1)
     * @param x
     * @return
     */
    private static int reverse(int x){

        //如果不做这个判断，下面的x=-x将会报错
        if (x == -2147483648){
            return 0;
        }
        // 如果是负数则取绝对值调用自身，最后将结果转为负数
        if (x < 0){
            return -reverse(-x);
        }
        int res = 0;
        // 每次得到最后一位数字，并将其作为结果中的当前最高位
        while (x != 0){
            //处理溢出
            if (res > 214748364){
                return 0;
            }
            res = res * 10 + x % 10;
            x /= 10;
        }
        // 如果溢出就返回0
        return res <= 0x7fffffff ? res : 0;
    }
}
