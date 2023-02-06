- {{cards [[Spring框架面试题]] }}
- Spring中用了哪些设计模式? #card
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2023-02-04T02:36:42.519Z
  card-last-reviewed:: 2023-01-31T02:36:42.519Z
  card-last-score:: 3
	- BeanFactory {{cloze 工厂模式}}
	- Bean {{cloze 单例模式}}
	- AOP {{cloze 代理模式}}
	- RestTemplate {{cloze 模板方法}}
	- ApplicationListener {{cloze 观察者模式}}
- 什么是Spring的IOC(控制反转) #card
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2023-02-04T02:41:52.878Z
  card-last-reviewed:: 2023-01-31T02:41:52.878Z
  card-last-score:: 3
	- {{cloze 原本由代码控制对象的生命周期，交由spring框架管理}}
	- 实现 {{cloze 工厂模式+反射机制}}
- BeanFactory 和 ApplicationContext的区别 #card
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2023-02-04T02:49:14.854Z
  card-last-reviewed:: 2023-01-31T02:49:14.854Z
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
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2023-02-04T02:50:37.362Z
  card-last-reviewed:: 2023-01-31T02:50:37.362Z
  card-last-score:: 3
	- 默认Bean的作用域 {{cloze singleton}}不是线程安全的
	- 如果是有状态的Bean，可以使用更高一级的 {{cloze prototype}}
		- 有状态指的是有数据的存储功能
	- 或者是该数据字段使用 {{cloze ThreadLocal}} 修饰
- Spring Bean的生命周期，描述一下 4阶段-2扩展点 #card
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2023-02-04T02:36:16.030Z
  card-last-reviewed:: 2023-01-31T02:36:16.030Z
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
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2023-02-04T02:36:26.690Z
  card-last-reviewed:: 2023-01-31T02:36:26.690Z
  card-last-score:: 3
	- BF {{cloze 是框架用来管理和装配普通bean的容器}}
	- FB {{cloze 是用以生产对象和装饰对象的工厂接口}}
- 如何解决Bean注入时的循环依赖问题  #card
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2023-02-04T02:42:19.402Z
  card-last-reviewed:: 2023-01-31T02:42:19.402Z
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
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2023-02-04T02:51:31.810Z
  card-last-reviewed:: 2023-01-31T02:51:31.810Z
  card-last-score:: 3
	- AOP 面向切面编程，核心是使用 {{cloze 代理模式}}，对目标方法执行前后进行处理
	- 应用场景 {{cloze 日志记录}} {{cloze 接口签名校验}}
- 为什么要用SpringBoot #card
  card-last-interval:: 2.34
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-02-02T09:17:38.646Z
  card-last-reviewed:: 2023-01-31T01:17:38.647Z
  card-last-score:: 3
	- {{cloze 独立运行spring项目}}
	- {{cloze 内嵌servlet容器}}
	- {{cloze 简化maven配置，使用 starter 扩展}}
	- {{cloze 引入相关 starter后，自动装配相关bean}}
- SpringBoot启动流程
	- 运行main方法，SpringBootApplication
	- 初始化阶段
		- 通过类加载器，读取classpath下的 {{cloze spring.factories}} 配置文件 和 {{cloze application.yml}} 资源文件
		- 创建 ApplicationContext，扫描指定包下的class文件，创建BeanFactory，开始自动装配
- 如何自定义一个SpringBoot-start #card
  card-last-interval:: 0.51
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-02-06T23:38:01.161Z
  card-last-reviewed:: 2023-02-06T11:38:01.162Z
  card-last-score:: 3
	- 引入 `spring-boot-auto-config` 和 `spring-boot-configuration-processor` 包
	- 定义并实现自己的Bean
	- 编写一个`Configuration类`，告知何时加载自己的Bean
	- 将这个类写入 `spring.factories` 便于启动时自行装载