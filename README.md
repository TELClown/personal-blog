# personal-blog

##项目说明

该项目为个人博客项目，使用SpringBoot，MybatisPlus，SpringSecurity，Redis完成

该项⽬是前后端分离的博客系统，主要为前端提供接⼝，后端完成相应接⼝的逻辑操作。功能主要包括⾸⻚⽂章展
⽰，⽂章内容详情展⽰，⽂章评论，显⽰友链，认证登录，⽤⼾信息展⽰以及修改，⽤⼾退出。

## 项目主要实现

1.使⽤OSS完成对头像的云保存，并将头像的URL存⼊数据库中。

2.使⽤SpringSecurity+JWT完成认证登录，并将个⼈登录状态信息存⼊Redis中。

3.使⽤Redis优化每次⽂章浏览数更改时需要修改频繁访问数据库，将浏览数存⼊Redis中，提⾼查询性能。通过设置定时任
务，每经过⼀段时间⾃动将Redis的浏览数同步⾄数据库中，保证数据⼀致性。

4.利⽤AOP实现⽇志输出，打印请求URL，请求⼊参，IP地址,Controller层执⾏的⽅法等。

