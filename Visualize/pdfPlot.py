import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import math

df = pd.read_csv('../output/velocitiesPDF.csv')
df.sort_values(by=['velocity'], inplace=True)

x = df['velocity']
y = df['probability']

for i in range(0,y.size,1):
    y[i] = 10 * y[i]

fig = plt.figure()
plt.title('PDF(Velocity)')
plt.xlabel('Velocity')
plt.ylabel('Probability')
#label='N = 300\nV0 = 10 m/s\n'
plt.plot(x, y,'r.')
plt.legend(loc='upper right')
plt.xticks(np.arange(0, 20, step=1))
plt.grid(ls='--')
plt.show()
