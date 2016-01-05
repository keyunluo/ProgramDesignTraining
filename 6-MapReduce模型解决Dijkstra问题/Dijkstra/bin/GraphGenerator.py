# -*- coding: utf-8 -*-
'''
随机数生成器：
'''

import random
import math


def createinput(start, nodeSize):

    #创建文件
    fileCount=math.ceil(nodeSize/10000.0)
    files=[]
    for count in range(fileCount):
        files.append(open('../input/input_%s'%count,'w'))
    i = 1
    inputfile=files[0]

    while i <= nodeSize:
	#设置邻接点大小：设为10-100
        adjSize = int(random.uniform(10, 100)) # 返回一个2和100之间的随机数
        j = 1
        distance = ''
	#邻接点
        adjList = []
        while j <= adjSize:
            disValue = int(random.uniform(3, 600))  #距离：3-600
            vertics = int(random.uniform(start, nodeSize))
            adjList.append(int(random.uniform(start, nodeSize)))

            if j == 1 & vertics == 1:
                vertics = int(random.uniform(start+1, nodeSize-1))

            for k in adjList:
                if k == vertics:
                    vertics = str(int(random.uniform(start, nodeSize)))

            if j <= adjSize - 1:
                distance += str(vertics) + ':' + str(disValue)+ ','
            else:
                distance += str(vertics) + ':' + str(disValue)
            j += 1

        if i%10001 == 0:
	        inputfile=files[int(i/10001)]
        if i == 1:
            inputfile.write(str(i) +'\t'+ str(0) +'_'+ distance + '\n')
        else:
            inputfile.write(str(i) +'\t' + str(-1) +'_'+ distance + '\n')
        i += 1

    #关闭文件
    for count in range(fileCount):
	    files[count].close


createinput(1,100000)
