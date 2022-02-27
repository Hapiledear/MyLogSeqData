- DDL中，指定文件格式.默认TextFile
	- ```SQL
	  stored As file_format
	  ```
- 文件格式-- TextFile
	- 默认存储格式，按行存储。
	- 工作和生活中最常见的数据文件格式。
	- 在导入时，Hive只是把数据copy到对应目录中，不做任何操作。
- 文件格式-- SequenceFile
	- kv形式存储的，二进制文件
	- 是 [[MapReduce]] 的直接输入输出。
	- 使用` insert into  ... select` 导入
- 文件格式--Parquet
	- 列式存储，