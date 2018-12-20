input file:
 first line ：T,L,n, T is the total service time，L is the number of berths，n is the number of vessels,T is the 
 next m lines: the information of each vessels
               each line contains three variables
	       ai,ti,bi  
	       which means Vessel i arrives at time ai with its service 
               time ti hours and it occupies bi berths
对于每一个case
第一行有3个输入，分别是港口服务总时长 T，港口允许停船的最大长度 L，以及要停船的船舶数量 N
接下来有N行输入，每一行的输入分别为第i艘船的到达时间，服务时间，以及占用的船位宽度

output file:
  n lines ,each line contains two variables: 
	bi si
  which means vessel i load starts on berth bi,time si
  the last line contains three variables:
    total waiting time,last departure time,f(x)

按读入顺序输出每艘船的开始停靠位置bi，开始停靠时间si
最后依次输出total waiting time
            last departure time
            f(x)



