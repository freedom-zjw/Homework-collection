import csv
import os
from os.path import join


def changedata1(Inputpath):
    reader = csv.reader(open(Inputpath))
    next(reader)
    Alldata = []
    newData = []
    for row in reader:
        if row[0].strip() == "0":
            continue
        if row[-1].strip() == "?":
            row = row[1:-1]
        else:
            row = row[1:]
        Alldata.append(row)

    for i in range(len(Alldata)):
        row = Alldata[i]
        len2 = len(row)
        line = []
        line.append(row[0])
        for j in range(1, len2):
            if row[j].strip() == "?":
                if i == 0:
                    row[j] = str(float(Alldata[i+1][j])/2)
                elif i == len(Alldata) - 1:
                    row[j] = str(float(Alldata[i-1][j])/2)
                else:
                    f1 = float(Alldata[i-1][j])
                    f2 = float(Alldata[i+1][j])
                    row[j] = str((f1+f2)/2)
            line.append(row[j])
        newData.append(line)
    return newData


def changedata2(Alldata, Outputpath):
    writer = open(Outputpath, "w", encoding="utf-8", newline="")
    content = ""
    weekday = [0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    for i in range(1, len(weekday)):
        weekday[i] += weekday[i-1]
    n = len(Alldata[0])
    for i in range(len(Alldata)):
        row = Alldata[i]
        date = row[0].split("/")
        line = [str(0) for j in range(89)]
        # 年
        line[int(date[0])-2011] = "1"  # 0~1列
        
        # season
        season = int((int(date[1])-1) / 3)  # 2~5列
        line[2 + season] = "1"

        # month
        line[5 + int(date[1])] = "1"  # 6~17列

        # day
        line[17 + int(date[2])] = "1"  # 18~48列

        # weekday 0-6
        wd = weekday[int(date[1]) - 1 + (int(date[0]) - 2011) * 12]
        wd = (wd + int(date[2]) + 5) % 7
        line[49 + wd] = "1"  # 49~55 列

        # hr
        line[56 + int(float(row[1]))] = "1"  # 56~79列

        # weather
        line[79 + int(float(row[2]))] = "1"  # 80~83列

        line[84] = row[3]  # temp
        line[85] = row[4]  # atemp
        line[86] = row[5]  # hum
        line[87] = row[6]  # windspeed
        if n == 8:
            line[88] = row[7]
     
        content += ' '.join(line) + '\n'
    writer.write(content)
    writer.close() 


if __name__ == "__main__":
    cp = os.getcwd()
    trainpath = join(cp, "train.csv")
    trainOutpath = join(cp, "train.txt")
    newData = changedata1(trainpath)
    changedata2(newData, trainOutpath)
    
    testpath = join(cp, "test.csv")
    testOutpath = join(cp, "test.txt")
    newData = changedata1(testpath)
    changedata2(newData, testOutpath)
    