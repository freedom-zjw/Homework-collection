% Program P3_2	% Time-Shifting Properties of DTFT	
clf;	
w = -pi:2*pi/255:pi; 
wo = 0.4*pi; 
D = 5;	
num = [1 2 3 4 5];	
h1 = freqz(num, 1, w);	
h2 = freqz([zeros(1,D) num], 1, w);
subplot(2,2,1)	plot(w/pi,abs(h1));grid	
title('Magnitude Spectrum of Original Sequence')	
subplot(2,2,2)	plot(w/pi,abs(h2));grid	
title('Magnitude Spectrum of Time-Shifted Sequence')	
subplot(2,2,3)	plot(w/pi,angle(h1));grid	
title('Phase Spectrum of Original Sequence')	
subplot(2,2,4)	plot(w/pi,angle(h2));grid	
title('Phase Spectrum of Time-Shifted Sequence')