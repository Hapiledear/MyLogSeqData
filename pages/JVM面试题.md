- {{cards [[JVM面试题]] }}
- [[Java 元空间]] 会产生内存溢出吗？ #card
  card-last-score:: 3
  card-repeats:: 9
  card-next-schedule:: 2024-08-13T09:12:22.968Z
  card-last-interval:: 0
  card-ease-factor:: 1.3
  card-last-reviewed:: 2024-08-13T09:12:22.969Z
	- 元空间的由来。 {{cloze 从java8+开始，用 元空间 替代了 永久代/方法区}}
	- 元空间的内存 {{cloze 并不使用虚拟机的内存，而是直接使用机器内存}}
	- 出现OOM的原因 {{cloze 加载到内存中的class文件数量太多or体积太大}}
	- 解决办法 {{cloze 增加元空间的大小 `-XX:MaxMetaspaceSize=512m`}}
- 说一下 JVM 的主要组成部分及其作用 #card
  card-last-score:: 3
  card-repeats:: 8
  card-next-schedule:: 2024-08-13T09:17:18.059Z
  card-last-interval:: 0
  card-ease-factor:: 1.3
  card-last-reviewed:: 2024-08-13T09:17:18.059Z
	- 类加载系统 {{cloze 根据给定的全限定类名，使用双亲委派模型，由类加载器加载class文件到 方法区}}
	- 运行时数据区 JVM内存模型 {{cloze 分为线程公有的堆、方法区，和线程私有的 虚拟机栈、本地方法栈、程序计数器}}
	- 执行引擎 {{cloze 执行字节码指令。分为 即时编译执行和解释执行。此外，**垃圾收集器** 也在其中}}
	- 本地接口 {{cloze 调用本地方法}}
- 说一下JVM内存模型 #card
  card-last-interval:: 0
  card-repeats:: 9
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-13T08:59:01.762Z
  card-last-reviewed:: 2024-08-13T08:59:01.762Z
  card-last-score:: 3
  id:: 66544681-7486-43a2-832f-a98d2314a5a4
	- 画一下JVM内存模型图，并描述每个模块的定义，作用，以及可能会存在的问题
	- 虚拟机栈
		- {{cloze 是线程私有的。每个方法被执行时，都在栈中同步创建一个栈帧}} 私有公有?何时创建
		- {{cloze 用于存储 局部变量表(基本数据类型及对象的引用)、操作数栈、动态链接、方法出口等信息}} 4个部分
		- {{cloze 申请不到足够内存时会抛出OOM异常；栈深度超过规定时会抛出StackOverFlowError}} 可能的异常
	- 程序计数器
		- {{cloze 当前线程所执行的字节码的行号}} 存储的内容
		- {{cloze 一个core只能运行一个线程，多线程是通过轮流切换、分配时间的方式来完成的。这就需要一个记录每个线程执行到哪里}} 为什么需要
	- 本地方法栈
		- {{cloze 线程私有的，执行的是native方法}}
	- 堆
		- {{cloze 线程共有，GC管理的内存区域}}
		- {{cloze 主要有 对象实例、字符串常量池、静态变量、和线程分配缓冲区}} 4个部分
	- 方法区
		- {{cloze 存储被虚拟机加载的类信息、常量，即时编译后的代码缓存}} 3个部分
		- {{cloze 1.8-叫永久代，1.8+改名为元空间，使用机器内存}} 演变过程
	- 直接内存
		- {{cloze 基于NIO的 DirectByteBuffer 而产生的堆外内存}} NIO
		- {{cloze 通过DirectByteBuffer对象的虚引用实现堆外内存的释放}} 如何回收
- 堆内存的组成 #card
  card-last-interval:: 0
  card-repeats:: 8
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-13T09:17:09.395Z
  card-last-reviewed:: 2024-08-13T09:17:09.395Z
  card-last-score:: 3
	- {{cloze 1/3的年轻代 2/3的老年代}} 2代
	- {{cloze  年轻代又分 eden区 s1和s2区 比例为 8:1:1}} 3区
	- {{cloze 通常 年轻代采用的GC算法是 复制算法，老年代采用的是标记-整理算法 }} GC算法
