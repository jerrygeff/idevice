## 编码规范
#### 1.【强制】包名统一使用小写，点分隔符之间有且仅有一个自然语义的英语单词。包名统一使用单数形 式，但是类名如果有复数含义，类名可以使用复数形式。 正例：应用工具类包名为 com.alibaba.ei.kunlun.aap.util；类名为 MessageUtils（此规则参考 spring 的框架结构）。

#### 2.【强制】避免在子父类的成员变量之间、或者不同代码块的局部变量之间采用完全相同的命名，使可理解性降低。

#### 3.【强制】杜绝完全不规范的英文缩写，避免望文不知义。 反例：AbstractClass“缩写”成 AbsClass；condition“缩写”成 condi；Function“缩写”成 Fu，此类随意缩写 严重降低了代码的可阅读性。

#### 4.【推荐】为了达到代码自解释的目标，任何自定义编程元素在命名时，使用完整的单词组合来表达。 正例：在 JDK 中，对某个对象引用的 volatile 字段进行原子更新的类名为 AtomicReferenceFieldUpdater。 反例：常见的方法内变量为 int a; 的定义方式。

#### 5.【推荐】在常量与变量命名时，表示类型的名词放在词尾，以提升辨识度。 正例：startTime / workQueue / nameList / TERMINATED_THREAD_COUNT
反例：startedAt / QueueOfWork / listName / COUNT_TERMINATED_THREAD

#### 6.【推荐】接口类中的方法和属性不要加任何修饰符号（public 也不要加），保持代码的简洁性，并加上 有效的 Javadoc 注释。尽量不要在接口里定义常量，如果一定要定义，最好确定该常量与接口的方法 相关，并且是整个应用的基础常量。

#### 7.接口和实现类的命名规则：【强制】对于 Service 和 DAO 类，基于 SOA 的理念，暴露出来的服务一定是接口，内部的实现类用 Impl 的后缀 与接口区别。 正例：CacheServiceImpl 实现 CacheService 接口。

#### 8.枚举类名带上 Enum 后缀，枚举成员名称需要全大写，单词间用下划线隔开。 说明：枚举其实就是特殊的常量类，且构造方法被默认强制是私有。

#### 9.Service接口必须有注释

#### 10.【参考】各层命名规约： A）Service /dao层方法命名规约：
* 获取单个对象的方法用 get 做前缀,User getUser(int id) {throw Ex};
* 获取多个对象的方法用 find做前缀，复数结尾，如：findUserPage() ,findUserList()
* 获取统计值的方法用 count 做前缀, Int countRegisterUser();
* 插入的方法用 add 做前缀，void add()
* 删除的方法用delete/做前缀，delete
* 修改的方法用 update 做前缀, update
* idea插件：Alibaba Java Coding Guidelines，SonarLint

#### 各层实体对象说明
* controller 涉及vo
  入参、出参都是vo
* application 涉及vo/bo
  入参、出参都是vo。调用service/manager时，需要转换成对应的bo
* service/manager 涉及bo/entity/qo
  入参、出参都是bo。调用其他service/manager时，转换成对应的bo；调用repository时，转换成entity/qo
* repository 涉及entity/qo
  入参、出参是entity或qo

#### 【强制】禁止Service继承mybatisPlus的BaseService，禁止使用Querywrapper写法