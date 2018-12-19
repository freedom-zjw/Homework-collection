
extrn _cmain:near 
extrn _calpos:near
extrn _pos:near
extrn _ch:near
extrn _x:near
extrn _y:near
extrn _offset_user:near
extrn _d:near
extrn _put_char:near
extrn _ouch:near
extrn _paint:near
extrn _ifouch:near

_TEXT segment byte public 'CODE'    
DGROUP group _TEXT,_DATA,_BSS       
	assume cs:_TEXT                 
	org 100h                        
start:
	mov  ax,  cs
	mov  ds,  ax         
	mov  es,  ax        
	mov  ss,  ax      
	mov  sp, 100h
	
	call int33h
	call int34h
	call int35h
	call int36h
	call set_clock_interrupt
	call near ptr _cmain
	jmp $              
	
;置光标位置
clock_vector dw 0,0
keyboard_vector dw 0,0
public _setcursor
_setcursor proc
	push ax
	push bx
	push dx
	mov ah,02h
	mov dh,byte ptr [_x]
	mov dl,byte ptr [_y]
	mov bh,0
	int 10h
	pop dx
	pop bx
	pop ax
	ret
_setcursor endp
;输出一个字符
public _printch
_printch proc
	push ax
	push es
	push bp
	push bx
	call _setcursor ;设置光标
	mov bp,sp
	mov ax,0b800h   
	mov es,ax
	mov al,byte ptr [bp+2+2+2+2+2];取出字符
	mov ah,0fh
	mov bx,word ptr [_pos] ;取出显示位置
	mov word ptr es:[bx],ax
	inc word ptr [_y] ;y坐标+1
	call near ptr _calpos ;重新计算坐标
	pop bx
	pop bp
	pop es
	pop ax
	ret
_printch endp
public _put_color_char
_put_color_char proc
	mov bp,sp
	push es
	push ax
	push bx
	
	mov ax,0b800h
	mov es,ax
	mov ax,word ptr [bp+4];x
	mov bx,80
	mul bx
	add ax,word ptr [bp+6];y
	mov bx,2
	mul bx
	mov bx,ax
	mov ax,word ptr [bp+2];char
	mov byte ptr es:[bx],al
	mov ax,word ptr [bp+8];color
	mov byte ptr es:[bx+1],al
	
	pop bx
	pop ax
	pop es
	ret
_put_color_char endp

;输出一个字符串
public _printstr
_printstr proc
    push bp
	mov	bp, sp
	push es
	push ax
	push bx
	push ds
	mov ax,cs
	mov ds,ax
	mov ax,0b800h
	mov es,ax
	mov bx,word ptr [bp+2+2]
	mov	si, word ptr [bp+2+2+2];取出首字符地址
	mov	di, word ptr [_pos];取出显示位置
.1:
	mov al,byte ptr [si]
	inc si
	test al,al;检测是否为空字符
	jz .2 ;是空字符就跳转.2
	cmp al,0ah;检测是否为换行
	jz .3 ;是换行就跳转.3
	mov ah,bl
	mov word ptr es:[di],ax
	inc byte ptr [_y]
	call near ptr _calpos
	mov di,word ptr [_pos]
	jmp .1
.3:;_x加1，_y清1，更新显示地址
	inc word ptr [_x]
	mov word ptr [_y],1
	call near ptr _calpos
	mov di,word ptr [_pos]
	jmp	.1
.2:;设置光标后退出
	call _setcursor
	pop ds
	pop bx
    pop ax
	pop es
	pop bp
ret
_printstr  endp


;键盘输入一个字符
public _inputch
_inputch proc
	push ax
	call _setcursor
	mov ax,0
	int 16h					;16h中断，从键盘键入一个字符
	mov byte ptr [_ch],al   ;键入的字符存放在al，移动到ch变量中
	pop ax
ret
_inputch endp

;清屏
public _clear
_clear proc 
    push ax
    push bx
    push cx
    push dx		
    mov	ax, 0600h	
	mov	bx, 0700h	
	mov	cx, 0		
	mov	dx, 184fh	
	int	10h		; 显示中断
	mov word ptr [_x],0
	mov word ptr [_y],0
	mov word ptr [_pos],0
	call _setcursor
	pop dx
	pop cx
	pop bx
	pop ax
