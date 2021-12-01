from nose.tools import assert_equals

from p1 import solve, solve2

def test_solve():
    assert_equals(solve('p1.sample.txt'), 7)

def test_solve2():
    assert_equals(solve2('p1.sample.txt'), 5)
