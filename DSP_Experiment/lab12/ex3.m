clc
clear all;
fp=6000;wp=2*pi*fp;        %输入高通滤波器的通带截止频率
fs=3500;ws=2*pi*fs;        %输入高通滤波器的阻带截止频率
Rp=1;As=40;                %输入滤波器的通阻带衰减指标
%cheb2ord 函数计算切比雪夫Ⅱ型数字滤波器的阶数n和截止频率wc
[n,wc]=cheb2ord(wp,ws,Rp,As,'s')

%计算n阶模拟低通原型，得到左半平面零极点
[z0,p0,k0]=cheb2ap(n,As)
b0=k0*real(poly(z0));       %求归一化的滤波器系数b0
a0=real(poly(p0));          %求归一化的滤波器系数a0
[H,w0]=freqs(b0,a0);        %求归一化的滤波器频率特性
dbH=20*log10(abs(H)+eps/max(abs(H)));%幅度为分贝值


%变换为实际模拟带通滤波器
[b1,a1]=lp2hp(b0,a0,wc);    %从归一化低通变换到实际高通
[Ha,wa]=freqs(b1,a1);       %求实际高通滤波器的频率特性
dbHa=20*log10(abs(Ha)+eps/max(abs(Ha)));%幅度化为分贝值

%为作图准备数据
wp0=fp/wc                 %通带截止频率归一化
wc0=wc/2/pi/wc            %截止频率归一化
ws0=fs/wc                 %阻带截止频率归一化
fc=floor(wc/2/pi);          %截止频率

%归一化模拟低通原型频率特性作图
subplot(2,1,1),plot(w0/(2*pi),dbH);
title('归一化模拟原型幅度');ylabel('dB');
axis([0,0.5,-100,1]);grid;
set(gca,'Xtick',[0,ws0,wc0,wp0,0.5]);
set(gca,'Ytick',[-100,-80,-60,-40,-20,-1]);
subplot(2,1,2),plot(w0/(2*pi),angle(H)/(pi*180));
title('归一化模拟原型相位');ylabel('\phi');
axis([0,0.5,-0.006,0.006]);grid;
set(gca,'Xtick',[0,ws0,wc0,wp0,0.5]);
set(gca,'Ytick',[-0.006:0.001:0.006]);

%实际模拟高通频率特性作图
figure;
subplot(2,1,1),plot(wa/(2*pi),dbHa);grid
title('实际模拟高通幅度');ylabel('dB');xlabel('频率(Hz)');
set(gca,'Xtick',[0,fs,fc,fp,2*fs]);
set(gca,'Ytick',[-100,-80,-60,-40,-20,-1]);
axis([0,2*fp,-100,1]);
subplot(2,1,2),plot(wa/(2*pi),angle(Ha)/(pi*180));grid
title('实际模拟高通相位');ylabel('\phi');xlabel('频率(Hz)');
set(gca,'Xtick',[0,fs,fc,fp,2*fs]);
set(gca,'Ytick',[-0.006:0.001:0.006]);
axis([0,2*fs,-0.006,0.006]);