ret
_clear endp

;读软盘到内存
;load(offset_begin,num_shanqu,pos_shanqu)
public _load
_load proc
	push ax
	push bx
	push cx
	push dx
	push es
	push bp
	mov bp,sp        
	mov ax,cs                
    mov es,ax                ;设置段地址
    mov bx,word ptr [bp+12+2]  ;偏移地址
    mov ah,2                 ; 功能号
    mov al,byte ptr [bp+12+4];扇区数
    mov dl,0                 ;驱动器号
    mov dh,0                 ;磁头号
    mov ch,0                 ;柱面号
    mov cl,byte ptr [bp+12+6];起始扇区号
    int 13H ;                
	pop bp
	pop es
	pop dx
	pop cx
	pop bx
	pop ax
	ret
_load endp
;跳转到用户程序
public _jmp
_jmp proc
	push ax
	push bx
	push cx
	push dx
	push es
	push ds
	call re_clock_interrupt
	call set_keyboard_interrupt
	call word ptr [_offset_user]
	call re_keyboard_interrupt
	call set_clock_interrupt
	pop ds
	pop es
	pop dx
	pop cx
	pop bx
	pop ax
	ret
_jmp endp
;filldata(int,int)
set_keyboard_interrupt proc
	cli
	push es
	push ax
	xor ax,ax
	mov es,ax
	;save the vector
	mov ax,word ptr es:[24h]
	mov word ptr [keyboard_vector],ax
	mov ax,word ptr es:[26h]
	mov word ptr [keyboard_vector+2],ax
	;fill the vector
	mov word ptr es:[24h],offset Ouch
	mov ax,cs
	mov word ptr es:[26h],ax
	;用于中断服务C程序的变量
	mov word ptr [_ifouch],1
	pop ax
	pop es
	sti
	ret
set_keyboard_interrupt endp
;恢复键盘中断
re_keyboard_interrupt proc
	cli
	push es
	push ax
	xor ax,ax
	mov es,ax
	mov ax,word ptr [keyboard_vector]
	mov word ptr es:[24h],ax
	mov ax,word ptr [keyboard_vector+2]
	mov word ptr es:[26h],ax
	;用于中断服务C程序的变量
	mov word ptr [_ifouch],0
	pop ax
	pop es
	sti
	ret
re_keyboard_interrupt endp

set_clock_interrupt proc
	cli
	push es
	push ax
	xor ax,ax
	mov es,ax
	;save the vector
	mov ax,word ptr es:[20h]
	mov word ptr [clock_vector],ax
	mov ax,word ptr es:[22h]
	mov word ptr [clock_vector+2],ax
	;fill the vector
	mov word ptr es:[20h],offset Paint
	mov ax,cs
	mov word ptr es:[22h],ax
	pop ax
	pop es
	sti
	ret
set_clock_interrupt endp
;恢复时钟中断
re_clock_interrupt proc
	cli
	push es
	push ax
	;es置零
	xor ax,ax
	mov es,ax
	;恢复原本的时钟中断的中断向量
	mov ax,word ptr [clock_vector]
	mov word ptr es:[20h],ax
	mov ax,word ptr [clock_vector+2]
	mov word ptr es:[22h],ax
	pop ax
	pop es
	sti
	ret
re_clock_interrupt endp
Paint proc
	cli
	push es
	push si
	push di
	push ax
	push bx
	push cx
	push dx
	push bp
	push ds
	
	call near ptr _paint
	
	mov al,20h
	out 20h,al
	out 0a0h,al

	pop ds
	pop bp
	pop dx
	pop cx
	pop bx
	pop ax
	pop di
	pop si
	pop es
	sti
	iret
Paint endp
Ouch proc
	cli
	push es
	push si
	push di
	push ax
	push bx
	push cx
	push dx
	push bp
	push ds
	
	call near ptr _ouch
	;读缓冲区
	in al,60h
	
	mov al,20h
	out 20h,al
	out 0a0h,al

	pop ds
	pop bp
	pop dx
	pop cx
	pop bx
	pop ax
	pop di
	pop si
	pop es
	sti
	iret
