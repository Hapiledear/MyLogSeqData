- {{cards [[MySql面试题]] }}
	- Summary: 1 items, 1 review counts [[2023年01月21日]]
		- Remembered:   0 (0%)
		- Forgotten :   0 (0%)
- 如无特殊说明，题中出现的存储引擎均为 [[InnoDb]]
- [[分布式事务]]
-
- 数据库死锁的触发条件 和 解决方案 #card
  id:: 66c2b8a3-dbe4-432d-92d9-e1b138d9ee50
  card-last-interval:: 0.14
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2024-08-23T04:17:14.731Z
  card-last-reviewed:: 2024-08-23T01:17:14.731Z
  card-last-score:: 3
	- 两个或以上的事务，各自持有对方想要的资源，且都不会主动释放。
	- 解决方案
		- 查看 {{cloze 数据库监控}} ，手动kill事务
		- 将 {{cloze 大事务}} 拆分为 {{cloze 小事务}}
		- 合理使用 {{cloze 索引}} 和 {{cloze 隔离级别}}
		- 改用 {{cloze 乐观锁}} 和 {{cloze 排队机制}}
- SQL查询过程 #card
  card-last-interval:: 0
  card-repeats:: 10
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T01:01:43.447Z
  card-last-reviewed:: 2024-08-14T01:01:43.448Z
  card-last-score:: 3
	- 建立连接 {{cloze 连接器}}
		- 负责对用户名&密码进行校验，连接的维护
	- 执行计划の缓存 {{cloze 查询缓存}}
		- 缓存 执行过的查询结果
	- SQL分析 {{cloze 分析器}} 生成查询树
		- 识别表名和列名，检查语法
	- SQL优化 {{cloze 优化器}} 生成 执行计划
		- 分析查询数，选择最优的执行计划
		- 对 **索引**的选择 和 **join**的优化(join算法、驱动表)
			- 有时优化后选择的索引与实际不太相符 -- order by a 与 where 条件
			- 正常的设想是 先使用where条件进行过滤，再排序
			- 经优化后可能会造成，在过滤扫描时使用了 a索引。而生产环境多少都有数据倾斜，导致效果南辕北辙。
	- SQL执行 {{cloze 执行器}}
		- 根据执行计划，向存储引擎发送数据请求
		- 对于引擎返回的记录，做 筛选、分组、排序
	- 委托给存储引擎
		- 进行实际的索引扫描
		- 将记录所在的数据页放入缓冲池 (磁盘->内存 )
- SQL update过程 #card
  card-last-interval:: 0
  card-repeats:: 10
  card-ease-factor:: 1.3
  card-next-schedule:: 2025-03-25T10:37:28.879Z
  card-last-reviewed:: 2025-03-25T10:37:28.879Z
  card-last-score:: 3
	- 查询出原数据，并**上锁**
	- 将修改的数据刷入内存 Buffer pool
	- 记录 Undo Log 和 Redo Log
	- 记录 bin log
	- 事务提交 ，等待被工作线程刷入磁盘
- 查询语句的各部分执行顺序 9 #card
  card-last-interval:: 62.21
  card-repeats:: 9
  card-ease-factor:: 1.48
  card-next-schedule:: 2024-09-22T13:35:41.274Z
  card-last-reviewed:: 2024-07-22T08:35:41.274Z
  card-last-score:: 5
	- {{cloze from -> join t2 on xxx  -> where yyy -> group by aaa -> having -> bbb -> select aaa,bbb,ccc -> distinct aaa ->order by bbb -> limit m,n}}
