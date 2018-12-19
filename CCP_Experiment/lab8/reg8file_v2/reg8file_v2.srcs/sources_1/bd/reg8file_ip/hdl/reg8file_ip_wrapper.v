//Copyright 1986-2016 Xilinx, Inc. All Rights Reserved.
//--------------------------------------------------------------------------------
//Tool Version: Vivado v.2016.3 (win64) Build 1682563 Mon Oct 10 19:07:27 MDT 2016
//Date        : Mon May 15 12:11:38 2017
//Host        : Freedom-computer running 64-bit major release  (build 9200)
//Command     : generate_target reg8file_ip_wrapper.bd
//Design      : reg8file_ip_wrapper
//Purpose     : IP block netlist
//--------------------------------------------------------------------------------
`timescale 1 ps / 1 ps

module reg8file_ip_wrapper
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

  wire clk;
  wire clrn;
  wire [7:0]d;
  wire [7:0]q;
  wire [2:0]rsel;
  wire wen;
  wire [2:0]wsel;

  reg8file_ip reg8file_ip_i
       (.clk(clk),
        .clrn(clrn),
        .d(d),
        .q(q),
        .rsel(rsel),
        .wen(wen),
        .wsel(wsel));
endmodule
