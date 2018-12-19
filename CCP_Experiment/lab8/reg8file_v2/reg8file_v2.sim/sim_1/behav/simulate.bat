@echo off
set xv_path=D:\\Xilinx\\Vivado\\2016.3\\bin
call %xv_path%/xsim reg8file_sim_behav -key {Behavioral:sim_1:Functional:reg8file_sim} -tclbatch reg8file_sim.tcl -log simulate.log
if "%errorlevel%"=="0" goto SUCCESS
if "%errorlevel%"=="1" goto END
:END
exit 1
:SUCCESS
exit 0
