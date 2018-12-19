del  *.obj
del  *.bin
del  *.img
del *.com
del  *.map
tcc -c c.c
tasm os.asm os.obj
tlink/3/t os.obj c.obj,os.com

