extrn _cmain:near 
extrn _cal_pos:near
extrn _pos:near
extrn _ch:near
extrn _x:near
extrn _y:near
extrn _offset_user:near
extrn _d:near
extrn _offset_user1:near
extrn _offset_user2:near
extrn _offset_user3:near
extrn _offset_user4:near
extrn _do_fork:near
extrn _do_wait:near
extrn _do_exit:near

extrn _Current_Process
extrn _Save_Process
extrn _schedule
extrn _Have_Program
extrn _special
extrn _Program_Num
extrn _CurrentPCBno
extrn _OsStack:near
extrn _cmain:near

_TEXT segment byte public 'CODE'
DGROUP group _TEXT,_DATA,_BSS
	assume cs:_TEXT
	org 100h
start:
	mov  ax,  cs
	mov  ds,  ax         
	mov  es,  ax       
	mov  ss, ax      
	mov  sp, word ptr _OsStack
	
	call int21h;
	call near ptr _cmain
	jmp $

;置光标位置
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
public _printchar
_printchar proc
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
	call near ptr _cal_pos ;重新计算坐标
	pop bx
	pop bp
	pop es
	pop ax
	ret
_printchar endp


;输出字符串
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
	call near ptr _cal_pos
	mov di,word ptr [_pos]
	jmp .1
.3:;_x加1，_y清1，更新显示地址
	inc word ptr [_x]
	mov word ptr [_y],1
	call near ptr _cal_pos
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
public _inputchar
_inputchar proc
	push ax
	call _setcursor
	mov ax,0
	int 16h;16h中断，从键盘键入一个字符
	mov byte ptr [_ch],al;键入的字符存放在al，移动到ch变量中
	pop ax
ret
_inputchar endp

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
    mov al,byte ptr [bp+12+4] ;扇区数
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



public _fork
_fork proc
   	mov ah,0
	int 21h
	ret
_fork endp

public _wait
_wait proc
   	mov ah,1
	int 21h
	ret
_wait endp

public _exit
_exit proc
   	mov ah,2
	int 21h
	ret
_exit endp

int21h proc 
	cli
	push ax
	push es
	xor ax,ax
	mov es,ax
	mov word ptr es:[84h],offset int21FUN
	mov ax,cs
	mov word ptr es:[86h],ax
	pop es
	pop ax
	sti
	ret
int21h endp

FUN_VECTOR dw FUN0,FUN1,FUN2
int21FUN proc
	cli
	mov al,ah
	xor ah,ah
	shl ax,1  
	mov bx,offset FUN_VECTOR
	add bx,ax   
	mov ax,cs
	mov es,ax
	mov ds,ax
	jmp word ptr [bx]
int21FUN endp

;do_fork
FUN0 proc	
   call _save
   call near ptr _do_fork
   jmp _restart
FUN0 endp
;do_wait
FUN1 proc
   call _save
   call near ptr _do_wait
   jmp _restart
FUN1 endp
;do_exit
FUN2 proc	
   call near ptr _do_exit
   jmp _restart
FUN2 endp

;Save  
ax_s dw ?
bp_s dw ?
ds_s dw ?
si_s dw ?
sp_s dw ?
ret_s dw ?
cs_kernal dw ?	
sp_kernal dw ?
ss_kernal dw ?
public _save 
_save proc         
	push ds   
	push cs  
	pop ds        
	mov word ptr ds:[cs_kernal],ds;ds=cs时,ds指向内核,保存ds即保存内核cs
	pop word ptr ds:[ds_s]
	pop word ptr ds:[ret_s]  
	mov word ptr ds:[bp_s],bp;保存内核bp
	mov word ptr ds:[ax_s],ax;保存内核ax
	
	call near ptr _Current_Process;返回的pcb指针在ax寄存器中
	mov bp,ax   
	
	pop word ptr ds:[bp+26]  ;ip
	pop word ptr ds:[bp+28]  ;cs
	pop word ptr ds:[bp+30]  ;flag
	mov word ptr ds:[bp+0],ss ;ss  
	mov word ptr ds:[sp_s],sp
	
	add bp, 26 ;将栈的指针指向pcb中的栈
	mov sp,bp
	push cs
	pop ss
	
	push word ptr ds:[ax_s]
	push cx
	push dx
	push bx
	push word ptr ds:[sp_s]
	push word ptr ds:[bp_s]
	push si
	push di
	push word ptr ds:[ds_s]
	push es
	.386
	push fs
	push gs
	.8086
	

	mov sp, word ptr [sp_kernal];恢复sp
	mov ax, ds
	mov es,ax ;es指向数据段	
	mov ax, word ptr ds:[ret_s]
	jmp ax	;调至返回地址
_save endp
;恢复
ret_s2 dw ?
public _restart
_restart proc
	mov ax,word ptr ds:[cs_kernal]  
	mov ds,ax
	mov es,ax
	call near ptr _Current_Process
	mov bp, ax
	;切换栈
	mov ss,word ptr [bp+0]         
	mov sp,word ptr [bp+16] ;ss和sp需要手动恢复
	;其他寄存器直接恢复
	push word ptr [bp+30]
	push word ptr [bp+28]
	push word ptr [bp+26]
	push word ptr [bp+2]
	push word ptr [bp+4]
	push word ptr [bp+6]
	push word ptr [bp+8]
	push word ptr [bp+10]
	push word ptr [bp+12]
	push word ptr [bp+14]
	push word ptr [bp+18]
	push word ptr [bp+20]
	push word ptr [bp+22]
	push word ptr [bp+24]
	pop ax
	pop cx
	pop dx
	pop bx
	pop bp
	pop si
	pop di
	pop ds
	pop es
	.386
	pop fs
	pop gs
	.8086

	push ax         
	mov al,20h
	out 20h,al
	out 0A0h,al
	pop ax
	 iret   
	;结束中断返回。
_restart endp

SetTimer: 
    push ax
    mov al,34h   ; 设控制字值 
    out 43h,al   ; 写控制字到控制字寄存器 
    mov ax,29830 ; 每秒 20 次中断（50ms 一次） 
    out 40h,al   ; 写计数器 0 的低字节 
    mov al,ah    ; AL=AH 
    out 40h,al   ; 写计数器 0 的高字节 
	pop ax
	ret
	
;时钟中断 08h
clock_vector dw 0,0
public _set_clock
_set_clock proc
	cli
	push es
	push ax
	call SetTimer
	xor ax,ax
	mov es,ax
	mov ax,word ptr es:[20h]
	mov word ptr [clock_vector],ax
	mov ax,word ptr es:[22h]
	mov word ptr [clock_vector+2],ax
	;fill the vector
	mov word ptr es:[20h],offset Pro_Timer
	mov ax,cs
	mov word ptr es:[22h],ax
	pop ax
	pop es
	sti
	ret
_set_clock endp

;恢复时钟中断
public _restart_clock
_restart_clock proc
	cli
	push es
	push ax
	xor ax,ax
	mov es,ax
	mov ax,word ptr [clock_vector]
	mov word ptr es:[20h],ax
	mov ax,word ptr [clock_vector+2]
	mov word ptr es:[22h],ax
	pop ax
	pop es
	sti
	ret
_restart_clock endp
	
Pro_Timer:
cli
call _save;保存到PCB
call near ptr _schedule;调度程序
jmp _restart;恢复寄存器
	
_TEXT ends

;************DATA segment*************
_DATA segment word public 'DATA'
_DATA ends
;*************BSS segment*************
_BSS	segment word public 'BSS'
_BSS ends
;**************end of file***********
end start
