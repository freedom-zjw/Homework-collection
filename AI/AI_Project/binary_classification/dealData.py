import csv
import os
import numpy as np
from os.path import join
from collections import Counter


def changedata(Inputpath, Outputpath):
    reader = csv.reader(open(Inputpath))
    writer = csv.writer(open(Outputpath, "w", encoding="utf-8", newline=""))
    for row in reader:
        line = []
        line.append(row[0])
        if "A" in row[1]:
            line = line + ["1", "0", "0", "0", "0"]
        elif "B" in row[1]:
            line = line + ["0", "1", "0", "0", "0"]
        elif "C" in row[1]:
            line = line + ["0", "0", "1", "0", "0"]
        elif "D" in row[1]:
            line = line + ["0", "0", "0", "1", "0"]
        elif "E" in row[1]:
            line = line + ["0", "0", "0", "0", "1"]
        line = line + row[2:10]
        if "1" not in row[10]:
            line = line + ["1", "0"]
        elif "1" in row[10]:
            line = line + ["0", "1"]
        if "3" not in row[11]:
            line = line + ["1", "0"]
        elif "3" in row[11]:
            line = line + ["0", "1"]
        line = line + row[12:]
        writer.writerow(line)


def changedata2(Inputpath, Outputpath):
    reader = csv.reader(open(Inputpath))
    writer = csv.writer(open(Outputpath, "w", encoding="utf-8", newline=""))
    for row in reader:
        line = []
        line.append(row[0])
        if "A" in row[1]:
            line = line + ["1"]
        elif "B" in row[1]:
            line = line + ["2"]
        elif "C" in row[1]:
            line = line + ["3"]
        elif "D" in row[1]:
            line = line + ["4"]
        elif "E" in row[1]:
            line = line + ["5"]
        line = line + row[2:]
        writer.writerow(line)


def dropdata(Inputpath, Outputpath, dropcol):
    reader = csv.reader(open(Inputpath))
    writer = csv.writer(open(Outputpath, "w", encoding="utf-8", newline=""))
    for row in reader:
        line = []
        for i in range(len(row)):
            if i not in dropcol:
                line.append(row[i])
        writer.writerow(line)


def calccorr(filepath):
    reader = csv.reader(open(filepath, "r", encoding='utf-8'))
    Alldata = []
    for row in reader:
        row = [float(x) for x in row]
        Alldata.append(row)
    Alldata = np.array(Alldata)
    corr = np.corrcoef(Alldata, rowvar=0)[-1:].tolist()[0]
    X = [i for i in range(len(corr))]
    import matplotlib.pyplot as plt 
    plt.bar(X, corr, 0.4)
    plt.show()


if __name__ == "__main__":
    cp = os.getcwd()

    trainpath = join(cp, "train.csv")
    trainOutpath = join(cp, "newTrain.csv")
    changedata(trainpath, trainOutpath)
    testpath = join(cp, "test.csv")
    testOutpath = join(cp, "newTest.csv")
    changedata(testpath, testOutpath)
  
    trainpath = join(cp, "newTrain.csv")
    trainOutpath = join(cp, "newTrain2.csv")
    dropdata(trainpath, trainOutpath, [14, 15, 16, 17, 18])
    testpath = join(cp, "newtest.csv")
    testOutpath = join(cp, "newTest2.csv")
    dropdata(testpath, testOutpath, [14, 15, 16, 17, 18])

    """
    trainpath = join(cp, "newTrain.csv")
    trainOutpath = join(cp, "newTrain3.csv")
    dropdata(trainpath, trainOutpath, [6, 7, 8, 11, 12, 18])
    testpath = join(cp, "newtest.csv")
    testOutpath = join(cp, "newTest3.csv")
    dropdata(testpath, testOutpath, [6, 7, 8, 11, 12, 18])
    """
    
