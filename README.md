## Netty技术指南

### 一、Netty简介
Netty：异步的，基于事件驱动和Java NIO的网络通讯框架。

|  Netty框架 |
|  ----  | 
| Java NIO | 
| JDK原生 |
| TCP/IP |

### 二、Netty的使用场景

- RPC分布式调用，例如：Dubbo、Spring Flux   
- 游戏网络通讯,手游、端游等
- 大数据领域，Avro，Akka、Flink、Spark等

### 三、Java的IO模型    

- Java BIO 同步阻塞式IO，一个线程处理一个连接，没有请求就一直线程阻塞，基于Socket和InputStream\OutputStream，JDK1引入，适用于连接比较少且固定的架构，可以通过线程池方式改善连接数
- Java NIO 同步非阻塞式IO，一个线程处理多个连接，多个请求注册到多路复用器
上，多路复用器轮询到IO上有请求就进行处理。基于Selector和Channel，JDK4引入，适用于连接多且连接比较短
- Java AIO 异步非阻塞IO，引入异步通道采用Proactor模式，由操作系统完成后通知服务启动线程处理，JDK7开始引入，适用于连接多且连接比较长

### 四、NIO的核心组件   

-  Selector（注册器）  
    一个线程监听一个Selector，一个Selector可以监听多个Channel，处理哪个Channel由Event(事件)决定的
-  Channel（连接）
    每个Channel都对应有一个Buffer，是双向的，可返回底层操作系统情况,例如Linux底层操作系统就是双向的。
-  Buffer（缓冲区，内存块）
    一个客户有一个Buffer ，本质是一个数组 ，面向缓冲或者面向块编程的。

### 五、Buffer缓冲区

1.  Buffer接口的7个实现类：
    ByteBuffer： 最常用的
    CharBuffer
    IntBuffer
    ShortBuffer
    FloatBuffer
    LongBuffer
    DoubleBuffer

2. Buffer核心的4个成员变量    
    作用：描述数据元素的信息     
    private int mark = -1; //标记    
    private int position = 0;//位置，下一个要被读或写元素的索引    
    private int limit;//表示缓冲区的当前终点，读写不可用超过极限，极限可修改     
    private int capacity; //容量，在缓冲区创建时设置不可变    

    大小关系：mark <= position <= limit <= capacity

3. Buffer的核心方法  
```
    //容量
     public final int capacity() {
        return capacity;
    }
    //位置    
    public final int position() {
        return position;
    }
    //设置位置    
    public final Buffer position(int newPosition) {
        if ((newPosition > limit) || (newPosition < 0))
            throw new IllegalArgumentException();
        position = newPosition;
        if (mark > position) mark = -1;
        return this;
    }
    //限制    
    public final int limit() {
        return limit;
    }
    //设置限制
    public final Buffer limit(int newLimit) {
        if ((newLimit > capacity) || (newLimit < 0))
            throw new IllegalArgumentException();
        limit = newLimit;
        if (position > limit) position = limit;
        if (mark > limit) mark = -1;
        return this;
    }
    //重置    
    public final Buffer reset() {
        int m = mark;
        if (m < 0)
            throw new InvalidMarkException();
        position = m;
        return this;
    }
    //反转缓冲区，读写反转
     public final Buffer flip() {
        limit = position;
        position = 0;
        mark = -1;
        return this;
    }
    //清除缓冲区，标记恢复初始状态，但数据并未清除
    public final Buffer clear() {
        position = 0;
        limit = capacity;
        mark = -1;
        return this;
    }  
    //判断当前位置和限制位置之间是否还有数据 
    public final boolean hasRemaining() {
        return position < limit;
    }   
    //是否只读 
    public abstract boolean isReadOnly();
    //返回底层数据数组
    public abstract Object array();
    //是否可访问底层数据数组
    public abstract boolean hasArray();
```

### 六、Channel通道
- 对比
|项目|Stream|Channel|
|---|---|---|
|读和写|只能读或者写|可同时读写|
|同异步|同步|异步|
|流和缓冲|面向流|面向缓冲区，可以双向读写|
-  实现
接口：Channel 继承 Closeable接口    
Channel 主要实现类：
    SocketChannel->SocketChannelImpl
    ServerSocketChannel->ServerSocketChannelImpl    
    FileChannel->文件读写FileChannelImpl    
    DateGramChannel->UDP读写DateGramChannelImpl
