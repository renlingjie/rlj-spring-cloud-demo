package com.rlj.springcloud.rules;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Renlingjie
 * @name
 * @date 2022-02-02
 */
@NoArgsConstructor //声明一个无参构造方法
public class ConsistentHashRule extends AbstractLoadBalancerRule implements IRule {
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String uri = request.getServletPath() + "?" + request.getQueryString();
        // uri是除却http://ip:port这个头部剩余的部分
        return route(uri,getLoadBalancer().getAllServers());
    }

    public Server route(String requestUri, List<Server> serverList){
        if (CollectionUtils.isEmpty(serverList)){
            return null;
        }
        // TreeMap不是向LinkedHashMap那样按照插入顺序进行排序的，而是默认按照键值进行升序排序的，这个就很适合那个"环"
        TreeMap<Long,Server> address = new TreeMap<>();
        /**
         * 根据AbstractLoadBalancerRule为我们提供的getLoadBalancer()方法拿到负载均衡器，进而拿到所有的服务节点，
         * 遍历每一个服务节点。每个服务节点进入8次轮回，每个轮回通过节点ID+当前轮回次数，拿到当前轮回的hash值，之后以
         * 每个hash为key，当前节点为value，存储到会对key排序的TreeMap中。也就是说：将每一个服务器，复制8份，分散到
         * "环"中，之后通过TreeMap的tailMap，能拿到所有比传入参数大(大即顺时针)的key，而最近的key就是我们要找的
         */
        serverList.stream().forEach(server -> {
            for (int i = 0; i < 8; i++){
                long hash = getHash(server.getId() + i);
                address.put(hash,server);
            }
        });
        long requestKey = getHash(requestUri);
        SortedMap<Long, Server> biggerKeySortedMap = address.tailMap(requestKey);
        if (biggerKeySortedMap.isEmpty()){ //没有比它大的，它右边就是环的开头，即拿最小的，完成闭环
            return address.firstEntry().getValue();
        }
        return biggerKeySortedMap.get(biggerKeySortedMap.firstKey());
    }

    public long getHash(String key){
        MessageDigest md5;
        try {
            // md5做初始化
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] keyByte = null;
        try {
            keyByte = key.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        md5.update(keyByte);            //update把key通过UTF-8得到的编码塞进md5中
        byte[] digest = md5.digest();   //md5此时通过digest生成一串16位的byte[]
        long hashCode = ((long) (digest[2] & 0xFF << 16))
                | ((long) (digest[1] & 0xFF << 8))
                | ((long) (digest[0] & 0xFF));

        return hashCode & 0xffffffffL;
    }
}
