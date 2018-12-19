org  7c00h		; BIOS将把引导扇区加载到0:7C00h处，并开始执行
Start:
     ;读软盘或硬盘上的若干物理扇区到内存的ES:BX处：
      mov ax,800h                ;段地址 ; 存放数据的内存基地址
      mov es,ax                ;设置段地址（不能直接mov es,段地址）
      mov bx,100h  ;偏移地址; 存放数据的内存偏移地址
      mov ah,2                 ; 功能号
      mov al,15                 ;扇区数
      mov dl,0                 ;驱动器号 ; 软盘为0，硬盘和U盘为80H
      mov dh,1                 ;磁头号 ; 起始编号为0
      mov ch,0                 ;柱面号 ; 起始编号为0
      mov cl,2                ;起始扇区号 ; 起始编号为1
      int 13H ;                调用读磁盘BIOS的13h功能
      jmp 800h:100h
	  
AfterRun:
      jmp $                     

	  times 510-($-$$) db 0
	  db 0x55,0xaa
	  incbin '31.bin'
	  incbin '32.bin'
	  incbin '33.bin'
	  incbin '34.bin'
	  incbin 'test.bin'
	  incbin 'p1.bin'
	  incbin 'p2.bin'
	  incbin 'p3.bin'
	  incbin 'p4.bin'
	  incbin 'lab4os.com'
