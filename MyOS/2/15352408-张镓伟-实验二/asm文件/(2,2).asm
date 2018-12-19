Start:
	mov ax,0a10h 
	mov ds,ax
	mov es,ax
Begin:
	mov ax,0400h
	int 1ah
	mov di,date
	add di,7
	mov al,ch
	call Trans
	mov al,cl
	call Trans
	inc di
	mov al,dh
	call Trans
	inc di
	mov al,dl
	call Trans
	mov bp,line
	mov cx,19
	mov ax,1301h
	mov bx,000fh
	mov dx,1232h
	int 10h
	mov bp,date
	mov cx,19
	mov ax,1301h
	mov bx,000fh
	mov dx,1332h
	int 10h
	mov bp,line
	mov cx,19
	mov ax,1301h
	mov bx,000fh
	mov dx,1432h
	int 10h
	
	mov ax,0100h
	int 16h
	jnz Return
	jmp Begin

Return:
	jmp 0000:07c00h
	
	
Trans:
	mov ah,al
	;先转换高4位
	and al,0f0h
	push cx
	mov cl,4
	shr al,cl
	add al,30h
	stosb
	;转换低4位
	mov al,ah
	and al,0fh
	add al,30h
	stosb
	pop cx
	RET


Define:
	line db '*******************'
	date db '* DATE:0000/00/00 *'
	times 510-($-$$) db 0
    db 0x55,0xaa