Ouch endp
;INT 33H 左上角显示
;填充33h中断向量表
int33h proc
	cli
	push es
	push ax
	;es置零
	xor ax,ax
	mov es,ax
	;填充中断向量
	mov word ptr es:[0cch],offset interrupt33h
	mov ax,cs
	mov word ptr es:[0ceh],ax
	pop ax
	pop es
	sti
	ret
int33h endp

message33h1 db 'This is program of INT 33H'
message33h1length equ $-message33h1
message33h2 db '-------Zhang Haitao--------'
message33h2length equ $-message33h2
message33h3 db 'Head of the dormitory 714'
message33h3length equ $-message33h3
;中断处理程序33h
interrupt33h proc
	cli
	push es
	push si
	push di
	push ax
	push bx
	push cx
	push dx
	push bp
	push ds
	
	mov ax,cs
	mov es,ax
	mov ax,1301h
	mov bx,0001h;bl颜色
	mov dx,0408h;行，列
	mov cx,message33h1length
	mov bp,offset message33h1
	int 10h
	
	mov ax,cs
	mov es,ax
	mov ax,1301h
	mov bx,0001h;bl颜色
	mov dx,050dh;行，列
	mov cx,message33h2length
	mov bp,offset message33h2
	int 10h
	
	mov ax,cs
	mov es,ax
	mov ax,1301h
	mov bx,0001h;bl颜色
	mov dx,0608h;行，列
	mov cx,message33h3length
	mov bp,offset message33h3
	int 10h
	
	mov al,20h
	out 20h,al
	out 0a0h,al

	pop ds
	pop bp
	pop dx
	pop cx
	pop bx
	pop ax
	pop di
	pop si
	pop es
	sti
	iret
interrupt33h endp

;INT 34H 右上角显示
;填充34h中断向量表
int34h proc
	cli
	push es
	push ax
	
	xor ax,ax
	mov es,ax
	mov word ptr es:[0d0h],offset interrupt34h
	mov ax,cs
	mov word ptr es:[0d2h],ax
	
	pop ax
	pop es
	sti
	ret
int34h endp

message34h1 db 'This is program of INT 34H'
message34h1length equ $-message34h1
message34h2 db '-------Zhang Hanyu--------'
message34h2length equ $-message34h2
message34h3 db 'Member of the dormitory 714'
message34h3length equ $-message34h3
;中断处理程序34h
interrupt34h proc
	cli
	push es
	push si
	push di
	push ax
	push bx
	push cx
	push dx
	push bp
	push ds
	
	mov ax,cs
	mov es,ax
	mov ax,1301h
	mov bx,0002h;bl颜色
	mov dx,042eh;行，列
	mov cx,message34h1length
	mov bp,offset message34h1
	int 10h
	
	mov ax,cs
	mov es,ax
	mov ax,1301h
	mov bx,0002h;bl颜色
	mov dx,0534h;行，列
	mov cx,message34h2length
	mov bp,offset message34h2
	int 10h
	
	mov ax,cs
	mov es,ax
	mov ax,1301h
	mov bx,0002h;bl颜色
	mov dx,062eh;行，列
	mov cx,message34h3length
	mov bp,offset message34h3
	int 10h
	
	mov al,20h
	out 20h,al
	out 0a0h,al

	pop ds
	pop bp
	pop dx
	pop cx
	pop bx
	pop ax
	pop di
	pop si
	pop es
	sti
	iret
interrupt34h endp

;INT 35H 左下角显示
;填充35h中断向量表
int35h proc
	cli
	push es
	push ax
	
	xor ax,ax
	mov es,ax
	mov word ptr es:[0D4h],offset interrupt35h
	mov ax,cs
	mov word ptr es:[0d6h],ax
	
	pop ax
	pop es
	sti
	ret
int35h endp

