.data 0x0
i: .space 4
foo: .word 1,2,3,4,5
.text 0x3000
.globl _start
_start:
 	sw $0,0($0) #i=0
 	lw $9,0($0)
 loop:
 	sll $10, $9, 2  #转化i to word offset
 	lw  $8, 4($10)   #load foo[i]
 	addiu $8 ,$8, 2  #foo[i]+=2
 	sw  $8, 4($10) 
 	addiu $9, $9, 1 #i=i+1
 	slti $10, $9, 5  #i>=5 则$10=0，反之=1
 	bne $10, $0, loop #$10不等于0则继续循环
 end:
	ori $v0, $0 , 10
syscall	
    