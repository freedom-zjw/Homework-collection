;zhangbo 15352400 zhang chao qiang 15352401 zhang gui yuan 15352404
START:
	mov ax,0b10h
	mov ds,ax
	mov ax,0b800h 
	mov es,ax
	mov cx,01fffh
	int 33h
	call DELAY
	int 34h
	mov cx,01fffh
	call DELAY
	int 35h
	mov cx,01fffh
	call DELAY
	int 36h
	mov cx,01fffh
	call DELAY

	ret
		DELAY:
	push cx
	mov cx,0ffffh
	DELAY2:
	loop DELAY2
	pop cx
	loop DELAY
	ret
	count db 0
	times 1022-($-$$) db 0
    db 0x55,0xaa