- 当查询发生时，Hive会用**最后一级目录作为底层处理数据的输入**
	- 目录如何被找到的？存在了Hive的元数据中.
	  ```SQL
	  select SD_ID from hive.TBLS where TBL_NAME = 'tb_login'
	  select * from hive.SDS where id = ${SD_ID}
	  ```
- 查询问题1：大表扫描
- 解决方案：使用 [[分区表]]
- 查询问题2：Join问题
	- Hive是通过 [[MapReduce]] 来实现的，在处理数据之间的join时有两种方式 MapJoin和ReduceJoin
	- 如果两张非常大的表要进行join，底层无法使用MapJoin提高join性能，只能走ReduceJoin
	- 而ReduceJoin必须经过 [[Shuffle]] 过程，相对性能较差，而且容易产生数据倾斜
- 解决方案：使用 [[分桶表]]
-