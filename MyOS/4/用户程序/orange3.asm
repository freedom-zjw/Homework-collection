org 09900h
START:
	mov ax,cs
	mov ds,ax
	mov es,ax
	cmp byte[run],1
	jz BEGIN
	mov byte[color],81h  ;初始化颜色变量
BEGIN:
	mov ax,0100h
	int 16h
	jnz READKEY
	jmp NOTREAD
READKEY:
	mov ax,0
	int 16h
	cmp al,'3'
	jz RETURN
NOTREAD:
	inc word[count]
	cmp word[count],0ffh
	jz RETURN
	jmp DELAY
RETURN:
	mov ax,0
	mov es,ax
	mov word[es:604h],0
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
	mov dx,0d07h  ;显示地址
	mov bp,line1  ;显示字符串
	mov cx,33     ;字符串长度
	int 10h       
	
	mov ax,1301h    
	mov bh,0
	mov bl,byte[color]
	mov dx,	0e05h
	mov bp,line2   
	mov cx,35
	int 10h
	
	mov ax,1301h
	mov bp,line3
	mov cx,38
	mov bh,0
	mov bl,byte[color]
	mov dx,	0f02h
	int 10h
	
	mov bp,line4
	mov cx,40
	mov ax,1301h
	mov bh,0 
	mov bl,byte[color]
	mov dx,	1000h
	int 10h
	
	mov bp,line4
	mov cx,27
	mov ax,1301h
	mov bh,0
	mov bl,byte[color]
	mov dx,	1100h
	int 10h
	
	
	mov bp,line4
	mov cx,25
	mov ax,1301h
	mov bh,0
	mov bl,byte[color]
	mov dx,	1201h
	int 10h
	
	mov bp,line4
	mov cx,21
	mov ax,1301h
	mov bh,0
	mov bl,byte[color]
	mov dx,	1303h
	int 10h
	
	
	mov bp,line4
	mov cx,17
	mov ax,1301h
	mov bh,0
	mov bl,byte[color]
	mov dx,	1405h
	int 10h
	
	mov bp,line4
	mov cx,13
	mov ax,1301h
	mov bh,0
	mov bl,byte[color]
	mov dx,	1507h
	int 10h
	
	mov bp,line4
	mov cx,9
	mov ax,1301h
	mov bh,0
	mov bl,byte[color]
	mov dx,	1609h
	int 10h
	
	mov bp,line4
	mov cx,5
	mov ax,1301h
	mov bh,0
	mov bl,byte[color]
	mov dx,	170bh
	int 10h
	
	mov bp,line4
	mov cx,1
	mov ax,1301h
	mov bh,0
	mov bl,byte[color]
	mov dx,	180dh
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
	cmp word [es:604h],1
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
	line1 db     	 '****     ****            15352405'
	line2 db   	   '*******   *******          15352406'
	line3 db 	'*********** ***********       15352407'
	line4 db '***************************     15352408'
	color db 81h
	times 1022-($-$$) db 0
    db 0x55,0xaa