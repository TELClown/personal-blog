package org.Chr.job;

import org.Chr.Utils.RedisCache;
import org.Chr.domain.entity.Article;
import org.Chr.mapper.ArticleMapper;
import org.Chr.service.ArticleService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

@Component
public class updateViewCountJob {

    @Resource
    RedisCache redisCache;
    @Resource
    ArticleMapper articleMapper;
    @Scheduled(cron = "0/59 * * * * ?")
    //定时任务，每分钟将redis中的数据同步至mysql中
    public void updateViewCount(){
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
        Set<String> articleId = viewCountMap.keySet();

        articleId.forEach(item ->{
            Long viewCount = Long.valueOf(viewCountMap.get(item).longValue());
            System.out.println(viewCount);
            articleMapper.updateViewCount(Long.valueOf(item),viewCount);
        });
        System.out.println("schedule run ");
    }
}
