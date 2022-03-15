- CDATA
  id:: 6230432e-a263-433d-932b-945343b5e26c
	- 术语 CDATA 指的是不应由 XML 解析器进行解析的文本数据（Unparsed Character Data）。
	- 在 XML 元素中，"<" 和 "&" 是非法的。
	- "<" 会产生错误，因为解析器会把该字符解释为新元素的开始。
	- "&" 也会产生错误，因为解析器会把该字符解释为字符实体的开始。
	- 某些文本，比如 JavaScript 代码，包含大量 "<" 或 "&" 字符。为了避免错误，可以将脚本代码定义为 CDATA。
	- CDATA 部分中的所有内容都会被解析器忽略。
	- CDATA 部分由 "<![CDATA[" 开始，由 "]]>" 结束：
		- ```xml
		  <script>
		  <![CDATA[
		  function matchwo(a,b)
		  {
		  if (a < b && a < 0) then
		    {
		    return 1;
		    }
		  else
		    {
		    return 0;
		    }
		  }
		  ]]>
		  </script>
		  ```