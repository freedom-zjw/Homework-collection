del  *.obj
del  *.bin
del  *.img
del *.com
del  *.map
tcc -c cmain.c
tasm os.asm os.obj
tlink/3/t os.obj cmain.obj,os.com

