- {{cards Java 源码面试题}}
	- Summary: 18 items, 18 review counts [[2025年03月26日]]
		- Remembered:   14 (77%)
		- Forgotten :   4 (22%)
	- Summary: 9 items, 9 review counts [[2024年08月07日]]
		- Remembered:   9 (100%)
		- Forgotten :   0 (0%)
- ArrayList
	- ArrayList的扩容机制/流程是怎么样的 #card
	  card-last-interval:: 15.05
	  card-repeats:: 4
	  card-ease-factor:: 1.94
	  card-next-schedule:: 2025-04-10T02:21:42.111Z
	  card-last-reviewed:: 2025-03-26T01:21:42.149Z
	  card-last-score:: 3
		- 初始容量,默认为 {{cloze 10}}
		- 触发时机，没有负载因子。 {{cloze 数组放不下}} 时立即触发
		- 扩容大小，新数组是原数组的 {{cloze 1.5倍}} `oldCapacity + (oldCapacity >> 1)`
		- 数据操作，新数组申请完成后，copy旧数组到新数组，位置不变
- HashMap
  id:: 66b20b36-7c48-424a-9b27-a2c18f1e2a9a
	- HashMap的扩容机制/流程是怎样的 #card
	  card-last-interval:: 15.05
	  card-repeats:: 4
	  card-ease-factor:: 1.94
	  card-next-schedule:: 2025-04-10T02:24:12.830Z
	  card-last-reviewed:: 2025-03-26T01:24:12.831Z
	  card-last-score:: 3
		- 初始容量，默认为 {{cloze 16}}
		- 触发时机, 元素 {{cloze 数量达到当前容量与负载因子(默认0.75)的}} 乘积时.
		- 扩容大小，新容量为 {{cloze 旧容量的两倍}} oldCap << 1
		- 数据操作：
			- 单节点数据， `e.hash & (newCap - 1)`
			- 链表节点和红黑树节点，`e.hash & oldCap` 根据 {{cloze 第n+1位的0 or 1 }} 分成两个链表/树，一个在旧位置idx, 另一个在新位置 {{cloze idx+oldCap}}
				- 假设 oldCap = 2^n
	- HashMap的key/value能否为null #card
	  card-last-interval:: 15.05
	  card-repeats:: 4
	  card-ease-factor:: 1.94
	  card-next-schedule:: 2025-04-10T02:25:04.767Z
	  card-last-reviewed:: 2025-03-26T01:25:04.767Z
	  card-last-score:: 3
		- key可以为null ,计算的hashCode = 0
		- value可以为null
- PriorityQueue
	- 存储元素使用的是数组，扩容机制同ArrayList
		- 使用`Object[]` 表示二叉树, 获取parent节点 `parent = (k - 1) >>> 1`
	- 优先队列的元素插入/删除机制 #card
	  card-last-interval:: 13.21
	  card-repeats:: 5
	  card-ease-factor:: 1.8
	  card-next-schedule:: 2025-04-08T06:27:25.080Z
	  card-last-reviewed:: 2025-03-26T01:27:25.080Z
	  card-last-score:: 3
		- 将元素key插入到末尾 i=size
		- 进行上浮操作
			- 获取parent节点 {{cloze `parent = (k - 1) >>> 1`}}
			- 不断比较它的父节点，直到 {{cloze key.compare(es[parent]) >0}} 或 到达顶端
			- 如果小于等于，则 {{cloze 交换两个的位置}}
