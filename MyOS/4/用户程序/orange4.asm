org 09d00h
START:
	mov ax,cs
	mov ds,ax
	mov es,ax
	cmp byte[run],1
	jz BEGIN
BEGIN:
	mov ax,0100h
	int 16h
	jnz READKEY
	jmp NOTREAD
READKEY:
	mov ax,0
	int 16h
	cmp al,'4'
	jz RETURN
NOTREAD:	
	inc byte[count]
	cmp byte[count],05fh
	jz RETURN
	jmp TIME
RETURN:
	mov ax,0
	mov es,ax
	mov word[es:606h],0
	mov byte[run],0
	ret
TIME:
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
	jmp BEGIN
	 push es
	mov ax,0
	mov es,ax
	cmp word [es:604h],1
	pop es
	jz FENSHI
	mov cx,0f0h
	jmp BEGIN
FENSHI:
	mov cx,090h
	ret
	
	
	
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
	
   

	ret

Define:
	count db 0
	run db 0
	line db '*******************'
	date db '* DATE:0000/00/00 *'
	times 1022-($-$$) db 0
    db 0x55,0xaa
