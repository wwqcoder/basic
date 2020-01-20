package test.工具合集.银行卡;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/8/9
 * @描述
 */
public class BankCardNumUtil {
    private static final String MIDD16 = "******";
    private static final String MIDD19 = "*********";

    /**
     * 隐藏银行卡号 , 留下前6后4 , 中间是*号
     * @param bankCardNum
     * @return
     */
    public static String hideBankCardNum(String bankCardNum) {
        bankCardNum = bankCardNum.trim();
        if (bankCardNum.length() == 16) {
            return bankCardNum.substring(0, 6) + MIDD16 + bankCardNum.substring(12);
        }
        else if (bankCardNum.length() == 19) {
            return bankCardNum.substring(0, 6) + MIDD19 + bankCardNum.substring(15);
        }
        else {
            return bankCardNum;
        }
    }

    public static void main(String[] args){
        String bankCardNum1= "1234567890123456";
        String bankCardNum2= "1234567890123456789";
        System.out.println(BankCardNumUtil.hideBankCardNum(bankCardNum1));
        System.out.println(BankCardNumUtil.hideBankCardNum(bankCardNum2));
    }
}
