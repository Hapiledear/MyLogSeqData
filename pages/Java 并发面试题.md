- {{cards [[Java 并发面试题]]}}
- 并发中的三大问题是如何产生的，都是如何解决的? #card #Java并发
  card-last-score:: 3
  card-repeats:: 7
  card-next-schedule:: 2024-08-08T08:50:22.507Z
  card-last-interval:: 0.03
  card-ease-factor:: 1.3
  card-last-reviewed:: 2024-08-08T08:50:22.507Z
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
  card-last-interval:: 0.04
  card-repeats:: 7
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-09T02:20:50.158Z
  card-last-reviewed:: 2024-08-09T02:20:50.159Z
  card-last-score:: 3
	- 解决了两大问题 {{cloze 保证变量对所有线程的可见性和禁止指令重排}} 通常搭配atomic类来实现 {{cloze 原子性}}
	- 实践 单例模式中 {{cloze volatile修饰instance. 保证其写操作发生在读之前}}
- volatile能将非原子操作变成原子操作吗 #card #Java并发
  card-last-interval:: 0.02
  card-repeats:: 8
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-09T02:22:02.526Z
  card-last-reviewed:: 2024-08-09T02:22:02.527Z
  card-last-score:: 3
	- 在32位的机器上，long和double分为了两部分 高位和低位。因此建议用volatile修饰
	- 但更建议JVM实现时将这种操作视为原子性的
- synchronized (简称 同步)的原理 #card #Java并发
  card-last-interval:: 0.05
  card-repeats:: 7
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-08T09:29:34.070Z
  card-last-reviewed:: 2024-08-08T08:29:34.070Z
  card-last-score:: 3
	- 通过 {{cloze `monitorEnter` 和`monitorExit`}} 指令，进行加锁和释放锁。是可重入的
	- {{cloze 遵循happends-before规则，被synch修饰的对象的写在读之前，读一定是从直接内存中读取}} ，保证可见性
	- 上锁过程 {{cloze 在对象头中，记录锁的类型 和 指向栈中的锁记录指针}}
	- 如果没有获取到锁，则会 {{cloze 让线程自旋等待，并不放弃CPU的执行时间}}
- synchronized的锁升级机制 #card #Java并发
  card-last-interval:: 0.02
  card-repeats:: 6
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-08T09:20:18.164Z
  card-last-reviewed:: 2024-08-08T09:20:18.164Z
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
  card-last-interval:: 0.04
  card-repeats:: 7
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-09T02:16:37.187Z
  card-last-reviewed:: 2024-08-09T02:16:37.187Z
  card-last-score:: 3
	- 修饰的地方 {{cloze volatile是变量修饰,synchronized可以修饰类、方法、变量}}
	- 解决的问题 {{cloze volatile 保证的是有序性和可见行，不能保证原子性，synch 保证的是可见性(Happends-before)和原子性}}
	- 线程是否阻塞 {{cloze volatie不会造成阻塞 synch 可能造成阻塞}}
	- 是否被编译优化 {{cloze volatile不会被编译优化，synch可以被优化}}
	- 实际开发中 {{cloze synch比volatile用得多一点，volatile需要搭配automit类使用}}
- 什么是CAS #card #Java并发
  card-last-interval:: 0.02
  card-repeats:: 6
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-08T09:19:09.269Z
  card-last-reviewed:: 2024-08-08T09:19:09.270Z
  card-last-score:: 3
	- compare and swap 比较并交换
	- 实现,是否加锁 {{cloze 内存位置v 预期原值A和新值B ，如果内存值=A 那么将内存值改为B}} {{cloze 不加锁，但会自旋一定次数}}
	- 实现 {{cloze atomic下的类大多是使用CAS操作来实现的}}
- CAS会产生什么问题以及解决办法 #card #Java并发
  card-last-interval:: 0.03
  card-repeats:: 6
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-08T09:17:41.773Z
  card-last-reviewed:: 2024-08-08T09:17:41.774Z
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
  card-last-interval:: 0.04
  card-repeats:: 5
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-07T01:22:14.849Z
  card-last-reviewed:: 2024-08-07T01:22:14.849Z
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
  card-last-interval:: 0.04
  card-repeats:: 7
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-07T02:34:19.786Z
  card-last-reviewed:: 2024-08-07T02:34:19.786Z
  card-last-score:: 3
	- 重入性
		- {{cloze 已获得锁的线程可以再次获取}}
		- {{cloze 获取n次锁，需要释放n次才算完全释放成功}}
		- 实现原理 {{cloze AQS头节点中的threadId ,Lock类自身的次数记录}}
	- 公平性
		- 非公平 {{cloze AQS的头节点和新执行到CAS修改状态处的线程}}
		- 公平 {{cloze AQS只允许头节点抢锁}}
