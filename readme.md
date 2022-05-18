### 1.使用方式
1. 引入该依赖包
2. 在应用的启动类上增加
    ```java
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    ```
3. 配置属性
    ```yaml
    yugb:
      log:
        log-table-name: sys_operate_log         #日志生成的表名
        weaving-type: afterReturning,afterThrowing    #aop织入类型包括before,after,around,afterReturning,afterThrowing
        operate-username-of-session: username   #当前登录用户用户存放在session中的key值
    ```
   
4. 在用户登录成功的时候，将当前用户名放入到session
   ```java
   @Autowired
   private LogConfigProperties properties;
   
   
   request.getSession().setAttribute(properties.getOperateUsernameOfSession(), userDetails.getUsername());
   ```
   
