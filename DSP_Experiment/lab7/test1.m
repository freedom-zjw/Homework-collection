clc
clear all
a=[1,-0.5,1,-0.5]; %输入系统a、b系数
b=[1,-1,1,0];
N=20;n=0:N-1;
for i=1:4
    if i==1     %建立输入信号x(n)
       x=0+[n>=3];  %u[n-3]
       figure('Name','x[n]=u[n-3]');
    elseif i==2
       x=0+[n==0]-[n==5];%δ[n]-δ[n-5]
       figure('Name','x[n]=δ[n]-δ[n-5]');
    elseif i==3
       x=exp(0.1.*n).*[n>=3];%e^0.1n*u(n－3)
       figure('Name','e0.1^n*u(n－3)');
    else
       x=0.5.^n.*[n>=0]; %0.5^n*u(n)
       figure('Name','x[n]=0.5^n*u(n)');
    end
    subplot(3,1,1);stem(n,x);title('输入信号')
    %用dlsim求
    y=dlsim(b,a,x);
    subplot(3,1,2);stem(n,y);title('dlsim求的系统完全响应')
    %用filtic和filter求
    x01=0;y01=0;%输入初始条件
    xi=filtic(b,a,y01,x01);%计算初始状态
    y=filter(b,a,x,xi); % 求系统完全响应
    subplot(3,1,3);stem(n,y);title('filtic和filter求的系统完全响应')
end