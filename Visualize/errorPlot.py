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
plt.title('Error(a)')
plt.xlabel('a')
plt.ylabel('Error(a)')

a = np.linspace(10**-10,20)
e=0
a=5.8
for i in range(0,x.size,1):
    e = e + (y[i] - f(x[i],a))**2

print(e)
#aPrima = np.arange(0.1,10,0.1)
#e=[]
#eValue = 0
#for j in range(0,aPrima.size,1):
#  eValue = 0
#  for i in range(0,x.size,1):
#    eValue = eValue + (y[i] - f(x[i],aPrima[j]))**2
#  e.append(eValue)
#print(e)

#aa=0.0001
#eValue=0
#for i in range(0,x.size,1):
#    eValue = eValue + (y[i] - f(x[i],aa))**2
#print(eValue)

#for j in range(0,aPrima.size,1):
#    print(aPrima[j])

#my_list = [] 
#my_list.append(0) 
#my_list.append(1) 
#my_list.append(2)

plt.plot(a, e, 'r-')
plt.legend(loc='upper right')
plt.xticks(np.arange(10**-10,20, step=1))
plt.grid(ls='--')
plt.show()
