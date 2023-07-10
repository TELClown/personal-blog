package org.Chr.runner;

import org.Chr.Utils.BeanCopyUtils;
import org.Chr.Utils.RedisCache;
import org.Chr.domain.entity.Article;
import org.Chr.domain.vo.ArticleViewCountVo;
import org.Chr.mapper.ArticleMapper;
import org.Chr.service.ArticleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class InitializeRunner implements CommandLineRunner {

    @Resource
    ArticleService articleService;

    @Resource
    ArticleMapper articleMapper;
    @Resource
    RedisCache  redisCache;

    @Override
    public void run(String... args) throws Exception {
        List<Article> articleList = articleService.list();
        Map<String,Integer> viewCountMap = articleList.stream().collect(Collectors.toMap(article -> article.getId().toString(), article -> {
            return article.getViewCount().intValue();
        }));
        redisCache.setCacheMap("article:viewCount",viewCountMap);
    }
}
