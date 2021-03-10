package org.geektimes.web.mvc.Context;


import org.geektimes.web.mvc.function.ThrowableAction;
import org.geektimes.web.mvc.function.ThrowableFunction;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.servlet.ServletContext;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * 应用上下文，全局使用
 * @author  mbond
 */
public class ComponentContext {

    /**
     * ComponentContext的创建由listener构建
     * 获取ComponentContext的key
     */
    public static final String CONTEXT_NAME = ComponentContext.class.getName();
    /**
     * jndi根目录
     */
    private static final String COMPONENT_ENV_CONTEXT_NAME = "java:comp/env";
    /**
     * java标准日志
     */
    private static final Logger logger = Logger.getLogger(CONTEXT_NAME);
    /**
     * 根上下文，容器提供，赋值在最开始的入口，以供后续使用
     * tomcat下部署多个webapp时不冲突，真实环境都是一个tomcat对应一个webapp
     * static字段属于classloder缓存，不是jvm缓存
     */
    private static ServletContext servletContext;
    /**
     * 组件上下文
     * javax.naming.Context
     */
    private Context envContext; // Component Env Context
    /**
     * 应用的classloader
     */
    private ClassLoader classLoader;
    /**
     * 组件实例有序集合
     */
    private Map<String, Object> componentsMap = new LinkedHashMap<>();

    /**
     * 获取 ComponentContext
     * 单例模式
     * @return
     */
    public static ComponentContext getInstance() {
        return (ComponentContext) servletContext.getAttribute(CONTEXT_NAME);
    }

    /**
     * 初始化
     * @param servletContext
     * @throws RuntimeException
     */
    public void init(ServletContext servletContext) throws RuntimeException {
        ComponentContext.servletContext = servletContext;
        servletContext.setAttribute(CONTEXT_NAME,this);
        this.classLoader = servletContext.getClassLoader();
        initEnvContext();
        instantiateComponents();
        initializeComponents();
    }
//=================step3==========
    /**
     * 初始化组件（支持 Java 标准 Commons Annotation 生命周期）
     * <ol>
     *  <li>注入阶段 - {@link Resource}</li>
     *  <li>初始阶段 - {@link //PostConstruct 初始化方法}</li>
     *  <li>销毁阶段 - {@link //PreDestroy 销毁方法}</li>
     * </ol>
     */
    private void initializeComponents() {
        componentsMap.values().forEach(component->{
            Class<?> componentClass = component.getClass();
            // 注入阶段 - {@link Resource}
            injectComponents(component, componentClass);
            // 初始阶段 - {@link PostConstruct}
            processPostConstruct(component, componentClass);
            // 销毁阶段 - {@link PreDestroy}
            processPreDestroy(component, componentClass);
        });
    }

    /**
     * 注入
     * @param component
     * @param componentClass
     */
    private void injectComponents(Object component, Class<?> componentClass) {
        Stream.of(componentClass.getDeclaredFields()).filter(field -> {
            int mods = field.getModifiers();
            //非static并且有@Resource注解的继续解析
            return !Modifier.isStatic(mods) &&
                    field.isAnnotationPresent(Resource.class);
        }).forEach(field -> {
            Resource resource = field.getAnnotation(Resource.class);
            String resoureName = resource.name();
            Object injectObjetc = componentsMap.get(resoureName);
            field.setAccessible(true);
            try {
                //注入对象
                field.set(component,injectObjetc);
            }catch (IllegalAccessException e){

            }
        });
    }



