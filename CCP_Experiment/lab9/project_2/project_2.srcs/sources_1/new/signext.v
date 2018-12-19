`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2017/05/19 08:13:46
// Design Name: 
// Module Name: signext
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


module signext( 
input [7:0] inst, // 输入8位
output [31:0] data // 输出32位
); 
 //将8位输入扩展成32位输出
 // assign data = inst[7:7]?{24'hffffff,inst}:{24'h000000,inst}; 
 assign data = {24'h000000,inst}; 
endmodule 
