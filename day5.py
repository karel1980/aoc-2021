import math

lines = [line.strip() for line in open('day5.txt').readlines()]

segments = []
for line in lines:
  p1,p2 = line.split(" -> ")
  x1,y1 = p1.split(",")
  x2,y2 = p2.split(",")
  segments.append(((int(x1),int(y1)),(int(x2),int(y2))))

xmax = max(max(map(lambda p: p[0][0], segments)), max(map(lambda p:p[1][0], segments)))
ymax = max(max(map(lambda p: p[0][1], segments)), max(map(lambda p:p[1][1], segments)))

SIZE = 1000
grid = []
for s in range(SIZE):
  grid.append([0] * SIZE)
print(grid)

for seg in segments:
  x1,y1 = seg[0]
  x2,y2 = seg[1]
  minx = min(x1,x2)
  maxx = max(x1,x2)
  miny = min(y1,y2)
  maxy = max(y1,y2)

  dx = abs(x2 - x1)
  dy = abs(y2 - y1)
  print("xxx", seg[0], seg[1], dx, dy)

  if dx > 0 and dy > 0:
    print("skipped", seg[0], seg[1])
    continue

  if dx == 0:
    for y in range(miny, maxy + 1):
      print("incrementing pos ", x1, y)
      grid[x1][y] += 1

  if dy == 0:
    for x in range(minx, maxx + 1):
      print("incrementing pos ", x, y1)
      grid[x][y1] += 1

for line in grid:
  print(" ".join([str(x) for x in line]))

total = 0
for row in grid:
  for col in row:
    if col > 1: 
      total += 1

print(total)