- [ThreadLocal](https://javaguide.cn/java/concurrent/threadlocal.html#threadlocalmap-get-%E8%AF%A6%E8%A7%A3)
  id:: 66b195b5-0b07-4b75-91af-088d7e445523
	- ThreadLocal如何防止内存溢出 #card
	  card-last-interval:: 15.05
	  card-repeats:: 4
	  card-ease-factor:: 1.94
	  card-next-schedule:: 2025-04-10T02:24:57.518Z
	  card-last-reviewed:: 2025-03-26T01:24:57.518Z
	  card-last-score:: 3
		- Map中的key是 {{cloze 弱引用WeekReference}} , GC后回收。key == null,表示过期状态
		- 手动调用remove方法，尤其是在与线程池配合使用时。
			- 将当前位置的 entry == null ，标记为可回收
			- 触发 ((66b1e39e-a9c7-4771-b6e0-d700bdd3808c))
	- ThreadLocalMap的哈希算法与哈希冲突 #card
	  card-last-interval:: 13.21
	  card-repeats:: 5
	  card-ease-factor:: 1.8
	  card-next-schedule:: 2025-04-08T06:27:53.879Z
	  card-last-reviewed:: 2025-03-26T01:27:53.879Z
	  card-last-score:: 3
		- ThreadLocal 有自己的hashCode算法
			- {{cloze 斐波那契数}} 的递增，只是初始值不是1,而是 `0x61c88647`
		- {{cloze threadHashCode & cap }} = 在桶中的位置，分别均匀
		- 冲突解决办法
			- {{cloze 线性探测}} 同时触发 {{cloze 过期元素清理}} ,详见 添加元素的过程
	- ThreadLocal添加元素的过程 #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.08
	  card-next-schedule:: 2025-03-26T16:00:00.000Z
	  card-last-reviewed:: 2025-03-26T01:21:25.712Z
	  card-last-score:: 1
		- 通过hash计算槽位后，此时分4种情况
			- 槽位Entity为空 {{cloze 直接放入}}
			- 槽位Entity不为空且 {{cloze key值一致}} 更新
			- 槽位Entity不为空且 {{cloze key值不一致}} ,需要向后散列。 一直向后，直到null位置
				- 向后过程中，没有遇到 {{cloze 过期的key}} && 没有遇到 {{cloze k== key}} , 插入 {{cloze null位置}}
				- 向后过程中，遇到 {{cloze 过期key}} && {{cloze 没有遇到k==key}} ，插入 {{cloze 过期key的位置}} ，触发 {{cloze 过期回收}}
				- 向后过程中，遇到 {{cloze 过期key}} && 遇到 {{cloze k==key}} 交换 {{cloze 过期key和k的位置}} ，触发 {{cloze 过期回收}}
		- Entity为空，说明被回收了， key为null 说明为过期key，被垃圾回收器回收了
	- ThreadLocal的过期回收过程 #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.08
	  card-next-schedule:: 2025-03-26T16:00:00.000Z
	  card-last-reviewed:: 2025-03-26T01:21:19.551Z
	  card-last-score:: 1
		- 探测式清理 `expungeStaleEntry( i )`
		  id:: 66b1e39e-a9c7-4771-b6e0-d700bdd3808c
			- 从位置i向后遍历槽位
				- 将过期数据 {{cloze 的`Entry` == `null`}}
				- 将未过期的数据reHash
					- reHash要么位置没变，要么向前移动
					- reHash后冲突，放入 {{cloze 后面的第一个null位置(是刚才回收的位置)}}
				- 碰到 {{cloze 空的槽位}} ，终止探测
		- 启发式清理 `cleanSomeSlots( i ,len)`
			- 假设 len = 2^k ,遍历 {{cloze k次}}
			- 每次遍历，都找到最近一个 {{cloze Entity == null}} 的位置，作为 探测式清理的起始位置。
	- ThreadLocalMap的扩容 #card
	  card-last-interval:: 15.05
	  card-repeats:: 4
	  card-ease-factor:: 1.94
	  card-next-schedule:: 2025-04-10T02:24:36.343Z
	  card-last-reviewed:: 2025-03-26T01:24:36.343Z
	  card-last-score:: 3
		- 初始大小是 {{cloze 16}}
		- 首先进行一次 {{cloze 全表清理}}
		- {{cloze size > 1/2 * len}} 触发扩容，扩容为原来的 {{cloze 2倍}}
		- idx  = {{cloze  ThreadHashCode & (newLen -1)}}
- CompletableFuture
	- CompletableFuture的实现和提供的功能 #card
	  card-last-interval:: 13.21
	  card-repeats:: 5
	  card-ease-factor:: 1.8
	  card-next-schedule:: 2025-04-08T06:28:01.166Z
	  card-last-reviewed:: 2025-03-26T01:28:01.167Z
	  card-last-score:: 3
		- 实现了两个接口 {{cloze `Future`}} 和 {{cloze `CompletionStage`}}
		- 多种任务之间的编排功能
			- 0依赖创建 `supplyAsync( )` 和 `runAsync( )`
			- 1元依赖 `cf1.thenApply( )` `thenCompose` `thenAccept`
			- 2元依赖 `thenCombine`
			- 多元依赖 `allOf`或`anyOf`
		- 异常处理 `handle( )` 和 `exceptionally( )`
	- CompletableFuture的源码 #card
	  card-last-score:: 1
	  card-repeats:: 1
	  card-next-schedule:: 2025-03-26T16:00:00.000Z
	  card-last-interval:: -1
	  card-ease-factor:: 2.22
	  card-last-reviewed:: 2025-03-26T01:19:44.790Z
		- 类似于 观察者模式
			- {{cloze result}} 用于存储当前CF的结果
			- {{cloze stack (Completion)}} 表示当前CF完成后需要触发的依赖动作
		- {{cloze 入栈之前会判断 CF是否执行完成}} 避免入栈之前就已经完成&已经发出通知。
		- {{cloze 入栈之后会判断CF是否执行完成。}} 非原子性的入栈和通知操作，避免错过通知。
		- 唤醒后续操作时(出栈),会 {{cloze CAS操作一个状态值}} 。 防止 多次唤醒
	-
- [[AQS 抽象同步器]]
- [线程池](https://www.throwx.cn/2020/08/23/java-concurrency-thread-pool-executor/)
  id:: 66b1bef3-8dcd-4de9-bf66-9e8e321e1f1c
	- https://tech.meituan.com/2020/04/02/java-pooling-pratice-in-meituan.html
	- 线程池的状态以及作用 #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.22
	  card-next-schedule:: 2025-03-26T16:00:00.000Z
	  card-last-reviewed:: 2025-03-26T01:20:19.759Z
	  card-last-score:: 1
		- `Running` 工作状态 -- {{cloze 接受并处理}} 任务
		- `Shutdown` 准备关闭 -- 不再 {{cloze 接受新任务}} ，但 {{cloze 完成已有任务}}
		- `Stop` 停止 -- {{cloze 尝试中断正在执行的}} 任务
		- `Tidying` 清理 -- 进行 {{cloze 资源回收或其他钩子方法}}
		- `Terminated` 已终止 -- 线程池 {{cloze 已经停止并且不能重新启动}}
	- 线程的状态 #card
	  card-last-interval:: 15.05
	  card-repeats:: 4
	  card-ease-factor:: 1.94
	  card-next-schedule:: 2025-04-10T02:26:18.913Z
	  card-last-reviewed:: 2025-03-26T01:26:18.913Z
	  card-last-score:: 3
		- `new` 新建 -- 线程对象被创建后，但尚未启动
		- `runnable` 可运行 -- 调用了`start()`方法后
		- `running` 正在执行 -- 获得CPU时间片并开始执行`run()`方法
		- `terminated` 终止
			- 线程的`run()`方法执行完成 -- 成功执行
			- 或者线程因为异常退出了`run()`方法 -- 异常退出
			- 或者线程被中断并抛出了`InterruptedException` -- 被中断
				- 中断，类似于*强制关机*按钮。任务超时/主动取消 时使用。
		- `waiting` 等待
			- 主动出让CPU，等待被其他线程唤醒
		- `blocked` 阻塞
			- 尝试获取系统层级/JVM层级的某项共享资源不成功后，由系统/JVM将其唤醒
			- `Lock.lock()` 方法，转移到的是`waiting` 等待状态，因为Lock底层是AQS, 被放入了队列，需要被释放锁的线程唤醒。
	- 线程池如何优雅的关闭 #card
	  card-last-interval:: 4
	  card-repeats:: 2
	  card-ease-factor:: 2.22
	  card-next-schedule:: 2025-03-30T01:19:32.821Z
	  card-last-reviewed:: 2025-03-26T01:19:32.858Z
	  card-last-score:: 3
		- 调用`shutdown()`
		- 如果是长时间任务，考虑在任务中实现 {{cloze 保存点，保存此刻的执行状态}} ，下次启动后接着执行。
	- 线程池支持哪些队列 #card
	  card-last-interval:: 4
	  card-repeats:: 2
	  card-ease-factor:: 2.22
	  card-next-schedule:: 2025-03-30T01:25:38.358Z
	  card-last-reviewed:: 2025-03-26T01:25:38.358Z
	  card-last-score:: 3
		- 有界队列/无界队列 `LinkedBlockQueue` 最大值为 `Integer.MAX_VALUE`
		- 双端队列 `LinkedBlockDeque`
		- 优先队列 `PriorityBlockingQueue`
		- 延迟队列 `DelayQueue`
		- 同步移交队列 `SynchronousQueue`
			- 种队列没有内部容量，任务提交者必须等待另一个线程接收并开始执行任务
	- 线程池支持哪些拒绝策略 #card
	  card-last-interval:: 4
	  card-repeats:: 2
	  card-ease-factor:: 2.22
	  card-next-schedule:: 2025-03-30T01:25:53.999Z
	  card-last-reviewed:: 2025-03-26T01:25:54.000Z
	  card-last-score:: 3
		- 拒绝并抛出异常 `RejectedExecutionException`
		- 丢弃且不会抛出异常
		- 提交任务的线程执行此任务
		- 丢弃队列中最旧的任务，然后尝试提交
	- 线程池的监控和动态修改手段 #card
	  card-last-interval:: 4
	  card-repeats:: 2
	  card-ease-factor:: 2.46
	  card-next-schedule:: 2025-03-30T01:25:10.504Z
	  card-last-reviewed:: 2025-03-26T01:25:10.504Z
	  card-last-score:: 5
		- **Dynamic TP** 监控告警
		- **Hippo** 动态修改线程池参数
	-
- 分布式锁Redisson #card
  id:: 66b9f4a6-7e94-4124-8048-1b4a483420ed
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2025-03-30T01:26:58.559Z
  card-last-reviewed:: 2025-03-26T01:26:58.559Z
  card-last-score:: 3
	- 加锁&释放锁
		- `hincryby key field value`   使用HashMap，field=uuid:thread_id
		- 使用Lua脚本保证原子性
		- 没有获取到时
			- 订阅锁释放的通知
			- 不断的重试，询问通知
	- 锁重入
		- 加锁脚本是CAS操作
		- key中包含唯一标识
		- value是重入次数
	- 锁续期
		- watchdog机制，每10秒执行一次
		- 基于时间轮的定时调度