- {{cards [[Redis面试题]]}}
	- Summary: 10 items, 1 review counts [[2023年01月16日]]
		- Remembered:   1 (100%)
		- Forgotten :   0 (0%)
- 项目中缓存是如何使用的，为什么要用 #card
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2023-01-20T09:29:40.417Z
  card-last-reviewed:: 2023-01-16T09:29:40.417Z
  card-last-score:: 3
	- 提高接口性能 {{cloze 缓存查询结果}} 用得最多的是 {{cloze 用户信息查询}} 接口
	- 降低数据库压力 {{cloze 热点数据缓存}}
	- 限流&幂等&分布式锁
	- 一级页面熔断降级 {{cloze 缓存数据，降级后直接取用}}
- 缓存使用不当会带来哪些后果，解决办法 #card
  card-last-interval:: 3.72
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-20T02:29:36.843Z
  card-last-reviewed:: 2023-01-16T09:29:36.843Z
  card-last-score:: 3
	- 缓存与数据库的双写不一致
		- 读的时候 {{cloze 先读缓存，再读数据库，最后把去除的数据放入缓存}}
		- 写的时候 {{cloze 先更新数据库，再删除缓存}}
	- 缓存雪崩
		- {{cloze 大量key设置了同一时间过期}} {{cloze 服务器宕机}}
		- {{cloze 设置过期时间时加上一个随机值，分散一些}} {{cloze 集群部署}} {{cloze 本地缓存,熔断降级}}
	- 缓存穿透
		- 问题描述 {{cloze 请求查询了缓存和数据库中都不存在的数据，这样每次请求都打到了数据库}}
		- 解决方案 {{cloze 对空值进行缓存}} {{cloze 对请求本身进行拦截和过滤}}
	- 缓存击穿
		- 问题描述 {{cloze 某一热点key突然过期，导致所有请求打到数据库上}}
		- 解决方案 {{cloze 热点key过期时间加长，永不过期}} {{cloze 实时监控科目数据，实时调整过期时间}} 代码层面 {{cloze 使用synch 或 ReentryLock 加锁更新，这样后续的请求就又会走缓存}}
- 在日常项目中用到了哪些Redis数据类型,底层结构是什么样的 #card
  card-last-interval:: 3.86
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-20T05:28:12.944Z
  card-last-reviewed:: 2023-01-16T09:28:12.944Z
  card-last-score:: 3
	- string
		- {{cloze 普通的kv存储，常用}}
		- 底层结构是 {{cloze 动态字符串SDS}}
	- set
		- {{cloze 集合的交并差操作，比如  你可能认识的人，共同的朋友}}
	- hash
		- {{cloze 层级为1的对象}}
		- {{cloze hash表，采用数组+链表 扩容时采用渐进式rehash}}
	- list
		- {{cloze 用户的粉丝列表，关注列表}} 可基于list实现分页查询
		- 底层结构是 {{cloze 双向无环链表}}
	- zset
		- {{cloze 自定义score排序，比如排行榜机制}}
		- {{cloze 跳跃表，节点按分值大小排序，再按对象大小排序}}
- Redis的过期策略有哪些?数据明明过期了，怎么还占用着内存 #card
  card-last-interval:: 3.59
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-20T18:33:08.111Z
  card-last-reviewed:: 2023-01-17T04:33:08.111Z
  card-last-score:: 3
	- 定期删除
		- {{cloze 每隔一段时间，删除过期key}} {{cloze 采用随机抽取策略}}
		- 为什么单个key不能到期删除? {{cloze 需要用监视器来负责监视key，虽然内存及时释放，但占用了宝贵的CPU资源}}
	- 惰性删除
		- {{cloze 当我们查询key的时候才对key进行检测}}
	- 内存淘汰机制
		- {{cloze noevivtion}} 直接拒绝
		- {{cloze allkeys-lru}} 所有key中 lru
		- {{cloze allkeys-random}} 所有key中 随机移除
		- {{cloze volatile-lru}} 有过期时间的key中 lru
		- {{cloze volatile-random}} 有过期时间的key中 随机移除
		- {{cloze volatile-ttl}} 有过期时间的key中 移除最早过期时间的key
- 了解Redis的事务机制吗？#card
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2023-01-20T09:28:18.588Z
  card-last-reviewed:: 2023-01-16T09:28:18.588Z
  card-last-score:: 3
	- 事务以 {{cloze MUTLI}}命令开始
	- 客户端接收一系列操作，放入队列中
	- 收到 {{cloze EXEC}}命令后，开始顺序执行操作。
	- 监视watch整个事务相关的key是否早已被修改，如果是则拒绝执行这个事务
	- 执行时不会被其他客户端发送来的命令请求打断
	- 不支持回滚，要么都执行，要么都不执行
