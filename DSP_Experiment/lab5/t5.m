% Program P3_5	
% Modulation Property of DTFT	
clf;	
w = -pi:2*pi/255:pi;	
x1 = [1 3 0 0 0 11 13 15 17];%变化后的序列1	
x2 = [1 -1 1 -1 -1 1 0 0 0];	%变化后的序列2
y = x1.*x2;	
h1 = freqz(x1, 1, w);	
h2 = freqz(x2, 1, w);	
h3 = freqz(y,1,w);
subplot(3,1,1)
plot(w/pi,abs(h1));grid
title('Magnitude Spectrum of First Sequence')
subplot(3,1,2)
plot(w/pi,abs(h2));grid
title('Magnitude Spectrum of Second Sequence')
subplot(3,1,3)plot(w/pi,abs(h3));grid
title('Magnitude Spectrum of Product Sequence')