@echo off
set xv_path=D:\\Xilinx\\Vivado\\2016.3\\bin
call %xv_path%/xelab  -wto 29167f3d2923474aae061e3a59e582f4 -m64 --debug typical --relax --mt 2 -L xil_defaultlib -L unisims_ver -L unimacro_ver -L secureip -L xpm --snapshot reg8file_sim_behav xil_defaultlib.reg8file_sim xil_defaultlib.glbl -log elaborate.log
if "%errorlevel%"=="0" goto SUCCESS
if "%errorlevel%"=="1" goto END
:END
exit 1
:SUCCESS
exit 0
