- {{cards [[Kafka面试题]]}}
- Kafka分区的好处 #card
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