clc
clear all
a=[1,-0.602012,0.495684,0.0359244]; %输入系统a、b系数
b=[0.187632,0.241242,0.241242,0.187632];
N=20;n=0:N-1;
for i=1:4
    if i==1     %建立输入信号x(n)
       x=0+[n==3];  %δ[n-3]
       figure('Name','x[n]=δ[n-3]');
    elseif i==2
       x=0+[n==0]+[n==1]+[n==2]+[n==3]+[n==4];%R5[n]
       figure('Name','x[n]=R5[n-3]');
    elseif i==3
       x=cos(2*pi*n/3)+sin(3*pi*n/10); 
       figure('Name','x[n]=cos(2pin/3)+sin(3pin/10)');
    elseif i==4
       x=0.6.^n.*[n>=3]; %0.6^n*u(n－3)
       figure('Name','x[n]=0.6^n*u(n－3)');
    end
    x0=zeros(1,N);%建立零输入信号
    y01=[5 5];%输入初始条件
    xi=filtic(b,a,y01);%计算初始状态
    y0=filter(b,a,x0,xi);%求零输入响应
    xi0=filtic(b,a,0);%计算初始状态为零的情况
    y1=filter(b,a,x,xi0);%求零状态响应
    y=filter(b,a,x,xi);%求系统的完全响应
    subplot(2,2,1),stem(n,y0);
    title('系统的零输入响应');
    subplot(2,2,2),stem(n,y1);
    title('系统的零状态响应');
    subplot(2,2,[3 4]),stem(n,y);        
    title('用filter求系统的完全响应y(n)');
end