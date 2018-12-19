clc
clear all
xn=[7,6,5,4,3,2];
N=length(xn);
n=0:N-1;
k=0:N-1;
Xk=xn*exp(-j*2*pi/N).^(n'*k);%离散傅里叶变换
x=(Xk*exp(j*2*pi/N).^(n'*k))/N;%离散傅里叶逆变换
subplot(2,2,1),stem(n,xn);%显示原信号序列
title('x(n)');
subplot(2,2,2),stem(n,abs(x));%显示逆变换结果
title('IDFT|X(k)|');
subplot(2,2,3),stem(k,abs(Xk));%显示|X(k)|
title('|X(k)|');
subplot(2,2,4),stem(k,angle(Xk));%显示arg|X(k)|
title('arg|X(k)|');
