function getstyle(sname) { 
    return document.getElementById(sname).style
} 

var move=true;
var count=77;
var index=0;
var left=0;
var id=0;

function SlideOneImg() {
    if (count < 77) {
        getstyle("slides").left = (left-10) + "px";
        left = left - 10;
        count = count + 1;
        setTimeout("SlideOneImg()", 15);
    }
    else if (left <= -2310){
        getstyle("slides").left = "0px";
        left = 0;
    }
}

function slide() {
    if (move) {
        count = 0;
        var p1, p2= null;
        if (index==3)p1 = getstyle("p1")
        else p1 = getstyle("p" + (index+1).toString())
        index = (index + 1) % 4;
        if (index==3)p2 = getstyle("p1")
        else p2 = getstyle("p" + (index+1).toString())
        SlideOneImg();
        p1.top = "0px";
        p2.top = "-24px";
        if (index==3) index = 0;
    }
    setTimeout("slide()", 3000);
}

function MouseOver() {
    move = false;
    getstyle("arrow1").visibility="visible";
    getstyle("arrow2").visibility="visible";
}

function MouseOut() {
    move = true;
    getstyle("arrow1").visibility="hidden";
    getstyle("arrow2").visibility="hidden";
}

function main() {
    setTimeout("slide()", 3000);
}

function click(idx) {
    var p1, p2 = null;
    count=77;
    p1 = getstyle("p" + (index+1).toString())
    index=idx;
    p2 = getstyle("p" + (index+1).toString())
    if (index == 0)left = 0;
    else if (index == 1)left = -10*77;
    else left = -10*77*2;
    getstyle("slides").left = left + "px";
    p1.top = "0px";
    p2.top = "-24px";

}

function click0() {click(0);}
function click1() {click(1);}
function click2() {click(2);}

function goLeft() {
    while (count < 77);
    p = getstyle("p" + (index+1).toString())
    p.top = "0px";
    index = (index - 1 + 3) % 3;
    p = getstyle("p" + (index+1).toString())
    p.top = "-24px";
    if (index == 0)left = 0;
    else if (index == 1)left = -10*77;
    else left = -10*77*2;
    getstyle("slides").left = left + "px";
}

function goRight() {
    while (count < 77);
    p = getstyle("p" + (index+1).toString())
    p.top = "0px";
    index = (index + 1) % 3;
    p = getstyle("p" + (index+1).toString())
    p.top = "-24px";
    if (index == 0)left = 0;
    else if (index == 1)left = -10*77;
    else left = -10*77*2;
    getstyle("slides").left = left + "px";
}