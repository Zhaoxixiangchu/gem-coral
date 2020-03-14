# coral

#### 介绍
Coral是Gem系列中的一员，Coral权限管理系统后台技术基于SpringBoot2.2、MyBatis、Shiro等开发；前端页面采用LayUi开发。本系统技术栈选型专门面向后台开发人员快速上手而选，适合所有中小型企业或开发团队，开箱即用。

基础能力建设：精细化权限管理，自定义菜单配置，安全身份认证，系统监控，代码生成，示例演示等。官方提供完善的API文档、部署文档、架构介绍文档以及视频教程帮助您快速学习入门，快速上手使用。

GemFrame团队致力为全球中小型企业提供更多基础框架最全面的解决方案！

```
   ______                          ________
 .' ___  |                        |_   __  |
/ .'   \_|   .---.   _ .--..--.     | |_ \_|  _ .--.   ,--.    _ .--..--.    .---.
| |   ____  / /__\\ [ `.-. .-. |    |  _|    [ `/'`\] `'_\ :  [ `.-. .-. |  / /__\\
\ `.___]  | | \__.,  | | | | | |   _| |_      | |     // | |,  | | | | | |  | \__.,
 `._____.'   '.__.' [___||__||__] |_____|    [___]    \'-;__/ [___||__||__]  '.__.'

         GemFrame一款基于SpringBoot优秀的国产开源框架 http://www.gemframework.com

```

#### 官方社区

[http://bbs.gemframework.com](http://www.gemframework.com/bbs) _推荐单机鼠标右键选择新页面打开_ 

支持我就帮忙在上方依次Watch、Star一波再离开，感激支持！

#### 代码结构
```
coral 
 |--coral-apirest 为前后端分离提供RESTful API
 |
 |--coral-admin 管理后台Web
 |
 |--coral-common 公共模块
 |
 |--coral-common-service 服务模块
 |
 |--coral-common-mapper 数据操作模块
 |
 |--coral-common-model 模型层

```
#### 包结构
```
java
  |
  |--com.gemframework.common 公共包
  |--com.gemframework.common.annotation 公共自定义注解
  |--com.gemframework.common.config 公共配置
  |--com.gemframework.common.constant 公共常量
  |--com.gemframework.common.utils 公共工具包

  |--com.gemframework.constant 常量包
  |--com.gemframework.config 配置包
  |--com.gemframework.utils 工具包

  |--com.gemframework.controller 控制器包

  |--com.gemframework.mapper 映射器包 

  |--com.gemframework.model 模型类包
  |--com.gemframework.model.annotation 模型注解
  |--com.gemframework.model.common 公共模型
  |--com.gemframework.model.entity 实体对象
  |--com.gemframework.model.entity.po 持久对象
  |--com.gemframework.model.entity.vo 表现层对象
  |--com.gemframework.model.request API请求体对象
  |--com.gemframework.model.response API响应体对象

  |--com.gemframework.service 接口服务包
  |--com.gemframework.service.impl 接口实现包

```
#### 静态资源包结构
```
resource
  |
  |--static 存放静态文件处 如css,js,img,fonts等
  |--templates 存放页面模版处 如html,jsp,vm等
  |--mapper 存放Mybatis SQL映射文件处

```


#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


