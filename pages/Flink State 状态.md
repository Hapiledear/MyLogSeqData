- State是Flink中的顶级概念.用于**保存中间计算结果**或者**缓存数据**
	- eg. 需要keep之前的数据,算最近一个小时内的总PV,这些数据就是stateful(有状态的),它所对应的计算就是有状态计算
- 如果计算需要依赖于之前或者后续的事件，则是有状态计算.
	- 每个算子 [[Flink StreamOperator]] 都有自己的State,对于同一Task是共享的,不同Task不共享.
- State是实现有状态计算下的Exactly-Once的基础 [[Flink 应用容错]]
- 如何使用状态?
	- 需要自定义RichFunction 或 ProcessFunction [[Flink UDF]]
	- code demo https://blog.csdn.net/shufangreal/article/details/104686064
- 使用场景
	- Sum 求和
	- 去重
	- [[Flink CEP]]中的模式检测
		- 处理事件的规则，被叫作模式（Pattern）
- 状态类型
	- keyed state
		- valueState 单个值
		- listState \UnionListState 一组值
		- mapState k-v对
		- reducing \ aggregating state 聚合操作,最终保存单个值
	- non-keyed state
		- listState
		- broadcastState 广播状态
			- 来自一个流的数据需要被广播到所有下游任务.eg 规则下发
- 状态访问
	- 只能访问上述State,而无法访问内部State(InternalXxxState)
	- 通过`OperatorStateStore` 和`KeyedStateStore`访问存储的数据,其本质是包装了一层`StateBackend`
- 状态存储
	- State 的存储在Flink 中叫作`StateBackend`,内置了3种`StateBackend`
		- `MemoryStateBackend` 纯内存|适用于验证\测试 [[Flink 内存管理]]
		- `FsStateBackend` 内存+文件|适用于长周期大规模数据
			- 可以使用分布式文件系统或本地文件系统
		- `RocksDBStateBackend`  [[RocksDB]]| 适用于长周期大规模数据
	- 全量持久化策略`RocksFullSnapshotStratey`
		- 适用于所有的存储
	- 增量持久化策略`RocksIncementalSnapshotStrategy`
		- 只适用于RocksDB
- 状态重分布
- 状态过期清理
	- 通过配置过期时间进行清理`StateTtlConfig`,可设置内容如下
		- 过期时间: 超过多长时间未访问，视为State过期
		- 更新策略:创建和写时更新、读取和写时更新
		- 过期可见性: 未清理可用，超期则不可用
	- ```scala
	  // 设置ttl的配置
	  val ttlConfig = StateTtlConfig
	      .newBuilder(Time.seconds(1))
	      .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite)
	      .setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired)
	      .build
	   //声明状态描述器   
	  val stateDescriptor = new ValueStateDescriptor[String]("text state", classOf[String])
	  //state.enableTimeToLive(ttl配置)
	    stateDescriptor.enableTimeToLive(ttlConfig)
	  ```
-