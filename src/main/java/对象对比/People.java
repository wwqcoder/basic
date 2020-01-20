package 对象对比;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/28
 * @描述
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-10-13 00:11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class People {

    @AliasForCompare(describe = "姓名")
    private String name;

    @AliasForCompare(describe = "性别")
    private String gender;

    @AliasForCompare(describe = "基础信息")
    private BaseInfo baseInfo;

}
