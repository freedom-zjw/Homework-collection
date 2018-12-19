
START:       ;初始化ds（代码寄存器），es（附加段寄存器）
	mov ax,0a10h
	mov ds,ax
	mov es,ax
	mov byte[color],81h  ;初始化颜色变量
BEGIN:
	;delay
	 mov cx,0F0h
	;input from keyboard
	;if input ,return
	mov ax,0100h
	int 16h     ;16h中断，键入内容
	jnz RETURN  ;zf寄存器为1，则跳转
	jmp DELAY
RETURN:
	jmp 0000:07c00h   ;返回调用它的主程序
	
DELAY:
	push cx         ;循环的次数是保存在cx中的
	mov cx,0ffffh   ;无限循环DELAY2
DELAY2:
	loop DELAY2
	pop cx
	loop DELAY
	
	jmp heart      ;显示心形图案
	jmp START 
	
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

	jmp CHANGE
	

;show the char

CHANGE:
	
	add byte[color],01h ;每次延迟之后，颜色变量加1
	cmp byte[color],86h ;跳变六次之后，初始化一次
	jz CHANGE2
	jmp BEGIN
CHANGE2:
	mov byte[color],81h ;初始化
	jmp START
end:
    jmp $ 

DEFINE:
	
	line1 db     	 '****     ****'
	line2 db   	   '*******   *******'
	line3 db 	'*********** ***********'
	line4 db '*****************************'
	color db 81h
	times 510-($-$$) db 0
    db 0x55,0xaa