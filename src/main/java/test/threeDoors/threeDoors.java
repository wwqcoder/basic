package test.threeDoors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/6/24
 * @描述
 */
public class threeDoors {
    public static void threeDoorsTest(){
        //换门的获奖总次数
        int changeWinCount = 0;
        //不换门的获奖总次数
        int unChangeWinCount = 0;
        Random random = new Random();
        for(int i =0; i<1000; i++){
            List<Integer> doors = new ArrayList(Arrays.asList(0,1,2));
            int bonusDoor = random.nextInt(3) ;
            int selectedDoor = random.nextInt(3) ;
            //主持人打开一扇空门
            for(int j=0;j<doors.size();j++){
                if(doors.get(j)!=selectedDoor && doors.get(j)!=bonusDoor){
                    doors.remove(j);
                    break;
                }
            }
            //获得换门的序号
            int changedDoor = doors.get(0);
            if(changedDoor == selectedDoor){
                changedDoor = doors.get(1);
            }
            //如果不换门有奖，unChangeWinCount加1；如果换门有奖，changeWinCount加1
            if(selectedDoor == bonusDoor){
                unChangeWinCount ++;
            }else if(changedDoor == bonusDoor){
                changeWinCount ++;
            }
        }
        System.out.println("不换门获奖总次数：" + unChangeWinCount);
        System.out.println("换门获奖总次数：" + changeWinCount);
    }

    public static void main(String[] args) {
        threeDoorsTest();
    }


}