    /**
     * 初始化
     * @param component
     * @param componentClass
     */
    private void processPostConstruct(Object component, Class<?> componentClass) {
        Stream.of(componentClass.getMethods())
                .filter(method ->
                        !Modifier.isStatic(method.getModifiers()) &&      // 非 static
                                method.getParameterCount() == 0 &&        // 没有参数
                                method.isAnnotationPresent(PostConstruct.class) // 标注 @PostConstruct
                ).forEach(method -> {
            try {
                // 执行目标方法
                method.invoke(component);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 销毁
     */
    private void processPreDestroy(Object component, Class<?> componentClass) {
        Stream.of(componentClass.getMethods())
                .filter(method ->
                        !Modifier.isStatic(method.getModifiers()) &&      // 非 static
                                method.getParameterCount() == 0 &&        // 没有参数
                                method.isAnnotationPresent(PreDestroy.class) // 标注 @PostConstruct
                ).forEach(method -> {
            try {
                // 执行目标方法
                method.invoke(component);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


//=================step3==========
//=================step2==========
    /**
     * 初始化组件实例
     */
    private void instantiateComponents() {
        // 遍历获取所有的组件名称name
        List<String> componentNames = listAllComponentNames();
       for (String name :componentNames){
           System.out.println("加载到的name数目："+name);
       }
        // 通过依赖查找，实例化对象（ Tomcat BeanFactory setter 方法的执行，仅支持简单类型）
        componentNames.forEach(name -> {
            componentsMap.put(name, lookupComponent(name));
            System.out.println(componentsMap.get(name).getClass());
        });
    }

    /**
     * 根据name获取实例
     * @param name
     * @param <C>
     * @return
     */
    protected <C> C lookupComponent(String name) {
        return executeInContext(context -> (C) context.lookup(name));
    }

    /**
     * 获取所有的组件名称
     *
     * @return
     */
    public List<String> getComponentNames() {
        return new ArrayList<>(componentsMap.keySet());
    }
    /**
     * 通过名称进行依赖查找
     *
     * @param name
     * @param <C>
     * @return
     */
    public <C> C getComponent(String name) {
        return (C) componentsMap.get(name);
    }

    /**
     * 遍历获取所有的组件名称
     * 私有，内部调用
     * @return
     */
    private List<String> listAllComponentNames() {
        return listComponentNames("/");
    }

    /**
     * 遍历获取所有的组件名称
     * protected，子类同包可以调用
     * @param name
     * @return
     */
    protected List<String> listComponentNames(String name) {
        return executeInContext(context -> {
            //获取jndi根目录下所有元素 context下配置的Resource
            NamingEnumeration<NameClassPair> e = executeInContext(context, ctx -> ctx.list(name), true);
            if(e==null){
                return Collections.emptyList();
            }
            List<String> fullNames = new LinkedList<>();//存储所有组件（同spring里的bean）的name
            //遍历
            while (e.hasMoreElements()){
                NameClassPair element = e.nextElement();
                String className = element.getClassName();//元素的type
                String elementName = element.getName();//元素的name
                Class<?> targetClass = classLoader.loadClass(className);//加载元素
                //如果元素是目录的话，递归，直到是自定义的resource
                if(Context.class.isAssignableFrom(targetClass)){
                    fullNames.addAll(listComponentNames(elementName));
                }else{
                    String fullName = name.startsWith("/") ?
                            elementName : name + "/" + elementName;
                    fullNames.add(fullName);
                }
            }
            return fullNames;
        });
    }


    /**
     * 在 Context 中执行，通过指定 ThrowableFunction 返回计算结果
     *
     * @param function ThrowableFunction
     * @param <R>      返回结果类型
     * @return 返回
     * @see ThrowableFunction#apply(Object)
     */
    protected <R> R executeInContext(ThrowableFunction<Context, R> function) {
        return executeInContext(function, false);
    }

    /**
     * 在 Context 中执行，通过指定 ThrowableFunction 返回计算结果
     *
     * @param function         ThrowableFunction
     * @param ignoredException 是否忽略异常
     * @param <R>              返回结果类型
     * @return 返回
     * @see ThrowableFunction#apply(Object)
     */
    protected <R> R executeInContext(ThrowableFunction<Context, R> function, boolean ignoredException) {
        return executeInContext(this.envContext, function, ignoredException);
    }
    /**
     * 在 Context 中执行，通过指定 ThrowableFunction 返回计算结果
     * @param context
     * @param function
     * @param ignoredException
     * @param <R>
     * @return
     */
    private <R> R executeInContext(Context context, ThrowableFunction<Context, R> function,
                                   boolean ignoredException) {
        R result = null;
        try {
            result = ThrowableFunction.execute(context, function);
        } catch (Throwable e) {
            if (ignoredException) {
                logger.warning(e.getMessage());
            } else {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
//=================step2==========
//=================step1==========
    /**
     * 初始化component的上下文
     * @throws RuntimeException
     * 抛出异常，启动时感知
     */
    private void initEnvContext() throws RuntimeException{
        if(envContext!=null){
            return;
        }
        Context context = null;
        try {
            context = new InitialContext();
            this.envContext = (Context) context.lookup(COMPONENT_ENV_CONTEXT_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(context);
        }
    }

    /**
     * jndi异常时关闭
     * lambda语法，传入方法作为参数调用
     * @param context
     */
    private static void close(Context context) {
        if (context != null) {
            ThrowableAction.execute(context::close);
        }
    }

//=================step1==========

    public void destroy() throws RuntimeException {
        close(this.envContext);
    }
}
