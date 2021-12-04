
import re

def read_input(filename):
    lines = open(filename).readlines()

    head = [int(x) for x in lines[0].split(',')]
    remain = lines[1:]
    boards = []

    boardcount = int(len(remain)/6 )

    for l in lines:
        print(l)

    for b in range(boardcount):
      #print("XXXXX", b)
      #print(remain[b*6+1:b*6+6])
      board = []
      bl = remain[b*6+1:b*6+6]
      for line in bl:
        board.append([int(x) for x in re.split(" +", line.strip())[:5]])
      boards.append(board)
      print("appended ", board)
      
    return head,boards

def find_winner(boards):
    for board in boards:
        if is_winner(board):
            return board

    return None

def is_winner(board):
    for row in board:
        if row == [None, None, None, None, None]:
            return True

    for col in range(5):
        colvalues = [ row[col] for row in board ]

        if colvalues == [None, None, None, None, None]:
          return True

    return False

def day4_part1(input_file):
    draws, boards = read_input(input_file)

    draw = None
    while find_winner(boards) is None:
      draw, draws = draws[0], draws[1:]
      print("drawn", draw) 

      for board in boards:
          for line in board:
              for pos, val in enumerate(line):
                  if val == draw:
                      line[pos] = None


    winner = find_winner(boards)
    print("winner: ", winner)
    winsum = 0
    for line in winner:
        winsum += sum(filter(lambda x: x is not None, line))

    print("winsum: ", winsum)
    print (winsum * draw)
    return winsum * draw

def count_non_winners(boards):
    return len(list(filter(lambda b: not is_winner(b), boards)))

def find_loser(boards):
    return list(filter(lambda b: not is_winner(b), boards))[0]

def part2():
    draws, boards = read_input("day4.txt")

    draw = None
    non_winners = boards
    while count_non_winners(boards) > 0:
      draw, draws = draws[0], draws[1:]
      print("drawn", draw) 

      non_winners = list(filter(lambda b: not is_winner(b), non_winners))

      for board in boards:
          for line in board:
              for pos, val in enumerate(line):
                  if val == draw:
                      line[pos] = None


    loser = non_winners[0]
    print("loser: ", loser)
    winsum = 0
    for line in loser:
        winsum += sum(filter(lambda x: x is not None, line))

    print("winsum: ", winsum)
    print (winsum * draw)


if __name__=="__main__":
    day4_part1("day4.txt") #8304 is too low
    # part2() #8304 is too low
