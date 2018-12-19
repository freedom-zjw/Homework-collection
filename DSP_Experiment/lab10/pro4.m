clc
clear all;
% b=[10,20,0,0];
% a=[1,8,19,12];
% [r p c]=residuez(b,a)
% N=10;n=0:N-1;
% x=r(1)*p(1).^n+r(2)*p(2).^n+r(3)*p(3).^n;
% subplot(1,2,1),stem(n,x);
% title('用部分分式法求反变换x(n)');
% x2=impz(b,a,N);
% subplot(1,2,2),stem(n,x2);
% title('用impz求反变换x(n)');
 
% b=[0,0,5];
% a=[1,1,-6];
% [r p c]=residuez(b,a)
% N=10;n=0:N-1;
% x=r(1)*p(1).^n+r(2)*p(2).^n+c(1).*[n==0];
% subplot(1,2,1),stem(n,x);
% title('用部分分式法求反变换x(n)');
% x2=impz(b,a,N);
% subplot(1,2,2),stem(n,x2);
% title('用impz求反变换x(n)');

b=[1,0,0,0];
a=[1,-0.9,-0.81,0.729];
[r p c]=residuez(b,a)
N=10;n=0:N-1;
x=r(1)*p(1).^n+r(2).*(n+1).*p(2).^n.*[n+1>=0]+r(3)*p(3).^n;
subplot(1,2,1),stem(n,x);
title('用部分分式法求反变换x(n)');
x2=impz(b,a,N);
subplot(1,2,2),stem(n,x2);
title('用impz求反变换x(n)');

