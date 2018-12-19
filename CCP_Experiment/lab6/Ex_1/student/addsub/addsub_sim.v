`timescale 1ns / 100ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2016/02/15 22:05:23
// Design Name: 
// Module Name: addsub_sim
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

module addsub_sim(    );
    // input
    reg [7:0] a = 8'b0111_1111;
    reg [7:0] b = 8'b0011_1111;
    reg sub = 0;
    
    //output
    wire [7:0] sum;
    wire cf;
    wire ovf;
    wire sf;
    wire zf;
   addsub8_wrapper addsub8
           (.a(a),
            .b(b),
            .cf(cf),
            .ovf(ovf),
            .sf(sf),
            .sub(sub),
            .sum(sum),
            .zf(zf));
    // initial
    addsub #(8) U (a,b,sub,sum,cf,ovf,sf,zf);
    initial begin
    #50 sub = 1;
    #50 begin a = 8'b0000_1111; b = 8'b0000_0010; sub = 0; end
    #50 begin a = 8'b0000_0011; b = 8'b0000_0010; sub = 0; end
    #50 begin a = 8'b0111_1111; b = 8'b0000_0010; sub = 0; end
    #50 begin a = 8'b0001_0110; b = 8'b0001_0111; sub = 1; end
    #50 begin a = 8'b0000_1111; b = 8'b0000_0001; sub = 0; end
    #50 begin a = 8'b1111_1111; b = 8'b0000_0001; sub = 0; end
    end
endmodule