package org.Chr.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleViewCountVo {
    private Long id;
    //访问量
    private Long viewCount;
}
