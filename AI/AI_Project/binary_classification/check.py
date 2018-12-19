import csv
import os
import numpy as np
from os.path import join
from collections import Counter


def loadResult(filename):
    reader = csv.reader(open(filename, "r", encoding='utf-8'))
    r = []
    for row in reader:
        row = [int(x) for x in row]
        r.append(row)
    return np.array(r, dtype=int)


def check(R):
    n = len(R)
    err = np.ones((n,n))
    for i in range(n):
        for j in range(n):
            err[i, j] = np.sum(R[i] != R[j])   
    return err 

if __name__ == "__main__":
    f1 = loadResult('66_v1KNN0.8训练集minmax.csv')
    f2 = loadResult('66_v2KNN全数据集minmax.csv')
    f3 = loadResult('66_v3KNN0.8训练集无归一化.csv')
    f4 = loadResult('66_v4.csv')
    R = [f1, f2, f3, f4]
    print(check(R))