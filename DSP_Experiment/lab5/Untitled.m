clf;	
n=-10:10;   %原序列对应下标
x=rand(1,length(n));%原序列
k=-100:100;
w=(pi/100)*k;
X=x*(exp(-j*pi/100)).^(n'*k);%原序列傅里叶变换
y=fliplr(x);m=-fliplr(n);%反转原序列
Y=y*(exp(-j*pi/100)).^(m'*k);%反转后的傅里叶变换
max(abs(Y-fliplr(X)))  %输出原序列傅里叶变换反转后
                       %与反转后的傅里叶变换的最大误差
