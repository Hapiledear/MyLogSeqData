- OutOfMemory 内存溢出异常
- 导致该异常的原因通常是 当前内存大小无法满足程序需求而又无法继续申请内存扩张了
- 堆内存溢出 #card
  id:: 66c2b37c-4e6f-47dc-a816-6830524773f1
  card-last-interval:: 0.14
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2024-08-19T12:13:10.260Z
  card-last-reviewed:: 2024-08-19T09:13:10.261Z
  card-last-score:: 3
	- 查看 heap dump文件
		- 启动配置时的 {{cloze  `-XX:HeapDumpOnOutOfMemoryError`}}
		- 命令手动触发 {{cloze  `jmap -dump`}}
	- 使用分析工具
		- {{cloze VisualVM}} 或 {{cloze JProfiler}} 等
		- 重点关注 {{cloze 内存泄漏}} 或 {{cloze 占用内存最多的对象}}
	- 可能的原因
		- 频繁的创建对象
		- 存在内存泄露
	- 解决方案
		- 定位并排查代码
		- 调整堆的大小
		- 调整年轻代的比例
- 元空间内存溢出 #card
  id:: 66c2b383-718f-4bc1-9945-e5973dcf23dd
  card-last-interval:: 0.14
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2024-08-23T05:06:24.763Z
  card-last-reviewed:: 2024-08-23T02:06:24.763Z
  card-last-score:: 3
	- 可能的原因
		- 频繁的 {{cloze 动态类}} 生成
		- 大量使用 {{cloze 匿名类和反射类}}
	- 解决方案
		- 调整初始大小 `-XX:MetaspaceSize` 和 空闲比例 `XX:MinMetaspaceFreeRatio`
		- 减少反射和动态类加载的使用
		- 熔断和限流
- 堆外内存溢出