import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import math
from scipy.stats import linregress

'''
df1 = pd.read_csv('../output/witness1/witness.csv')
'''
df2 = pd.read_csv('../output/witness1/witness.csv')
'''
df3 = pd.read_csv('../output/witness3/witness.csv')
df4 = pd.read_csv('../output/witness4/witness.csv')
df5 = pd.read_csv('../output/witness5/witness.csv')
df6 = pd.read_csv('../output/witness6/witness.csv')
df7 = pd.read_csv('../output/witness7/witness.csv')
df8 = pd.read_csv('../output/witness8/witness.csv')
df9 = pd.read_csv('../output/witness9/witness.csv')
df10 = pd.read_csv('../output/witness10/witness.csv')
'''

zeros = df2.iloc[0:1, 1:]
x = []
y = []
yerr = []

#Final - 348
#1 - 131
#2 - 308
for i in range(0,131):
    aux = []
    x.append(df2.iloc[i,0])
    
    for j in range(0,10):
        xAux = df2.iloc[i,(j*2)+1]
        yAux = df2.iloc[i,(j*2)+2]
        if math.isnan(xAux) != True:
            xDiff = zeros.iloc[0,(j*2)] - xAux
            yDiff = zeros.iloc[0,(j*2)+1] - yAux
            aux.append(np.sqrt((xDiff**2) + (yDiff**2)))
    y.append(np.mean(np.asarray(aux)))
    yerr.append(np.asarray(aux).std())


plotEveryX = np.arange(0, int(round(np.max(x))), step=1)

fig = plt.figure()
plt.title('MSD vs Tiempo - N = 50 - W = 10')
plt.xlabel('Tiempo [s]')
plt.ylabel('MSD [m]')

plt.errorbar(x, y, yerr=yerr, markevery=1, errorevery=5, lw=0.5, ms=0.5, marker='.',
 c='c', elinewidth=1, capsize=5, label='MSD')

slope, intercept, r_value, p_value, std_err = linregress(x, y)
plt.plot(x, intercept + slope*np.asarray(x), 'r', label='fitted line')

plt.legend(loc='lower right')
plt.xticks(plotEveryX)
plt.grid(ls='--')
#plt.savefig('../analisis/tp3/msdWitnessLR')
plt.show()

print(slope)
print(std_err)