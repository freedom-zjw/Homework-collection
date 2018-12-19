clc
clear all
syms n z a w0;
X1=z/(z-a);               x1=iztrans(X1);
X2=z/(z-a)^2;             x2=iztrans(X2);
X3=z/(z-exp(i*w0));       x3=iztrans(X3);
X4=(1-z^(-3))/(1-z^(-1)); x4=iztrans(X4);
%由于求出来之后太长，所以我用了simplify函数化简表达式
x1=simplify(x1)
x2=simplify(x2)
x3=simplify(x3)
x4=simplify(x4)
