module reg8file(
    input clk,
    input clrn,
    input wen,
    input [7:0] d,
    input [2:0] wsel,
    input [2:0] rsel,
    output reg [7:0] q
    );
   reg [7:0] we_n; 
   wire [7:0] r0,r1,r2,r3,r4,r5,r6,r7;
   reg8  rf0(clk,clrn,we_n[0],d,r0); 
   reg8  rf1(clk,clrn,we_n[1],d,r1); 
   reg8  rf2(clk,clrn,we_n[2],d,r2); 
   reg8  rf3(clk,clrn,we_n[3],d,r3); 
   reg8  rf4(clk,clrn,we_n[4],d,r4); 
   reg8  rf5(clk,clrn,we_n[5],d,r5); 
   reg8  rf6(clk,clrn,we_n[6],d,r6); 
   reg8  rf7(clk,clrn,we_n[7],d,r7); 
   
   always @(*)
   begin
     if(!wen)
     begin
        case(wsel)
            3'b000:we_n=8'b11111110;
            3'b001:we_n=8'b11111101;
            3'b010:we_n=8'b11111011;
            3'b011:we_n=8'b11110111;
            3'b100:we_n=8'b11101111;
            3'b101:we_n=8'b11011111;
            3'b110:we_n=8'b10111111;
            3'b111:we_n=8'b01111111;
            default we_n=8'b11111111;
        endcase
     end else begin
       we_n  = 8'b11111111;
     end
	 
     case(rsel)
       3'b000:q=r0;
       3'b001:q=r1;
       3'b010:q=r2;
       3'b011:q=r3;
       3'b100:q=r4;
       3'b101:q=r5;
       3'b110:q=r6;
       3'b111:q=r7;
     default q=8'bzzzzzzzz;
    endcase
   end
endmodule

