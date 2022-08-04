- TaskSlot 产生的问题
	- 1. 每个slot的内存都相同 
	  2. 仅对内存进行了隔离,未对CPU进行隔离
	- 那么,有的算子计算时内存需求大，有些算子内存需求小,就会导致资源没有充分利用.
- 共享槽 SlotSharingGroup
	- 通过设置 slot sharing grop 解决 内存没有充分利用 的问题
	- 算子的默认group为default，所有任务可以共享同一个slot
	- 如果下游算子没有设置分组，它继承上游算子的分组
	- 用户可以通过编码,强制指定 SlotSharingGroup `someStream.filter(...).slotSharingGroup("group1")`
-