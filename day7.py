import math
line = """16,1,2,0,4,2,7,1,2,14"""
line = open('day7.txt').readlines()[0]

data = [int(l) for l in line.split(",")]
print (data)

print(len(data))

avg = int((sum(data)+0.5) / len(data))
avg = 2
print (avg)

best = 9999999999
besti = -1
for target in range(min(data), max(data)):
    score = 0
    for pos in data:
      dist = int(abs(target - pos))
      if dist > 0:
        score += int(dist * (dist+1) / 2)

    if score < best:
        besti = target
        best = score


print (target, best)
