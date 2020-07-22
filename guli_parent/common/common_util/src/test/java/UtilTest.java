import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/22 21:55
 * @Description: TODO
 */
public class UtilTest {
    public static void main(String[] args) throws ParseException {
        //获取实例
        NumberFormat instance = NumberFormat.getInstance();
        //获取带有金额的实例
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
        //获取带有百分比的实例
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        instance.setGroupingUsed(true);//三个数字为一组
        instance.setMaximumIntegerDigits(3);//设置整数部分的位数, 从小数点处开始计算
        instance.setMaximumFractionDigits(1);//设置小数部分的位数, 从小数点处开始计算
        //同理setMinimum
        String num = instance.format(00100000.1200);//会裁去开头的0和末尾的0
        System.out.println(num + instance.getCurrency());//获取当前系统所在位置的货币单位
        System.out.println("-----------------------");
        //#开头的0和末尾的0不显示,
        DecimalFormat decimalFormat = new DecimalFormat("%");
        System.out.println(decimalFormat.format(0.26));//26%
    }
}
