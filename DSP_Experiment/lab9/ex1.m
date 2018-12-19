clc
clear all
xn1=[0,1,2,4]; %建立xn1序列
xn2=[1,0,1,0,1];%建立xn2序列
N1=length(xn1);%求xn1的长度
N2=length(xn2);%求xn2的长度
N=max(N1,N2);%确定N
if N1>N2
    xn2=[xn2,zeros(1,N1-N2)];%对长度短的序列补0
elseif N2>N1
    xn1=[xn1,zeros(1,N2-N1)];
end
yn=2*xn1+3*xn2;%计算yn
n=0:N-1;
k=0:N-1;
Yk1=yn*(exp(-j*2*pi/N)).^(n'*k);%求yn的N点DFT
Xk1=xn1*(exp(-j*2*pi/N)).^(n'*k);%求xn1的N点DFT
Xk2=xn2*(exp(-j*2*pi/N)).^(n'*k);%求xn2的N点DFT
Yk2=2*Xk1+3*Xk2;%由Xk1、Xk2求Yk
subplot(2,1,1);stem(n,Yk1); %画出Yk1
axis([-1,N,-12,30]);
title('Yk1');
subplot(2,1,2);stem(n,Yk2); %画出Yk1
axis([-1,N,-12,30]);
title('Yk');