package cn.wwq.algorithm;

/**
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 *
 * 如果不存在公共前缀，返回空字符串 ""。
 *
 * 示例 1:
 * 输入: ["flower","flow","flight"]
 * 输出: "fl"
 *
 * 示例 2:
 * 输入: ["dog","racecar","car"]
 * 输出: ""
 * 解释: 输入不存在公共前缀。
 *
 * 说明: 所有输入只包含小写字母 a-z 。
 */
public class algDemo5 {
    public static void main(String[] args) {
        System.out.println(longestCommonPrefix1(new String[]{"wwqoo","wwqoosdfs","wwqoofdfg"}));
    }

    /**
     * 时间复杂度为O(n * len(str[0]))  空间复杂度O(n)
     * @param strs
     * @return
     */
    private static String longestCommonPrefix(String[] strs){
        if (strs.length == 0){
            return "";
        }
        // 最长字串的长度一定不会超过第0个字符串的长度
        int end = strs[0].length();
        for (int i = 1; i < strs.length; i++) {
            int j = 0;
            while (j < end && j < strs[i].length() && strs[0].charAt(j) == strs[i].charAt(j)){
                j++;
            }
            end = j;
        }
        return strs[0].substring(0,end);
    }

    /**
     * 时间复杂度为O(n * len(str[0]))  空间复杂度O(n)
     * @param strs
     * @return
     */
    private static String longestCommonPrefix1(String[] strs){
        if (strs.length == 0){
            return "";
        }

        for (int i = 0; i < strs[0].length(); i++) {
            // 第 0 个字符不需要比较，可以直接从第 1 个字符开始进比较
            for (int j = 1; j < strs.length; j++) {
                // 只要strs中存在比当前长度i更短的string，立刻返回上一轮LCP，即strs[0][:i]
                // 只要strs中存在当前index字符与LCP该index不相同的字符串，立刻返回上一轮LCP，即strs[0][:i]
                if (i >= strs[j].length() || strs[0].charAt(i) != strs[j].charAt(i)){
                    return strs[0].substring(0,i);
                }
            }
        }
        return strs[0];
    }
}
