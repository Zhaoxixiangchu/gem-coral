### 项目介绍

> Coral是Gem家族成员之一，英文发音[ˈkɒrəl] 释义“珊瑚”。2020年首次与大家见面。她美如其名，不但外在美，内在更美...


Coral 企业快速开发框架，基于SpringBoot2.2x，MyBatis，Shiro等主流框架开发；前端页面采用LayUi开发。本系统技术栈选型专门面向后台开发人员快速上手而选，适合所有中小型企业或开发团队，开箱即用。http://www.gemframework.com
##### “一簇五彩斑斓的珊瑚” 

```
   ______                          ________
 .' ___  |                        |_   __  |
/ .'   \_|   .---.   _ .--..--.     | |_ \_|  _ .--.   ,--.    _ .--..--.    .---.
| |   ____  / /__\\ [ `.-. .-. |    |  _|    [ `/'`\] `'_\ :  [ `.-. .-. |  / /__\\
\ `.___]  | | \__.,  | | | | | |   _| |_      | |     // | |,  | | | | | |  | \__.,
 `._____.'   '.__.' [___||__||__] |_____|    [___]    \'-;__/ [___||__||__]  '.__.'

         GemFrame一款基于SpringBoot优秀的国产开源框架 http://www.gemframework.com


```


### 项目背景
自从[Pearl权限管理快速开发框架](https://gitee.com/gemteam/pearl)上线以来受到了大家的喜爱和认可，于是趁热打铁的把Coral赶出来了。

### 项目命名
项目的命名没有什么特殊的含义和规划，仅仅是一个代号，但还是会有很多人有意无意的问我，既然非要做一些解释（我看了好多项目发现现在好像都流行这个，解释一下显得有逼格有情怀）在这里统一解释：Coral释义为“珊瑚”一种珍贵且美丽的生物，也正好可以表达出我对她的定位和期待。

> ##### 刚刚社区的一位“霞光”悄悄的对我说....Coral 谐音“烤肉”。这太巧了，大概是因为我爱吃烤肉吧，所以我就把她做出来了。

### 项目理念：
这是一个技术栈爆炸的时代，我们不谈概念，不搞逼格，在我看来实际需求的技术选型和功能实现是最重要的，所以让我们一些化繁为简，返璞归真。技术没有好与坏，只有适不适合。

### 基础能力：
精细化权限管理，自定义菜单配置，安全身份认证，系统监控，代码生成，示例演示等。官方提供完善的API文档、部署文档、架构介绍文档以及视频教程帮助您快速学习入门，快速上手使用。


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