- MySQL的join过程 #card
  card-last-interval:: 29.21
  card-repeats:: 5
  card-ease-factor:: 1.94
  card-next-schedule:: 2024-09-10T13:01:14.997Z
  card-last-reviewed:: 2024-08-12T08:01:14.997Z
  card-last-score:: 3
	- 确定驱动表 - 在SQL优化阶段,优化器根据 {{cloze 查询条件}} 预估 每张表的数据量大小。选择 {{cloze 最小}} 的作为驱动表
	- 加载驱动表的数据到内存
	- 执行join算法 - 在SQL执行阶段，如果有可以利用的索引也会利用，达到join的同时进行数据过滤
	- join算法有三种 {{cloze 嵌套循环join}} {{cloze Sort Merge Join}} 和 {{cloze Hash Join}}
	- 从 MySQL 8.0.20 版本开始使用 {{cloze Hash Join}} 废弃 {{cloze 恰套循环join}}
- 存储引擎InnoDb和MyISAM的区别 #card
  card-last-interval:: 0
  card-repeats:: 11
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T01:32:57.371Z
  card-last-reviewed:: 2024-08-14T01:32:57.371Z
  card-last-score:: 3
	- 事务支持
		- {{cloze 不支持}} M
		- {{cloze 支持}} I
	- 表锁差异
		- {{cloze 只支持表级锁}} M
		- {{cloze 支持表锁和行级锁}} I
	- 索引结构
		- {{cloze 索引文件和数据文件是分离的，B+树的叶子节点存放的是 数据记录地址 }} M
		- {{cloze 叶节点本身保存了完整的数据记录}} I
	- 表主键
		- {{cloze 允许没有任何索引和主键的表存在}}
		- {{cloze 如果用户不设定，就会自动生成一个6字节的主键}}
	- 表外键
		- {{cloze 不支持}}
		- {{cloze 支持}}
	- count(*)
		- {{cloze 直接取出该值}}
		- {{cloze 只能遍历}}
- 事物的四大特性，分别是如何解决的 #card
  card-last-interval:: 0
  card-repeats:: 10
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T00:59:33.459Z
  card-last-reviewed:: 2024-08-14T00:59:33.460Z
  card-last-score:: 3
	- 一致性 是目标，其他三者是手段
		- {{cloze 事物在执行前后其状态一致,A+B=100 无论A B怎么转账，最终都是100}}
		- {{cloze Undo Log}}
	- 原子性
		- {{cloze 在事物中的一组操作，要么全部成功提交，要么全部失败回滚}}
		- {{cloze Undo Log}}
	- 持久性
		- {{cloze 事物一旦提交，发生的改变就是永久的}}
		- {{cloze Redo Log}}
	- 隔离性
		- 多个事物同时进行时，会出现3种问题.为解决这三个问题，进而提出了4种隔离级别
			- 3个问题 {{cloze 脏读}} {{cloze 不可重复读}} {{cloze 幻读}}
			- 4种隔离级别 {{cloze 读未提交}} {{cloze 读已提交}} {{cloze 可重复读读}} {{cloze 串行化}}
		- {{cloze 锁机制 和 MVVC机制}}
- Undo Log 和Redo Log #card
  card-last-interval:: 0
  card-repeats:: 10
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T01:03:13.404Z
  card-last-reviewed:: 2024-08-14T01:03:13.405Z
  card-last-score:: 3
	- Undo Log
		- 存储的是 {{cloze 旧数据的版本链集合}}
		- 原子性的体现 {{cloze 如果出错，可以用它来回滚数据}}
	- Redo Log
		- re-do 重新做一遍操作
		- 存储的是 {{cloze 最新版本的数据}}
		- 持久性的体现 {{cloze 如果系统崩溃，可以用它来进行恢复}}
	- 具体的崩溃恢复流程
		- 关键词 活跃事物表、脏页表
		- ((669e05fa-6ec5-4ce7-8d72-ec8fa277ff80))
- 描述一下事物产生的三个问题 #card
  card-last-interval:: 0
  card-repeats:: 11
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T01:31:26.596Z
  card-last-reviewed:: 2024-08-14T01:31:26.596Z
  card-last-score:: 3
	- 脏读
		- {{cloze 事物A读取到了事物B 未提交的数据}}
	- 不可重复读
		- {{cloze 事物A在读取数据时，两次读取的**数据结果**不一致}}
	- 幻读
		- 范围查询 {{cloze 事物A在进行范围查询时，两次读取的**记录数**不一致}}
