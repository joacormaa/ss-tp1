import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

dfAA = pd.read_csv('../analisis/metricsAA.csv')
dfAB = pd.read_csv('../analisis/metricsAB.csv')
dfBA = pd.read_csv('../analisis/metricsBA.csv')
dfBB = pd.read_csv('../analisis/metricsBB.csv')

fig = plt.figure()
plt.title('Prueba 1')
plt.xlabel('Tiempo')
plt.ylabel('Orden')

plt.plot(dfAA['time'], dfAA['order'], lw=0.5, ms=0.5, marker='o', label='Ad - Ar')
plt.plot(dfAB['time'], dfAB['order'], lw=0.5, ms=0.5, marker='v', label='Ad - Br')
plt.plot(dfBA['time'], dfBA['order'], lw=0.5, ms=0.5, marker='s', label='Bd - Ar')
plt.plot(dfBB['time'], dfBB['order'], lw=0.5, ms=0.5, marker='p', label='Bd - Br')
plt.legend(loc='lower right')
plt.xticks(np.arange(0, 300, step=20))
plt.grid(ls='--')
plt.show()

