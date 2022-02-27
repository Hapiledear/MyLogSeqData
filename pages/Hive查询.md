- 当查询发生时，Hive会用**最后一级目录作为底层处理数据的输入**
	- 目录如何被找到的？存在了Hive的元数据中.
	  ```SQL
	  select SD_ID from hive.TBLS where TBL_NAME = 'tb_login'
	  select * from hive.SDS where id = ${SD_ID}
	  ```
-