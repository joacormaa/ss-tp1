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

# err_vals = []
# min_error = 100000
# min_c = 1
# min_n = 1
#
# c_range = np.arange(-6,6,step=0.1)
# n_range = np.arange(50,1000,step=10)
#
# for c in c_range:
#     err_row = []
#     for n in n_range:
#         curr_error = E(c,n)
#         if curr_error < min_error:
#             min_error = curr_error
#             min_c = c
#             min_n = n
#         err_row.append(curr_error)
#     err_vals.append(err_row)
#
# X, Y = np.meshgrid(c_range, n_range)
#
# fig = plt.figure()
# ax = fig.add_subplot(111, projection = '3d')
# ax.plot_surface(X, Y, err_vals)
#
#
# # plt.plot(np.arange(-6,3,step=0.001),errors)
# # plt.show()
# print(min_c)
# print(min_n)
# print(min_error)

ds = np.arange(0.15, 0.25, 0.0001)


plt.plot(ds, [Q(d, c, n) for d in ds], 'r-', label='Beverloo con c = {}'.format(c))
plt.errorbar(apertures, flows, yerr=errors, fmt='bo', label='mediciones')
plt.ylabel('Caudal en partículas por segundo')
plt.xlabel('apertura del silo [m]')
plt.legend()
plt.show()

print(E(c))