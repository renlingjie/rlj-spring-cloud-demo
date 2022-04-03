package con.rlj.springcloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Renlingjie
 * @name
 * @date 2022-03-08
 */
@RestController
@RefreshScope
public class Controller {

    @Value("${name}")
    private String name;

    @Value("${myWords}")
    private String words;

    // 发送请求，从配置中心获取配置，配置中心接收到请求，如果配置中心本地有，
    // 就从本地拿，如果没有，就从存放配置文件的Gitee上拿，拿到后保存到本地一份
    @GetMapping("/name")
    public String getName(){
        return name;
    }

    @GetMapping("/words")
    public String getWords(){
        return words;
    }
}