- 事物的4种隔离级别 #card
  card-last-interval:: 59.03
  card-repeats:: 10
  card-ease-factor:: 1.4
  card-next-schedule:: 2024-09-19T08:02:47.898Z
  card-last-reviewed:: 2024-07-22T08:02:47.898Z
  card-last-score:: 5
	- 读未提交
		- {{cloze 允许读取到还没有提交的数据}}
	- 读已提交
		- {{cloze 在事物A中只可以 读到事物B已经提交的数据}}
	- 可重复读
		- {{cloze 事物A中，多次读取同一个数据时，其值都和开始时一致}}
		- 是否存在幻读？ {{cloze 快照读时(非上锁语句)不存在幻读。由于MVCC + Read View}}
	- 串行化
		- {{cloze 所有事物被串行执行}}
- 什么是幻读？如何防止？ #card
  card-last-interval:: 0
  card-repeats:: 11
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T01:32:50.636Z
  card-last-reviewed:: 2024-08-14T01:32:50.636Z
  card-last-score:: 3
	- 范围查询 {{cloze 事物A的两次查询之间，被事物B插入or删除了记录，产生结果集的不一致}}
	- 防止方式
		- {{cloze 间隙锁}} + {{cloze 行锁}} 的组合方式 解决 读最新数据下的幻读
			- 记录锁 & 行锁
				- {{cloze 锁住索引记录}}
				- 上锁时机
					- {{cloze select ... for update}}
					- {{cloze 通过 主键索引与唯一索引 对数据进行update操作}}
			- 间隙锁
				- {{cloze 锁住索引记录之间的间隙}}
			- 临键锁 next-key lock
				- {{cloze 加在某条记录以及这条记录前面的间隙(左开右闭) 上锁}}
				- {{cloze 只与 非唯一索引列 有关}}
		- MVCC + Read View 的组合方式 解决 快照读下的幻读
- 读取数据的两种方式 #card
  card-last-interval:: 59.03
  card-repeats:: 10
  card-ease-factor:: 1.4
  card-next-schedule:: 2024-09-19T07:57:12.729Z
  card-last-reviewed:: 2024-07-22T07:57:12.730Z
  card-last-score:: 5
	- mvcc {{cloze 快照读}}
		- 简单的select操作
		- 如何解决幻读 {{cloze MVCC + Read View}}
	- 最新记录 {{cloze 当前读}}
		- 加锁的select操作 {{cloze lock in share mode}} {{cloze for update}}
		- 插入、更新、删除操作
		- 如何解决幻读 {{cloze next-key Lock 行锁与间隙锁的组合}}
- 什么是MVCC #card
  card-last-interval:: 0
  card-repeats:: 10
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T00:55:34.915Z
  card-last-reviewed:: 2024-08-14T00:55:34.916Z
  card-last-score:: 3
  id:: 66544682-3ef3-48a1-8016-f7c29dd8c85d
	- 多版本并发控制
	- 只工作在两种事物隔离级别下
		- {{cloze 读已提交}} {{cloze 可重复读}}
	- 具体实现依靠 {{cloze undo log}} 形成 {{cloze 读视图}} 机制 关键词 copyOnWrite 版本号-> 版本链
		- Undo Log 存储旧的数据，并构成版本链
		- Read View 读视图 用以判断事务的可见性
			- {{cloze 当前事务ID}} {{cloze 未提交的事务列表}} {{cloze 最小事务ID和最大事务ID}}
		- 读已提交的实现
			- {{cloze 每次读之前都生成一个Read View}}
		- 可重复读的实现
			- {{cloze 只在第一次执行查询语句时生成 Read View}}
