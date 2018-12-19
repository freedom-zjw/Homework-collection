;zhangbo 15352400 zhang chao qiang 15352401 zhang gui yuan 15352404
START:
	mov ax,0a50h
	mov ds,ax
	mov ax,0b800h
	mov es,ax
	mov word[x],-1
	mov word[y],51
	mov byte[dir],D_R
	mov byte[cnt],2
	mov byte[color],1fh
	mov byte[count],0
	BEGIN:
	;delay
	mov cx,0F0h
	inc byte [count]
	cmp byte[count],09fh
	jz RETURN
	jmp DELAY
RETURN:
	ret
DELAY:
	push cx
	mov cx,0ffffh
DELAY2:
	loop DELAY2
	pop cx
	loop DELAY	
SEC:
	;select one direction	
	cmp byte[dir],1
	jz DDRR
	cmp byte[dir],2
	jz DDLL
	cmp byte[dir],3
	jz UURR
	cmp byte[dir],4
	jz UULL
	jmp START
;down and right	
DDRR:
	mov byte[dir],D_R
	cmp word[y],65
	jz DDLL
	cmp word[x],12
	jz UURR	
	inc word[x]
	inc word[y]
	jmp SHOW
;down and left
DDLL:
	mov byte[dir],D_L
	cmp word[x],12
	jz UULL
	cmp word[y],51
	jz DDRR
	inc word[x]
	dec word[y]
	jmp SHOW
;up and left
UULL:
	mov byte[dir],U_L
	cmp word[y],51
	jz UURR
	cmp word[x],0
	jz DDLL
	dec word[x]
	dec word[y]
	jmp SHOW
;up and right
UURR:
	mov byte[dir],U_R
	cmp word[x],0
	jz DDRR
	cmp word[y],65
	jz UULL
	dec word[x]
	inc word[y]
	jmp SHOW
;show the char
SHOW:
	;calculate the color of char
	inc byte[cnt]
	cmp byte[cnt],0fh
	jz CHANGE
	jmp NEXT
CHANGE:
	mov byte[cnt],2
	;calculate the color of the rectangle which shows the name and ID
	add byte[color],10h	
	cmp byte[color],7fh
	jz CHANGE2	
	jmp NEXT
CHANGE2:
	mov byte[color],1fh	
	inc word[temp]    ;变换名字和ID	 
	cmp word[temp],3
	jz CHANGE3
	jmp NEXT
CHANGE3:
	mov word[temp],0    ;重置
NEXT:
	;show the char 'A'
	mov ax,[x]
	mov cx,80
	mul cx
	add ax,[y]
	mov cx,2
	mul cx
	mov bx,ax
	mov al,'A'
	mov ah,byte[cnt]
	mov [es:bx],ax
	jmp SHOW_NAME
	jmp BEGIN
;show the name and id
SHOW_NAME: 

	;show the name
	mov word[xx],4
	mov word[yy],51
	mov ax,word[temp]
	mov cx,14
	mul cx
	mov si,ax
	mov cx,14
LOOP3:
	call CAL
	;mov al,byte[myname+si]	
	mov al,byte[myname+si]
	mov byte[es:bx],al
	mov al,[color]
	mov byte[es:bx+1],al
	inc word[yy]
	inc si
	loop LOOP3
	;show the ID
	mov word[xx],5
	mov word[yy],54
	mov ax,word[temp]
	mov cx,8
	mul cx
	mov si,ax
	mov cx,8
LOOP4:
	call CAL
	mov al,byte[myid+si]
	mov byte[es:bx],al
	mov al,[color]
	mov byte[es:bx+1],al
	inc word[yy]
	inc si
	loop LOOP4
	

	jmp BEGIN

;calculate the position, using (x*80+y)*2
CAL:mov ax,word[xx]
	mov bx,80
	mul bx
	add ax,word[yy]
	mov bx,2
	mul bx
	mov bx,ax
	ret

end:
    jmp $ 

DEFINE:
	D_R equ 1
	D_L equ 2
	U_R equ 3
	U_L equ 4
	xx dw 0
	yy dw 0
	x dw 0
	y dw 0
	temp dw 0
	dir db D_R	
	myid db '153524011535240015352404'	
	myname db 'ZhangChaoQiang  Zhang   Bo  Zhang Gui Yuan'
	cnt db 0
	count db 0
	color db 1fh
	times 1022-($-$$) db 0
    db 0x55,0xaa