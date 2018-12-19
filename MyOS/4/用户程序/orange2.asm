org 09500h
START:
	mov ax,cs
	mov ds,ax
	;mov ax,0b800h
	mov es,ax
	cmp byte[run],1
	jz BEGIN
	mov word[color],81h  ;初始化颜色变量
	;mov byte[count],0
BEGIN:
	mov ax,0100h
	int 16h
	jnz READKEY
	jmp NOTREAD
READKEY:
	mov ax,0
	int 16h
	cmp al,'2'
	jz RETURN
NOTREAD:	
	inc word[count]
	cmp word[count],0ffh
	jz RETURN
	jmp DELAY
RETURN:
	mov ax,0
	mov es,ax
	mov word[es:602h],0
	mov byte[run],0
	ret
DELAY:
	push cx         ;循环的次数是保存在cx中的
	mov cx,0ffh   ;无限循环DELAY2
DELAY2:
	loop DELAY2
	pop cx
	loop DELAY
	
	jmp heart      ;显示心形图案
	
heart:    ;图形显示
	mov ax,1301h ;功能号
	mov bh,0
	mov bl,byte[color]  ;颜色
	mov dx,0035h  ;显示地址
	mov bp,line1  ;显示字符串
	mov cx,13     ;字符串长度
	int 10h       
	
	mov ax,1301h    
	mov bh,0
	mov bl,byte[color]
	mov dx,	0133h
	mov bp,line2   
	mov cx,17
	int 10h
	
	mov ax,1301h
	mov bp,line3
	mov cx,23
	mov bh,0
	mov bl,byte[color]
	mov dx,	0230h
	int 10h
	
	mov bp,line4
	mov cx,27
	mov ax,1301h
	mov bh,0 
	mov bl,byte[color]
	mov dx,	032eh
	int 10h
	
	mov bp,line4
	mov cx,27
	mov ax,1301h
	mov bh,0
	mov bl,byte[color]
	mov dx,	042eh
	int 10h
	
	
	mov bp,line4
	mov cx,25
	mov ax,1301h
	mov bh,0
	mov bl,byte[color]
	mov dx,	052fh
	int 10h
	
	mov bp,line4
	mov cx,21
	mov ax,1301h
	mov bh,0
	mov bl,byte[color]
	mov dx,	0631h
	int 10h
	
	
	mov bp,line4
	mov cx,17
	mov ax,1301h
	mov bh,0
	mov bl,byte[color]
	mov dx,	0733h
	int 10h
	
	mov bp,line4
	mov cx,13
	mov ax,1301h
	mov bh,0
	mov bl,byte[color]
	mov dx,	0835h
	int 10h
	
	mov bp,line4
	mov cx,9
	mov ax,1301h
	mov bh,0
	mov bl,byte[color]
	mov dx,	0937h
	int 10h
	
	mov bp,line4
	mov cx,5
	mov ax,1301h
	mov bh,0
	mov bl,byte[color]
	mov dx,	0a39h
	int 10h
	
	mov bp,line4
	mov cx,1
	mov ax,1301h
	mov bh,0
	mov bl,byte[color]
	mov dx,	0b3bh
	int 10h
DELAY3:
	push cx         ;循环的次数是保存在cx中的
	mov cx,0ffh   ;无限循环DELAY2
DELAY4:
	loop DELAY4
	pop cx
	loop DELAY3
	jmp CHANGE
	

;show the char

CHANGE:
	
	add byte[color],01h ;每次延迟之后，颜色变量加1
	cmp byte[color],86h ;跳变六次之后，初始化一次
	jz CHANGE2
	jmp BEGIN
CHANGE2:
	mov byte[color],81h ;初始化
	push es
	mov ax,0
	mov es,ax
	cmp word [es:602h],1
	pop es
	jz FENSHI
	mov cx,0f0h
	call DELAY5
	jmp BEGIN
FENSHI:
	mov cx,090h
	call DELAY5
	ret
DELAY5:
	push cx
	mov cx,0ffffh
DELAY6:
	loop DELAY6
	pop cx
	loop DELAY5
	ret
	
end:
    jmp $ 

DEFINE:
	count db 0
	run db 0
	line1 db     	 '****     ****'
	line2 db   	   '*******   *******'
	line3 db 	'*********** ***********'
	line4 db '*****************************'
	color db 81h
	times 1022-($-$$) db 0
    db 0x55,0xaa
	