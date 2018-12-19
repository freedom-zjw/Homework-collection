`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2017/05/18 20:48:19
// Design Name: 
// Module Name: alusim
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

module alusim; 
// Inputs 
reg [7:0] input1; 
reg [3:0] aluCtr; 
wire [7:0] output1;
wire zero; 
// Instantiate the Unit Under Test (UUT) 
top uut ( 
    .input1(input1), 
    .aluCtr(aluCtr), 
   .output1(output1),
    .zero(zero)
); 
initial begin 
// Initialize Inputs 
    input1 = 1;      //1-15
    aluCtr = 4'b0110; 
    #100;           //15-15
        input1 = 15; 
        aluCtr = 4'b0110; 
    #100           //1+15
        input1 = 1;  
        aluCtr = 4'b0010; 
    #100          //1&15
        input1 = 1; 
        aluCtr = 4'b0000; 
    #100          //1|15
        input1 = 1; 
        aluCtr = 4'b0001; 
    #100          //1<15
        input1 = 1; 
        aluCtr = 4'b0111; 
    #100        //16<15
        input1 = 16; 
        aluCtr = 4'b0111; 
end 
endmodule 