message35h1 db 'This is program of INT 35H'
message35h1length equ $-message35h1
message35h2 db '-------Zhang Haoran--------'
message35h2length equ $-message35h2
message35h3 db 'Member of the dormitory 714'
message35h3length equ $-message35h3
;中断处理程序35h
interrupt35h proc
	cli
	push es
	push si
	push di
	push ax
	push bx
	push cx
	push dx
	push bp
	push ds
	
	mov ax,cs
	mov es,ax
	mov ax,1301h
	mov bx,0003h;bl颜色
	mov dx,1008h;行，列
	mov cx,message35h1length
	mov bp,offset message35h1
	int 10h
	
	mov ax,cs
	mov es,ax
	mov ax,1301h
	mov bx,0003h;bl颜色
	mov dx,110dh;行，列
	mov cx,message35h2length
	mov bp,offset message35h2
	int 10h
	
	mov ax,cs
	mov es,ax
	mov ax,1301h
	mov bx,0003h;bl颜色
	mov dx,1208h;行，列
	mov cx,message35h3length
	mov bp,offset message35h3
	int 10h
	
	mov al,20h
	out 20h,al
	out 0a0h,al

	pop ds
	pop bp
	pop dx
	pop cx
	pop bx
	pop ax
	pop di
	pop si
	pop es
	sti
	iret
interrupt35h endp

;INT 36H 右下角显示
;填充36h中断向量表
int36h proc
	cli
	push es
	push ax
	
	xor ax,ax
	mov es,ax
	mov word ptr es:[0d8h],offset interrupt36h
	mov ax,cs
	mov word ptr es:[0dah],ax
	
	pop ax
	pop es
	sti
	ret
int36h endp

message36h1 db 'This is program of INT 36H'
message36h1length equ $-message36h1
message36h2 db '-------Zhang Jiawei--------'
message36h2length equ $-message36h2
message36h3 db 'Member of the dormitory 714.'
message36h3length equ $-message36h3
;中断处理程序36h
interrupt36h proc
	cli
	push es
	push si
	push di
	push ax
	push bx
	push cx
	push dx
	push bp
	push ds
	
	mov ax,cs
	mov es,ax
	mov ax,1301h
	mov bx,0004h;bl颜色
	mov dx,102eh;行，列
	mov cx,message36h1length
	mov bp,offset message36h1
	int 10h
	
	mov ax,cs
	mov es,ax
	mov ax,1301h
	mov bx,0004h;bl颜色
	mov dx,1134h;行，列
	mov cx,message36h2length
	mov bp,offset message36h2
	int 10h
	
	mov ax,cs
	mov es,ax
	mov ax,1301h
	mov bx,0004h;bl颜色
	mov dx,122eh;行，列
	mov cx,message36h3length
	mov bp,offset message36h3
	int 10h
	
	mov al,20h
	out 20h,al
	out 0a0h,al

	pop ds
	pop bp
	pop dx
	pop cx
	pop bx
	pop ax
	pop di
	pop si
	pop es
	sti
	iret
interrupt36h endp

public _filldata
_filldata proc
	push ax
	push bx
	push cx
	push dx
	push es
	push ds
	
	mov bp,sp
	mov ax,0
	mov es,ax
	mov bx,word ptr [bp+12+2];偏移地址
	mov ax,word ptr [bp+12+4];数据
	mov word ptr es:[bx],ax
	
	pop ds
	pop es
	pop dx
	pop cx
	pop bx
	pop ax
	ret
_filldata endp
;readdata(int)
public _readdata 
_readdata proc
	push ax
	push bx
	push cx
	push dx
	push es
	push ds
	
	mov bp,sp
	mov ax,0
	mov es,ax
	mov bx,word ptr [bp+12+2];偏移地址
	mov ax,word ptr es:[bx]
	mov word ptr [_d],ax
	
	pop ds
	pop es
	pop dx
	pop cx
	pop bx
	pop ax
	ret
_readdata endp

_TEXT ends

;************DATA segment*************
_DATA segment word public 'DATA'    ;数据段，存放初始化的数据
_DATA ends                          
;*************BSS segment*************
_BSS	segment word public 'BSS'   ;数据段，存放未初始化的数据
_BSS ends                          
;**************end of file***********
end start
