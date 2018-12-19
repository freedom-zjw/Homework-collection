.data 0x0
z:.space 192 #学号15352408，则int z[48],占内存4*48=192?
str:.asciiz " " #输出间隔
.text 0x3000 
main:
	la $s0,z #$s0，表示z[k]的地址
	li $t0,0 #$t0为k，初始为0 
	li $t1,56 #$t2为Y，初始值56
loop:
	slti $t2,$t0,48 #判断k是否于48
	beq $t2,$0,exit #当k大于等于50,循环结束,退出
	srl $t3,$t0,2  #k/4
	addi $t3,$t3,210 #k/4+210
	sll $t3,$t3,4 #16*(k/4+210)
	sub $t3,$t1,$t3 #y-16*(k/4+210)
	sw  $t3,0($s0) #写进z[k]
	
	li $v0,1  #输出
	addi $a0,$s0,0 
	syscall
	
	li $v0,4  #输出间隔
	la  $a0,str 
	syscall 
	
	addi $s0,$s0,4 #地址下一位
	addi $t0,$t0,1 #k++
	j loop  #循环
exit:
	li $v0,10
    syscall
    