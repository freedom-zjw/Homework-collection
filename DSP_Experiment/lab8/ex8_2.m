clc
clear all
xn=[7,6,5,4,3,2];
N=length(xn);
n=0:3*N-1;
k=0:3*N-1;
xn1=xn(mod(n,N)+1);%mod (a, m), a is dividend,and  is divisor 
%即xn1＝［xn，xn，xn，xn]
Xk=xn1*exp(-j*2*pi/N).^(n'*k);%离散傅里叶变换
subplot(2,2,1),stem(xn);%显示序列主值
title('原主值信号x(n)');
subplot(2,2,2),stem(n,xn1);%显示周期序列
title('周期序列信号');
subplot(2,2,3),stem(k,abs(Xk));%显示序列的幅度谱
title('|X(k)|');
subplot(2,2,4),stem(k,angle(Xk));%显示序列的相位谱
title('arg|X(k)|');
