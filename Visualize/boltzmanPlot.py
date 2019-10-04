import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import math

df = pd.read_csv('../output/velocitiesPDF.csv')
df.sort_values(by=['velocity'], inplace=True)

x = df['velocity']
y = df['probability']

def f(x,a):
  return math.sqrt(2/math.pi)*(x**2)*math.e**(-x**2/(2*a**2))/(a**3)

for i in range(0,y.size,1):
    y[i] = 10 * y[i]

fig = plt.figure()
plt.title('Maxwell-Boltzman')
plt.xlabel('Velocity')
plt.ylabel('Maxwell-Boltzman(velocity)')

a=5.8
b=f(x,a)
    

plt.plot(x, b, lw=0.5, ms=0.5, marker='o', label='Maxwell-Boltzman\n')
plt.plot(x, y, 'r.', label='PDF Velocity\n')
plt.legend(loc='upper right')
plt.xticks(np.arange(0, 20, step=1))
plt.grid(ls='--')
plt.show()
