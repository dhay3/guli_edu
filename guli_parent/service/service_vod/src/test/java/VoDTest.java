import com.aliyuncs.exceptions.ClientException;
import com.chz.vod.util.VoDUtil;
import org.junit.jupiter.api.Test;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/17 19:13
 * @Description: TODO
 */
public class VoDTest {
    @Test
    void test() throws ClientException {
        System.out.println(VoDUtil.getPlayAuth("cc3523f8d9e345d5bc709e8b6e31e765"));
    }
}
