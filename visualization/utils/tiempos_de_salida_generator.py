import sys

orig_stdout = sys.stdout
f = open('out.txt', 'w')
sys.stdout = f

for i in range(0,200):
    print('i')

sys.stdout = orig_stdout
f.close()