- MySql中的锁有哪些 #card
  card-last-interval:: 0
  card-repeats:: 9
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-13T09:19:54.540Z
  card-last-reviewed:: 2024-08-13T09:19:54.540Z
  card-last-score:: 3
  id:: 66544682-bdee-4d8c-bf84-15f2624311e0
	- 表锁
		- 意向锁
			- 作用 {{cloze 让表锁和行锁的共存更高效，大概的记录一下表中是否存在 行锁}}
			- 分类 {{cloze 排它型、共享型}}
			- 举例 for update 锁住一行后，事务b要想获得该表t1的表锁，必须检查2项
				- {{cloze 没有其它事务持有t1的排它锁}}
				- {{cloze 表中的任意一行都没有行级排它锁}} -> {{cloze t1表没有意向共享锁}}
	- 行锁 or 记录锁
		- 作用 {{cloze 锁住该条记录，防止其它事务的插入、更新、删除}}
		- 限制条件 {{cloze 必须要有 唯一索引 和 主键索引做条件 (查出一条记录) 否则退化成 临界锁}}
		- 共享型 读
			- 手动加s锁 {{cloze select ... lock in share mode}}
		- 排它型 写
			- 自动加x锁 {{cloze update\delete\insert 自动加x锁}}
			- 手动加x锁 {{cloze select ... for update }}
	- 间隙锁
		- 作用 {{cloze 在 RR 级别下解决 直接读的 幻读 问题}} {{cloze 锁住 一段 开区间}}
		- 限制条件 {{cloze 非唯一索引}}
	- next-key lock 临界锁
		- 限制条件 {{cloze 非唯一索引}}
		- 作用 {{cloze 锁住一段 左开右闭的 区间}} 相当于 行锁+间隙锁
	- 插入意向锁
		- 是一把 间隙锁
		- 作用 {{cloze 在RR事务级别下，解决并发插入问题}}
		- 原理 {{cloze 区间相同但插入记录本身不同的情况下 互不排斥}}
- 主键的选择，使用自增ID 还是 UUID #card
  card-last-interval:: 0
  card-repeats:: 11
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T01:13:31.593Z
  card-last-reviewed:: 2024-08-14T01:13:31.594Z
  card-last-score:: 3
	- 自增型ID {{cloze 符合主键索引的顺序排布，插入快速，产生碎片较少}}
	- UUID {{cloze 全局唯一，跨系统存取方便}} {{cloze 较为分散，不会产生数据倾斜}}
	- 雪花算法 {{cloze 按照一定规则填充Long的64位: 时间（毫秒级）+集群ID+机器ID+序列号}}
	- ((66544683-504d-4344-a4e9-d0abfdb0bd53))
- B+树与B树的对比 #card
  card-last-interval:: 0
  card-repeats:: 9
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-13T09:16:00.986Z
  card-last-reviewed:: 2024-08-13T09:16:00.986Z
  card-last-score:: 3
	- 数据记录 {{cloze 都存放在叶子节点中}} {{cloze 非叶节点只存储键值信息}}
	- {{cloze 所有叶子节点之间都有一个链指针}}
	- B树可以在所有节点中存储数据
- 索引的优缺点 #card
  card-last-interval:: 0
  card-repeats:: 9
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-13T09:12:10.350Z
  card-last-reviewed:: 2024-08-13T09:12:10.351Z
  card-last-score:: 3
	- 优点
		- {{cloze 提高数据检索效率，降低IO}}
		- {{cloze 索引本身有一定顺序，降低排序成本}}
	- 缺点
		- {{cloze 需要占用物理空间}}
		- {{cloze 每次增改数据，都需要动态维护索引，导致增改时间变长}}
- 聚集索引和非聚集索引 #card
  card-last-interval:: 0
  card-repeats:: 9
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-13T09:15:57.258Z
  card-last-reviewed:: 2024-08-13T09:15:57.258Z
  card-last-score:: 3
	- 聚簇索引
		- {{cloze b+树的叶子节点中存放了表中一行的全部数据}}
		- {{cloze 查询时，只需扫描一次B+树，就能找到记录}}
	- 非聚簇索引
		- {{cloze 除聚簇索引以外的索引}}
		- 回表 {{cloze 第一次定位到聚簇索引，第二次通过聚簇索引定位到查找记录}}
