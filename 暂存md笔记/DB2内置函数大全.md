DB2 内置函数大体分为以下几类：

```
1.聚合函数   

2.类型转换函数   

3.数学函数   

4.字符串函数   

5.日期时间函数   

6. XML  函数   

7.分区函数   

8.安全函数   

9.其他   
```



# 一、聚合函数

| 函数    | 功能描述       |
| ------- | -------------- |
| count() | 返回查询记录数 |
| sum()   | 返回合计值     |
| avg()   | 返回平均值     |
| max()   | 返回最大值     |
| min()   | 返回最小 值    |







# 二、类型转换函数



DB2 为每种数据类型都提供了相应的函数，一般情况下它们之间的相互转换是非常简单的，请看下表：

| 函数               | 功能描述                              |
| ------------------ | ------------------------------------- |
| SMALLINT           | 返回 SMALLINT 类型的值                |
| INTEGER            | 返回 INTEGER 类型的值                 |
| BIGINT             | 返回 BIGINT 类型的值                  |
| DECIMAL            | 返回 DECIMAL 类型的值                 |
| REAL               | 返回 REAL 类型的值                    |
| DOUBLE             | 返回 DOUBLE 类型的值                  |
| FLOAT              | 返回 FLOAT 类型的值                   |
| CHAR               | 返回 CHARACTER 类型的值               |
| VARCHAR            | 返回 VARCHAR 类型的值                 |
| VARCHAR_FORMAT_BIT | 将位字符序列格式化为 VARCHAR 类型返回 |
| VARCHAR_BIT_FORMAT | 将格式化后位字符序列返回到格式化前    |
| LONG_VARCHAR       | 返回 LONG VARCHAR 类型的值            |
| CLOB               | 返回 CLOB 类型的值                    |
| GRAPHIC            | 返回 GRAPHIC 类型的值                 |
| VARGRAPHIC         | 返回 VARGRAPHIC 类型的值              |
| LONG_VARGRAPHIC    | 返回 LONG VARGRAPHIC 类型的值         |
| DBCLOB             | 返回 DBCLOB 类型的值                  |
| BLOB               | 返回 BLOB 类型的值                    |
| DATE               | 返回 DATE 类型的值                    |
| TIME               | 返回 TIME 类型的值                    |
| TIMESTAMP          | 返回 TIMESTAMP 类型的值               |





# 三、数学函数

| 函数              | 功能描述                                                 |
| ----------------- | -------------------------------------------------------- |
| ABS,ABSVAL        | 返回参数的绝对值                                         |
| SIGN              | 如果参数大于 0 则返回 1 ，小于 0 返回 -1 ，等于 0 返回 0 |
| RAND              | 返回 0 和 1 之间的随机浮点数                             |
| MOD               | 求余数                                                   |
| ROUND             | 返回参数 1 小数点右边的第参数 2 位置处开始的四舍五入值   |
| TRUNCATE OR TRUNC | 从表达式小数点右边的位置开始截断并返回该数值             |
| FLOOR             | 返回小于或等于参数的最大整数                             |
| CEILING OR CEIL   | 返回大于或等于参数的最小的整数值                         |
| POWER             | 返回参数 1 的参数 2 次幂                                 |
| SQRT              | 返回该参数的平方根                                       |
| DIGITS            | 返回参数绝对值的字符串表示                               |
| MULTIPLY_ALT      | 返回参数的乘积                                           |
| DEGREES           | 求角度                                                   |
| RADIANS           | 将度转换为弧度                                           |
| SIN               | 正弦函数                                                 |
| SINH              | 双曲线正弦函数                                           |
| ASIN              | 反正弦函数                                               |
| COS               | 余弦函数                                                 |
| COSH              | 双曲线余弦函数                                           |
| ACOS              | 反余弦函数                                               |
| TAN               | 正切函数                                                 |
| TANH              | 双曲线正切函数                                           |
| ATAN              | 反正切函数                                               |
| ATANH             | 双曲线反正切函数                                         |
| ATAN2             | 反正切函数                                               |
| COT               | 余切函数                                                 |
| LN                | 返回参数的自然对数                                       |
| LOG               | 返回参数的自然对数                                       |
| LOG10             | 返回基于 10 的自然对数                                   |
| EXP               | 返回参数的指数函数                                       |





# 四、字符串函数

| 函数                          | 功能描述                                       |
| ----------------------------- | ---------------------------------------------- |
| ASCII                         | 将字符转化为 ASCII 码                          |
| CHR                           | 将 ASCII 码转化为字符                          |
| STRIP                         | 删除字符串开始和结尾的空白字符或其他指定的字符 |
| TRIM                          | 删除字符串开始和结尾的空白字符或其他指定的字符 |
| LTRIM                         | 删除字符串开始的空白字符                       |
| RTRIM                         | 删除字符串尾部的空白字符                       |
| LCASE or LOWER                | 返回字符串的小写                               |
| UCASE OR UPPER                | 返回字符串的大写                               |
| SUBSTR                        | 返回子串                                       |
| SUBSTRING                     | 返回子串                                       |
| LEFT                          | 返回开始的 N 个字符                            |
| RIGHT                         | 返回结尾的 N 个字符                            |
| POSITION                      | 返回参数 2 在参数 1 中的第一次出现的位置       |
| POSSTR                        | 返回参数 2 在参数 1 中的第一次出现的位置       |
| LOCATE                        | 返回参数 2 在参数 1 中的第一次出现的位置       |
| SPACE                         | 返回由参数指定的长度 , 包含空格在内的字符串    |
| REPEAT                        | 返回参数 1 重复参数 2 次后的字符串             |
| CONCAT                        | 连接两个字符串                                 |
| INSERT                        | 向指定字符串添加字符串                         |
| REPLACE                       | 替换字符串                                     |
| TRANSLATE                     | 将字符串中的一个或多个字符替换为其他字符       |
| CHARACTER_LENGTH              | 返回字符串的长度                               |
| OCTET_LENGTH                  | 返回字符串的字节数                             |
| ENCRYPT                       | 对字符串加密                                   |
| DECRYPT_BIN and DECRYPT_CHARs | 对加密后的数据解密                             |
| GETHINT                       | 返回密码提示                                   |
| GENERATE_UNIQUE               | 生成唯一字符序列                               |





