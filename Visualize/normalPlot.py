import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

df = pd.read_csv('../output/metrics.csv')
df.sort_values(by=['time'], inplace=True)

time = df['time']
distance = df['distance']
speed = df['speed']
x = df['x']
y = df['y']

print(time.iloc[-1])
print(np.sum(distance.iloc[:]))
print(np.hypot(x[0]-x.iloc[-1],y[0]-y.iloc[-1]))
print(np.mean(speed))
print(np.std(speed))


