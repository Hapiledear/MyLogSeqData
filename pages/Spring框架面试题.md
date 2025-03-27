- {{cards [[Spring框架面试题]] }}
	- Summary: 10 items, 10 review counts [[2025年03月27日]]
		- Remembered:   10 (100%)
		- Forgotten :   0 (0%)
	- Summary: 15 items, 15 review counts [[2025年03月24日]]
		- Remembered:   15 (100%)
		- Forgotten :   0 (0%)
- Spring中用了哪些设计模式? #card
  card-last-interval:: 0
  card-repeats:: 12
  card-ease-factor:: 1.3
  card-next-schedule:: 2025-03-27T05:44:01.470Z
  card-last-reviewed:: 2025-03-27T05:44:01.470Z
  card-last-score:: 3
	- BeanFactory {{cloze 工厂模式}}
	- Bean {{cloze 单例模式}}
	- AOP {{cloze 代理模式}}
	- RestTemplate {{cloze 模板方法}}
	- ApplicationListener {{cloze 观察者模式}}
- 什么是Spring的IOC(控制反转) #card
  card-last-interval:: 0
  card-repeats:: 12
  card-ease-factor:: 1.3
  card-next-schedule:: 2025-03-27T05:44:11.819Z
  card-last-reviewed:: 2025-03-27T05:44:11.820Z
  card-last-score:: 3
	- {{cloze 原本由代码控制对象的生命周期，交由spring框架管理}}
	- 实现 {{cloze 工厂模式+反射机制}}
- BeanFactory 和 ApplicationContext的区别 #card
  card-last-interval:: 0
  card-repeats:: 13
  card-ease-factor:: 1.3
  card-next-schedule:: 2025-03-27T05:44:28.246Z
  card-last-reviewed:: 2025-03-27T05:44:28.246Z
  card-last-score:: 3
	- BF 可以理解为 “低级容器”，只是简单的 k-v结构
	- 依赖关系 {{cloze ApplicationContext是BeanFactory的子接口,扩展了BeanFactory的功能}}
	- 装载Bean的方式
		- {{cloze 采用延迟加载的形式来注入Bean}} BF
		- {{cloze 在容器启动时，一次性创建了所有的Bean}} AC
	- 注册Bean的方式
		- {{cloze 手动注册}} BF
		- {{cloze 自动注册}} AC
- Spring中的Bean是线程安全的吗？如何处理线程并发问题 #card
  card-last-interval:: 82.64
  card-repeats:: 11
  card-ease-factor:: 1.4
  card-next-schedule:: 2025-06-17T18:14:05.654Z
  card-last-reviewed:: 2025-03-27T03:14:05.655Z
  card-last-score:: 5
	- 默认Bean的作用域 {{cloze singleton}}不是线程安全的
	- 如果是有状态的Bean，可以使用更高一级的 {{cloze prototype}}
		- 有状态指的是有数据的存储功能
	- 或者是该数据字段使用 {{cloze ThreadLocal}} 修饰
- Spring Bean的生命周期，描述一下 4阶段-2扩展点 #card
  card-last-interval:: 0
  card-repeats:: 11
  card-ease-factor:: 1.3
  card-next-schedule:: 2025-03-27T05:42:29.331Z
  card-last-reviewed:: 2025-03-27T05:42:29.331Z
  card-last-score:: 3
	- 实例化
	- 属性填充
	- 初始化
	- 初始化扩展
		- xxxAware接口，用以获取spring容器的资源和属性
		- BeanPostProcessor接口
		- init-method方法
	- 销毁
	- 销毁扩展
		- DisposableBean接口的destory方法
		- destory-method方法
- BeanFactory 和 FactoryBean的区别 #card
  card-last-interval:: 0
  card-repeats:: 12
  card-ease-factor:: 1.3
  card-next-schedule:: 2025-03-27T05:42:39.899Z
  card-last-reviewed:: 2025-03-27T05:42:39.899Z
  card-last-score:: 3
	- BF {{cloze 是框架用来管理和装配普通bean的容器}}
	- FB {{cloze 是用以生产对象和装饰对象的工厂接口}}
- 如何解决Bean注入时的循环依赖问题  #card
  card-last-interval:: 0
  card-repeats:: 11
  card-ease-factor:: 1.3
  card-next-schedule:: 2025-03-27T05:41:59.871Z
  card-last-reviewed:: 2025-03-27T05:41:59.871Z
  card-last-score:: 3
	- 循环依赖能自动解决的前提条件
		- {{cloze 不是全属性构造器}}
		- {{cloze 单例}}
	- 三级缓存
		- 成品 -- 一级缓存 singletonObjects
			- {{cloze 完整的Bean，也即完成了属性填充，彻底完成初始化}}
		- 半成品--二级缓存
			- {{cloze 用以保存 实例化完成的Bean}}
			- {{cloze 通过 getObject() 从三级缓存中取出一次bean}}
		- 原材料工厂 --三级缓存 singletonFactories
			- {{cloze 用于保存Bean创建工厂}}
	- 查询过程
		- {{cloze 一级缓存 -> 二级缓存 -> 三级缓存 }}
		- 对象存放在哪个缓存，看对象的创建进度
	- 为什么要有三级缓存
		- {{cloze 保证不管什么时候获取到的都是同一对象}}
		- spring有 普通对象 和 代理对象,在未执行完 BeanPostProcessor时不知道使用哪一个
