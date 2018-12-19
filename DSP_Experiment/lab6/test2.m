clc
clear all
a=[1,-1,1];
b=[1,-0.5,0];
N=32;
n=0:N-1;
hn=impz(b,a,n);%求时域单位冲激响应
gn=dstep(b,a,n);%求时域单位阶跃响应
subplot(1,2,1),stem(n,hn);%显示冲激响应曲线
title('系统的单位冲激响应');
ylabel('h(n)');xlabel('n');
axis([0,N-1,1.1*min(hn),1.1*max(hn)]);
n=0:N-2;
subplot(1,2,2),stem(n,gn);%显示阶跃响应曲线
title('系统的单位阶跃响应');
ylabel('g(n)');xlabel('n');
axis([0,N-1,1.1*min(gn),1.1*max(gn)]);

a=[1,-1,1];
b=[1,-0.5,0];
x01=0;y01=0;N=32;
xi=filtic(b,a,0);%求等效初始条件的输入序列
n=0:N-1;    %建立N点的时间序列
x1=[n==0];   %建立输入单位冲激信号x1(n)
hn=filter(b,a,x1,xi);%对输入单位冲激信号进行滤波，求冲激响应
x2=[n>=0];%建立输入单位阶跃信号x2(n)
gn=filter(b,a,x2,xi);%对输入单位阶跃信号进行滤波，求阶跃响应
figure
subplot(1,2,1),stem(n,hn);
title('系统单位冲激响应');
ylabel('h(n)');xlabel('n');
axis([0,N-1,1.1*min(hn),1.1*max(hn)]);
subplot(1,2,2),stem(n,gn);
title('系统单位阶跃响应');
ylabel('g(n)');xlabel('n');
axis([0,N-1,1.1*min(gn),1.1*max(gn)]);
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
clc
clear all
m=[0,1,0,0,0;0.5,-8,0.5,-0.5,-1]
m=mapminmax(m)   %归一化处理
a=m(1:1,1:5).*(1/m(1,1))
b=m(2:2,1:5).*(1/m(1,1))
N=32;
n=0:N-1;
hn=impz(b,a,n);%求时域单位冲激响应
gn=dstep(b,a,n);%求时域单位阶跃响应
figure;
subplot(1,2,1),stem(n,hn);%显示冲激响应曲线
title('系统的单位冲激响应');
ylabel('h(n)');xlabel('n');
axis([0,N-1,1.1*min(hn),1.1*max(hn)]);
n=0:N-2;
subplot(1,2,2),stem(n,gn);%显示阶跃响应曲线
title('系统的单位阶跃响应');
ylabel('g(n)');xlabel('n');
axis([0,N-1,1.1*min(gn),1.1*max(gn)]);

m=[0,1,0,0,0;0.5,-8,0.5,-0.5,-1]
m=mapminmax(m)     %归一化处理
a=m(1:1,1:5).*(1/m(1,1))
b=m(2:2,1:5).*(1/m(1,1))
x01=0;y01=0;N=32;
xi=filtic(b,a,0);%求等效初始条件的输入序列
n=0:N-1;    %建立N点的时间序列
x1=[n==0];   %建立输入单位冲激信号x1(n)
hn=filter(b,a,x1,xi);%对输入单位冲激信号进行滤波，求冲激响应
x2=[n>=0];%建立输入单位阶跃信号x2(n)
gn=filter(b,a,x2,xi);%对输入单位阶跃信号进行滤波，求阶跃响应
figure
subplot(1,2,1),stem(n,hn);
title('系统单位冲激响应');
ylabel('h(n)');xlabel('n');
axis([0,N-1,1.1*min(hn),1.1*max(hn)]);
subplot(1,2,2),stem(n,gn);
title('系统单位阶跃响应');
ylabel('g(n)');xlabel('n');
axis([0,N-1,1.1*min(gn),1.1*max(gn)]);