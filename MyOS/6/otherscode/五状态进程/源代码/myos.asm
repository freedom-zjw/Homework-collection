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
extrn _StackForOs:near
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
	mov  sp, word ptr _StackForOs
	
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




;输出一个字符串
public _printstring
_printstring proc
    push bp
	push es
	push ax
	mov	bp, sp
	mov ax,0b800h
	mov es,ax
	mov	si, word ptr [bp+2+2+2+2];取出首字符地址
	mov	di, word ptr [_pos];取出显示位置
.1:
	mov al,byte ptr [si];把字符取出给AL
	inc si;地址变为下个字符的地址
	test al,al;检测是否为空字符
	jz .2 ;是空字符就跳转.2
	cmp al,0ah;检测是否为换行
	jz .3 ;是换行就跳转.3
	;既不是空字符也不是换行，则送显示位置显示，并更新显示位置
	mov	ah, 0Fh
	mov word ptr es:[di],ax
	inc byte ptr [_y]
	call near ptr _cal_pos
	mov di,word ptr [_pos]
	jmp .1
.3:;_x加1，_y清0，更新显示地址
	inc word ptr [_x]
	mov word ptr [_y],0
	call near ptr _cal_pos
	mov di,word ptr [_pos]
	jmp	.1
.2:;设置光标后退出
	call _setcursor
    pop ax
	pop es
	pop bp
ret
_printstring  endp

;键盘输入一个字符
public _inputchar
_inputchar proc
	push ax
	call _setcursor
	mov ax,0
	int 16h
	mov byte ptr [_ch],al
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
    mov	ax, 0600h	; AH = 6,  AL = 0
	mov	bx, 0700h	; 黑底白字(BL = 7)
	mov	cx, 0		; 左上角: (0, 0)
	mov	dx, 184fh	; 右下角: (24, 79)
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



public _forking
_forking proc
   	mov ah,0
	int 21h
	ret
_forking endp

public _waiting
_waiting proc
   	mov ah,1
	int 21h
	ret
_waiting endp

public _exiting
_exiting proc
   	mov ah,2
	int 21h
	ret
_exiting endp


int21h proc 
	cli
	push ax
	push es
	xor ax,ax
	mov es,ax
	mov word ptr es:[84h],offset mysyscall
	mov ax,cs
	mov word ptr es:[86h],ax
	pop es
	pop ax
	sti
	ret
int21h endp


;跳转分支表
syscall_vector dw syscall0,syscall1,syscall2
;系统调用入口，根据ah来判断具体功能
;因为此处用到al,bx，所以al,bx不能作为系统调用的参数
mysyscall proc
	cli
	mov al,ah
	xor ah,ah
	shl ax,1  ;ax=ax*2
	mov bx,offset syscall_vector
	add bx,ax   ;中断向量加上偏移量，即可得到不同功能号的程序
	mov ax,cs
	mov es,ax
	mov ds,ax
	jmp word ptr [bx]
mysyscall endp


;系统调用0，ah=0
syscall0 proc	
   call _save
   call near ptr _do_fork
   jmp _restart
syscall0 endp


;系统调用1，ah=1
syscall1 proc
	
   call _save
   call near ptr _do_wait
   jmp _restart
syscall1 endp

;系统调用1，ah=2
syscall2 proc	

   call _save
   call near ptr _do_exit
   jmp _restart
syscall2 endp





ds_save dw ?
ret_save dw ?
si_save dw ?
kernal_sp dw ?
kernal_ss dw ?
kernal_cs dw ?	
bp_save dw ?
ax_save dw ?
sp_save dw ?

