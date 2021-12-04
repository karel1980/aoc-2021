import re


def day4_part1(input_file):
    draws, boards = read_input(input_file)

    last_draw = None
    while not has_winner(boards):
        last_draw, draws = draws[0], draws[1:]
        mark_boards(boards, last_draw)

    winner = find_winner(boards)

    return calculate_board_score(winner, last_draw)


def day4_part2(input_filename):
    draws, boards = read_input(input_filename)

    last_draw = None
    remaining_boards = boards
    while len(remaining_boards) > 1:
        last_draw, draws = draws[0], draws[1:]
        remaining_boards = find_non_winners(remaining_boards)
        mark_boards(boards, last_draw)

    if len(remaining_boards) == 0:
        raise Exception("No boards remaining, did multiple boards win in the last draw?")

    loser = remaining_boards[0]
    return calculate_board_score(loser, last_draw)


def calculate_board_score(board, last_draw):
    return sum_of_remaining_board_values(board) * last_draw


def find_non_winners(boards):
    return list(filter(lambda b: not is_winner(b), boards))


def read_input(filename):
    lines = open(filename).readlines()

    draws = [int(x) for x in lines[0].split(',')]
    boards = create_boards_from_lines(lines[1:])

    return draws, boards


def create_boards_from_lines(remain):
    board_count = int(len(remain) / 6)
    boards = []
    for board_num in range(board_count):
        board_lines = remain[board_num * 6 + 1:board_num * 6 + 6]
        boards.append(create_board_from_lines(board_lines))
    return boards


def create_board_from_lines(board_lines):
    board = []
    for line in board_lines:
        board.append([int(x) for x in re.split(" +", line.strip())[:5]])
    return board


def mark_boards(boards, draw):
    for board in boards:
        for line in board:
            for pos, val in enumerate(line):
                if val == draw:
                    line[pos] = None


def sum_of_remaining_board_values(board):
    total = 0

    for line in board:
        total += sum(filter(lambda x: x is not None, line))

    return total


def find_winner(boards):
    for board in boards:
        if is_winner(board):
            return board

    return None


def has_winner(boards):
    return find_winner(boards) is not None


def count_non_winners(boards):
    return len(find_winners(boards))


def find_winners(boards):
    return list(filter(lambda b: not is_winner(b), boards))


def is_winner(board):
    return has_complete_row(board) or has_complete_column(board)


def has_complete_row(board):
    for row in board:
        if all([value is None for value in row]):
            return True

    return False


def has_complete_column(board):
    for column_index in range(len(board[0])):
        if all([row[column_index] is None for row in board]):
            return True

    return False


if __name__ == "__main__":
    print("part 1:", day4_part1("day4.txt"))
    print("part 2:", day4_part2("day4.txt"))
