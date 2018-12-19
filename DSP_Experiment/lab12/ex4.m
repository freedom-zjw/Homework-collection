clc
clear all;
fp1=3500;wp1=2*pi*fp1;%输入带通滤波器的通带截止频率
fp2=5500;wp2=2*pi*fp2;
wp=[wp1,wp2];
fs1=3000;ws1=2*pi*fs1;%输入带通滤波器的阻带截止频率
fs2=6000;ws2=2*pi*fs2;
ws=[ws1,ws2]
As=40;Rp=1           ;%输入滤波器的通阻带衰减指标
bw=wp2-wp1;w=sqrt(wp1*wp2);%求通带宽度和中心频率
%计算滤波器的阶数和截止频率
[n,wn]=ellipord(wp,ws,Rp,As,'s')
%计算n阶模拟低通原型，得到左半平面零极点
[z0,p0,k0]=ellipap(n,Rp,As)
b0=k0*real(poly(z0));  %求归一化的滤波器系数b0
a0=real(poly(p0));     %求归一化的滤波器系数a0
[H,w0]=freqs(b0,a0);   %求归一化的滤波器频率特性
dbH=20*log10(abs(H)+eps/max(abs(H)));%幅度化为分贝值
%变换为实际模拟带通滤波器
[b1,a1]=lp2bp(b0,a0,w,bw);%从归一化低通变换到模拟带通
[Ha,wa]=freqs(b1,a1);     %求实际带通滤波器的频率特性
dbHa=20*log10(abs(Ha)+eps/max(abs(Ha)));%幅度化为分贝值

%归一化模拟低通原型频率特性作图
subplot(2,2,1),plot(w0/(2*pi),dbH);grid
title('归一化模拟原型幅度');ylabel('dB');
subplot(2,2,2),plot(w0/(2*pi),angle(H)/(pi*180));grid;
title('归一化模拟原型相位');ylabel('\phi');

%实际模拟带通频率特性作图
subplot(2,2,3),plot(wa/(2*pi),dbHa);grid
title('实际模拟带通幅度');ylabel('dB');xlabel('频率(Hz)');
axis([0,15000,-150,0]);
set(gca,'Xtick',[0,fs1,fp1,fp2,fs2,2*fp1]);
set(gca,'Ytick',[-150,-100,-80,-40,-20,-1]);
subplot(2,2,4),plot(wa/(2*pi),angle(Ha)/(pi*180));grid
title('实际模拟带通相位');ylabel('\phi');xlabel('频率(Hz)');
