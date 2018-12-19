`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2016/01/14 16:12:25
// Design Name: 
// Module Name: reg8
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


module reg8(
    input clk,
    input clrn,
    input wen,
    input [7:0] d,
    output [7:0] q
    );
    dffe r0(clk,clrn,wen,d[0],q[0]);
    dffe r1(clk,clrn,wen,d[1],q[1]);
    dffe r2(clk,clrn,wen,d[2],q[2]);
    dffe r3(clk,clrn,wen,d[3],q[3]);
    dffe r4(clk,clrn,wen,d[4],q[4]);
    dffe r5(clk,clrn,wen,d[5],q[5]);
    dffe r6(clk,clrn,wen,d[6],q[6]);
    dffe r7(clk,clrn,wen,d[7],q[7]);
endmodule