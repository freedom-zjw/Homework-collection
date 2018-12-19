org 0A100h
Start:
    mov ax,cs
	mov ds,ax
	mov es,ax
	int 33h
	int 34h
	int 35h
	int 36h
	call DELAY
	ret
DELAY:
DELAY1:
	push cx
	mov cx,0ffffh
DELAY2:
	loop DELAY2
	pop cx
	loop DELAY1
	ret	
Define:
	times 1022-($-$$) db 0
    db 0x55,0xaa
	
	