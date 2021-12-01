
def read(input_path):
    return [int(line.strip()) for line in open(input_path).readlines()]

def solve(input_path):
    measurements = read(input_path)
    return count_increments(measurements)

def solve2(input_path):
    measurements = read(input_path)
    window_measurements = [sum(measurements[i:i+3]) for i in range(len(measurements)-2)]
    return count_increments(window_measurements)

def count_increments(measurements):
    print(measurements)
    prevdepth = None
    increments = 0
    for depth in measurements:
      if prevdepth is not None:
          if depth > prevdepth:
              increments += 1
      prevdepth = depth

    return increments

if __name__=="__main__":
    print(solve('p1.txt'))
    print(solve2('p1.txt'))
