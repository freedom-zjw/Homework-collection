clc
clear all;
%先根据零-极点模型的系统函数写出z,p,k
z=[1,-3,5]';  
p=[2,4,-6]';
k=3;
[num,den]=zp2tf(z,p,k)%使用zp2tf求tf模型
[sos,g]=zp2sos(z,p,k)%使用zp2sos求sos模型
[r,p,k] = residuez(num,den)%可通过之前求出的tf模型使用residuez求极点留数(rpk)模型