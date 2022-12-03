# Redis中雪崩、击穿、穿透详解
Redis应用中故障及应用：缓存雪崩、缓存穿透、缓存击穿、缓存预热、缓存更新、缓存降级

1：如何理解Redis缓存的雪崩、穿透、击穿问题：
## 雪崩：
定义：redis缓存中大量的key同时失效，此时又刚好有大量的请求打进来，直接打到数据库层，造成数据库阻塞甚至宕机。

解决办法：缓存雪崩的事前事中事后的解决方案如下：

事前：Redis 高可用，主从+哨兵，Redis cluster，避免全盘崩溃。
原有的失效时间基础上增加一个随机值，比如1-5分钟随机
加锁，当出现缓存失效的情况通过锁控制一定时间只有一个线程获取到资源从数据库拿到数据后写回缓存，后面基于缓存做查询
缓存预热
热点key设置永不过期。
事中：本地 ehcache 缓存 + hystrix 限流&降级，避免 MySQL 被打死。
事后：Redis 持久化，一旦重启，自动从磁盘上加载数据，快速恢复缓存数据。

## 穿透：
定义：指查询一个缓存和数据库都不存在的数据，导致尽管数据不存在但是每次都会到数据库查询。在访问量大时可能DB就会挂掉。如果有人利用不存在的key频繁攻击，则这就形成了漏洞。

解决办法：

如果一个查询返回的数据为空，我们仍然把这个空结果进行缓存，但它的过期时间会很短，最长不超过五分钟。
接口层增加校验，用户鉴权，id做基础校验。
采用布隆过滤器，将所有可能存在的数据hash到一个足够大的bitmap中。（会有一定的出错率）
布隆过滤器：类似于HashSet，可以快速判断一个元素在集合中是否存在，应用场景：快速判断一个元素是否在某容器内，不存在直接返回。（关键点在于hash算法和容器大小。）

核心思想：

通过hash函数将一个元素映射到位阵列（bit array）中的一个点

多个hash函数，增加随机性，减少hash碰撞的概率。

扩大数组范围，使hash值均匀分布，减少hash碰撞的概率。

使用过程需要引入包：guava
```
package com.weidd.best.redis;
 
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
/**
 * @program: SortDemo
 * @author: weidd
 * @date: 2021-02-27 18:58
 **/
public class TestBloomFilter {
    //测试使用布隆过滤器解决缓存穿透问题
    //需引入:guava包
    private static final int capacity = 10000000;
    private static final int key = 9222222;
    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), capacity);
 
    static {
        for (int i = 0; i < capacity; i++) {
            bloomFilter.put(i);
        }
    }
 
    public static void main(String[] args) {
        long star=System.nanoTime();//微秒: 1 秒=1000000 微秒
        if(bloomFilter.mightContain(key)){
            System.out.println("布隆过滤器过滤到"+key);
        }
        long end=System.nanoTime();//微秒: 1 秒=1000000 微秒
        System.out.println("过滤消耗时间:"+(end-star)+"微秒");
 
    }
}
```
> 运行结果：千万级的查询判断时间在0.2秒，时间相当的快。

> 布隆过滤器过滤到9222222
> 过滤消耗时间:230571微秒
> 经过多次验证发现，查不到的情况下耗时更短，千万级：0.058秒。
 
> 过滤消耗时间:58920微秒

解决穿透问题实际应用如下代码：

```
public String getByKey(String key) {
    // 通过key获取value
    String value = redisService.get(key);
    if (StringUtil.isEmpty(value)) {
        if (bloomFilter.mightContain(key)) {
            value = userService.getById(key);
            redisService.set(key, value);
            return value;
        } else {
            return null;
        }
    }
    return value;
}
```
## 击穿：

击穿与雪崩的不同在于缓存key失效的量级上。击穿是对于单个key值的缓存失效过期，雪崩则是大面积key同时失效。

解决办法：

> 若缓存数据基本不会发生更新，则可尝试将热点数据设置为永不过期。
若缓存的数据更新不频繁，且缓存刷新的整个流程耗时较少的情况下，则可以采用基于 Redis、zookeeper 等分布式中间件的分布式互斥锁，或者本地互斥锁以保证仅少量的请求能请求数据库并重新构建缓存，其余线程则在锁释放后能访问到新缓存。
若缓存的数据更新频繁或者在缓存刷新的流程耗时较长的情况下，可以利用定时线程在缓存过期前主动地重新构建缓存或者延后缓存的过期时间，以保证所有的请求能一直访问到对应的缓存。
