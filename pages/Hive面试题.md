- 常见的SQL调优方法 #card
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2023-10-28T01:47:51.972Z
  card-last-reviewed:: 2023-10-24T01:47:51.972Z
  card-last-score:: 3
	- Map端优化（无shuffle）
		- 分区剪裁-- 过滤条件中使用分区匹配
			- 分区过滤能显著降低查询的数据量，不然会扫描全表数据。
		- 列裁剪-- select 只读取需要的字段
			- 对于读取列式存储文件，剪枝操作会有较大性能提升，减少了读取数据量。
		- 行裁剪 -- 谓词下推
			- 过滤条件放在前面，减少读取的数据量
		- like用正则替换
			- 多个like语句合并成一个正则并时候 regexp 函数
	- Reduce端优化 (有shuffle)
		- join
			- 右表的过滤条件放on中 >关联后where
			- 大表 join 小表
		- group by
			- 减少group by的字段数量
			- 将distinct数据量大的列放在前面
		- order by
			- 尽量不使用Order by,使用时加上limit
			- 如果需要按照多个字段order by，可以将排序操作放在ADS层数据，使用Spark排序，获取rn（序号），这样就可以使用presto只根据rn1个字段排序
		- distinct
			- 消耗资源较大。可考虑用近似聚合approx_distinct()代替。
		- union
			- 如没有去重需求，使用union all代替union。
	-
-