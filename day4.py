
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
        print("xxx", row, col)
        colvalues = [ row[col] for row in board ]

        if colvalues == [None, None, None, None, None]:
          return True

    return False

if __name__=="__main__":
    draws, boards = read_input("day4.txt")

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
