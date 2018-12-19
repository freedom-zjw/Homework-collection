clc
clear all;
syms w0 n z a;
x1=n*a^n;                X1=ztrans(x1)
x2=sin(w0*n);            X2=ztrans(x2)
x3=2^n;                  X3=ztrans(x3)
x4=exp(-a*n)*sin(n*w0);  X4=ztrans(x4)
