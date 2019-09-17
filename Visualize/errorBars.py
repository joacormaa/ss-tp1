import pandas as pd
import numpy as np
import matplotlib.pyplot as plt


#varios
df1 = pd.read_csv('../output/hundred1/metrics.csv')
df2 = pd.read_csv('../output/hundred2/metrics.csv')
'''
df3 = pd.read_csv('../output/forty3/metrics.csv')
df4 = pd.read_csv('../output/forty4/metrics.csv')
df5 = pd.read_csv('../output/forty5/metrics.csv')
'''
df = [df1, df2]
dfSizes = [0, df1['time'].size, df2['time'].size]

'''
#individual
df1 = pd.read_csv('../output/twenty1/metrics.csv')
df = [df1]
dfSizes = [0, df1['time'].size]
''' 

#indistinto
dfSizes.sort()
maxSize = np.max(dfSizes)

x = []
y = []
yerr = []

for i in range(0, maxSize):
    xVals = []
    yVals = []

    for data in df:
        if i < data['time'].size:
            xVals.append(data.iloc[i,0])
            yVals.append(data.iloc[i,3])

    x.append(np.mean(np.asarray(xVals)))
    y.append(np.mean(np.asarray(yVals)))
    yerr.append(np.asarray(yVals).std())

plotEveryX = np.arange(0, int(round(np.max(x))), step=50)

myColors = ['r', 'b', 'g', 'c', 'm']
myLabels = ['Numero de Sim. = 2', 'Numero de Sim. = 1', 'Numero de Sim. = 3',
 'Numero de Sim. = 2', 'Numero de Sim. = 1']
fig = plt.figure()
plt.title('FP vs Tiempo - N = 100')
plt.xlabel('Tiempo [s]')
plt.ylabel('FP')

'''
#indiv
plt.plot(x, y, lw=0.5, ms=0.5, marker='.', label='Numero de Sim. = 1', c='c')
'''

#varios
for j in range(0, 2):
    plt.errorbar(x[dfSizes[j] : dfSizes[j+1]], y[dfSizes[j] : dfSizes[j+1]], yerr=yerr[dfSizes[j] : dfSizes[j+1]], markevery=50,
     errorevery=250, lw=0.5, ms=0.5, marker='.', c=myColors[j], elinewidth=1, capsize=5, label=myLabels[j])

plt.legend(loc='lower right')
plt.xticks(plotEveryX)
plt.grid(ls='--')
plt.savefig('../analisis/tp3/hundredVarios')
plt.show()
