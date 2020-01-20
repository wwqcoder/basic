package test.算法;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/28
 * @描述   去掉一个数，如何让剩余的数乘积最大
 *  情况A：奇数个负数
 *  情况B：偶数(包括0)个负数
 *   子情况：没有非负数
 */
public class OddAndEven {


    public static int findRemovedIndex(int[] array){
        // 1.统计负元素的个数
        int negativeCount = 0;
        for(int i=0; i<array.length; i++){
            if(array[i] < 0){
                negativeCount ++;
            }
        }
        // 2.根据不同情况，选择要删除的元素
        int tempIndex = 0;
        if((negativeCount&1)==1){
            //情况A：负数个数是奇数
            for(int i=1; i<array.length; i++){
                if(array[i] < 0){
                    if(array[tempIndex]>=0 || array[i]>array[tempIndex]){
                        tempIndex = i;
                    }
                }
            }
            return tempIndex;
        } else {
            //情况B：负数个数是偶数
            if(array.length == negativeCount){
                //子情况：所有元素都是负数
                for(int i=1; i<array.length; i++){
                    if(array[i] < array[tempIndex]){
                        tempIndex = i;
                    }
                }
                return tempIndex;
            };
            for(int i=1; i<array.length; i++){
                if(array[i] >= 0){
                    if(array[tempIndex]<0 || array[i]<array[tempIndex]){
                        tempIndex = i;
                    }
                }
            }
            return tempIndex;
        }
    }
    public static void main(String[] args) {
        int[] array1 = {-4,3,-5,-7,-21,9,-1,5,6};
        int index = findRemovedIndex(array1);
        System.out.println("删除元素下标："+ array1[index]);
        int[] array2 = {4,3,5,-7,-21,9,-1,-5,6,0};
        index = findRemovedIndex(array2);
        System.out.println("删除元素下标："+ array2[index]);
        int[] array3 = {-4,-3,-5,-7,-21,-9,-1,-8};
        index = findRemovedIndex(array3);
        System.out.println("删除元素下标："+ array3[index]);
    }

}
