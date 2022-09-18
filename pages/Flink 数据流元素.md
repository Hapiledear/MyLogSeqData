- 一条数据流包含4种元素
	- StreamRecord: 数据流中的一条记录
	- Latency Marker: 估计数据在整个DAG图中流转花费的时间，用来近似地
	  评估总体上的处理延迟。
	- Watermark: 用来告诉算子所有时间早于等于
	  Watermark的事件或记录都已经到达
	- StreamStatus:用来通知Task是否会继续接收到上游的记录或者Watermark
- 4种数据流元素都被序列化成二进制数据，形成混
  合的数据流,在使用时将其反序列化出来
-