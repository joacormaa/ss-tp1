import math
import numpy as np
import matplotlib.pyplot as plt


r = 0.025
g = 10

apertures = [0.15, 0.175, 0.2, 0.225, 0.25]
flows = [48.0, 70.5, 100, 128, 145]
errors = [1.24, 2.24, 3.54, 3.86, 5.47]


c = 2.232
n=555.5 #Uso este numero para que el grafico quede bien. Usando el real ~250 la aproximacion es pesima.

def Q(d, c, n):
    return n*math.sqrt(g)*math.pow((d-c*r), 1.5)


def E(c,n):
    error = 0
    for aperture, flow in zip(apertures, flows):
        error += math.pow(flow - Q(aperture, c, n), 2)
    return error

err_vals = []
min_error = 100000
min_c = 1
min_n = 1

c_range = np.arange(0.5,4,step=0.001)

for c_v in c_range:
    curr_error = E(c_v,n)
    if curr_error < min_error:
        min_error = curr_error
        min_c = c_v
        min_n = n
    err_vals.append(curr_error)

plt.plot(c_range,err_vals)
plt.ylabel('ECM')
plt.xlabel('c')
plt.show()
print(min_c)
print(min_n)
print(min_error)

ds = np.arange(0.15, 0.25, 0.0001)


plt.plot(ds, [Q(d, c, n) for d in ds], 'r-', label='Beverloo [c = {}]'.format(c))

plt.errorbar(apertures, flows, yerr=errors, lw=0, ms=0.5, marker='.', c='blue', elinewidth=1, capsize=5, label='muestras')
plt.ylabel('Q [p/s]')
plt.xlabel('Hw [m]')
plt.legend()
plt.show()

print(E(c))