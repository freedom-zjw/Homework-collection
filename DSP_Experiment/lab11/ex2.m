clc
clear;
num=[2,3,0];%分子系数向量
den=[1,0.4,1];%分母系数向量
[z,p,k] = tf2zp(num, den)%使用tf2zp求零-极点增益模型
[sos,g] = tf2sos(num, den)%使用tf2sos求二次分式模型
[r,p,k] = residuez(num, den)%使用residuez求极点留数(rpk)模型
[A,B,C,D] = tf2ss(num,den)%使用tf2ss(num,den)求状态变量模型