- 索引优化策略 #card
  card-last-interval:: 0
  card-repeats:: 11
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T01:31:18.220Z
  card-last-reviewed:: 2024-08-14T01:31:18.220Z
  card-last-score:: 3
	- 索引覆盖 {{cloze select字段 存在索引中}}
	- 最左匹配原则 {{cloze 利用最左匹配，减少索引的建立}}
- 索引失效的13种场景 #card
  card-last-interval:: 0
  card-repeats:: 9
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-13T09:05:12.100Z
  card-last-reviewed:: 2024-08-13T09:05:12.101Z
  card-last-score:: 3
  id:: 66544682-8b5a-4888-9bd0-2104beb329f7
	- 联合索引不满足最左匹配原则
	- 使用了 `select * ` 肯定不会走 覆盖索引
	- 索引列 参与了运算
	- 索引列 使用了函数
	- `like`时，只有右百分号才会走索引
	- 等号两边类型不匹配，导致隐式类型转换
	- 使用了`or`操作
	- 两列做比较
	- `<>` `!=`
	- `is not null`
	- `not in ` `not exists`
		- 小表之间走全表扫码
		- 可能被优化器优化改写
	- 非主键的`order by ` 但需要具体情况具体验证
	- 表数据较少时，走全表扫描比走索引效率更高 (内置优化策略)
- 索引下推 #card
  card-last-score:: 3
  card-repeats:: 9
  card-next-schedule:: 2024-08-14T01:23:15.444Z
  card-last-interval:: 0
  card-ease-factor:: 1.3
  card-last-reviewed:: 2024-08-14T01:23:15.444Z
	- Mysql5.6-之前，没有充分利用非聚簇索引
		- 假设有索引(name,age)和查询条件 name like 张% and age = 10
	- 不存在索引下推时 {{cloze 先根据非聚簇索引查记录，再根据where条件过滤}}
		- 只会利用索引中的name字段
	- 使用索引下推后 {{cloze 先根据 where条件过滤记录，再进行非聚簇索引查询}}
		- 充分利用了name,age 字段，减少了回表次数
- MRR优化 #card
  card-last-interval:: 0
  card-repeats:: 11
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T01:30:20.044Z
  card-last-reviewed:: 2024-08-14T01:30:20.045Z
  card-last-score:: 3
	- 当使用二级索引进行范围查询时，从随机读变为顺序读 {{cloze 通过对索引值排序，把无序回表IO变成有序回表IO}}
- Explain 查询计划 #card
  card-last-interval:: -1
  card-repeats:: 1
  card-ease-factor:: 1.3
  card-next-schedule:: 2025-03-25T16:00:00.000Z
  card-last-reviewed:: 2025-03-25T10:38:17.537Z
  card-last-score:: 1
  id:: 66544682-c74c-4d87-b12d-00a4d0f39d87
	- id 执行顺序
		- {{cloze id不同，值越大越先执行}}
		- {{cloze id相同，从上到下的顺序}}
	- select_type 查询类型
	- table 当前执行的表
	- type 使用了哪种类型的查询
		- {{cloze system > constanse> range > ref > eq_ref > index > all}}
	- possible_keys 和 key 可能使用的索引
	- ref 索引的哪一列被使用了
	- rows 预计扫描行数
	- extra 额外信息
		- Using filesort 使用了非索引列排序
		- Using temporary 使用了临时表
		- Using index 使用了覆盖索引
		- Using where 该索引还用来执行查找操作