- 什么是Spring 的AOP,项目中是如何使用的 #card
  card-last-interval:: 0
  card-repeats:: 14
  card-ease-factor:: 1.3
  card-next-schedule:: 2025-03-27T05:45:22.470Z
  card-last-reviewed:: 2025-03-27T05:45:22.470Z
  card-last-score:: 3
	- AOP 面向切面编程，核心是使用 {{cloze 代理模式}}，对目标方法执行前后进行处理
	- 应用场景 {{cloze 日志记录}} {{cloze 接口签名校验}}
- 为什么要用SpringBoot #card
  card-last-interval:: 0
  card-repeats:: 12
  card-ease-factor:: 1.3
  card-next-schedule:: 2025-03-27T05:43:31.423Z
  card-last-reviewed:: 2025-03-27T05:43:31.423Z
  card-last-score:: 3
	- {{cloze 独立运行spring项目}}
	- {{cloze 内嵌servlet容器}}
	- {{cloze 简化maven配置，使用 starter 扩展}}
	- {{cloze 引入相关 starter后，自动装配相关bean}}
- SpringBoot启动流程 #card
  card-last-interval:: 7.33
  card-repeats:: 5
  card-ease-factor:: 1.52
  card-next-schedule:: 2025-03-31T07:38:06.017Z
  card-last-reviewed:: 2025-03-24T00:38:06.017Z
  card-last-score:: 3
	- 运行main方法，SpringBootApplication
	- 初始化阶段
		- 通过类加载器，读取classpath下的 {{cloze spring.factories}} 配置文件 和 {{cloze application.yml}} 资源文件
		- 创建 ApplicationContext，扫描指定包下的class文件，创建BeanFactory，开始自动装配
- 如何自定义一个SpringBoot-start #card
  card-last-interval:: 0
  card-repeats:: 8
  card-ease-factor:: 1.3
  card-next-schedule:: 2025-03-27T05:44:49.198Z
  card-last-reviewed:: 2025-03-27T05:44:49.198Z
  card-last-score:: 3
	- 引入 `spring-boot-auto-config` 和 `spring-boot-configuration-processor` 包
	- 定义并实现自己的Bean
	- 编写一个`Configuration类`，告知何时加载自己的Bean
	- 将这个类写入 `spring.factories` 便于启动时自行装载
- Spring中的嵌套事务如何实现 #card
  card-last-interval:: 7.33
  card-repeats:: 5
  card-ease-factor:: 1.52
  card-next-schedule:: 2025-03-31T07:37:19.817Z
  card-last-reviewed:: 2025-03-24T00:37:19.817Z
  card-last-score:: 3
	- 对于支持嵌套事务的数据库，直接嵌套
	- MySQL不支持嵌套事务，而是采用 {{cloze 保存点}}的机制
- Spring 注解失效的场景 #card
  card-last-interval:: 7.33
  card-repeats:: 5
  card-ease-factor:: 1.52
  card-next-schedule:: 2025-03-31T07:37:42.425Z
  card-last-reviewed:: 2025-03-24T00:37:42.425Z
  card-last-score:: 3
	- 没有获取到代理对象
	- 跨线程调用没有传递必要参数，如在事务中开启多线程会使得事务失效
- Spring MVC中，过滤器和拦截器的区别 #card
  card-last-interval:: 15.05
  card-repeats:: 4
  card-ease-factor:: 1.94
  card-next-schedule:: 2025-04-08T01:39:16.497Z
  card-last-reviewed:: 2025-03-24T00:39:16.497Z
  card-last-score:: 3
	- 实现方式
		- 过滤器是基于java的Filter接口
		- 拦截器是基于spring框架的 HandlerInterceptor接口
	- 执行时机
		- 过滤器是在进入Servlet容器之后，Controller之前。更倾向于处理与HTTP请求和响应
		- 拦截器是在到达Controller之后、处理Controller的具体逻辑之前，对Controller进行前置和后置处理。
	- 拦截目标
		- 过滤器，可拦截所有请求，包括静态资源
		- 拦截器，更精细的控制拦截，只拦截特定Controller的请求
	- 执行流程
		- 过滤器，按照定义顺序执行
		- 拦截器，在代码中控制自由控制是否调用后续拦截器
- Spring 事务的传播机制 #card
  card-last-interval:: 13.21
  card-repeats:: 5
  card-ease-factor:: 1.8
  card-next-schedule:: 2025-04-06T05:42:58.768Z
  card-last-reviewed:: 2025-03-24T00:42:58.768Z
  card-last-score:: 3
	- 基于对现有事务的态度分类
		- `REQUIRED` 当前*保证有*
		- `REQUIRES_NEW` 当前*要求新建*
		- `SUPPORTS` 当前*可以有*
		- `NOT_SUPPORTED` 当前*不支持*，以非事物方式运行
		- `NEVER` 当前和上下文*都不支持*，否则抛错
		- `NESTED` 当前开启*嵌套*事务
		- `MANDATORY` *上下文必须有*，否则抛错
-