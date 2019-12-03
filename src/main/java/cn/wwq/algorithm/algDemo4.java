package cn.wwq.algorithm;

/**
 * 罗马数字包含以下七种字符： I、 V、 X、 L、C、D 和 M。
 *
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1；12 写做 XII ，即为 X + II ； 27 写做  XXVII, 即为 XX + V + II 。
 *
 * 通常情况下，罗马数字中小的数字在大的数字的右边，但也存在特例。例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 *
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9；
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90；
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900；
 * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
 *
 * 示例 1:
 * 输入: 3
 * 输出: "III"
 *
 * 示例 2:
 * 输入: 4
 * 输出: "IV"
 *
 * 示例 3:
 * 输入: 9
 * 输出: "IX"
 *
 * 示例 4:
 * 输入: 58
 * 输出: "LVIII"
 * 解释: L = 50, V = 5, III = 3.
 *
 * 示例 5:
 * 输入: 1994
 * 输出: "MCMXCIV"
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 */

/**
 * 以整数 3568 为例，3568 的对应罗马数字为：MMMDLXVIII
 *
 * 第一次 for 循环 ————> symbol = 'M' val = 1000 , num >= val成立，进入 while 循环：
 * 	第一次 while 循环：
 * 		将 'M' 拼接到 roman 中， num 减去当前 val ，此时 roman 为 'M',num 为 2568
 * 	第二次 while 循环：
 * 		将 'M' 拼接到 roman 中，num 减去当前 val ，此时 roman 为 'MM',num 为 1568
 * 	第三次 while 循环：
 * 		将 'M' 拼接到 roman 中，num 减去当前 val ，此时 roman 为 'MMM',num 为 568 					num >= val 不成立，退出 while 循环。
 * 第二次 for 循环 ————> symbol = 'CM' val = 900 , num >= val不成立，不能进入 while 循环：
 * 第三次 for 循环 ————> symbol = 'D' val = 500 , num >= val成立，进入 while 循环：
 * 	第一次 while 循环：
 *     	将 'D' 拼接到 roman 中， num 减去当前 val ，此时 roman 为 'MMMD',num 为 68
 *         num >= val 不成立，退出 while 循环。
 * 第四次 for 循环 ————> symbol = 'CD' val = 400 , num >= val不成立，不能进入 while 循环：
 * …………
 * 第七次 for 循环 ————> symbol = 'L' val = 50 , num >= val成立，进入 while 循环：
 * 	第一次 while 循环：
 *     	将 'L' 拼接到 roman 中， num 减去当前 val ，此时 roman 为 'MMMDL',num 为 18
 *         num >= val 不成立，退出 while 循环。
 * …………依次执行后得出结果为 MMMDLXVIII 。
 */
public class algDemo4 {
    public static void main(String[] args) {
//        System.out.println(intToRoman(2700));
        System.out.println(romanToInt("IV"));
    }

    /**
     * 时间复杂度为O(n)  空间复杂度O(1)
     * @param num
     * @return
     */
    private static String intToRoman(int num){
        // 初始化了一个一一对应的map，方便后面取出符号。
        String[][] lookup = {
                /**
                 * 罗马数字 代表整数 1 2 3 4 5 6 7 8 9
                */
                {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"},
                /**
                 * 罗马数字 代表整数 10 20 30 40 50 60 70 80 90
                */
                {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"},
                /**
                 * 罗马数字 代表整数 100 200 300 400 500 600 700 800 900
                */
                {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"},
                /**
                 * 罗马数字 代表整数 1000 2000 3000
                */
                {"", "M", "MM", "MMM"}
        };
        String ret = "";
        for (int i = 0; i < 4; i++) {
            /**
             * 下标为 0 代表个位
             * 下标为 1 代表十位
             * 下标为 2 代表百位
             * 下标为 3 代表千位
             */
            ret = lookup[i][num % 10] + ret;
            num /= 10;
        }
        return ret;
    }

    /**
     * 罗马数字转整数
     * @param s
     * 时间复杂度为O(n)  空间复杂度O(1)
     * @return
     */
    public static int romanToInt(String s) {
        // 初始化了一个一一对应的map，方便后面取出符号。
        String[][] lookup = {
                {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"},
                {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"},
                {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"},
                {"", "M", "MM", "MMM"}
        };
        int ret = 0;
        int base = 1000;
        int x = 3;
        int y = 3;
        int pos = 0;
        while (pos < s.length()){
            if (pos + lookup[x][y].length() <= s.length()) {
                boolean wrong = false;
                for (int i = 0; i < lookup[x][y].length(); i++) {
                    if (lookup[x][y].charAt(i) != s.charAt(pos + i)){
                        wrong = true;
                        break;
                    }
                }
                if (!wrong) {
                    pos += lookup[x][y].length();
                    ret += base * y;
                }
            }
            y--;
            if (y == 0) {
                base /= 10;
                x--;
                y = 9;
            }
        }
        return ret;
    }
}
