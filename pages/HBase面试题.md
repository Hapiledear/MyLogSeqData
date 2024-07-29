- {{cards [[HBase面试题]] }}
- 项目中有哪些场景下使用了HBase #card
  card-last-interval:: 0.01
  card-repeats:: 4
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-07-29T06:36:57.309Z
  card-last-reviewed:: 2024-07-29T06:36:57.310Z
  card-last-score:: 3
	- 日志记录
	- 超过亿级的表数据
	- 帖子和评论 -- 无固定且需要扩展的列
- 项目中的rowkey是如何设计的 #card
  card-last-interval:: 0.01
  card-repeats:: 4
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-07-29T06:32:42.315Z
  card-last-reviewed:: 2024-07-29T06:32:42.315Z
  card-last-score:: 3
	- userId倒序取模
	- userId + Long.Max - currentTimeStamp 便于scan查询，最近的记录在前
- 简述HBase的读和写流程 #card
  card-last-interval:: 0.2
  card-repeats:: 2
  card-ease-factor:: 1.3
  card-next-schedule:: 2024-07-29T12:09:17.991Z
  card-last-reviewed:: 2024-07-29T08:09:17.991Z
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