- Redis为什么快？#card
  card-last-interval:: 3.59
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-20T16:11:05.620Z
  card-last-reviewed:: 2023-01-17T02:11:05.622Z
  card-last-score:: 3
	- 基于内存操作
	- 使用单线程， {{cloze 避免线程切换和竞争产生的消耗}}
	- 基于 {{cloze IO多路复用模型}} IO模型 {{cloze 让单个线程高效处理多个连接请求}}
	- {{cloze C语言实现，更加优化的基础数据结构}} 语言、底层数据结构
- Redis是单线程的吗？#card
  card-last-interval:: 3.59
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-19T23:30:09.059Z
  card-last-reviewed:: 2023-01-16T09:30:09.060Z
  card-last-score:: 3
	- 只有 {{cloze 网络请求模块}} 和 {{cloze 数据操作模块}} 是单线程的 其他的如 持久化模块、集群模块是多线程的
	- 多线程的好处 {{cloze 使用多线程可以提升 IO利用率和CPU利用率}}
		- Redis无需提升CPU利用率，基础操作都是基于内存的
		- 多线程切换会带来开销，且代码复杂性上升。最终选择了IO多路复用
	- 在6.0+，针对 {{cloze 网络请求}}改成了多线程
- Redis的分布式锁如何实现 #card
  card-last-interval:: 3.86
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-20T05:25:23.600Z
  card-last-reviewed:: 2023-01-16T09:25:23.600Z
  card-last-score:: 3
	- {{cloze setnx来争抢锁，再用expire加上过期时间}}
- 如何把固定前缀开通的key全部找出来#card
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2023-01-20T09:25:15.232Z
  card-last-reviewed:: 2023-01-16T09:25:15.232Z
  card-last-score:: 3
	- {{cloze scan命令可以提取指定模式的key列表}}
- Redis的持久化机制 #card
  card-last-interval:: 3.59
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-20T18:31:08.811Z
  card-last-reviewed:: 2023-01-17T04:31:08.812Z
  card-last-score:: 3
	- bgsave 全量持久化
		- 过程 {{cloze 生成子进程和当前内存快照，子进程进行持久化}}
		- 缺点 {{cloze 非子进程的方式耗时较长，会导致停止服务}} {{cloze 如果不停机，可能丢失最后一部分数据}}
	- aof 增量持久化
		- 过程 {{cloze 以日志的形式记录每个写操作}}
		- 缺点 {{cloze AOF文件大，恢复速度慢}}
	- aof的重写机制
		- 目的 {{cloze 解决AOF 文件过大的问题}}
		- {{cloze 将过期的、不再使用的、重复的以及可优化的命令进行优化，重新生成AOF文件}}
		- 流程 {{cloze fork一个子进程}} {{cloze 重写期间，主线程的写命令被保存在缓冲区中}}
- RDB和AOF各自的优缺点#card
  card-last-interval:: 3.59
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-20T18:28:55.528Z
  card-last-reviewed:: 2023-01-17T04:28:55.528Z
  card-last-score:: 3
	- RDB优点
		- 生成的文件 {{cloze 只有一个紧凑的二进制文件，非常适合备份、全量复制场景}}
		- {{cloze 文件随意拷贝，容灾性好}}
		- 恢复速度快
	- RDB的缺点
		- 实时性 {{cloze 低，每个一段时间进行持久化}}
		- 兼容问题 {{cloze Redis在演化过程中存在多个格式的RDB文件}}
	- AOF的优点
		- 实时性 {{cloze 高，没进行一次写操作就记录到aof文件中}}
	- AOF的缺点
		- 生成的文件 {{cloze 比RDB大}} {{cloze 恢复速度慢}}
- Redis的主从复制过程#card
  card-last-interval:: 3.59
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-20T18:32:24.841Z
  card-last-reviewed:: 2023-01-17T04:32:24.842Z
  card-last-score:: 3
	- 从服务器首次连接 {{cloze 全量复制}} {{cloze rdb or aof文件}}
	- 复制后数据先 {{cloze 保存到本地磁盘}} 后 {{cloze 从磁盘读取到内存}}
	- 重新连接 {{cloze 部分数据复制}} {{cloze offset偏移量 repl-backlog-buffer 复制积压缓冲区}}
- Redis数据如何预热