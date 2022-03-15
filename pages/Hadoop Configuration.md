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
		- 通过形如 `${hadoop.tmp.dir}` 的形式,引用定义好的其它属性
- 资源读取方式:
	- `addResource()`有如下4种方式读取资源
		- InputStream in = 一个已经打开的输入流
		- Path file = Hadoop文件路径,即HDFS
		- String name =ClassPath资源
		- URL = http链接
	-
- 资源加载流程
	-