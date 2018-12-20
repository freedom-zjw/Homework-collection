`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2016/12/11 17:02:29
// Design Name: 
// Module Name: lightstudentnum
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


module lightstudentnum( 
    input wire clk,
    input wire reset,
    output reg [6:0] light,
    output reg[3:0] an,
    output wire dp
    );
    reg [3:0] state,nextstate;
    parameter S0=4'b0000,S1=4'b0001,S2=4'b0010,S3=4'b0011,S4=4'b0100,S5=4'b0101,
                S6=4'b0110,S7=4'b0111,S8=4'b1000,S9=4'b1001,S10=4'b1010,S11=4'b1011;
    
    reg [25:0] clkdiv;
    reg [3:0] digit;
    wire [3:0] aen;
    wire [1:0] s;
    wire c;
    assign s=clkdiv[19:18];
    assign c=clkdiv[25:25];
    assign aen=4'b1111;
    assign dp=1;
    
    //选择S管显示的数字
    always@(*)
        case(s)
            3:  if (state==S4) digit = 4'b0001;//1
                else if (state==S5)digit = 4'b0101;//5
                else if (state==S6)digit = 4'b0011;//3
                else if (state==S7)digit = 4'b0101;//5
                else if (state==S8)digit = 4'b0010;//2
                else if (state==S9)digit = 4'b0100;//4
                else if (state==S10)digit = 4'b0000;//0
                else if (state==S11)digit = 4'b1000;//8
                else digit = 4'b1111;
            2:  if (state==S3)digit = 4'b0001;//1
                else if (state==S4)digit = 4'b0101;//5
                else if (state==S5)digit = 4'b0011;//3
                else if (state==S6)digit = 4'b0101;//5
                else if (state==S7)digit = 4'b0010;//2
                else if (state==S8)digit = 4'b0100;//4
                else if (state==S9)digit = 4'b0000;//0        
                else if (state==S10)digit = 4'b1000;//8       
                else digit = 4'b1111;
            1:  if (state==S2)digit = 4'b0001;//1
                else if (state==S3)digit = 4'b0101;//5
                else if (state==S4)digit = 4'b0011;//3
                else if (state==S5)digit = 4'b0101;//5
                else if (state==S6)digit = 4'b0010;//2
                else if (state==S7)digit = 4'b0100;//4
                else if (state==S8)digit = 4'b0000;//0
                else if (state==S9)digit = 4'b1000;//8
                else digit = 4'b1111;
            0:  if (state==S1)digit = 4'b0001;//1
                else if (state==S2)digit = 4'b0101;//5
                else if (state==S3)digit = 4'b0011;//3
                else if (state==S4)digit = 4'b0101;//5
                else if (state==S5)digit = 4'b0010;//2
                else if (state==S6)digit = 4'b0100;//4
                else if (state==S7)digit = 4'b0000;//0
                else if (state==S8)digit = 4'b1000;//8
                else digit = 4'b1111;
            default:digit = 4'b1111;
        endcase
    
    //state change
    always @(posedge reset or posedge c)
        if (reset) state <= S0;
        else if (c)state <= nextstate;
    
    //nextstate change
    always @(*)
        case (state)
            S0: nextstate = S1;
            S1: nextstate = S2;
            S2: nextstate = S3;
            S3: nextstate = S4;
            S4: nextstate = S5;
            S5: nextstate = S6;
            S6: nextstate = S7;
            S7: nextstate = S8;
            S8: nextstate = S9;
            S9: nextstate = S10;
            S10: nextstate = S11;
            S11: nextstate = S0;
            default: nextstate = S0;
        endcase
        
    //7段数码管编码
    always @(*)
        case(digit)
            //          a-g低电平有效
            0: light = 7'b0000001;
            1: light = 7'b1001111;
            2: light = 7'b0010010;
            3: light = 7'b0000110;
            4: light = 7'b1001100;
            5: light = 7'b0100100;
            6: light = 7'b0100000;
            7: light = 7'b0001111;
            8: light = 7'b0000000;
            9: light = 7'b0000100;
            default: light = 7'b1111111;
        endcase
     //选择哪个位置显示数字
     always@(*)
            begin
            an=4'b1111;
                if(aen[s]==1)
                    an[s]=0;
            end
        
     //时钟计数
     always @(posedge clk or posedge reset)
            begin
                if(reset)
                    clkdiv<=0;
                else 
                    clkdiv<=clkdiv+1;
            end
endmodule
