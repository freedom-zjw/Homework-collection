clc
clear all
xn=[4,0,3,0,2,0,1];%����xn����
Nx=length(xn);
n=0:Nx-1;
nx1=-Nx:2*Nx-1;%�����������صķ�Χ
x1=xn(mod(nx1,Nx)+1);%����������������
ny1=nx1+2;
y1=x1;%��x1����2λ���õ�y1
RN=(nx1>=0)&(nx1<Nx);%��x1��λ������nx1��������ֵ��
RN1=(ny1>=0)&(ny1<Nx);%��y1��λ������ny1��������ֵ��
subplot(4,1,1),stem(nx1,RN.*x1);%����x1����ֵ����
title('x1����ֵ����')
subplot(4,1,2),stem(nx1,x1);%����x1
title('x1��������');
subplot(4,1,3),stem(ny1,y1);%����y1
title('��λ�����������y1');
axis([-10,15,0,4]);
subplot(4,1,4),stem(ny1,RN1.*y1);%����y1����ֵ����
title('y1����ֵ����');
axis([-10,15,0,4]);