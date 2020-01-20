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
 * @description: 基本信息
 * @author: hu_pf
 * @create: 2019-10-13 00:12
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseInfo {

    @AliasForCompare(describe = "地址")
    private String addree;

    @AliasForCompare(describe = "邮箱")
    private String email;

    @AliasForCompare(describe = "手机号")
    private String mobiePhone;
}
