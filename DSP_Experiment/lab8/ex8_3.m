xn=[7,6,5,4,3,2];    
N=length(xn);     
n=0:N-1;    
w=linspace(-2*pi,2*pi,500);  %将［－2p，2p］频率区间分割为500份
X=xn*exp(-j*n'*w);%离散时间傅里叶变换	
subplot(3,1,1),stem(n,xn,'k');	
ylabel('x(n)');
subplot(3,1,2),plot(w,abs(X),'k');%显示序列的幅度谱
axis([-2*pi,2*pi,1.1*min(abs(X)),1.1*max(abs(X))]);
ylabel('幅度谱');
subplot(3,1,3),plot(w,angle(X),'k');%显示序列的相位谱
axis([-2*pi,2*pi,1.1*min(angle(X)),1.1*max(angle(X))]);
ylabel('相位谱');

figure;         
N=100;         
xn=[7,6,5,4,3,2,zeros(1,N-6)];
n=0:N-1;
k=0:N-1;
Xk=xn*exp(-j*2*pi/N).^(n'*k);%离散傅里叶变换
x=(Xk*exp(j*2*pi/N).^(n'*k))/N;%离散傅里叶逆变换
subplot(2,2,1),stem(n,xn);%显示原信号序列
title('x(n)');
subplot(2,2,2),stem(n,abs(x));%显示逆变换结果
title('IDFT|X(k)|');
subplot(2,2,3),stem(k,abs(Xk));%显示|X(k)|
title('|X(k)|');
subplot(2,2,4),stem(k,angle(Xk));%显示arg|X(k)|
title('arg|X(k)|');





