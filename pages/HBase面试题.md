- {{cards [[HBase面试题]] }}
- 项目中有哪些场景下使用了HBase #card
  card-last-interval:: -1
  card-repeats:: 1
  card-ease-factor:: 1.3
  card-next-schedule:: 2025-03-27T16:00:00.000Z
  card-last-reviewed:: 2025-03-27T05:57:49.469Z
  card-last-score:: 1
	- 日志记录
	- 超过亿级的表数据
	- 帖子和评论 -- 无固定且需要扩展的列
- 项目中的rowkey是如何设计的 #card
  card-last-interval:: 0.23
  card-repeats:: 2
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T06:22:09.183Z
  card-last-reviewed:: 2024-08-14T01:22:09.183Z
  card-last-score:: 3
	- 倒序取模，使得频繁更改的地方在最后
	- userId + Long.Max - currentTimeStamp 便于scan查询，最近的记录在前
- 简述HBase的读和写流程 #card
  card-last-interval:: 0
  card-repeats:: 8
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-08-14T01:15:16.393Z
  card-last-reviewed:: 2024-08-14T01:15:16.394Z
  card-last-score:: 3
	- 读流程
		- {{cloze zk}} 获取 {{cloze Meta信息和RegionServer}}
		- 连接 {{cloze RegionServer}} {{cloze BlockCache}} -> {{cloze MemStore}} -> {{cloze StoreFile}} 中查询
	- 写流程
		- {{cloze zk}} 获取 {{cloze meta信息 和 RegionServer}}
		- 双写 {{cloze 先写入HLog (write ahead log)，再写 MemStore}}
			- {{cloze HLog用于宕机恢复和故障转移}}
		- flush {{cloze 当MemStore写入数据到达一个阈值时，写入磁盘文件 StoreFile}}
			- 小文件合并 {{cloze 多个小的storefile进行合并操作，同时进行版本合并和数据删除}}
			- 大文件拆分 {{cloze storeFile达到一定阈值会进行拆分。原Region下线，新的2个Region会被分配到不同的RegionServer}}
-