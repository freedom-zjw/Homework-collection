 main:
	lui	$a0,0x8000  #$a0=1后跟31个0
	jal	first1pos
	jal	printv0
	lui	$a0,0x0001  #15个0,1,16个0
	jal	first1pos
	jal	printv0
	li	$a0,1      #$a0=1
	jal	first1pos
	jal	printv0
	add	$a0,$0,$0  #$a0=0
	jal	first1pos
	jal	printv0
	li	$v0,10
	syscall

first1pos:	# your code goes here
        move	$fp, $sp
        addiu   $sp,$sp,-20
        sw      $a0,16($sp)
        sw      $ra,20($sp)
        
        addiu   $a0,$0,31  #初始化v0=31
        sw      $a0,4($sp)
        
        addiu   $a0,$0,0x80000000 #掩码
        sw      $a0,8($sp)
        
        addiu   $a0,$0,-1   #终止条件
        sw      $a0,12($sp)

        loop:   
            lw $a0,16($sp)
            lw $a1,8($sp)
        	and $a1,$a0,$a1
        	bne $a1,$0,return   #and了之后不为0说明当前位为1
        	
        	lw $a1,4($sp)
            addiu $a1,$a1,-1    #v0-=1
            sw $a1,4($sp)
            
            lw $a0,12($sp)
            beq $a1,$a0,return  #v0到-1就退出循环,说明$a0=0
            
            lw $a0,16($sp)
            sll $a0,$a0,1       #将原数左移一位
            sw $a0,16($sp)
            beq $a0,$a0,loop    
        return:
            lw  $v0,4($sp)
            lw  $ra,20($sp)
            addi $sp,$sp,20
	  	    jr  $ra
printv0:
	addi	$sp,$sp,-4
	sw	$ra,0($sp)
	add	$a0,$v0,$0
	li	$v0,1
	syscall
	li	$v0,11
	li	$a0,'\n'
	syscall
	lw	$ra,0($sp)
	addi	$sp,$sp,4
	jr	$ra
