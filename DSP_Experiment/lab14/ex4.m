clc
clear all;
Fs = 10;
fp1=1;fp2=4.5;   %滤波器通带截止频率
wp1=2*fp1/Fs;wp2=2*fp2/Fs;
wp=[wp1,wp2];
fs1=2;fs2=3.5;   %滤波器阻带截止频率
ws1=2*fs1/Fs;ws2=2*fs2/Fs;
ws=[ws1,ws2];
Rp=1;As=20;        %输入滤波器通阻带衰减指标

[n,wc]=cheb1ord(wp,ws,Rp,As)%计算阶数n和截止频率

[b,a]=cheby1(n,Rp,wc,'stop') %直接求数字带通滤波器系数

[H,w]=freqz(b,a);%求数字系统的频率特性
dbH=20*log10((abs(H)+eps)/max(abs(H))); %化为分贝值

subplot(2,2,1),plot(w/pi,abs(H)); 
title('幅度响应');grid;
xlabel('w(π)');ylabel('|H|');
set(gca,'Xtick',[0,wp1,ws1,ws2,wp2,1]);
subplot(2,2,2),plot(w/pi,angle(H));
title('相位响应');grid;
xlabel('w(π)');ylabel('θ');
subplot(2,2,3),plot(w/pi,dbH);
title('幅度响应');grid;
xlabel('w(π)');ylabel('dB');
axis([0,1,-40,0]);
set(gca,'Xtick',[0,wp1,ws1,ws2,wp2,1]);
set(gca,'Ytick',[-40,-20,-1,0]);
subplot(2,2,4),zplane(b,a);grid;
title('零极点图');grid;
xlabel('Real Part');ylabel('Imaginary Part');
·





