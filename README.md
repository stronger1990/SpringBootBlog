简单看了一下，没深入看，完整的博客包括前台文章显示以及后台管理，如果想做博客或后台管理系统，可以参考这个文章。
感觉这个文章亮点在前端页面(H5)和业务逻辑的处理(表和字段如何分配等比较完整)，没有用到很多编程技巧，中规中矩。
不过登录页UI、拦截器拦截在Session和Cookie没有登录信息的请求，重定向到登录页，这部分还是值得我去模仿。

# Kyrie Blog

## 个人博客系统
Kyrie Blog是由SpringBoot1.5 + MyBatis + Thymeleaf等技术实现的个人网站，学习重点是博客业务逻辑。
### 适用对象
* Spring Boot 初学者。该博客系统综合运用了作者发表的 《Spring Boot 入门》 系列的文章提及的知识内容，初学者可以阅读文章以及结合该项目学习。
* 与作者一样，使用 hexo 但苦于没有后台管理工具（界面）管理文章的写作者。该博客系统模仿 hexo 生成的访问路径，并支持 markdown 文件导入功能。
* 懵懂者。初次接触博客系统的人。
### 技术栈
#### 后端 （重点，可以不用看Thymeleaf和Commonmark）
* 核心框架：SpringBoot
* 持久层框架：MyBatis
* 模板框架：Thymeleaf
* 分页插件：PageHelper
* 缓存框架：Ehcache
* Markdown：Commonmark

#### 前端 （这个可以不看，因为我目标是学习后台做个后台工程师，而不是前端，简单了解即可）
* JS框架：Jquery
* CSS框架：Bootstrap
* 富文本编辑器：editor.md
* 文件上传：dropzone
* 弹框插件：sweetalert

#### 第三方 （这个可以不看，因为不同平台有不同的API，阿里云的OSS也不错）
* 七牛云（文件上传）
* 百度统计

### 预览效果
#### 前端效果
![index](https://github.com/caozongpeng/github-static/blob/master/springBootBlog/index.png)

![archives](https://github.com/caozongpeng/github-static/blob/master/springBootBlog/archives.png)

![detail](https://github.com/caozongpeng/github-static/blob/master/springBootBlog/detail.png)

![category](https://github.com/caozongpeng/github-static/blob/master/springBootBlog/category.png)

![about](https://github.com/caozongpeng/github-static/blob/master/springBootBlog/about.png)

#### 后端效果

![adminlogin](https://github.com/caozongpeng/github-static/blob/master/springBootBlog/adminlogin.png)

![adminindex](https://github.com/caozongpeng/github-static/blob/master/springBootBlog/adminindex.png)

![articlepublish](https://github.com/caozongpeng/github-static/blob/master/springBootBlog/articlepublish.png)

![articlemanager](https://github.com/caozongpeng/github-static/blob/master/springBootBlog/articlemanager.png)

![filemanager](https://github.com/caozongpeng/github-static/blob/master/springBootBlog/filemanager.png)

![setting](https://github.com/caozongpeng/github-static/blob/master/springBootBlog/setting.png)

### 安装
下载源码，执行sql文件，然后修改application-dev.yml文件中连接数据库的用户名、密码。运行项目即可。

前端访问地址：http://localhost:8888

后台访问地址：http://localhost:8888/admin 用户名：admin 密码：123456

### 交流群
#### 欢迎加入：1103081979
![setting](https://github.com/caozongpeng/github-static/blob/master/springBootBlog/study.png)

###  如果此博客能帮助到你，请作者喝杯咖啡吧或者建设演示服务器
![wacht](https://github.com/caozongpeng/github-static/blob/master/money/wacht.jpg)
![alipay](https://github.com/caozongpeng/github-static/blob/master/money/alipay.jpg)
