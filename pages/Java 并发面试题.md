- 并发中的三大问题是如何产生的，都是如何解决的? #card #Java并发 /cloze
  card-last-interval:: 4
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-19T04:50:11.746Z
  card-last-reviewed:: 2023-01-15T04:50:11.748Z
  card-last-score:: 3
	- 原子性
		- {{cloze `i++` 一行代码被编译为机器指令时可能是多步操作}}
		- {{cloze CAS , synchroize，显示锁}}
	- 有序性
		- {{cloze CUP指令重排序，JVM编译重排序}}
		- {{cloze CPU的内存屏障 As-if-Serial规则，JMM的内存屏障、Happens-Before规则}}
	- 可见性
		- {{cloze CUP 的物理缓存结构,JMM内存模型也有缓存}}
		- {{cloze CUP层级的MESI协议，JMM的八种操作规范}}
- volatile关键字的原理及其实战 #card #Java并发
	- 解决了两大问题 {{cloze 可见性和禁止指令重排}} 通常搭配atomic类来实现 {{cloze 原子性}}
	- 实践 单例模式中 {{cloze volatile修饰instance. 保证其写操作发生在读之前}}
- volatile能将非原子操作变成原子操作吗 #card #Java并发
  card-last-interval:: 4
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-19T04:53:07.113Z
  card-last-reviewed:: 2023-01-15T04:53:07.113Z
  card-last-score:: 3
	- 在32位的机器上，long和double分为了两部分 高位和低位。因此建议用volatile修饰
	- 但更建议JVM实现时将这种操作视为原子性的
- synchronized (简称 同步)的原理 #card #Java并发
	- 通过 {{cloze `monitorEnter` 和`monitorExit`}} 指令，进行加锁和释放锁。是可重入的
	- {{cloze 遵循happends-before规则，写在读之前}} ，保证可见性
	- 上锁过程 {{cloze 在对象头中，记录锁的类型 和 指向栈中的锁记录指针}}
	- 如果没有获取到锁，则会 {{cloze 让线程自旋等待，并不放弃CPU的执行时间}}
- synchronized的锁升级机制 #card #Java并发
  card-last-interval:: 4
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-19T04:51:11.602Z
  card-last-reviewed:: 2023-01-15T04:51:11.602Z
  card-last-score:: 3
	- 锁消除 无锁
		- {{cloze 在编译时明显检测到不会被其他线程访问到}}
	- 偏向锁
		- 2个条件 {{cloze 无竞争情况下}} {{cloze 对象头的threadId空间可用}}
		- 如何设置 {{cloze CAS操作 threadId=this}}
		- 竞争加剧 {{cloze 膨胀为轻量级锁}}
	- 轻量级锁
		- 栈和锁对象的结构 {{cloze 栈中增加 LockRecord 指向锁对象}} {{cloze 对象头的一部分保存 LockRecord指针}}
		- CAS 自适应自旋 {{cloze 根据上次获得锁的时间与结果自动调整 次数和等待时长}} {{cloze 进行空转，并未放弃时间片}}
	- 重量级锁 监视器(Monitor)机制
		- 同步功能
			- {{cloze 抢锁线程->竞争队列->等待队列->队头线程参与竞争->持有锁->被阻塞，进入waitSet->被唤醒，进入等待队列}}
		- 协作机制
			- 持有许可的线程可以 {{cloze 主动放弃许可进入阻塞}} {{cloze 发送信号，唤起阻塞线程}}
- synchronized和volatie的区别  #card #Java并发
  card-last-interval:: 4
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-19T04:52:19.690Z
  card-last-reviewed:: 2023-01-15T04:52:19.690Z
  card-last-score:: 3
	- 修饰的地方 {{cloze volatile是变量修饰,synchronized可以修饰类、方法、变量}}
	- 解决的问题 {{cloze volatile 保证的是有序性和可进行，不能保证原子性，synch 保证的是可见性(Happends-before)和原子性}}
	- 线程是否阻塞 {{cloze volatie不会造成阻塞 synch 可能造成阻塞}}
	- 是否被编译优化 {{cloze volatile不会被编译优化，synch可以被优化}}
	- 实际开发中 {{cloze synch比volatile用得多一点，volatile需要搭配automit类使用}}
- 什么是CAS #card #Java并发
	- compare and swap 比较并交换
	- 实现,是否加锁 {{cloze 内存位置v 预期原值A和新值B ，如果内存值=A 那么将内存值改为B}} {{cloze 不加锁，但会自旋一定次数}}
	- 实现 {{cloze atomic下的类大多是使用CAS操作来实现的}}
- CAS会产生什么问题以及解决办法 #card #Java并发
  card-last-interval:: 4
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-19T04:52:50.617Z
  card-last-reviewed:: 2023-01-15T04:52:50.618Z
  card-last-score:: 3
	- ABA问题
		- {{cloze 用链表的例子更直观}}
		- {{cloze 使用带版本号的CAS操作}}
	- 开销大
		- {{cloze 竞争激烈的情况下，自旋概率大，从而浪费更多CPU资源}}
		- {{cloze 分散操作热点 LongAdder}} {{cloze 放入一个队列中排队，降低争用 AQS}}
	- 只能保证一个共享变量的原子操作
		- {{cloze 将两个变量放入一个对象中}} {{cloze 上锁}}
