- {{cards [[HBase面试题]] }}
- 项目中有哪些场景下使用了HBase #card
  card-last-interval:: -1
  card-repeats:: 1
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-07-22T16:00:00.000Z
  card-last-reviewed:: 2024-07-22T08:20:38.737Z
  card-last-score:: 1
	- 日志记录
	- 超过亿级的表数据
	- 帖子和评论 -- 无固定且需要扩展的列
- 项目中的rowkey是如何设计的 #card
  card-last-interval:: 27.63
  card-repeats:: 8
  card-ease-factor:: 1.38
  card-next-schedule:: 2023-11-05T22:59:27.266Z
  card-last-reviewed:: 2023-10-09T07:59:27.267Z
  card-last-score:: 3
	- userId倒序取模
	- userId + Long.Max - currentTimeStamp 便于scan查询，最近的记录在前
- 简述HBase的读和写流程 #card
  card-last-interval:: 27.63
  card-repeats:: 8
  card-ease-factor:: 1.38
  card-next-schedule:: 2023-11-05T22:41:40.803Z
  card-last-reviewed:: 2023-10-09T07:41:40.803Z
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