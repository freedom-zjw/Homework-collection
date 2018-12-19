# -*- coding:utf-8 -*-
import numpy as np
import matplotlib
import matplotlib.pyplot as plt
import csv
import os
from os.path import join


def load_data(filepath):
    reader = csv.reader(open(filepath, encoding="utf-8"))
    next(reader)
    id, acc, pre, recall, f = [], [], [], [], []
    for row in reader:
        id.append(row[0])
        acc.append(row[1])
        pre.append(row[2])
        recall.append(row[3])
        f.append(row[4])
    return [id, acc, pre, recall, f]

def draw(data):
    plt.figure(1)
    plt.plot(data[0], data[1])
    plt.ylim(0.7, 0.85)
    plt.show()

if __name__ == "__main__":
    path = os.getcwd()
    filepath = join(path, "init_data.csv")
    data = load_data(filepath)
    draw(data)