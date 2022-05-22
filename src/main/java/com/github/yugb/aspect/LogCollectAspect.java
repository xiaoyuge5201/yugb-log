package com.github.yugb.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.yugb.annotation.YLog;
import com.github.yugb.bean.RequestLog;
import com.github.yugb.bean.enums.OperatorType;
import com.github.yugb.config.LogConfigProperties;
import com.github.yugb.dao.RequestLogDao;
import com.github.yugb.util.InsertLogThread;
import com.github.yugb.util.LoggerUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * 记录某些比较重要的用户请求并存入到数据库
 *
 * @author xiaoyuge
 */
@Order(1)
@ComponentScan(value = "com.github.yugb")
@Aspect
@EnableAspectJAutoProxy(exposeProxy = true)
@Component
public class LogCollectAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    HttpServletRequest request;

    private final ExecutorService service = Executors.newFixedThreadPool(20, r -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        thread.setName("Thread" + thread.getId());
        return thread;
    });

    @Autowired
    private RequestLogDao requestLogDao;

    @Autowired
    private LogConfigProperties properties;

    /**
     * 申明一个切点 里面是 execution表达式
     */
    @Pointcut("@annotation(com.github.yugb.annotation.YLog)")
    public void RequestAspect() {
    }

    /**
     * 织入类型
     */
    private List<String> weavingTypes;

    @PostConstruct
    public void initData() {
        List<String> lowCaseList = Arrays.asList(properties.getWeavingType().split(","));
        weavingTypes = lowCaseList.stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    /**
     * 前置通知(应用：各种校验)：在方法执行前执行，如果通知抛出异常，阻止方法运行
     * 请求method前打印内容
     */
    @Before(value = "RequestAspect()")
    @SuppressWarnings("CatchAndPrintStackTrace")
    public void doBefore(JoinPoint joinPoint) {
        try {
            if (weavingTypes.contains("before")) {
                RequestLog logObj = new RequestLog();
                logObj.setCreateDate(LocalDateTime.now(ZoneId.of("Asia/Shangha")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                logObj.setRequestUri(request.getRequestURL().toString());
                logObj.setMethod(request.getMethod());
                logObj.setRemoteAddr(LoggerUtil.getClientIP(request));
                logObj.setLogType("info");
                getTypeInfo(joinPoint, logObj);
                Map<String, String[]> parameterMap = request.getParameterMap();
                logObj.setMapToParams(parameterMap);
                Future<?> future = service.submit(new InsertLogThread(logObj, requestLogDao));
                logger.debug("@Before:日志拦截对象：{}，线程状态{}", logObj.toString(), future.isDone());
            }
        } catch (Exception ex) {
            logger.error("something has gone terribly wrong", ex);
        }
    }

    /**
     * 环绕通知(应用：十分强大，可以做任何事情)：方法执行前后分别执行，可以阻止方法的执行，必须手动执行目标方法
     * Around如果不执行proceed()，那么原方法将不会执行
     *
     * @param pjp 切点
     */
    @Around(value = "RequestAspect()")
    @SuppressWarnings("CatchAndPrintStackTrace")
    public Object doAround(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        try {
            if (weavingTypes.contains("around")) {
                RequestLog logObj = new RequestLog();
                logObj.setCreateDate(LocalDateTime.now(ZoneId.of("Asia/Shangha")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                logObj.setRequestUri(request.getRequestURL().toString());
                logObj.setMethod(request.getMethod());
                logObj.setRemoteAddr(LoggerUtil.getClientIP(request));
                logObj.setLogType("info");
                getTypeInfo(pjp, logObj);
                Map<String, String[]> parameterMap = request.getParameterMap();
                logObj.setMapToParams(parameterMap);
                Future<?> future = service.submit(new InsertLogThread(logObj, requestLogDao));
                logger.debug("@Around:日志拦截对象：{},线程状态：{}", logObj.toString(), future.isDone());
            }
            return pjp.proceed(args);
        } catch (Throwable throwable) {
            logger.error("something has gone terribly wrong", throwable);
        }
        return null;
    }

    /**
     * 后置通知(应用：常规数据处理)：方法正常返回后执行，如果方法中抛出异常，通知无法执行，必须在方法执行后才执行，所以可以获得方法的返回值。
     *
     * @param joinPoint 切点
     */
    @AfterReturning(value = "RequestAspect()")
    @SuppressWarnings("CatchAndPrintStackTrace")
    public void doAfterReturning(JoinPoint joinPoint) {
        if (weavingTypes.contains("afterreturning")) {
            RequestLog logObj = new RequestLog();
            logObj.setCreateDate(LocalDateTime.now(ZoneId.of("Asia/Shangha")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            logObj.setRequestUri(request.getRequestURL().toString());
            logObj.setMethod(request.getMethod());
            logObj.setRemoteAddr(LoggerUtil.getClientIP(request));
            logObj.setLogType("info");
            getTypeInfo(joinPoint, logObj);
            Map<String, String[]> parameterMap = request.getParameterMap();
            logObj.setMapToParams(parameterMap);
            Future<?> future = service.submit(new InsertLogThread(logObj, requestLogDao));
            logger.debug("@AfterReturning:日志拦截对象：{},线程状态：{}", logObj.toString(), future.isDone());
        }
    }

    /**
     * 最终通知(应用：清理现场)：方法执行完毕后执行，无论方法中是否出现异常
     *
     * @param joinPoint 切点
     */
    @After(value = "RequestAspect()")
    public void doAfter(JoinPoint joinPoint) {
        if (weavingTypes.contains("after")) {
            RequestLog logObj = new RequestLog();
            logObj.setCreateDate(LocalDateTime.now(ZoneId.of("Asia/Shangha")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            logObj.setRequestUri(request.getRequestURL().toString());
            logObj.setMethod(request.getMethod());
            logObj.setRemoteAddr(LoggerUtil.getClientIP(request));
            logObj.setLogType("info");
            getTypeInfo(joinPoint, logObj);
            Map<String, String[]> parameterMap = request.getParameterMap();
            logObj.setMapToParams(parameterMap);
            Future<?> future = service.submit(new InsertLogThread(logObj, requestLogDao));
            logger.debug("@After:日志拦截对象：{},线程状态：{}", logObj.toString(), future.isDone());
        }
    }

    /**
     * 异常通知 记录操作报错日志
     *
     * @param joinPoint 切点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "RequestAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        if (weavingTypes.contains("afterthrowing")) {
            logger.error("进入日志切面异常通知,异常信息为：{}", e.getMessage());
            RequestLog logObj = new RequestLog();
            logObj.setCreateDate(LocalDateTime.now(ZoneId.of("Asia/Shangha")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            logObj.setLogType("error");
            logObj.setRequestUri(request.getRequestURL().toString());
            logObj.setMethod(request.getMethod());
            logObj.setRemoteAddr(LoggerUtil.getClientIP(request));
            logObj.setException(e.toString());
            getTypeInfo(joinPoint, logObj);
            Future<?> future = service.submit(new InsertLogThread(logObj, requestLogDao));
            logger.error("@AfterThrowing:日志拦截对象：{},线程状态：{}", logObj.toString(), future.isDone());
        }
    }

    /**
     * 解析注解参数
     *
     * @param point     切入点
     * @param logObject 对象
     */
    public void getTypeInfo(JoinPoint point, RequestLog logObject) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        YLog yLog = method.getAnnotation(YLog.class);
        OperatorType type = yLog.type();
        switch (type) {
            case Create:
                logObject.setOperateType("增加操作");
                break;
            case Update:
                logObject.setOperateType("修改操作");
                break;
            case Delete:
                logObject.setOperateType("删除操作");
                break;
            case Retrieve:
                logObject.setOperateType("检索操作");
                break;
            case LOGIN:
                logObject.setOperateType("登录操作");
                break;
            case DownLoad:
                logObject.setOperateType("下载操作");
                break;
            case UpLoad:
                logObject.setOperateType("上传操作");
                break;
            case PAGE:
                logObject.setOperateType("进入页面操作");
                break;
            case COMMAND:
                logObject.setOperateType("指令下发操作");
                break;
            case API:
                logObject.setOperateType("接口调用");
                break;
            case TOKEN:
                logObject.setOperateType("获取TOKEN");
                break;
            case CHECK:
                logObject.setOperateType("验证");
                break;
            default:
                break;
        }
        //下面两个方法在没有使用JSF的项目中是没有区别的
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        //从session里面获取对应的值
        String username = (String) requestAttributes.getAttribute(properties.getOperateUsernameOfSession(), RequestAttributes.SCOPE_SESSION);
        logObject.setModule(yLog.module());
        logObject.setDescription(yLog.desc());
        logObject.setUsername(username);
    }

    /**
     * 获取注解中传递的动态参数的参数值
     *
     * @param joinPoint 切入点
     * @param name      名称
     * @return 返回结果
     */
    @SuppressWarnings("StringSplitter")
    public String getAnnotationValue(JoinPoint joinPoint, String name) {
        String paramName = name;
        // 获取方法中所有的参数
        Map<String, Object> params = getParams(joinPoint);
        // 参数是否是动态的:#{paramName}
        if (paramName.matches("^#\\{\\D*\\}")) {
            // 获取参数名
            paramName = paramName.replace("#{", "").replace("}", "");
            // 是否是复杂的参数类型:对象.参数名
            if (paramName.contains(".")) {
                String[] split = paramName.split("\\.", -1);
                // 获取方法中对象的内容
                Object object = getValue(params, split[0]);
                // 转换为JsonObject
                if (object != null) {
                    JSONObject jsonObject = JSON.parseObject(object.toString());
                    Object o = jsonObject.get(split[1]);
                    return String.valueOf(o);
                }
                return null;
            }
            // 简单的动态参数直接返回
            return String.valueOf(getValue(params, paramName));
        }
        // 非动态参数直接返回
        return name;
    }

    /**
     * 根据参数名返回对应的值
     *
     * @param map       对象
     * @param paramName 参数名称
     * @return 返回结果
     */
    public Object getValue(Map<String, Object> map, String paramName) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals(paramName)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * 获取方法的参数名和值
     *
     * @param joinPoint 切点
     * @return 返回结果
     */
    public Map<String, Object> getParams(JoinPoint joinPoint) {
        Map<String, Object> params = new HashMap<String, Object>(8);
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] names = signature.getParameterNames();
        for (int i = 0; i < args.length; i++) {
            params.put(names[i], args[i]);
        }
        return params;
    }
}