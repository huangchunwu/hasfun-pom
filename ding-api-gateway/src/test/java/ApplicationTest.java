import cn.hasfun.ding.api.gateway.config.DingConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DingConfig.class)
public class ApplicationTest {

    @Autowired
    private DingConfig dingConfig;

    @Test
    public void contextLoads() {
        System.out.println(dingConfig.toString());
    }
}
