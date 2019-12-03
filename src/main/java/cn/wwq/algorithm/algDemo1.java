package cn.wwq.algorithm;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回它们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *
 * 示例:
 *
 * 给定 nums = [2, 7, 11, 15], target = 9
 *
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 */
public class algDemo1 {
    public static void main(String[] args) {

        int[] nums = {2,3,4,1,7};
        System.out.println(Arrays.toString(twoNum1(nums,6)));

    }

    /**
     * 时间复杂度为O(n²)  空间复杂度O(1)
     * @param nums
     * @param target
     * @return
     */
    private static int[] twoNum(int[] nums,int target){
        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target){
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }

    /**
     * 时间复杂度为O(n)  空间复杂度O(n)
     * @param nums
     * @param target
     * @return
     */
    private static int[] twoNum1(int[] nums,int target){

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            // 将原本为两个目标值切换为一个目标值，只需要每次从 map 中寻找目标值即可
            int num = target - nums[i];
            if (map.containsKey(num)){
                return new int[]{map.get(num),i};
            }
            // 每次遍历过的值都存储到 map 中，这样之后就能从 map 中寻找需要的目标值
            map.put(nums[i],i);
        }
        return null;
    }
}
