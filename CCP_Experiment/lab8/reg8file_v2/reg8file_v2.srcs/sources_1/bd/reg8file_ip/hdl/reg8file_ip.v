//Copyright 1986-2016 Xilinx, Inc. All Rights Reserved.
//--------------------------------------------------------------------------------
//Tool Version: Vivado v.2016.3 (win64) Build 1682563 Mon Oct 10 19:07:27 MDT 2016
//Date        : Mon May 15 12:11:38 2017
//Host        : Freedom-computer running 64-bit major release  (build 9200)
//Command     : generate_target reg8file_ip.bd
//Design      : reg8file_ip
//Purpose     : IP block netlist
//--------------------------------------------------------------------------------
`timescale 1 ps / 1 ps

(* CORE_GENERATION_INFO = "reg8file_ip,IP_Integrator,{x_ipVendor=xilinx.com,x_ipLibrary=BlockDiagram,x_ipName=reg8file_ip,x_ipVersion=1.00.a,x_ipLanguage=VERILOG,numBlks=1,numReposBlks=1,numNonXlnxBlks=1,numHierBlks=0,maxHierDepth=0,numSysgenBlks=0,numHlsBlks=0,numHdlrefBlks=0,numPkgbdBlks=0,bdsource=USER,synth_mode=OOC_per_IP}" *) (* HW_HANDOFF = "reg8file_ip.hwdef" *) 
module reg8file_ip
   (clk,
    clrn,
    d,
    q,
    rsel,
    wen,
    wsel);
  input clk;
  input clrn;
  input [7:0]d;
  output [7:0]q;
  input [2:0]rsel;
  input wen;
  input [2:0]wsel;

  wire clk_1;
  wire clrn_1;
  wire [7:0]d_1;
  wire [7:0]reg8file_0_q;
  wire [2:0]rsel_1;
  wire wen_1;
  wire [2:0]wsel_1;

  assign clk_1 = clk;
  assign clrn_1 = clrn;
  assign d_1 = d[7:0];
  assign q[7:0] = reg8file_0_q;
  assign rsel_1 = rsel[2:0];
  assign wen_1 = wen;
  assign wsel_1 = wsel[2:0];
  reg8file_ip_reg8file_0_0 reg8file_0
       (.clk(clk_1),
        .clrn(clrn_1),
        .d(d_1),
        .q(reg8file_0_q),
        .rsel(rsel_1),
        .wen(wen_1),
        .wsel(wsel_1));
endmodule
