# CS：客户端服务器架构模式

*优点：*充分利用客户端机器的资源，减轻服务器的负荷（一部分安全要求不高的计算任务存储任务放在客户端进行，不需要把所有的计算和存储都放在服务器执行，从而能够减轻服务器的压力，也能够减轻网络负荷）

***

*缺点：*需要安装，升机维护成本较高

<br/>

<br/>

# BS：浏览器服务器架构模式

*优点：*客户端不需要安装，维护成本较低

***

*缺点：*所有的计算和存储任务都是放在服务器端的，服务器的负荷较重，在服务器端计算完成之后把结果再传输给客户端，因此客户端和服务器端会进行非常频繁的数据通信，从而网络负荷较重。