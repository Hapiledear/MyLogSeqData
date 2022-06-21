- 在Watermark 之前的数据,虽然不能保证顺序,但一定能保证它们一个都不少.
	- Watermark 本质是时间戳，与业务数据一样无差别地传递下去，目的是衡量事件时间的进度
	- Watermark(T) 表示目前系统的时间事件是 T，即系统后续没有 T'<T 的事件即 Event(T'<T)
- ```java
  DataStream<Tuple2<String, Integer>> dataStream =
  	env.socketTextStream("localhost",9999,"\n")
  		.assignTimestampsAndWatermarks(WatermarkStrategy.forBoundedOutOfOrderness(Duration.ofMillis(10)))
  ```
- WatermarkOperator类型关系
	- ((62b171e7-cc34-45b9-9fdd-4340c6281eaf))
- Watermark的产生&传递&消费
	- 产生相关代码在 `TimestampsAndWatermarksOperator.onProcessingTime()`中,最终调用的是`WatermarkGenerator.onPeriodicEmit()` 并发送出去
	- 消费相关代码在`TimestampsAndWatermarksOperator.processElement()`中,最终调用的是`WatermarkGeneratoronEvent()` 将ts置为`Math.max(maxTimestamp, eventTimestamp)`
	- 阻断上游watermark 在`TimestampsAndWatermarksOperator.processWatermark()` ,将时间置为 `Long.MAX_VALUE)`
	- 发送watermark在 `Output.emitWatermark()`中,发送给下游
		- 问题: input和output是如何关联起来的?这里应该指向flink的作业调度页面 #问题
	- 下游window接收并\处理\传递watermark,在`AbstractStreamOperator.processWatermark()`中
- Watermark类
	-
-
- 相关链接
	- [[Flink Time 时间]]
-