- 工作中你是如何优化慢查询的 #card
  card-last-interval:: 0
  card-repeats:: 10
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-19T09:25:31.093Z
  card-last-reviewed:: 2024-08-19T09:25:31.093Z
  card-last-score:: 3
	- 添加合适的索引
		- {{cloze 频繁查询的条件 和 排序字段 建立索引}}
		- {{cloze 考虑建立组合索引的顺序}}
		- {{cloze 索引不宜太多，5个以内}}
	- 优化表结构
		- {{cloze 数值型优于字符串}}
		- {{cloze 数据量过大，进行拆分}}
		- {{cloze 冷热数据备份}}
	- 优化查询语句 explant
		- {{cloze 是否加载了不必要的字段}}
		- {{cloze 是否命中索引}}
		- 降低SQL语句的复杂度
- bin log 和 redo log的区别 #card
  card-last-interval:: 0
  card-repeats:: 11
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T01:31:24.572Z
  card-last-reviewed:: 2024-08-14T01:31:24.573Z
  card-last-score:: 3
	- bin log
		- 是MySql层的日志系统 ，记录的是 {{cloze SQL语句的原始逻辑}}
	- redo log
		- 是存储引擎 InnoDb的日志 ，记录的是 {{cloze 某个数据做了什么修改}}
- MySql的主从复制 3线程 #card
  card-last-interval:: 0.01
  card-repeats:: 7
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T01:18:09.346Z
  card-last-reviewed:: 2024-08-14T01:18:09.393Z
  card-last-score:: 3
	- Master开启 bin log ,对数据的更新操作会按顺序写入 bin log
	- Slave连接Master后，Slave的一个`IO线程` 请求 bin log dump
	- Master开启 `IO线程` 开始传输bin log
	- Slave 启动线程  `SQL线程` ，实时监控 中继日志是否有更新，解析SQL语句，在S库中执行
- 分库分表策略 及 数据分片规则 #card
  card-last-interval:: 0
  card-repeats:: 9
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-13T09:13:33.345Z
  card-last-reviewed:: 2024-08-13T09:13:33.345Z
  card-last-score:: 3
	- 水平拆分
		- {{cloze 将一张表中的数据，按照一定策略(Hash取模、range) ，拆分到多个库/表中}}
	- 垂直拆分
		- {{cloze 按照 业务归属不同 or 字段查询频率不同 ，拆分到不同表/库中}}
	- 为什么要分库分表
		- 连接数不够，产生 资源报警
		- 慢查询增多，占用了大量的IO和网络带宽
- 分库分表会带来哪些问题，如何解决 #card
  card-last-interval:: 0
  card-repeats:: 9
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T01:06:07.925Z
  card-last-reviewed:: 2024-08-14T01:06:07.925Z
  card-last-score:: 3
  id:: 66544682-0e06-4a44-b423-789a8907f544
	- 分布式事务问题
		- {{cloze 使用相关中间件}}
	- 跨节点join问题
		- {{cloze 冗余字段}}
		- {{cloze 服务端自己组装}}
	- 跨节点分页、排序问题
		- {{cloze 每个库中都查询，在服务端组装}}
		- {{cloze 使用ES }}
	- 数据偏移问题
		- {{cloze 使用雪花算法生成UUID，确保数据均匀一致}}
	- 数据迁移问题
		- 双写方式
			- {{cloze 所有的旧代码，都对新库进行增删改}}
			- {{cloze 不断从老库抽数据，往新库写。边写边比对数据是不是最新的}}
- 业务中影响MySQL性能的场景 #card
  card-last-interval:: 0
  card-repeats:: 11
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T01:12:54.705Z
  card-last-reviewed:: 2024-08-14T01:12:54.705Z
  card-last-score:: 3
	- 大规模数据导出功能
		- {{cloze 建议`limit m,n` 替换成id的范围查询}}
	- ERP系统的 join \分组\排序
		- {{cloze 尽量使用索引}}
	- 各种看板和统计报表
		- {{cloze 数据工厂，离线计算。 直接接入ES}}
