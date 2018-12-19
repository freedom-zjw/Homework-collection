;声明外部函数
extrn _cmain:near 
extrn _calpos:near
;声明外部变量
extrn _pos:near
extrn _ch:near
extrn _x:near
extrn _y:near
extrn _offset_user:near
extrn _d:near

_TEXT segment byte public 'CODE'    ;定义代码段
DGROUP group _TEXT,_DATA,_BSS       ;将_TEXT,_DATA,_BSS合成一组
	assume cs:_TEXT                 ;关联段名与段寄存器
	org 100h                        ;定义全局基地址
start:
	mov  ax,  cs
	add  ax,800h         ;800h*10h+100h=8100h
	mov  ds,  ax         
	mov  es,  ax        
	mov  ss,  ax      
	mov  sp, 100h
	;设置段地址之后，访问c代码中的_camain
	call near ptr _cmain
	jmp $               ;访问当前指令，即死循环
	
;置光标位置
public _setcursor
_setcursor proc
	;保存现场
	push ax
	push bx
	push dx
	;显示光标
	mov ah,02h
	mov dh,byte ptr [_x]
	mov dl,byte ptr [_y]
	mov bh,0
	int 10h
	;恢复现场
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

;输出一个字符串
public _printstr
_printstr proc
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
	call near ptr _calpos
	mov di,word ptr [_pos]
	jmp .1
.3:;_x加1，_y清0，更新显示地址
	inc word ptr [_x]
	mov word ptr [_y],0
	call near ptr _calpos
	mov di,word ptr [_pos]
	jmp	.1
.2:;设置光标后退出
	call _setcursor
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
	mov bp,sp        ;这个sp是什么时候更改的？
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
	call word ptr [_offset_user]
	pop ds
	pop es
	pop dx
	pop cx
	pop bx
	pop ax
	ret
_jmp endp
;filldata(int,int)
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
