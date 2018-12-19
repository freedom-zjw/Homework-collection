org 7c00h ;引导地址
OffSetOfUserPrg1 equ 0A100h;偏移地址使其他执行程序以这个地址为基准
START:
	mov ax, cs
	mov ds, ax
	mov es, ax
	call CLEARScreen;调用清屏程序
	mov ah, 13h;功能号
	mov al, 01h
	mov bl,1bh 
	mov bh,0 ; 第0页
	mov dh,0bh ; 第11行
	mov dl,1ah ; 第30列
	mov bp,Message1 ; BP=串地址
	mov cx,Message1_length ; 串长
	int 10h
	mov dh,0ch ; 第11行
	mov dl,10h ; 第30列
	mov bp,Message4 ; BP=串地址
	mov cx,Message4_length ; 串长
	int 10h
	mov dh, 0eh
	mov dl, 2ch
	mov bp,Message2 ; BP=串地址
	mov cx,Message2_length ; 串长
	int 10h
	mov dh, 10h;
	mov dl, 2ch;
	mov bp,Message3 ; BP=串地址
	mov cx,Message3_length ; 串长
	int 10h
BEGIN:
   mov ax,0100h
	int 16h
	jnz THEN
	int 10h	
	jmp BEGIN
THEN:;通过按键进行判断跳转界面，如果没按键就持续在当前界面停留
	mov ah,0;功能号
	int 16h
	cmp al,'1'
	jz P1
	cmp al,'2'
	jz P2
	cmp al,'3'
	jz P3
	cmp al,'4'
	jz P4
	jmp START
P1:
	mov byte[program],0
	jmp LOAD
P2:
	mov byte[program],1
	jmp LOAD
P3:
	mov byte[program],2
	jmp LOAD
P4:
	mov byte[program],3
	jmp LOAD
LOAD:
	call CLEARScreen
	mov ax, cs
	mov es, ax
	mov bx ,OffSetOfUserPrg1
	mov ax,0201h
	mov dx,0
	mov cx,02h
	add cl,byte[program]
	int 13h;读盘
	jmp OffSetOfUserPrg1
CLEARScreen:
push es
	mov ax,0b800h 
	mov es,ax
	mov word[x],0
	mov cx,25
LOOP1:	
	push cx
	mov word[y],0
	mov cx,80
LOOP2:
	mov ax,word[x]
	mov bx,80
	mul bx
	add ax,word[y]
	mov bx,2
	mul bx
	mov bx,ax
	mov word[es:bx],20h
	inc word[y]	
	loop LOOP2
	pop cx
	inc word[x]
	loop LOOP1
	pop es
	RET 
	
DEFINE:
	Message1 db 'PLEASE USE YOUR KEYBOARD TO INPUT COMMAND'
	Message1_length equ ($-Message1)
	Message2 db '15352405 15352406'
	Message2_length equ ($-Message2)
	program db 1
	Message3 db '15352407 15352408'
	Message3_length equ ($-Message3)
	Message4 db '1 for left-up ,2 for right-up ,3 for left-down, 4 for right-down'
	Message4_length equ ($-Message4)
	x dw 0
	y dw 0
	times 510-($-$$) db 0
    db 0x55,0xaa
	incbin '1.bin'
	incbin '2.bin'
	incbin '3.bin'
	incbin '4.bin'