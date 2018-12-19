.text 0x3000
.globl main
main:
    addu $t0,$0,$s0
    addu $t1,$0,$s1
    addu $t2,$t0,$t1
    addu $t3,$t1,$t2
    addu $t4,$t2,$t3
    addu $t5,$t3,$t4
    addu $t6,$t4,$t5
    addu $t7,$t5,$t6
end:
    ori $v0, $0 , 10
syscall