- 大批量数据的插入 #card
  card-last-interval:: 0
  card-repeats:: 12
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-23T01:26:49.882Z
  card-last-reviewed:: 2024-08-23T01:26:49.883Z
  card-last-score:: 3
	- 开启事务
	- 合并插入：一条语句插入多条数据
	- 顺序插入：数据按主键ID排序好
- 用户订单表(uid,aid,create_time) 要想让查询强制走uid索引，可以怎么做？ #card
  card-last-interval:: 7.33
  card-repeats:: 5
  card-ease-factor:: 1.52
  card-next-schedule:: 2024-08-26T16:05:07.843Z
  card-last-reviewed:: 2024-08-19T09:05:07.843Z
  card-last-score:: 3
	- {{cloze force index}} 关键字
	- 强制类型转化 将 {{cloze create_time 转为int型}}
- 什么情况下，MySQL会锁住整个表? 如何减少或避免此类情况？#card
  card-last-score:: 3
  card-repeats:: 3
  card-next-schedule:: 2024-08-27T03:12:36.509Z
  card-last-interval:: 7.76
  card-ease-factor:: 1.94
  card-last-reviewed:: 2024-08-19T09:12:36.510Z
  id:: 669f0d56-9b74-4b45-a032-989c4137ad4c
	- 注意，锁住整个表A != 对表A施加表锁
	- 表结构层面
		- 对 {{cloze 表结构}}进行修改
		- 手动执行 {{cloze Lock Tables}}语句
		- MyISAM的写操作
		- 对一个大表进行 {{cloze 新建或删除}}索引
	- 列索引层面
		- select ... {{cloze for update}} 无可用索引
		- {{cloze 更新/删除}} 时, where条件 没命中索引
		- {{cloze 查询}} 时，索引失效
	- 如何减少或避免此类情况？
		- 数据监控和慢查询分析
		- 选择合适的 {{cloze 存储引擎}}
		- 优化 {{cloze 查询和索引}}
		- 设置合理的 {{cloze 锁策略和隔离级别}}
		- 分解 {{cloze 大事务}} ，减少事务持续时间
		- {{cloze 分库分表}} -减少每次操作锁定的范围
		- {{cloze 读写分离}} -将读操作分散到从库上，以减轻主库的负载和锁定压力
- MySQL主从同步延迟的原因有哪些 #card
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2024-08-18T01:19:57.747Z
  card-last-reviewed:: 2024-08-14T01:19:57.747Z
  card-last-score:: 3
  id:: 669f1220-37aa-4091-a51e-76a66e191b29
	- {{cloze 主库负载过高}} ，产生的SQL数量超过从库*一个sql线程*所能承受的范围
	- {{cloze 从库负载过高}} ，大量查询产生了锁等待
	- {{cloze 网络延迟}}
	- {{cloze 机器性能不足}}
- MySQL脏页的刷新时机 #card
  card-last-interval:: 0.14
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2024-08-13T11:53:37.608Z
  card-last-reviewed:: 2024-08-13T08:53:37.608Z
  card-last-score:: 3
	- 定时刷新
	- 缓存空间不足
	- RedoLog空间不足
		- 内存 --> 磁盘缓冲 --> 磁盘
	- 整体脏页比例达到一定阈值
- # 一些思考
- 在没有事务控制的情况下，select 语句读取的数据是**快照**内容还是**最新**内容？
	- 这取决于当前设定的**数据隔离级别**。
	- 无论有无事务控制，都会生成一个MVCC，只不过当前事务ID为空。
- 在没有事务控制的情况下，update操作时，记录在Log中的条目有事物ID吗？
	- 没有事务&开启**autocommit**，update会形成一个独立事务。
	- 没有事物&不开启autocommit, update之后再执行update会抛出错误。
- 由于内存满了而淘汰的脏页A，需要刷入磁盘，可此时脏页A关联事物T1还未提交，MYSQL会如何处理
	- 会刷入磁盘，不等待事物T1的提交
	- 无论有无事物控制，都会生成一个MVCC，便可以通过版本链保证读取到的已提交最新数据
-