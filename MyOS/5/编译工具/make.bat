del  *.obj
del  *.bin
del  *.img
del *.com
del  *.map
tcc -c cmain.c
tasm klibc.asm klibc.obj
tasm myos.asm myos.obj
tlink/3/t myos.obj klibc.obj cmain.obj,os.com

