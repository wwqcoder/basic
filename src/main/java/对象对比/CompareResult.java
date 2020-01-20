package 对象对比;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/28
 * @描述
 */
import lombok.Data;

/**
 * @program: springBootPractice
 * @description: 比对结果
 * @author: hu_pf
 * @create: 2019-10-13 00:34
 **/
@Data
public class CompareResult {

    private String paraName;

    private String describe;

    private Object oldValue;

    private Object newValue;
}