public _save 
_save proc         
	push ds   
    ;当前栈顶：psw\cs\ip\call_ret\ds	
	;PCB:
	push cs  
    ;当前栈顶：psw\cs\ip\call_ret\ds\cs 	
	;PCB:		
	pop ds        ;ds=cs     
	;当前栈顶：psw\cs\ip\call_ret\ds 	
	;PCB:				   
	mov word ptr ds:[kernal_cs],ds	
	;此时ds=cs，存下ds的值即可存下内核cs的值
	pop word ptr ds:[ds_save]
	;当前栈顶：psw\cs\ip\call_ret	
	;PCB:	
	
	pop word ptr ds:[ret_save]  
	;当前栈顶：psw\cs\ip	
	;PCB:	
	
	mov word ptr ds:[bp_save],bp
	mov word ptr ds:[ax_save],ax
	;接下来要改变ax跟bp寄存器先存下来
	
	call near ptr _Current_Process
	;返回的pcb指针在ax寄存器中
	mov bp,ax   ;bp=ax
	
	pop word ptr ds:[bp+26]  ;ip
	;当前栈顶：psw\cs	
	;PCB:	ip
	
	pop word ptr ds:[bp+28]  ;cs
	;当前栈顶：psw	
	;PCB:	cs\ip
	pop word ptr ds:[bp+30]  ;flag
	;当前栈顶:	
	;PCB:	psw\cs\ip
	
	mov word ptr ds:[bp+0], ss ;ss  
    ;当前栈顶:	
	;PCB:	psw\cs\ip\ss	
	
	mov word ptr ds:[sp_save],sp
	;在sp改变之前存下sp的值
	
	add bp, 26
	mov sp,bp
	push cs
	pop ss
	;进行栈的切换，当前栈变成了pcb中的栈
	;为什么sp=bp+26？
	;可以查看我的PCB数据结构
	;这是为了让sp=pcb中sp的真实位置
	;pcb中sp是pcb指针偏移26的位置
	
	push word ptr ds:[ax_save]
	;当前栈（pcb）：psw\cs\ip\ax
	push cx
	;当前栈（pcb）：psw\cs\ip\ax\cx
	push dx
	;当前栈（pcb）：psw\cs\ip\ax\dx
	push bx
	;当前栈（pcb）：psw\cs\ip\ax\dx\bx
	push word ptr ds:[sp_save]
	;当前栈（pcb）：psw\cs\ip\ax\dx\bx\sp
	push word ptr ds:[bp_save]
	;当前栈（pcb）：psw\cs\ip\ax\dx\bx\sp\bp
	push si
	;当前栈（pcb）：psw\cs\ip\ax\dx\bx\sp\bp\si
	push di
	;当前栈（pcb）：psw\cs\ip\ax\dx\bx\sp\bp\si\di
	push word ptr ds:[ds_save]
	;当前栈（pcb）：psw\cs\ip\ax\dx\bx\sp\bp\si\di\ds
	push es
	;当前栈（pcb）：psw\cs\ip\ax\dx\bx\sp\bp\si\di\ds\es
	.386
	push fs
	;当前栈（pcb）：psw\cs\ip\ax\dx\bx\sp\bp\si\di\ds\es\fs
	push gs
	;当前栈（pcb）：psw\cs\ip\ax\dx\bx\sp\bp\si\di\ds\es\fs\gs
	.8086
	
	;保存完毕
	mov sp, word ptr [kernal_sp]
	;让sp变回内核的sp
	
	mov ax, ds
	mov es,ax
	;es指向数据段
	
	mov ax, word ptr ds:[ret_save]
	jmp ax	
	;返回call的下一跳指令
_save endp
		
	
	

ret_save2 dw ?
public _restart
_restart proc
	mov ax,word ptr ds:[kernal_cs]  
	mov ds,ax
	mov es,ax
	;ds跟es都指向内核
	call near ptr _Current_Process
	mov bp, ax

	mov ss,word ptr [bp+0]         
	mov sp,word ptr [bp+16]
	;进行栈的切换 
	;ss,sp的值手动恢复
	
	push word ptr [bp+30]
	;当前栈:psw
	push word ptr [bp+28]
	;当前栈:psw\cs
	push word ptr [bp+26]
	;当前栈:psw\cs\ip
	push word ptr [bp+2]
	;当前栈:psw\cs\ip\gs
	push word ptr [bp+4]
	;当前栈:psw\cs\ip\gs\fs
	push word ptr [bp+6]
	;当前栈:psw\cs\ip\gs\fs\es
	push word ptr [bp+8]
	;当前栈:psw\cs\ip\gs\fs\es\ds
	push word ptr [bp+10]
	;当前栈:psw\cs\ip\gs\fs\es\ds\di
	push word ptr [bp+12]
	;当前栈:psw\cs\ip\gs\fs\es\ds\di\si
	push word ptr [bp+14]
	;当前栈:psw\cs\ip\gs\fs\es\ds\di\si\bp
	push word ptr [bp+18]
	;当前栈:psw\cs\ip\gs\fs\es\ds\di\si\bp\bx
	push word ptr [bp+20]
	;当前栈:psw\cs\ip\gs\fs\es\ds\di\si\bp\bx\dx
	push word ptr [bp+22]
	;当前栈:psw\cs\ip\gs\fs\es\ds\di\si\bp\bx\dx\cx
	push word ptr [bp+24]
    ;当前栈:psw\cs\ip\gs\fs\es\ds\di\si\bp\bx\dx\cx\ax
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
	;恢复结束
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

clock_vector dw 0,0

;设置时钟中断 08h
public _set_clock_interrupt
_set_clock_interrupt proc
	cli
	push es
	push ax
	
	call SetTimer
	
	xor ax,ax
	mov es,ax
	;save the vector
	mov ax,word ptr es:[20h]
	mov word ptr [clock_vector],ax
	mov ax,word ptr es:[22h]
	mov word ptr [clock_vector+2],ax
	;fill the vector
	mov word ptr es:[20h],offset Timer
	mov ax,cs
	mov word ptr es:[22h],ax
	pop ax
	pop es
	sti
	ret
_set_clock_interrupt endp

;恢复时钟中断
public _re_clock_interrupt
_re_clock_interrupt proc
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
_re_clock_interrupt endp
	
	

quick dw 0


Timer:
cli
call _save  
;函数保存当前正在运行程序的栈到PCB表中
call near ptr _schedule
;调度程序，轮转到下一程序
jmp _restart
;restart函数切换栈，恢复各个状态寄存器。
	
	
	
	

	
	
	
	
	
	






_TEXT ends

;************DATA segment*************
_DATA segment word public 'DATA'
_DATA ends
;*************BSS segment*************
_BSS	segment word public 'BSS'
_BSS ends
;**************end of file***********
end start
