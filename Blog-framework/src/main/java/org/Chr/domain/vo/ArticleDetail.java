package org.Chr.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetail {
    private Long id;
    //标题
    private String title;
    //所属分类名
    private String categoryName;
    //所属分类id
    private Long categoryId;
    //访问量
    private Long viewCount;
    //内容
    private String content;
    //是否允许评论 1是，0否
    private String isComment;

    private Date createTime;
}
