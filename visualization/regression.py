import math
import numpy as np
import matplotlib.pyplot as plt


n = 1775
r = 0.0125
g = 10

flows = [348, 485, 647, 802]
apertures = [0.15, 0.18, 0.22, 0.25]
errors = [11.7, 17.6, 24.8, 44.3]


c = -1.35
def Q(d, c):
    return n*math.sqrt(g)*math.pow((d-c*r), 1.5)


def E(c):
    error = 0
    for aperture, flow in zip(apertures, flows):
        error += math.pow(flow - Q(aperture, c), 2)
    return error


ds = np.arange(0.15, 0.25, 0.0001)


plt.plot(ds, [Q(d, c) for d in ds], 'r-', label='ley de Beverloo con c = {}'.format(c))
plt.errorbar(apertures, flows, yerr=errors, fmt='bo', label='mediciones')
plt.ylabel('Caudal en partículas por segundo')
plt.xlabel('apertura del silo [m]')
plt.legend()
plt.show()

print(E(c))