- Lock与synchronize的区别 #card #Java并发
  card-last-interval:: 4
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-19T04:54:58.146Z
  card-last-reviewed:: 2023-01-15T04:54:58.147Z
  card-last-score:: 3
	- 实现方式
		- {{cloze Lock基于Java类实现，易于扩展，功能丰富}} {{cloze synch基于对象头实现，无法自定义}}
	- 公平性
		- {{cloze Lock有公平和非公平两种}} {{cloze synch是非公平的}}
	- 中断机制
		- {{cloze lock可以在等待锁的时候响应中断}}
	- 锁的使用范围
		- {{cloze Lock可以在不同范围、不同的顺序获取和释放锁}} {{cloze synch只能在进临界区获取和释放}}
- 可重入锁的实现原理  #card #Java并发
  card-last-interval:: 4
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-19T04:54:10.529Z
  card-last-reviewed:: 2023-01-15T04:54:10.529Z
  card-last-score:: 3
	- 重入性
		- {{cloze 已获得锁的线程可以再次获取}}
		- {{cloze 获取n次锁，需要释放n次才算完全释放成功}}
		- 实现原理 {{cloze AQS头节点中的threadId ,Lock类自身的次数记录}}
	- 公平性
		- 非公平 {{cloze AQS 任何节点都可以抢锁}}
		- 公平 {{cloze AQS只允许头节点抢锁}}
- AQS是什么，抢锁过程 #card #Java并发
	- AQS是一个用来构建锁和同步器的框架
	- 队列 {{cloze 有一个CLH 的双向链表组成的队列}}
	- 状态 {{cloze 每个节点有一个int state 标识同步状态，使用CAS操作进行修改,=0表示未锁定}}
	- 对资源的处理方式
		- 独占
			- 公平锁 {{cloze 严格按照队列顺序获取锁}}
			- 非公平锁 {{cloze 无视队列直接抢锁}}
		- 共享
			- 多个线程可同时执行
	- 模板接口
		- 独占方式获取和释放资源 {{cloze tryAcquire/tryRelease}}
		- 共享方式 {{cloze tryAquireShared/treReleaseShared}}
- ConcurrentHashMap的原理 #card #Java并发
	- 1.8- {{cloze 将map分段为segment，对每一段分别加锁}}
	- 1.8+ {{cloze 弃用了分段，而是改用了CAS操作}}
	- 与HashMap的相同点
		- 存储结构 {{cloze 数组+链表->红黑树}}
		- 扩容 {{cloze 默认大小为 16 加载因子0.75 阈值=大小*因子}}
- synchronizeMap与ConcurrentHashMap的区别 #card #Java并发
	- 锁住数据的粒度
		- {{cloze synchMap 一次锁住一整张表}}
		- {{cloze ConcurMap一次只锁一个分段}}
- CopyOnWriteArrayList是什么，实践场景 #card #Java并发
	- 字如其名 {{cloze 它的写入操作将copy出一份副本 ，再在这个副本上进行更改}}
	- 缺点 {{cloze copy数字代价高昂，消耗内存}}
	- 适用场景 {{cloze 读多写少}}
- 阻塞队列BlockingQueue的实现原理 #card #Java并发
	- 拯救了生产者-消费者模型的控制逻辑
		- {{cloze 在队列为空时，获取元素的线程会等待队列变为非空}}
		- {{cloze 在队列满时，存储元素的线程会等待队列可用}}
	- 阻塞队列的经典应用场景是 {{cloze socket客户端的数据读取和解析}}
- 什么是原子操作 #card #Java并发
	- 定义 {{cloze 一连串操作，要么都执行完，要么都不执行}}
	- 如何保证原子性 {{cloze CAS指令}} {{cloze 并发包下的原子类}}
- java中有哪些原子类  #card #Java并发
	- 基本类型 {{cloze AtomicBoolean}} {{cloze AtomicInteger}} {{cloze AtomicReference}}
	- 数组 {{cloze AtomicXXXArray}}
	- 属性 {{cloze AtomicxxxFiledUpdater}}
	- 带版本号的 {{cloze AtomicMarkableReference}} {{cloze AtomicStampedReferece}}
- atomic的原理  #card #Java并发
	- {{cloze CAS+volatile方式保证原子性}} 避免synch的高开销
- CountdownLatch和CycleBarrar的区别 #card #Java并发
	- 线程等待和执行方式 CD {{cloze 某个线程A等待若干线程执行完后才执行}} CB {{cloze 一组线程在某个状态等待，然后再同时执行}}
	- 当前线程是否阻塞 CD {{cloze 调用countDown方法后，当前线程不会阻塞}} CB {{cloze 调用 await方法后，会阻塞当前线程}}
	-
- final类 不可变对象 描述
- ThreadLocal原理
- 线程池调度流程&各项参数
- 线程池调优策略