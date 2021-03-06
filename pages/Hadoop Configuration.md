- Hadoop使用了一套自己的Configuration #配置文件
	- 配置文件采用[[xml]]格式,由多个`property`节点组成,每个节点包含 `name,value,description和final`属性
		- 每个name都是String类型,但value可以通过`getXXX()`方法来转换成对应的Java基本类型\File\数组
	- 如果有多个配置文件,它们会合并成一个. #资源合并
		- ```java
		  Configuration conf = new Configuration
		  conf.addResource("core-default.xml");
		  conf.addResource("core-site.xml");
		  ```
		- 如果该属性不是final的,那么后面的配置将会覆盖前面的
		- 如果该属性是final的,那么在加载后面的相同名称配置时,会打印警告
		- 合并代码见 `org.apache.hadoop.conf.Configuration#overlay`
	- 属性扩展 #属性扩展
	  id:: 62300a4c-ed2b-42d6-979f-4f30c66133c0
		- 通过形如 `${hadoop.tmp.dir}` 的形式,引用定义好的其它属性
		- 引用发生在定义之前or之后都行
		- 最多嵌套20层
- 资源加载流程
  collapsed:: false
	- 资源读取
		- `addResource()`有如下4种方式读取资源
		  collapsed:: true
			- InputStream in = 一个已经打开的输入流
			- Path file = Hadoop文件路径,即HDFS
			- String name = [[ClassPath资源]]
			- URL = http链接
		- `addResource()` 并不会立即加载资源,通过`reloadConfiguration()`方法清空`properties`和`finalParameters`
	- 资源加载和解析 #延迟加载
		- 当外部真正调用`getXXX()`方法时,如果发现`properties`为空,则触发`loadResources()`开始加载配置资源
		- 使用[[JAPX]]处理 [[xml]] 文件,采用的是[[SAX]]方式
			- 通过`isRestricted`控制配置文件中能否使用 `xi:include` 引用别的xml配置
			- 将 ((6230432e-a263-433d-932b-945343b5e26c))字段保存在token中,用于处理 含特殊字符的情况
			- 将解析出来的属性分别保留在变量 `confName;confValue;confInclude; confFinal;confSource` 中,并在标签退出后调用`loadProperty()` 方法,加入`properties`
		- 注意!加载和解析这一步,并未处理 `${var}`类似的 ((62300a4c-ed2b-42d6-979f-4f30c66133c0))
			- 可以解决配置文件之间的循环依赖问题
	- 资源的get和set
		- 在get时,会调用`substituteVars()` 处理 `${key}` ,如果`${key}` 被替换为实际值后,仍有属性扩展,这个过程将会继续进行,直到不再出现变量为止,或到达上限,抛出错误
			- 首先,将`${key}` 匹配出来,类似于用正则表达式`\$\{[^\}\$ ]+\}` 的效果,但实际上是用java实现,效率更高
			- 获取到了key,先使用系统变量的value,再去Configuration中找`getProps()`
			- 将这段替换,继续循环.直到没有属性扩展,或达到20次循环,抛出异常
		- set方法则比较简单
			- 将传入的k,v保存在`overlay` 和`properties` 中
- `Configurable` 接口
	- 辅助实例对象的初始化,其作用有点类似于spring中的属性注入.
	- 实例对象在接口的`void setConf(Configuration conf)` 方法,对一些属性初始化值
	- 而Hadoop在使用该对象时,会先用自己的`ReflectionUtils.newInstance(Class<T> theClass, Configuration conf)` 利用反射的方式来创建对象
	- 好处
		- 做到了风格的统一,只需使用 无参构造函数即可创建对象,再通过conf进行属性初始化
		- 做到了属性的外部可配置
-
- 附注
	- 关键类
		- `org.apache.hadoop.conf.Configuration`
		- `org.apache.hadoop.conf.Configurable`
		- `org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFilter.RegexFilter`
		- `org.apache.hadoop.util.ReflectionUtils#newInstance`