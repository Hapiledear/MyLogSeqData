# MyLogSeqData
!! 同步时先pull 后打开软件 !!
## 介绍
使用Logseq来记录的笔记数据

## 目录介绍
aaa

## 使用说明

### Logseq的内容组织形式

页面\块\标签

一个页面是由多个块(段落)和缩进组成的,每个段落后都可以添加任意数量的标签

1.  `[[aaa]]` 用来引用标题为aaa的页面
2.  `((bbb))` 用来引用内容含有bbb的块
3.  在任意块后添加`#ccc` 可以把内容打上ccc的标签

所有这些操作,都会自动建立双向链接.

#### 页面和标签
用标签:
- 提炼和概括内容
- 提供某些线索

标签打开之后,正文没有东西,只是下面出现相关引用内容.

用页面:
- 页面引用出现的正文
- 承载和罗列内容


### 全局查询

快捷键 `Ctrl + k`

P=Page

B=Block

Random Access = 全局(包括正文内容)查询

Context Access = 在上下文网络中查询

#### Query命令
- and
- or
- not
- between

步骤如下:
1. 新建页面
2. `/query = {{query (and [research] [idea])}}` research 和 idea是标签 或 页面
3. 后续在`日记`中添加了相关信息后,会自动的添加到该页面.

### 连接Zotero

设置->Zotero Settings->Zotero API Key

## 与卡片笔记法的对应关系

journals=闪念笔记,内容包括:
- 闪现在脑中的想法
- 值得记录的事情
- 在书上的划线或评论

Zotero=文献笔记 如果不是写论文,这东西其实不重要
pages =永久笔记,内容由闪念笔记整理而来,且新加入的卡片需要与现有卡片关联起来.