- AQS是什么，抢锁过程 #card #Java并发
  card-last-interval:: 0.03
  card-repeats:: 6
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-08T09:03:31.501Z
  card-last-reviewed:: 2024-08-08T09:03:31.540Z
  card-last-score:: 3
	- AQS是一个用来构建锁和同步器的框架
	- 队列 {{cloze 有一个CLH 的虚拟双向链表组成的队列}}
	- 状态 {{cloze 每个节点有一个int state 标识同步状态，使用CAS操作进行修改,=n表示未锁定}}
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
  card-last-interval:: 0.24
  card-repeats:: 2
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-08T14:01:28.243Z
  card-last-reviewed:: 2024-08-08T09:01:28.243Z
  card-last-score:: 3
	- 1.8- {{cloze 将map分段为segment，对每一段分别加锁}}
	- 1.8+ {{cloze 弃用了分段，而是改用了CAS操作}}
	- 与HashMap的相同点
		- 存储结构 {{cloze 数组+链表->红黑树}}
		- 扩容 {{cloze 默认大小为 16 加载因子0.75 阈值=大小*因子}} {{cloze 扩容后大小为2^n次方，采用位运算加速 rehash}}
			- 单节点 {{cloze hash & newCap-1 }}
			- 链表与红黑树 {{cloze hash & oldCap}} (oldCap=2^n,本质是看第n+1位是0 or 1)，确定是留在原位置idx 还是 移动到新位置 {{cloze idx+oldCap}}
- synchronizeMap与ConcurrentHashMap的区别 #card #Java并发
  card-last-interval:: 0.03
  card-repeats:: 5
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-08T09:17:44.059Z
  card-last-reviewed:: 2024-08-08T09:17:44.060Z
  card-last-score:: 3
	- 锁住数据的粒度
		- {{cloze synchMap 一次锁住一整张表}}
		- {{cloze ConcurMap一次只锁一个分段}}
- CopyOnWriteArrayList是什么，实践场景 #card #Java并发
  card-last-interval:: 0.03
  card-repeats:: 4
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-07T02:29:04.734Z
  card-last-reviewed:: 2024-08-07T02:29:04.734Z
  card-last-score:: 3
	- 字如其名 {{cloze 它的写入操作将copy出一份副本 ，再在这个副本上进行更改}}
	- 缺点 {{cloze copy数组代价高昂，消耗内存}}
	- 适用场景 {{cloze 读多写少}}
- 阻塞队列BlockingQueue的实现原理 #card #Java并发
  card-last-interval:: 0.03
  card-repeats:: 6
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-08T09:18:36.628Z
  card-last-reviewed:: 2024-08-08T09:18:36.628Z
  card-last-score:: 3
	- 拯救了#生产者-消费者 模型的控制逻辑
		- {{cloze 在队列为空时，获取元素的线程会等待队列变为非空}}
		- {{cloze 在队列满时，存储元素的线程会等待队列可用}}
	- 阻塞队列的经典应用场景是 {{cloze socket客户端的数据读取和解析}}
- 什么是原子操作 #card #Java并发
  card-last-interval:: 0.03
  card-repeats:: 7
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-08T08:50:07.075Z
  card-last-reviewed:: 2024-08-08T08:50:07.075Z
  card-last-score:: 3
	- 定义 {{cloze 一连串操作，要么都执行完，要么都不执行}}
	- 如何保证原子性 {{cloze CAS指令}} {{cloze Lock和synchroize}}
- java中有哪些原子类  #card #Java并发
  card-last-interval:: 0.02
  card-repeats:: 8
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-09T02:29:50.445Z
  card-last-reviewed:: 2024-08-09T02:29:50.446Z
  card-last-score:: 3
	- 基本类型 {{cloze AtomicBoolean}} {{cloze AtomicInteger}} {{cloze AtomicReference}}
	- 数组 {{cloze AtomicXXXArray}}
	- 属性 {{cloze AtomicxxxFiledUpdater}}
	- 带版本号的 {{cloze AtomicMarkableReference}} {{cloze AtomicStampedReferece}}
- atomic的原理  #card #Java并发
  card-last-interval:: 5.57
  card-repeats:: 4
  card-ease-factor:: 1.34
  card-next-schedule:: 2024-08-12T15:31:24.231Z
  card-last-reviewed:: 2024-08-07T02:31:24.232Z
  card-last-score:: 3
	- {{cloze CAS+volatile方式保证原子性}} 避免synch的高开销
- CountdownLatch和CycleBarrar的区别 #card #Java并发
  card-last-interval:: 0.04
  card-repeats:: 5
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-07T01:22:03.441Z
  card-last-reviewed:: 2024-08-07T01:22:03.441Z
  card-last-score:: 3
	- 线程等待和执行方式 CD {{cloze 某个线程A等待若干线程执行完后才执行}} CB {{cloze 一组线程在某个状态等待，然后再同时执行}}
	- 当前线程是否阻塞 CD {{cloze 调用countDown方法后，因为是计数器减1，当前线程不会阻塞}} CB {{cloze 调用 await方法后，会阻塞当前线程}}
	- 复用 CD {{cloze 不能复用}} CB {{cloze 可以复用}}
