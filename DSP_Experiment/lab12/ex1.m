clc 
clear all;
fp=2000;Omgp=2*pi*fp;  %输入实际滤波器的通带截止频率
fs=5000;Omgs=2*pi*fs;  %输入实际滤波器的阻带截止频率
Rp=1;As=20;            %输入滤波器的通阻带衰减指标
%buttord 函数计算巴特沃斯数字滤波器的阶数n和3 dB截止频率Omgc
[n,Omgc]=buttord(Omgp,Omgs,Rp,As,'s')

%计算n阶模拟低通原型，得到左半平面零极点
% buttap用于计算N阶巴特沃斯归一化（3dB截止频率Ωc=1）
% 模拟低通原型滤波器系统函数的零、极点和增益因子。
[z0,p0,k0] = buttap(n);
b0 = k0*real(poly(z0))  %求归一化的滤波器系数b0
a0 = real(poly(p0))     %求归一化的滤波器系数a0
[H,Omg0]=freqs(b0,a0);  %求归一化的滤波器频率特性
dbH = 20*log10((abs(H)+eps)/max(abs(H))); %幅度化为分贝值

%变换为实际模拟低通滤波器
[ba,aa]=lp2lp(b0,a0,Omgc);%从归一化低通变换到实际低通
[Ha,Omga]=freqs(ba,aa);%求实际系统的频率特性
dbHa=20*log10((abs(Ha)+eps)/max(abs(Ha))); %幅度化为分贝值

%为作图准备数据
Omg0p=fp/Omgc;%通带截止频率归一化
Omg0c=Omgc/2/pi/Omgc;%3 dB截止频率归一化
Omg0s=fs/Omgc;%阻带截止频率归一化
fc=floor(Omgc/2/pi);%3 dB截止频率

%归一化模拟低通原型频率特性作图
subplot(2,1,1),plot(Omg0/2/pi,dbH);
axis([0,1,-50,1]);title('归一化模拟低通原型幅度');
ylabel('dB');
set(gca,'Xtick',[0,Omg0p,Omg0c,Omg0s,1]);
set(gca,'Ytick',[-50,-20,-3,-1]);grid
subplot(2,1,2),plot(Omg0/2/pi,angle(H)/pi*180);
axis([0,1,-200,200]);title('归一化模拟低通原型相位');
ylabel('＼phi');
set(gca,'Xtick',[0,Omg0p,Omg0c,Omg0s,1]);
set(gca,'Ytick',[-180,-120,0,90,180]);grid 

%实际模拟低通频率特性作图
figure;
subplot(2,1,1),plot(Omga/2/pi,dbHa);
axis([0,2*fs,-50,1]);title('实际模拟低通幅度');
ylabel(' dB');xlabel('频率(Hz)');
set(gca,'Xtick',[0,fp,fc,fs,2*fs]);
set(gca,'Ytick',[-50,-20,-3,-1]);grid
subplot(2,1,2),plot(Omga/2/pi,angle(Ha)/pi*180);
axis([0,2*fs,-200,200]);title('实际模拟低通相位');
set(gca,'Xtick',[0,fp,fc,fs,2*fs]);
set(gca,'Ytick',[-180,-120,0,90,180]);grid 
ylabel('＼phi');xlabel('频率(Hz)');