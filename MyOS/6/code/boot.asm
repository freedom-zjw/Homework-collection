;该程序为引导程序，作用是读磁盘并且加载到内核程序
org 7c00h  ;将引导扇区加载到0：7c00h，并开始执行
OffSetOfKernal equ 100h;这是内核程序的偏移地址，由自己设定，但是要大于引导程序的加载地址即7c00h
Start:
    mov ax,cs    ;cs代码段寄存器,其值为代码段的段值
	mov es,ax    ;es=ax,附加段寄存器(Extra Segment Register)，其值为附加数据段的段值
	mov bx, 8100h  ;bx=offsetofkernal
	;下面是中断号为16H的2号功能调用，该功能是读取磁盘
	mov ah, 2    
	mov al, 24   ;扇区数为24
	mov dl, 0   ;软盘的驱动器号
	;软盘为0，硬盘和U盘为80H
	mov dh, 0   ;软盘磁头号为0
	mov ch, 0  ;软盘柱面号
	mov cl,2    ;起始扇区号，这里为10，起始编号为1
	int 13H     ;调用读磁盘的中断号
	;jmp OffSetOfKernal ;跳至内核程序
	jmp 800h:100h
	
AfterRun:
    jmp $         ;无限循环
times 510-($-$$) db 0
db 0x55,0xaa         
incbin 'os.com'
	  
	  
	  
