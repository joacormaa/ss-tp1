import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from sklearn.metrics import mean_squared_error as mse


x = [0.01, 0.001, 0.0005, 0.0001, 0.00005, 0.00001]
methods = ['ANALYTICAL','VERLET','BEEMAN','GPCO5']

vals = [[0 for i in range(len(methods))] for j in range(len(x))]

dfe = pd.read_csv('../output/001/ANALYTICALParticle.csv')
dfe.sort_values(by=['time'], inplace=True)
vals[0][0] = dfe['position']

df1e = pd.read_csv('../output/001/VERLETParticle.csv')
df1e.sort_values(by=['time'], inplace=True)
vals[0][1] = df1e['position']

df2e = pd.read_csv('../output/001/BEEMANParticle.csv')
df2e.sort_values(by=['time'], inplace=True)
vals[0][2] = df2e['position']

df3e = pd.read_csv('../output/001/GPCO5Particle.csv')
df3e.sort_values(by=['time'], inplace=True)
vals[0][3] = df3e['position']

dfa = pd.read_csv('../output/0005/ANALYTICALParticle.csv')
dfa.sort_values(by=['time'], inplace=True)
vals[1][0] = dfa['position']

df1a = pd.read_csv('../output/0005/VERLETParticle.csv')
df1a.sort_values(by=['time'], inplace=True)
vals[1][1] = df1a['position']


df2a = pd.read_csv('../output/0005/BEEMANParticle.csv')
df2a.sort_values(by=['time'], inplace=True)
vals[1][2] = df2a['position']

df3a = pd.read_csv('../output/0005/GPCO5Particle.csv')
df3a.sort_values(by=['time'], inplace=True)
vals[1][3] = df3a['position']

dfb = pd.read_csv('../output/0001/ANALYTICALParticle.csv')
dfb.sort_values(by=['time'], inplace=True)
vals[2][0] = dfb['position']

df1b = pd.read_csv('../output/0001/VERLETParticle.csv')
df1b.sort_values(by=['time'], inplace=True)
vals[2][1] = df1b['position']

df2b = pd.read_csv('../output/0001/BEEMANParticle.csv')
df2b.sort_values(by=['time'], inplace=True)
vals[2][2] = df2b['position']

df3b = pd.read_csv('../output/0001/GPCO5Particle.csv')
df3b.sort_values(by=['time'], inplace=True)
vals[2][3] = df3b['position']

dfc = pd.read_csv('../output/00005/ANALYTICALParticle.csv')
dfc.sort_values(by=['time'], inplace=True)
vals[3][0] = dfc['position']

df1c = pd.read_csv('../output/00005/VERLETParticle.csv')
df1c.sort_values(by=['time'], inplace=True)
vals[3][1] = df1c['position']

df2c = pd.read_csv('../output/00005/BEEMANParticle.csv')
df2c.sort_values(by=['time'], inplace=True)
vals[3][2] = df2c['position']

df3c = pd.read_csv('../output/00005/GPCO5Particle.csv')
df3c.sort_values(by=['time'], inplace=True)
vals[3][3] = df3c['position']

dfd = pd.read_csv('../output/00001/ANALYTICALParticle.csv')
dfd.sort_values(by=['time'], inplace=True)
vals[4][0] = dfd['position']

df1d = pd.read_csv('../output/00001/VERLETParticle.csv')
df1d.sort_values(by=['time'], inplace=True)
vals[4][1] = df1d['position']

df2d = pd.read_csv('../output/00001/BEEMANParticle.csv')
df2d.sort_values(by=['time'], inplace=True)
vals[4][2] = df2d['position']

df3d = pd.read_csv('../output/00001/GPCO5Particle.csv')
df3d.sort_values(by=['time'], inplace=True)
vals[4][3] = df3d['position']

dff = pd.read_csv('../output/01/ANALYTICALParticle.csv')
dff.sort_values(by=['time'], inplace=True)
vals[5][0] = dfd['position']

df1f = pd.read_csv('../output/01/VERLETParticle.csv')
df1f.sort_values(by=['time'], inplace=True)
vals[5][1] = df1d['position']

df2f = pd.read_csv('../output/01/BEEMANParticle.csv')
df2f.sort_values(by=['time'], inplace=True)
vals[5][2] = df2d['position']

df3f = pd.read_csv('../output/01/GPCO5Particle.csv')
df3f.sort_values(by=['time'], inplace=True)
vals[5][3] = df3d['position']

'''
time = 10
stepa = 0.001
stepb = 0.0006
stepc = 0.0001
stepd = 0.00005
stepe = 0.01
cuta = int(time/stepa)
cutb = int(time/stepb)
cutc = int(time/stepc)
cutd = int(time/stepd)
cute = int(time/stepe)
'''


for i in range(len(x)):
    expected = vals[i][0]
    for j in range(len(methods)):
        vals[i][j] = vals[i][j].sub(expected)


yval = [[0 for i in range(len(methods))] for j in range(len(x))]
yerr = [[0 for i in range(len(methods))] for j in range(len(x))]

yval = np.array(yval)
yerr = np.array(yerr)

yval.dtype = 'float32'
yerr.dtype = 'float32'

#Errores Normalizados

for i in range(len(x)):
    for j in range(len(methods)):
        curr = vals[i][j]
        yval[i][j] = np.mean(np.power(curr, 2))
        yerr[i][j] = np.std(curr)

print(yval)
print(yerr)
fig = plt.figure()
plt.xlabel('dt (s)')
plt.ylabel('Error (m)')

#con error
plt.plot(x, yval[:,1], lw=0, ms=10, marker='.', c='green', label=methods[1])
plt.plot(x, yval[:,2], lw=0, ms=10, marker='.', c='blue', label=methods[2])
plt.plot(x, yval[:,3], lw=0, ms=10, marker='.', c='purple', label=methods[3])

#sin error
#plt.plot(x, yVerlet, lw=0.5, ms=0.5, marker='.', c='red', label='VERLET')
#plt.plot(x, yBeeman, lw=0.5, ms=0.5, marker='.', c='green', label='BEEMAN')
#plt.plot(x, yGPCo5, lw=0.5, ms=0.5, marker='.', c='purple', label='GPCo5')

plt.legend(loc='lower right')
plt.yscale("log")
plt.xscale("log")
plt.xticks(x)
plt.grid(ls='--')

plt.show()


