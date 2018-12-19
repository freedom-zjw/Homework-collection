clc
clear all
x1=[8,7,6,5,4,3];%建立x(n) N＝6序列
N=length(x1);n=0:N-1;
y1=x1(mod(-n,N)+1);%建立x(－n)，N＝6序列
N2=10;
x2=[x1,zeros(1,N2-N)];%建立x(n) N＝10序列
n2=0:N2-1;
y2=x2(mod(-n2,N2)+1);%建立x(－n)，N＝10序列
k=0:N2-1;
Xk=x2*exp(-j*2*pi/N2).^(n2'*k)%求x(n)的DFT
Yk=y2*exp(-j*2*pi/N2).^(n2'*k)%求x(－n)的DFT
subplot(1,2,1),stem(n2,x2,'k'),title('x(n)');%画x(n)，N＝10
subplot(1,2,2),stem(n2,y2,'k'),title('x(-n)');%画x(-n)，N＝10
