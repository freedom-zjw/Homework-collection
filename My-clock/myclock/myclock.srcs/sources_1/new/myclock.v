`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2016/12/15 20:57:10
// Design Name: 
// Module Name: main
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
module myclock(
    input             clk100MHZ,//系统的时钟
    input       [15:0] SW,//开关
    input       [4:0]  BNT,//按钮
    output reg [15:0] LED,//led灯
    output reg [6:0]  SEG,//数码管七段码
    output reg [3:0]  AN//数码管选通
    ); 
    reg [7:0]  clk = 8'b0;//8位clock，clk[7]为2HZ,clk[0]为256HZ
    reg [5:0]  clk2 = 6'b0;//6位clock，clk[5[为8HZ
    reg [31:0] clk_cnt = 32'b0;//100MHZ计数器
    reg [7:0]  second = 0;//存储秒
    reg [7:0]  minute = 0;//存储分
    reg [7:0]  hour = 0;//存储时
    reg [3:0]  digit;//存储显示的数字
    wire [1:0]  scan;//数字的扫描显示频率
    reg [7:0]   alerh = 0;//存储闹钟的时
    reg [7:0]   alerm = 0;//存储闹钟的分
    assign scan = clk_cnt[15:14];
    
    //////////////////////////////////呼吸灯
    parameter FREQUENCE=100_000_000;
    parameter WIDTH=9;
    reg [WIDTH:0] state0;
    reg [WIDTH-1:0] state1;
    reg[31:0]cnt0;
    reg [6:0] led;
    //控制每个占空比时间
    always@(posedge clk100MHZ)
    begin
        if (cnt0==(FREQUENCE/(2**WIDTH)))
        begin
            cnt0<=0;
            state0 <= state0 + 1'b1;
        end
        else begin
            cnt0 <= cnt0 + 1'b1;
        end
    end
    //控制占空比增大与减小
    always@(posedge clk100MHZ)
    begin
        if(state0[WIDTH])state1<=state0[WIDTH-1:0];
        else state1<=~state0[WIDTH-1:0];
       
    end
    //生成与state1进行大小比较的计数器cnt1
    wire [WIDTH-1:0] time_over;
    assign time_over={WIDTH{1'b1}};
    reg[WIDTH-1:0] cnt1;
    always@(posedge clk100MHZ)
    begin
        if (cnt1==time_over)cnt1<=0;
        else cnt1<=cnt1+1'b1;
    end
    //计数器cnt1与state1进行大小比较，以使led脉冲的占空比实现渐变
    always @(posedge clk100MHZ)
    begin
        if (cnt1<=state1)
            led[6:0]<=7'b000_0000;
         else led[6:0]<=7'b111_1111;
    end
    /////////////////////////////////////////////////
    
    ////////////////////////////////////////////时钟
    //产生时钟信号
    always @(posedge clk100MHZ)
    begin
        if (clk_cnt >= 390_625)
        begin
            clk <= clk + 1;
            clk2 <= clk2 + 1;
            clk_cnt <= 32'b0;
        end else begin
            clk_cnt <= clk_cnt + 1;
        end
    end
    
   //当前时间达到闹钟设定的时间则亮呼吸灯
    always @(*)
    begin
        if (SW[13])begin
            if (alerh==hour)begin
                if (alerm==minute)begin
                    LED[15:9]=led[6:0];
                end
                else  LED[15:9]=7'b000_0000;
            end
            else  LED[15:9]=7'b000_0000;
        end
        else LED[15:9]=7'b000_0000;
    end
    
    //显示数字
    always @(*)
    begin
        case (digit)
            //                abcd_efg
            0:       SEG = 7'b0000_001;            //0
            1:       SEG = 7'b1001_111;            //1
            2:       SEG = 7'b0010_010;            //2
            3:       SEG = 7'b0000_110;            //3
            4:       SEG = 7'b1001_100;            //4
            5:       SEG = 7'b0100_100;            //5
            6:       SEG = 7'b0100_000;            //6
            7:       SEG = 7'b0001_111;            //7
            8:       SEG = 7'b0000_000;            //8
            9:       SEG = 7'b0000_100;            //9
            default: SEG = 7'b1111_111;
        endcase
    end
    
    //选择显示的位置和根据不同模式选择相应的数字
    always @(*)
    begin
        case (SW[12])
            1:begin//设置闹钟模式
                 case (scan)
                    3: begin
                        AN = 4'b0111;
                        digit = alerh / 10;
                    end
                    2: begin
                        AN = 4'b1011;
                        digit = alerh % 10;
                    end
                    1: begin
                        AN = 4'b1101;
                        digit = alerm / 10;
                    end
                    0: begin
                        AN = 4'b1110;
                        digit = alerm % 10;
                    end                     
                    default: AN = 4'b1111;
                 endcase
            end
            0:begin
                case (SW[15])
                    0:begin//显示时分的模式
                        case (scan)
                            3: begin
                                AN = 4'b0111;
                                digit = hour / 10;
                            end
                            2: begin
                                AN = 4'b1011;
                                digit = hour % 10;
                            end
                            1: begin
                                AN = 4'b1101;
                                digit = minute / 10;
                            end
                            0: begin
                                AN = 4'b1110;
                                digit = minute % 10;
                            end                     
                            default: AN = 4'b1111;
                        endcase
                    end
                    1:begin//显示分秒的模式
                        case (scan)
                            3: begin
                                AN = 4'b0111;
                                digit = minute / 10;
                            end
                            2: begin
                                AN = 4'b1011;
                                digit = minute % 10;
                            end
                            1: begin
                                AN = 4'b1101;
                                digit = second / 10;
                            end
                            0: begin
                                AN = 4'b1110;
                                digit = second % 10;
                            end                     
                            default: AN = 4'b1111;
                        endcase
                    end   
                endcase
            end
        endcase
        LED[6:0] = second[6:0]; //秒还以二进制的形式亮led灯
    end
    
    //设置闹钟时间
    always @(posedge clk2[0])
    begin
        if (clk2 == 6'b11_1110)begin
            if (SW[12] & ~SW[14] & BNT[0])begin
                alerh = (alerh +1) % 24;
            end
            else if (SW[12] & ~SW[14] & BNT[2])begin
                alerh = (alerh - 1);
                if (alerh >=24 )alerh = 23; 
            end
            else if (SW[12] & ~SW[14] & BNT[1])begin
                alerm = (alerm + 1) % 60; 
            end 
            else if (SW[12] & ~SW[14] & BNT[3])begin
                alerm = (alerm - 1 );
                if (alerm >=60 ) alerm = 59;
            end
            else if (SW[12] & ~SW[14] & BNT[4])begin
                alerm = 0;
                alerh = 0;
            end
        end
    end
    
    //时间变化和设置当前时间
    always @(posedge clk[0])
    begin     
        if (clk[7:0] == 8'b1111_1110)begin//时间变化
                second = second + 1;           
                minute = minute + second / 60;
                second = second % 60;
                hour = hour + minute / 60;
                minute = minute % 60;
                hour = hour % 24;    
        end
        if (clk[5:0] == 6'b11_1110) begin//按0.25s的速度扫描按钮，设置时间
                if (SW[14] & ~SW[12] & BNT[0]) begin
                        hour = (hour + 1) % 24;
                end 
                else if (SW[14] & ~SW[12] & BNT[2]) begin
                        hour = (hour - 1);
                        if (hour >= 24) hour = 23;
                end 
               else if (SW[14] & ~SW[12] & BNT[1]) begin
                        minute = (minute + 1) % 60;
                end 
                else if (SW[14] & ~SW[12] & BNT[3]) begin
                        minute = (minute - 1);
                        if (minute >= 60) minute = 59;
                end 
                else if (SW[14] & ~SW[12] & BNT[4]) begin
                        second=0;
                        hour=0;
                        minute=0;
                end
        end
    end

endmodule