- final类 不可变对象 描述 #card #Java并发
  card-last-interval:: 0.22
  card-repeats:: 2
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-07T07:07:10.851Z
  card-last-reviewed:: 2024-08-07T02:07:10.851Z
  card-last-score:: 3
	- 对象一旦被创建，它的属性值就不能改变 ，可以通过反射方式进行破坏
	- 保证了内存的可见性，不需要额外的同步手段，提升代码执行效率
- ThreadLocal原理 #card #Java并发
  card-repeats:: 8
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-09T02:00:58.210Z
  card-last-reviewed:: 2024-08-09T02:00:58.210Z
  card-last-score:: 3
  card-last-interval:: 0.04
	- 每一个thread持有一个threadLocalMap {{cloze Entity是弱引用}} {{cloze key是被修饰的threadLocal对象}} {{cloze value是对象值}}
	- 使用场景
		- {{cloze 线程隔离}}
		- {{cloze 跨函数传递数据}}
	- 使用时需要注意 {{cloze 变量回收}}  特别是在线程池的场景下
- 线程池调度流程&各项参数 #card #Java并发
  card-last-interval:: 0.03
  card-repeats:: 7
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-08T08:47:01.415Z
  card-last-reviewed:: 2024-08-08T08:47:01.415Z
  card-last-score:: 3
	- 任务优先占用 {{cloze 核心线程数}}
	- 核心线程满 {{cloze 进入等待队列}}
	- 等待队列满 {{cloze 占用最大线程，立即执行}}
	- 最大线程满 {{cloze 执行拒绝策略}}
	- 如何创建线程 {{cloze 通过线程工厂 ThreadFactory}}
	- 何时缩容到核心线程数 {{cloze 线程最大空闲时长}}
- 线程池调优策略  #card #Java并发
  card-last-interval:: 0.03
  card-repeats:: 4
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-07T02:28:01.973Z
  card-last-reviewed:: 2024-08-07T02:28:01.974Z
  card-last-score:: 3
	- 通用规则2条
		- {{cloze 最大线程数=核心线程数 优先创建线程而不是入队等待}}
		- {{cloze 使用有界阻塞队列}}
	- IO密集型
		- 核心线程数= {{cloze CPU核数*2}}
	- CPU密集型
		- 核心线程数= {{cloze CPU核数}}
	- 混合型
		- 核心线程数= {{cloze (等待时间+cpu时间)/cpu时间 *cpu核数}}
- 什么是伪共享？如何解决 #card
  card-last-interval:: 15.05
  card-repeats:: 4
  card-ease-factor:: 1.94
  card-next-schedule:: 2024-08-22T03:34:07.640Z
  card-last-reviewed:: 2024-08-07T02:34:07.640Z
  card-last-score:: 3
	- 多核多线程程序，因为并发读写同一个Cache Line的数据（临近位置的内存数据），导致Cache Line的频繁失效，内存的频繁Load/Store，从而导致性能急剧下降
		- 从内存加载数据到Cache的时候，是以Cache Line为长度单位的.
		- 假设一个Cache Line中有数据a\b\c ，同时假设线程都是在不同core上运行的，线程1修改a 而线程2修改b.
		- 当一个Core修改其缓存中的值时，其他Core不能再使用旧值。该内存位置将在所有缓存中失效，然后重新从内存里加载最新数据。
	- 通过增加填充，让a和b两个变量分布到不同的Cache Line
- Java中有哪些并发工具 #card
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2024-08-10T13:34:48.373Z
  card-last-reviewed:: 2024-08-06T13:34:48.374Z
  card-last-score:: 3
	- 创建线程池和任务调度的 {{cloze Executor}}
	- 同步辅助类 {{cloze CountdowmLatch、CycleBarrier 等}}
	- 并发集合 {{cloze ConcurrentHashMap 等}}
	- 原子变量 {{cloze AutomicXXX}}
	- 锁机制 {{cloze Lock 和 synchronized }}
	- 条件变量 {{cloze condition}}
	- 异步任务和编排框架 {{cloze Fluter 和 CompletableFuture}}
	- Fork/Join框架
	- 同步队列
- Lock与synchronized的性能差异体现在哪 #card
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2024-08-10T13:34:34.189Z
  card-last-reviewed:: 2024-08-06T13:34:34.190Z
  card-last-score:: 3
	- `Lock` 允许更细粒度的锁定
	- `Lock` 可以更灵活地控制线程的阻塞和唤醒