- 非堆内存有哪些 #card
  card-last-interval:: 0.01
  card-repeats:: 7
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T01:32:53.652Z
  card-last-reviewed:: 2024-08-14T01:32:53.652Z
  card-last-score:: 3
	- {{cloze DirectByteBuffer 所申请的直接内存}} NIO
	- {{cloze 方法区的具体实现 元空间}}
- ## 为什么 字符串常量池 #常量池 被移动到了堆中？ #card
  card-last-interval:: 0
  card-repeats:: 8
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-13T09:18:23.083Z
  card-last-reviewed:: 2024-08-13T09:18:23.084Z
  card-last-score:: 3
	- [[Java 永久代]]在 Full GC/Major GC  时才会触发，条件苛刻
	- 开发中会有大量字符串被创建，回收效率低导致永久代内存不足
	- 放在堆中，可以及时回收内存
	- 字符串常量首先在堆中创建，然后尝试复制到元空间的字符串常量池中
		- 通过 `new String()` 这样的方式创建字符串，除非明确调用了 `intern()` 方法，否则不会自动放入字符串常量池
- ## 几种变量存放在哪里？ #card
  card-last-interval:: 0
  card-repeats:: 8
  card-ease-factor:: 1.3
  card-last-reviewed:: 2024-08-13T09:18:34.811Z
  card-next-schedule:: 2024-08-13T09:18:34.811Z
  card-last-score:: 3
	- ``` java
	  	  public class StaticObjTest {
	  	      static class Test{
	  	            // 静态变量&类变量
	  	            // 一个java.lang.Class类型的对象实例引用了此变量
	  	            static ObjectHolder staticObj = new ObjectHolder();
	  	            // 实例变量&成员变量
	  	            ObjectHolder instanceObj = new ObjectHolder();
	  	            void foo() {
	  	                // 局部变量
	  	                ObjectHolder localObj = new ObjectHolder()();
	  	                System.out.println("done");
	  	                }
	  	          }
	  	          private static class ObjectHolder{
	  	          }
	  	          public static void main(String[] args) {
	  	            Test test = new StaticObjTest.Test();
	  	            test.foo();
	  	      	}
	  	  }
	  	  
	  ```
	- 静态变量&类变量
		- `static`所修饰的，定义在方法外的变量
		- {{cloze 1.8-随着Test类信息存放于 [[Java 方法区]] ，1.8+存放于[[Java 堆内存]]中}}
	- 实例变量&成员变量
		- 没有`static`修饰的，定义在类中
		- {{cloze 在[[Java 对象创建过程]] 时，从 运行时常量池 Runtime Constant pool #常量池 取出直接引用或值，随着Test对象实例存放于[[Java 堆内存]]中}}
	- 局部变量
		- 定义在类的方法中
		- {{cloze 方法被调用时放入[[Java 虚拟机栈]]的栈帧中，方法结束后从栈中弹出}}
	- 变量的引用对应的对象实体
		- {{cloze 存放在[[Java 堆内存]]}}
-
- ## [[Java 堆内存]] 与[[Java 虚拟机栈]] 的区别 #card
  card-last-interval:: 0
  card-repeats:: 8
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-23T01:30:51.146Z
  card-last-reviewed:: 2024-08-23T01:30:51.147Z
  card-last-score:: 3
	- 物理地址
		- 堆的物理地址分配是不连续的。所以GC时有各种算法
		- 栈的物理地址分配所连续的
	- 内存分别
		- 堆分配的内存在**运行期**确认的，大小不固定，但远大于栈
		- 栈分配的内存大小在**编译器**将确认，大小所固定的
	- 存放的内容
		- 堆存放的所对象实例和数组，更侧重于数据的存储
		- 栈存放了 局部变量、操作数栈、返回结果，更侧重于方法的执行
	- 程序可见度
		- 堆对于整个应用程序都是共享的、可见的
		- 栈是线程私有的
- 对象创建过程 #card
  card-last-interval:: 0.01
  card-repeats:: 6
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-13T08:19:14.351Z
  card-last-reviewed:: 2024-08-13T08:19:14.351Z
  card-last-score:: 3
	- 类加载
	- 分配内存
		- 两种方式 {{cloze 指针碰撞，空闲列表}}
	- 分配时的并发处理
		- 两种方式 {{cloze CAS+失败重试,本地线程分配缓冲}}
	- 初始化
	- <init>方法
- 触发类加载的时机 4 #card
  card-last-interval:: 0.22
  card-repeats:: 2
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T06:22:11.555Z
  card-last-reviewed:: 2024-08-14T01:22:11.556Z
  card-last-score:: 3
	- new关键字
	- Class or Constructor的newInstance方法
	- clone方法
	- 反序列化
- 类加载过程 #card
  card-last-interval:: 0.01
  card-repeats:: 7
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T01:32:15.940Z
  card-last-reviewed:: 2024-08-14T01:32:15.940Z
  card-last-score:: 3
	- 加载 {{cloze 导入class文件}}
	- 验证 {{cloze 检测class文件的正确性}}
	- 准备 {{cloze 给静态变量分配内存空间}}
	- 解析 {{cloze 将常量池中的符号引用替换成直接引用}}
	- 初始化 {{cloze 对静态变量和代码块执行初始化工作}}
- 类加载器分类和双亲委派模型 #card
  card-last-interval:: 0
  card-repeats:: 10
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T01:03:33.819Z
  card-last-reviewed:: 2024-08-14T01:03:33.819Z
  card-last-score:: 3
	- 启动类加载器
	- 扩展类加载器
	- 应用程序类加载器
	- 自定义类加载器
	- 双亲委派： {{cloze 先将其委派给父类，如果父类不能加载，再自己来}}
- 对象如何访问 #card
  card-last-interval:: 0.01
  card-repeats:: 7
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-23T02:20:17.171Z
  card-last-reviewed:: 2024-08-23T02:20:17.171Z
  card-last-score:: 3
	- 句柄
		- {{cloze 栈中对象的引用 ->堆中句柄池中的句柄 -> 堆中的实例 和 方法区的类型信息}} 栈-> 堆->堆和方法区
		- {{cloze 由于GC频繁导致对象的地址变更，这样做可以不用修改引用}} 为什么这么做
	- 直接指针
		- {{cloze 栈中的对象引用 -> 堆中的对象实例 -> 方法区中对应的类型信息}} 栈->堆->方法区
		- {{cloze 节省了一次指针定位的时间}} 好处
- 对象分配策略 #card
  card-last-interval:: 0
  card-repeats:: 8
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-19T09:04:05.644Z
  card-last-reviewed:: 2024-08-19T09:04:05.644Z
  card-last-score:: 3
	- 新对象分配在eden区，大对象直接进入老年代
	- 经历过一次Minor GC ,复制到surviro区，年龄+1
	- 经历了多次Minor GC 且年龄超过一定限制，复制到老年代
		- 年龄 = 相同年龄对象大小之和 > S 区的一半
	- 老年代满了则触发 Full GC
- JVM中的垃圾回收器有哪些,及其工作原理 #card
  card-last-interval:: 0
  card-repeats:: 9
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-13T09:14:41.683Z
  card-last-reviewed:: 2024-08-13T09:14:41.683Z
  card-last-score:: 3
	- Serial
		- 串行收集
	- Parral
		- Servial的并行版本
	- CMS
		- 初始标记->并发标记->重新标记->并发清理
	- G1
		- 将堆内存分块，每个块都看作Eden s1 s2 Huge old
		- 只针对新生代的Young GC 和 新生代&老年代的Mix GC
- 4种引用及应用场景 #card
  card-last-interval:: 0
  card-repeats:: 9
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-13T09:04:56.803Z
  card-last-reviewed:: 2024-08-13T09:04:56.804Z
  card-last-score:: 3
	- 强引用
		- new关键字触发
		- 宁可抛错也不回收
	- 软引用
		- SoftReference修饰
		- 内存不足时回收
		- 可用来做本地缓存
	- 弱引用
		- WeekReference修饰
		- 每次GC时回收
		- ThreadLocalMap用于防止内存溢出
	- 虚引用
		- 每次GC时回收
		- 利用回收时的通知机制来管理堆外内存
- 永久代会发生垃圾回收吗 #card
  card-last-interval:: 0.03
  card-repeats:: 3
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T01:21:35.963Z
  card-last-reviewed:: 2024-08-14T01:21:35.963Z
  card-last-score:: 3
	- 会，但是回收的条件比较苛刻
		- 类及其所有对象都被回收
		- 常量池中的常量无任何引用
- ## 如何排查JVM问题 #card
  card-last-interval:: 0
  card-repeats:: 9
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T01:07:50.950Z
  card-last-reviewed:: 2024-08-14T01:07:50.950Z
  card-last-score:: 3
	- 使用 {{cloze `jmap`}} 查看各区域的使用情况
	- 使用 {{cloze `jstack`}} 查看线程的运行情况，关注阻塞的线程、是否出现了死锁
	- 使用 {{cloze `jstat`}} 查看GC情况，特别注意Full GC
	- 使用 {{cloze `visualvm`}} 分析大内存占用，如 短时间内产生的大量小对象，长期的大对象以及长期得不到回收的对象
- JVM参数 调优经验 #card
  card-last-interval:: 0.02
  card-repeats:: 4
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-13T09:01:54.566Z
  card-last-reviewed:: 2024-08-13T09:01:54.566Z
  card-last-score:: 3
	- {{cloze -Xms -Xmx }} 初始堆内存，最大堆内存
	- {{cloze -Xmn -XX: SuvivorRation -XX:NewRation}} 新生代大小 e,s1,s2的比例 新生代:老年代
	- {{cloze -xx:maxTenuringThreshOld=0}} 进入老年代的门槛年龄
	- {{cloze -XX:+PrintGC -xloggc:gc.log}}打印GC过程信息 指定GC日志
	- {{cloze -XX:+UseG1GC -XX:MaxGCPauseMillis=50 -XX:ParallelGCThreads}} 指定GC算法 最大停顿时间 并行线程数
	- {{cloze -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath}} OOM时自动Dump Dump下的日志路径
	- {{cloze -xss1m}} 设置单个线程栈大小
	- {{cloze -xx:maxMetaspaceSize=2g}} 设置元空间的最大值
- 对象一定都是在堆上分配吗？ 逃逸分析 #card
  card-last-interval:: 0.01
  card-repeats:: 7
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-13T09:18:37.843Z
  card-last-reviewed:: 2024-08-13T09:18:37.844Z
  card-last-score:: 3
	- 如果对象不会逃逸出方法外部，则会在栈上分配
	- 如果符合标量替换条件(不逃逸&不会被本线程外的线程访问)，则会将对象分解成若干个方法内从成员变量
- HotSpot JVM 为什么叫这个名字 #card
  card-last-interval:: 0
  card-repeats:: 8
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-13T09:16:11.178Z
  card-last-reviewed:: 2024-08-13T09:16:11.178Z
  card-last-score:: 3
	- 即时编译功能
		- 寻找热点代码，将热点代码编译成机器码并缓存起来
	- 即时编译器中所作的优化
		- 方法内联-将目标方法的方法体直接copy过来
		- 逃逸分析
		- 字段读取&存储
		- 循环优化-无关代码外提、循环展开(次数减少，重复相同逻辑x次)
		-
- Java程序 CPU突然飙升 该如何处理？#card
  card-last-interval:: 0
  card-repeats:: 11
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-23T01:43:08.450Z
  card-last-reviewed:: 2024-08-23T01:43:08.450Z
  card-last-score:: 3
  id:: 66b2c808-ec8f-41cc-85b4-9e91f056ce7c
	- 问题定位
		- {{cloze top 命令}} 查看CPU占用情况，找到进程id
		- {{cloze top -Hp}} 查看进程中的线程，找到占用最高的线程ID
		- {{cloze  jstack 定位代码}} 导出堆栈信息，根据线程ID找到对应线程的堆栈
		- {{cloze 分析代码，解决问题}}
	- 问题解决
		- 空循环，或者空自旋 {{cloze 使用Thread.sleep或者加锁，让线程适当的阻塞}}
		- 频繁Full GC {{cloze 减少对象的创建数量 or 使用对象池}}
		- 并发量突增
		- 日志打印过多
- JVM的**年轻代**内存分配为什么是1:1:8 #card
  card-last-score:: 3
  card-repeats:: 9
  card-next-schedule:: 2024-08-14T01:08:23.359Z
  card-last-interval:: 0
  card-ease-factor:: 1.3
  card-last-reviewed:: 2024-08-14T01:08:23.359Z
	- 为什么要有Eden和servivor区
		- {{cloze 减少被送入老年代的对象，进而减少Full GC次数}}
	- 为什么要有2个servivor区
		- {{cloze 更贴合复制算法，解决碎片化问题}}
- 监控JVM时，会关注哪些指标 #card
  card-last-interval:: 0.14
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2024-08-16T04:09:52.621Z
  card-last-reviewed:: 2024-08-16T01:09:52.621Z
  card-last-score:: 3
  id:: 66544681-b7e8-492b-b50b-84d4270751e8
	- {{cloze 堆内存}} 使用情况
		- 总量、使用量、大对象占比等
	- {{cloze GC}} 情况
		- 次数、耗时、GC类型
	- {{cloze 线程}} 使用情况
		- 总数、活跃数、状态、是否死锁
	- {{cloze CPU}} 、 {{cloze 磁盘IO}} 和 {{cloze 网络流量}}
	- {{cloze 类加载}} 的数量和大小
	- {{cloze 日志}} 中的错误信息
- JVM频繁GC，你会怎么办 #card
  id:: 66bdbe38-595c-4b97-acf6-d42aa64126fc
  card-last-interval:: 0.14
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2024-08-23T05:04:13.254Z
  card-last-reviewed:: 2024-08-23T02:04:13.255Z
  card-last-score:: 3
	- 导出和分析日志
		- 使用 {{cloze `-Xloggc`}} 命令，找到GC日志的地址
		- 导入GC分析工具,有Java自带的 {{cloze VisualVM}} 和各种开源的如 {{cloze gceasy}}
	- 关注核心指标
		- {{cloze 堆内存}} 大小的变化趋势
		- GC {{cloze 停顿}} 时间
		- 在GC {{cloze 前后各区域大小}} 变化
		- {{cloze 对象的}} 一些统计信息
	- 现象和相应的策略
		- 频繁Youn GC
			- 调整 {{cloze 堆的大小 `--Xms:2g Xmx:2g`}} 和 {{cloze 新生代的大小`-Xmn:521m`}}
			- GC后内存得不到大量释放，可能是代码有 {{cloze 内存泄露}}
		- 频繁Full GC
			- 调整 {{cloze 触发老年代回收}} 的阈值
			- 调整 {{cloze 进入老年代最小的GC}} 年龄
			- {{cloze 大对象进入老年代}} 的标准
		- 通用策略
			- 调整 {{cloze 内存区域大小比率}}
			- 设置符合预期的 {{cloze 停顿时间}}
			- 调整 {{cloze 回收线程的数量}}
- 如何判断出现了内存泄露 #card
  id:: 66544682-4d24-4646-8ba6-527ef3a72f1d
  card-last-interval:: 0.14
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2024-08-23T05:18:01.075Z
  card-last-reviewed:: 2024-08-23T02:18:01.076Z
  card-last-score:: 3
	- 堆内存使用量 {{cloze 持续增长}} ，即使在 {{cloze 垃圾回收}} 后也未显著下降
	- 使用性能分析工具Virtual VM，找出 {{cloze 长时间存活且未被回收的对象}}
	- 使用第三方内存分析工具 如Jprofile
- 如何修复内存泄露 #card
  id:: 66c2e7b0-6752-45f9-89fa-11fe7162bbf7
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2024-08-23T09:25:49.480Z
  card-last-reviewed:: 2024-08-19T09:25:49.481Z
  card-last-score:: 3
	- 审查并优化 {{cloze 代码}}
	- 使用 {{cloze 软引用、弱引用、虚引用}}
	- 及时清除 {{cloze 集合或缓存中的}} 对象
	-