## 公共项目集合
基于开源项目 springBoot plus 开发了一套用户管理系统。目前集成了用户登录模块，系统模块，日志模块，权限模块

## 主要特性

1. 集成spring boot 常用开发组件集、公共配置、AOP日志等
2. 集成mybatis plus快速dao操作
3. 集成swagger2，可自动生成api文档
4. 集成jwt、shiro权限控制
5. 集成redis、spring cache缓存
6. 集成druid连接池，JDBC性能和慢查询检测

## 现有公共功能

1. 日志功能

   全局控制层日志处理器，目前支持输出方式有两种：

   1. 输出到文件
   2. 输出到mysql 数据库，默认输出到mysql

   具体操作在配置文件中修改：

   ```
   aop:
       log:
         # 是否启用
         enabled: true
         # 1. 输出到文件
         # 2. 输出到数据库
         output-type: 2
         # 请求日志在控制台是否格式化输出，local环境建议开启，服务器环境设置为false
         request-log-format: true
         # 响应日志在控制台是否格式化输出，local环境建议开启，服务器环境设置为false
         response-log-format: true
   ```

2. 全局异常处理

   ​	目前有全局的异常处理机制，用户在控制层只需要处理正常情况即可。如果需要自行捕获异常，请在catch中抛出对应的自定义异常信息，例子如下：

   ```Java
   try {
      todo 
   } catch (Exception e) {
      throw new BusinessException("异常信息"); // 使用已定义好的一些异常类
   }
   ```

3. 使用 swagger 生成 api 文档

   地址：http://localhost:8888/swagger-ui.html#/

   命名规范:
    1. 路由地址不能出现动词
   例子：
   
   /sys/dept post

   **注意：修改成自己的端口**

4. 自定义拦截器

   

## 使用说明
