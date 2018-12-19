@echo off
set xv_path=D:\\Xilinx\\Vivado\\2016.3\\bin
call %xv_path%/xsim alusim_behav -key {Behavioral:sim_1:Functional:alusim} -tclbatch alusim.tcl -log simulate.log
if "%errorlevel%"=="0" goto SUCCESS
if "%errorlevel%"=="1" goto END
:END
exit 1
:SUCCESS
exit 0
