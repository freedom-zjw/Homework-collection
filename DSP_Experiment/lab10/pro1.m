b=[1,0,0];%b为分子多项式的系数
a=[1,-1.5,0.5];%a为分母多项式的系数
[r p c]=residuez(b,a)
%把b(z)/a(z)展开
N=20;n=0:N-1;
x=r(1)*p(1).^n+r(2)*p(2).^n%根据[r,p,c]写出z反变换x(n)的表达式
stem(n,x);
title('用部分分式法求反变换x(n)');