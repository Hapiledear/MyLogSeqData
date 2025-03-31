- 通信协议 RESP(Rediscover Serialization Protocol)
	- 每个字符项以特定字符`+ - : $ * `开头表示类型,以`\r\n`作为行尾标记
	- 协议数据类型及格式
		- 简单字符串
			- `+` 开头，后跟字符串内容，以 `\r\n` 结尾
			- +OK\\r\\n
		- 错误信息
			- `-` 开头，后跟错误类型和消息，以 `\r\n` 结尾
			- -ERR unknown command 'foobar'\\r\\n
		- 整数
			- `:` 开头，后跟数字，以 `\r\n` 结尾
			- :1000\r\n
		- 批量字符串
			- $<长度>\\r\\n
			  <数据内容>\\r\\n
			- 空字符串表示为 `$0\r\n\r\n`
			- NULL 值表示为 `$-1\r\n`
		- 数组
			- *<元素数量>\\r\\n
			  <元素1><元素2>...<元素N>
			- 空数组表示为 `*0\r\n`
			- NULL 数组表示为 `*-1\r\n`
			- 嵌套数组支持
			- ```
			  *2\r\n
			  $3\r\n
			  foo\r\n
			  $3\r\n
			  bar\r\n
			  ```
	- 协议通信流程
	  collapsed:: true
		- 客户端总是以**数组**的形式发送命令
			- eg `SET key value`
			- ```
			  *3\r\n
			  $3\r\n
			  SET\r\n
			  $3\r\n
			  key\r\n
			  $5\r\n
			  value\r\n
			  ```
		- 服务端根据命令返回不同类型
			- 简单命令：`+OK\r\n`
			- 错误：`-ERR...\r\n`
			- 数据查询：`$...\r\n` 或 `*...\r\n`
	- 对浮点数的支持
		- 将浮点数转为**字符串**后使用 Bulk String 类型传输
		- Redis 内部使用 IEEE 754 标准的二进制表示
			- ```c
			  // Redis 源码中的浮点数处理（redis.h）
			  typedef union {
			      double d; // 浮点数
			      unsigned char bytes[sizeof(double)]; // 浮点数的字符串形式
			  } redisDouble;
			  ```
		-
- [[Redis面试题]]
-