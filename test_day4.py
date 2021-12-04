from nose.tools import assert_equals

from day4 import day4_part1, day4_part2


def test_day4_part1_sample():
    assert_equals(day4_part1("day4.sample.txt"), 4512)


def test_day4_part2_sample():
    assert_equals(day4_part2("day4.sample.txt"), 1924)
