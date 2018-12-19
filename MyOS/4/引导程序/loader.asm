org  7c00h		; BIOS将把引导扇区加载到0:7C00h处，并开始执行
SegOfKernal equ 8000h ;内核加载到的段地址
OffsetOfKernal equ 100h;内核加载到的偏移地址
Start:
     ;读软盘或硬盘上的若干物理扇区到内存的ES:BX处：
      mov ax,SegOfKernal                ;段地址 ; 存放数据的内存基地址
      mov es,ax                ;设置段地址（不能直接mov es,段地址）
      mov bx,OffsetOfKernal ;偏移地址; 存放数据的内存偏移地址
      mov ah,2                 ; 功能号
      mov al,10                 ;扇区数
      mov dl,0                 ;驱动器号 ; 软盘为0，硬盘和U盘为80H
      mov dh,0                 ;磁头号 ; 起始编号为0
      mov ch,0                 ;柱面号 ; 起始编号为0
      mov cl,12                ;起始扇区号 ; 起始编号为1
	 
      int 13H ;                调用读磁盘BIOS的13h功能
      jmp SegOfKernal:OffsetOfKernal
	  
AfterRun:
      jmp $    	  ;无限循环
DEFINE:
      Message db'hello'
	  Message_length equ ($-Message)
	  times 510-($-$$) db 0
	  db 0x55,0xaa
	  
	  incbin 'orange1.bin'
	  incbin 'orange2.bin'
	  incbin 'orange3.bin'
	  incbin 'orange4.bin'
	  incbin 'orange5.bin'
	  incbin 'os.com'
	  ;incbin 't1.bin'