# 五、日期时间函数

| 函数             | 功能描述                                                     |
| ---------------- | ------------------------------------------------------------ |
| YEAR             | 返回日期的年部分                                             |
| MONTH            | 返回日期的月部分                                             |
| DAY              | 返回日期的日部分                                             |
| HOUR             | 返回日期的小时部分                                           |
| MINUTE           | 返回日期的分钟部分                                           |
| SECOND           | 返回日期的秒部分                                             |
| MICROSECOND      | 返回日期的微秒部分                                           |
| MONTHNAME        | 返回日期的月份名称                                           |
| DAYNAME          | 返回日期的星期名称                                           |
| QUARTER          | 返回指定日期是第几季度                                       |
| WEEK             | 返回当前日期是一年的第几周，每周从星期日开始                 |
| WEEK_ISO         | 返回当前日期是一年的第几周，每周从星期一开始                 |
| DAYOFWEEK        | 返回当前日期是一周的第几天，星期日是 1                       |
| DAYOFWEEK_ISO    | 返回当前日期是一周的第几天，星期一是 1                       |
| DAYOFYEAR        | 返回当前日期是一年的第几天                                   |
| DAYS             | 返回用整数表示的时间，用来求时间间隔                         |
| JULIAN_DAY       | 返回从 January 1, 4712 B.C(Julian date calendar) 到指定日期的天数 |
| MIDNIGHT_SECONDS | 返回午夜到指定时间的秒数                                     |
| TIMESTAMPDIFF    | 返回两个 timestamp 型日期的时间间隔                          |
| TIMESTAMP_ISO    | 返回 timestamp 类型的日期                                    |
| TO_CHAR          | 返回日期的字符串表示                                         |
| VARCHAR_FORMAT   | 将日期格式化为字符串                                         |
| TO_DATE          | 将字符串转化为日期                                           |
| TIMESTAMP_FORMAT | 将字符串格式化为日期                                         |





# 六、 XML 函数



DB2 UDB Version 8.2 支持七种 SQL/XML 发布函数：

| 函数          | 功能描述                                                     |
| ------------- | ------------------------------------------------------------ |
| XMLSERIALIZE  | 将 XML 值转化成存储为 CHAR、VARCHAR 或 CLOB 值的字符串       |
| XMLELEMENT    | 构造一个命名的 XML 元素节点                                  |
| XMLFOREST     | 构造一个 XML 元素节点序列(森林)                              |
| XMLATTRIBUTES | 为 XML 元素节点构造一个或多个 XML 属性节点                   |
| XMLCONCAT     | 生成 XML 表格数据                                            |
| XMLAGG        | 在生成的 XML 值中将 XML 值聚合为一系列的项。XMLAGG 是一种聚合(列)函数 |
| XMLNAMESPACES | 从参数中构造 XML 名称空间声明                                |





# 七、分区函数

| 函数             | 功能描述                                    |
| ---------------- | ------------------------------------------- |
| DATAPARTITIONNUM | 返回数据分区中的序列号                      |
| DBPARTITIONNUM   | 返回行的分区号                              |
| HASHEDVALUE      | 返回行的 distribution map index (0 to 4095) |





# 八、安全函数

| 函数             | 功能描述             |
| ---------------- | -------------------- |
| SECLABEL         | 返回未命名的安全标签 |
| SECLABEL_BY_NAME | 返回具体的安全标签   |
| SECLABEL_TO_CHAR | 返回标签的所有元素   |





# 九、其他

| 函数               | 功能描述                                                     |
| ------------------ | ------------------------------------------------------------ |
| COALESCE           | 将 null 转化为其他值                                         |
| VALUE              | 将 null 转化为其他值                                         |
| NULLIF             | 如果两个参数相等，则返回 null ，否则，返回第一个参数         |
| HEX                | 返回一个值的 16 进制表示                                     |
| LENGTH             | 返回一个值的长度                                             |
| TABLE_NAME         | 返回 table 名                                                |
| TABLE_SCHEMA       | 返回 schema 名                                               |
| TYPE_ID            | 返回数据类型表示                                             |
| TYPE_NAME          | 返回数据类型名                                               |
| TYPE_SCHEMA        | 返回 schema 名                                               |
| DEREF              | 返回参数类型的实例                                           |
| IDENTITY_VAL_LOCAL | 返回最后分配给标识列的值                                     |
| REC2XML            | 返回 XML 标记格式的字符串，包含列名和列数据                  |
| EVENT_MON_STATE    | 返回某事件监视器的操作状态                                   |
| RAISE_ERROR        | 抛出错误，可以指定 sqlstate 和 error_message, 有点像 java 的抛出异常 |

