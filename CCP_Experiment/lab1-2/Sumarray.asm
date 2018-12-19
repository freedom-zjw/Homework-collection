.data 0x0
sum: .space 4
i: .space 4
a: .half 7,8,9,10,8
.text 0x3000
.globl main
main: 
	sh $0,0($0)  #sum=0;
	sh $0,4($0)  # for (i = 0;
	lh $9,4($0)  # allocate register for i
	lh $8,0($0)  # choose register $8 to hold value for 
loop:
	sll $10, $9, 1 # covert "i" to halfword offset
	lh $10, 8($10) # load a[i]
	addu $8,$8,$10  # sum = sum + a[i];
	sh $8, 0($0)    # update variable in memory
	addi $9, $9,1   # for (...; ...; i++
	sh $9, 4($0)    # update memory
	slti $10, $9,5  # for (...; i<5;
	bne $10, $0, loop
end:
	ori $v0, $0 , 10
	syscall
