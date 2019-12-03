package cn.wwq.algorithm;

import java.util.Stack;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 *
 * 有效字符串需满足：
 *  1.左括号必须用相同类型的右括号闭合。
 *  2.左括号必须以正确的顺序闭合。
 *
 * 注意：空字符串可被认为是有效字符串。
 *
 * 示例 :
 *
 * 输入: "()"
 * 输出: true
 *
 * 输入: "()[]{}"
 * 输出: true
 *
 * 输入: "([)]"
 * 输出: false
 *
 * 输入: "{[]}"
 * 输出: true
 */
public class algDemo6 {
    public static void main(String[] args) {
        System.out.println(isValid1("[]{}"));
    }

    /**
     *
     * 无限循环，每次将成对的括号替换为空字符串
     * 如果替换之前和替换之后的长度一样，说明没有成对的字符串，或者字符串为空字符串，跳出循环体
     * 如果替换之前和替换之后的长度不一样，说明有成对的字符串被替换掉，继续循环替换成对的字符串
     * 直到替换之前和替换之后的长度一样，跳出循环体为止
     * 最后判断字符串的长度是否为 0 来判断字符串是否是有效的括号
     * @param s
     * @return
     */
    private static boolean isValid(String s){
        int length;
        while (true){
            length = s.length();
            s = s.replace("[]","");
            s = s.replace("{}","");
            s = s.replace("()","");
            if (length == s.length()){
                break;
            }
        }
        return length == 0;
    }

    public static boolean isValid1(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            // 如果是左括号就入栈
            if (s.charAt(i) == '{'|| s.charAt(i) == '[' || s.charAt(i) == '(') {
                stack.push(s.charAt(i));
            }
            // 如果是右括号就判断栈是否为空，以及栈顶是否和右括号匹配，如果不匹配直接可以返回 false
            //stack.peek() : 只查看栈顶元素而不删除该元素
            if (s.charAt(i) == ']') {
                if (stack.isEmpty() || stack.peek() != '[') {
                    return false;
                }
                stack.pop();
            }
            if (s.charAt(i) == '}') {
                if (stack.isEmpty() || stack.peek() != '{') {
                    return false;
                }
                stack.pop();
            }
            if (s.charAt(i) == ')') {
                if (stack.isEmpty() || stack.peek() != '(') {
                    return false;
                }
                stack.pop();
            }
        }
        // 如果全部匹配，则栈为空，否则不为空
        return stack.isEmpty();
    }
}
