`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2017/05/19 08:15:02
// Design Name: 
// Module Name: top
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
module top( 
  input [7:0] input1, 
 // input [31:0] input2,
  input [3:0] aluCtr, 
 // output [31:0] aluRes,
  output [7:0] output1, 
  output  zero 
); 
  wire [31:0] input2;
  assign input2=32'h0000_000f; //input2=15
  wire [31:0] aluRes;
  wire[31:0] expand; 
  //扩展数字模块
   signext sign_expand(
                .inst(input1),
                .data(expand)
           );
   // 实例化ALU模块
   alu myalu(.input1(expand), 
           .input2(input2), 
           .aluCtr(aluCtr), 
           .aluRes(aluRes),
           .zero(zero)); 
   assign output1={aluRes[31:28],aluRes[3:0]};
endmodule
