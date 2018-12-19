`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2017/05/12 23:04:46
// Design Name: 
// Module Name: reg8file_sim
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


module reg8file_sim(

    );
    reg clk;
    reg clrn;
    reg wen;
    reg[7:0] d;
    wire[7:0] q;
    reg[2:0] wsel;
    reg[2:0] rsel;
    reg8file_ip reg_sim(
        .clk(clk),
        .clrn(clrn),
        .wen(wen),
        .d(d),
        .wsel(wsel),
        .rsel(rsel),
        .q(q)
    );

    initial begin
    #0 begin clrn=0;clk=0;wen=0;wsel=3'b001;rsel=3'b001;d=8'b0000001;end //清零端开着，不会亮
    #5 begin clrn=1;end //清零端关闭 也没变化，因为clk不在上升沿
    #5 begin clk=1;end  //上升沿
    #10 begin clk=0;wsel=3'b010;rsel=3'b010;d=8'b0000010;end //二号寄存器存2
    #10 begin clk=1;end
    #10 begin clk=0;d=8'b0000011;end //二号寄存器存3 
    #10 begin clk=1;end
    #10 begin clk=0;wen=1;d=8'b0000100;end //关闭使能，二号寄存器值没变
    #10 begin clk=1;end
    #10 begin clk=0;wsel=3'b011;rsel=3'b011;d=8'b0000110;end //使能未开，三号寄存器存2失败
    #10 begin clk=1;end
    #10 begin clk=0;wen=0;end //使能打开，二号寄存器成功存3 
    #10 begin clk=1;end
    #10 begin clk=0;clrn=0;end//清零
    #10 begin clk=1;end
    end
endmodule

