- {{cards [[Kafka面试题]]}}
- 介绍一下Kafka #card
	- Producer 生产者
		- 将消息发送到指定 {{cloze topic}}
		- 支持消息 {{cloze 分区路由}}
		- 提供 {{cloze ACK确认}} 机制
	- Broker Kafka集群机器单元
		- 消息 {{cloze 持久化存储}}
		- 处理 {{cloze 生产者/消费者}} 请求
		- {{cloze 副本}} 管理和 {{cloze 故障}} 转移
	- Consumer 消费者
		- 从 {{cloze Topic}} 拉取消息
		- 维护 {{cloze 消费偏移量(offset)}}
		- 支持 {{cloze 消费者组(Consumer Group)}}
	- Topic 主题
		- 消息的逻辑分类单元
		- Partition 分区
			- 每个分区是 {{cloze 有序的}} 消息序列
			- 分区可以分布在不同的Broker
		- Replica 副本
			- Leader {{cloze 读写}} - Follower {{cloze 同步数据}}
	- Zookeeper
		- 集群 {{cloze 元数据}} 管理
		- Broker注册信息
		- Topic配置
		- 消费者offset
		- 控制器选举
- Kafka如何保证消息的顺序性 #card
	- 单个分区[Partition]内的消息一定是顺序的
		- 日志的有序性
		- 按append方式写入
		- 消费者按存储顺序读取
	- 生产者重试-导致消息重复发送
		- 启用 ((67e666e3-8842-4534-b702-1ec4c80a4818))
	- 多分区下的顺序策略
		- 设置合理的Key\自定义分区策略 确保相同key被发送到同一分区
			- eg.对同一订单的a、b、c操作
	- ((67e0c8e2-9091-4f40-91e2-9d313e2d8d70)) 发生-可能导致原topic分配给另外的consumer
		- eg. 消息3正在处理时,发起了重平衡. 消息3被重复消费了
		- 避免动态改变分区数
		- 业务层校验 前置消息 eg.通过自增Id 或 时间戳 或 消息依赖关系
		- 使用静态成员功能
			- 允许消费者在断开和重新连接时保持其消费者组内的身份，这可以减少因短暂的网络问题或消费者重启导致的不必要的重平衡
- Kafka的事务是如何实现的\Exactly-Once语义 #card
	- 开启单条消息的幂等性
	- 设置事务隔离级别-已提交读
	- 事务消息存储：
		- 业务消息与 ((67e665d9-a1c0-47fe-9ed6-3126d0adecce)) 同存于日志
		- 控制消息包含事务最终状态
		- 消费者自动过滤未提交消息
- Kafka如何保证消息不丢失 #card
	- 消息丢失场景
		- 生产者端丢失
			- 网络中断-消息发送后未收到Broker确认
			- 异步发送-缓冲区未刷磁盘时,进程崩溃
			- 配置不当-`acks=0 or 1` 时,Leader故障
		- Broker端丢失
			- 副本未同步-`min.insync.replicas=1`leader崩溃
			- 磁盘故障-页缓存未刷磁盘时,机器宕机
			- 日志清理-消息未消费就被日志删除策略清理
		- 消费者端丢失
			- 自动提交offset - 消费未处理完成,但已提交offset
			- Rebalance - 分区再分配时位移提交错误
			- 处理异常 - 业务逻辑出错,但offset已提交
	- 完整的不丢失方案
		- 生产者端
			- `acks=all` 所有副本确认
			- 使用同步发送
			- 开启幂等性
		-
- Kafka分区的好处 #card
  card-last-interval:: 0.14
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2024-09-02T05:59:41.285Z
  card-last-reviewed:: 2024-09-02T02:59:41.285Z
  card-last-score:: 3
	- 并行处理
	- 负载均衡
	- 多副本容错
	- partition内部消息保证顺序
- Kafka中，消费者的分区方案都有哪些 #card
  card-last-interval:: 0.14
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2024-09-02T05:53:29.294Z
  card-last-reviewed:: 2024-09-02T02:53:29.294Z
  card-last-score:: 3
	- Range Assignor: 根据 {{cloze 分区范围}} 进行分配，简单但可能导致不均衡
	- Round Robin Assignor 通过 {{cloze 轮询方式}} 分配分区，实现均衡负载
	- Sticky Assignor: 尝试保持分区分配的 {{cloze 稳定性}} ，同时考虑负载均衡
- Kafka的消息顺序性是如何保证的，如果需要全局顺序性应该如何处理？#card
  card-last-interval:: 0.14
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2024-09-02T05:52:40.998Z
  card-last-reviewed:: 2024-09-02T02:52:40.999Z
  card-last-score:: 3
	- 生产端 -- 分区键 和 唯一ID
	- Broken端 -- 幂等，单分区内的顺序性
	- 消费端-- 消费者偏移量，保证单分区内顺序消费
- 在 Kafka 中，如何确保消费者不会重复消费消息？ #card
  card-last-interval:: 0.14
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2024-09-02T05:57:20.237Z
  card-last-reviewed:: 2024-09-02T02:57:20.237Z
  card-last-score:: 3
	- 消费端 -- 手动提交偏移量 、 业务幂等处理
	- Broken端 -- 幂等+事物机制
- Kafka的性能优化策略 #card
  card-last-interval:: 0.14
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2024-09-02T06:00:59.125Z
  card-last-reviewed:: 2024-09-02T03:00:59.125Z
  card-last-score:: 3
	- 调整分区数
	- 优化生产端配置 `batch.size` 和 `linger.ms`
		- 如果我们为这个分区积累的字节数少于这个数目，我们将在`linger.ms`上“逗留”一段时间，等待更多的记录出现
	- 开启幂等特性
	- 配置合理的副本和ISR
	- JVM参数调优
	- 网络和磁盘优化
- 有哪些配置项可以提高Kafka的性能? #card
  card-last-interval:: 0.14
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2024-09-02T05:54:45.525Z
  card-last-reviewed:: 2024-09-02T02:54:45.525Z
  card-last-score:: 3
	- 批处理优化
		- `batch.size` -- 控制 生产者 批次大小。可以减少网络请求次数，但可能会增加消息延迟。
		- `linger.ms` -- 配合batchSize使用，控制延迟发送时间
		- `buffer.memory` -- 控制 消息缓冲区的大小。可以缓存更多的消息、提高吞吐量
	- 消息格式优化
		- `compression.type` -- 消息压缩格式
		- `key.serializer` 和 `value.serializer` -- kV的序列化方式
	- 消息发送优化
		- `acks` -- 定义消息的确认级别
			- =0 不等待任何确认
			- =1 等待Leader确认
			- =all 等待所有的ISR中的副本确认
		- `retries` 和 `retry.backoff.ms` -- 发送失败时的重试次数和重试间隔
	-
- 消费者故障，出现活锁问题如何解决 #card
  card-last-interval:: 0.14
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2024-09-02T05:56:46.589Z
  card-last-reviewed:: 2024-09-02T02:56:46.589Z
  card-last-score:: 3
	- MQ中的活锁通常是指，消费者处理消息的速度非常**慢**，或者在处理过程中频繁地因为等待某些条件而**暂停**
	- 优化 {{cloze 消息处理}} 逻辑
	- 设置合理的 {{cloze 超时时间}}
	- 合理配置 {{cloze 一次拉取}} 数量 `max.poll.records`
	- 引入 {{cloze 优先级}} 机制