package test.算法;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/7/24
 * @描述
 */
public class Algorithm {
    public static void main(String[] args) {
        int a[]={5,2,8,4,1,9,16};
        System.out.println("冒泡排序法:"+ Arrays.toString(bubble(a)));
        int b[]={5,2,8,4,1,9,16};
        System.out.println("选择排序法"+Arrays.toString(select(b)));
        int c[]={5,2,8,4,1,9,16};
        System.out.println("插入排序法"+Arrays.toString(insert(c)));

        System.out.println("位置:"+find(a,8));
    }

    /**
     * 冒泡法
     * @param a 排序数组
     * @return 计算完的数组
     */
    public static int [] bubble(int a[]){
        for(int i=0;i<a.length-1;i++){
            for(int j=0;j<a.length-i-1;j++){
                if(a[j]<a[j+1]){
                    int temp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
        }
        return a;
    }

    /**
     * 选择排序法 优于冒泡法
     * @param a 排序数组
     * @return 计算完的数组
     */

    public static int [] select(int a[]){
        for(int i=0;i<a.length-1;i++){
            int min=i;
            for(int j=i+1;j<a.length;j++){
                if(a[min]>a[j]){
                    min=j;
                }
            }
            if(min!=i){
                int temp=a[i];
                a[i]=a[min];
                a[min]=temp;
            }
        }
        return a;
    }

    /**
     * 插入排序法 优于前两种
     * @param a 排序数组
     * @return 计算完的数组
     */
    public static int [] insert(int a[]){
        int compare;
        for(int mark=1;mark<a.length;mark++){
            int  temp =a[mark];
            compare=mark;
            while(compare>0&&a[compare-1]>temp){
                a[compare]=a[compare-1];
                compare--;
            }
            a[compare]=temp;
        }
        return a;
    }

    /**
     * 二分法查找,前提是数组已经有序 小到大的
     * @param a 由小到大的有序数组
     * @param value 需要找的值
     * @return
     */
    public static int find(int a[],int value){
        int start=0;
        int end=a.length-1;
        while(end>=start){
            int index =(start + end )/2;
            if(a[index]==value){
                return index;
            }else if(a[index]>value){
                end=index-1;
            }else{
                start = index+1;
            }
        }
        return a.length;
    }




}
