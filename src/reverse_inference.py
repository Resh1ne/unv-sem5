from parsing import Matrix, parse_str, PRECISION
from typing import List, Set, Tuple, Generator
import copy
import itertools


class EquationSolutions(list):
    pass


class Interval:
    NotDefined = "NotDefined"

    def __init__(self, start=None, end=None):
        self.start = start
        self.end = end

    def __repr__(self):
        if self.start is None or self.end is None:
            return "NotDefined"
        if self.start == self.end:
            return f"[{self.start / PRECISION}]"

        return f"[{self.start / PRECISION}, {self.end / PRECISION}]"

    def is_defined(self):
        return self.start is not None and self.end is not None

    def __eq__(self, other):
        if isinstance(other, Interval):
            return (self.start, self.end) == (other.start, other.end)
        return False

    def __hash__(self):
        return hash((self.start, self.end))


def get_solutions_1(
    i: int,
    matrix: Matrix,
    new_solutions: EquationSolutions,
    intervals: List[List[Interval]],
):
    solution = []
    for j in range(matrix.eq_size()):
        el = matrix[i][j]
        if el == PRECISION:
            intervals[j].append(Interval(PRECISION, PRECISION))
            solution.append(Interval(PRECISION, PRECISION))
        else:
            intervals[j].append(Interval())
            solution.append(Interval())

    new_solutions.append(solution)


def get_solutions_0(
    i: int,
    matrix: Matrix,
    new_solutions: EquationSolutions,
    intervals: List[List[Interval]],
):
    for j in range(matrix.eq_size()):
        el = matrix[i][j]
        el_x = PRECISION - el

        solution: List[Interval] = [Interval(Interval)] * matrix.eq_size()
        solution[j] = Interval(0, el_x)
        if matrix.eq_size() == 1:
            intervals[j].append(Interval(0, el_x))

        for k in range(matrix.eq_size()):
            if j == k:
                continue

            intervals[j].append(Interval(0, el_x))

            intervals[k].append(Interval(0, PRECISION))
            solution[k] = Interval(0, PRECISION)

        new_solutions.append(solution)


def get_solutions_y(
    i: int,
    y: int,
    matrix: Matrix,
    new_solutions: EquationSolutions,
    intervals: List[List[Interval]],
):
    for j in range(matrix.eq_size()):
        el = matrix[i][j]
        el_x = PRECISION - el + y

        solution: List[Interval] = [Interval(Interval)] * matrix.eq_size()
        solution[j] = Interval(el_x, el_x)
        if matrix.eq_size() == 1:
            if el_x >= PRECISION:
                intervals[j].append(Interval())
            else:
                intervals[j].append(Interval(el_x, el_x))

        for k in range(matrix.eq_size()):
            if j == k:
                continue

            if el_x >= PRECISION:
                intervals[j].append(Interval())
            else:
                intervals[j].append(Interval(el_x, el_x))

            other_el = matrix[i][k]
            other_el_x = PRECISION - other_el + y
            if other_el_x > PRECISION:
                intervals[k].append(Interval())
                solution[k] = Interval()
            else:
                intervals[k].append(Interval(other_el_x, PRECISION))
                solution[k] = Interval(other_el_x, PRECISION)

        new_solutions.append(solution)


def get_solutions(
    matrix: Matrix,
    i: int,
    y: int,
    intervals: List[List[Interval]],
    solutions: List[EquationSolutions],
):
    new_solutions = EquationSolutions()
    if y == 0:
        get_solutions_0(i, matrix, new_solutions, intervals)
    elif y == 1:
        get_solutions_1(i, matrix, new_solutions, intervals)
    else:
        get_solutions_y(i, y, matrix, new_solutions, intervals)

    solutions.append(new_solutions)


def cartesian_product(
    sets: List[List[Interval]],
) -> Generator[List[Interval], None, None]:
    return (list(comb) for comb in itertools.product(*sets))


def in_interval(outer: Interval, inner: Interval) -> bool:
    if not outer.is_defined() or not inner.is_defined():
        return False

    return outer.start <= inner.start and outer.end >= inner.end


def is_solution_eq(
    pos_res: List[Interval], eq_solutions: EquationSolutions, matrix: Matrix
):
    for solution in eq_solutions:
        all = True
        for j in range(matrix.eq_size()):
            if solution[j] == Interval():  # made a break
                all = False
                break

            if not in_interval(solution[j], pos_res[j]):
                all = False
                break

        if all:
            return True

    return False


def is_solution(
    pos_res: List[Interval], solutions: List[EquationSolutions], matrix: Matrix
):
    for eq_solutions in solutions:
        if not is_solution_eq(pos_res, eq_solutions, matrix):
            return False

    return True


def printout(set_name: str, intervals: List[List[Interval]]):
    if not intervals:
        print(f"No inference")
        return

    print(
        f"<{', '.join(f'{set_name}(x{i + 1})' for i in range(len(intervals[0])))}>∈",
        end="",
    )
    for i, r in enumerate(intervals):
        if i == len(intervals) - 1:
            print(f"({'*'.join(str(elem) for elem in r)})")
        else:
            print(f"({'*'.join(str(elem) for elem in r)})", end="u")


def check_solution_overlap(x: List[Interval], y: List[Interval]) -> bool:
    for k in range(len(x)):
        if x[k] != y[k] and not in_interval(y[k], x[k]):
            return False
    return True


def clear_intervals(intervals: List[List[Interval]]):
    i = 0
    while i < len(intervals):
        to_clear = any(
            check_solution_overlap(intervals[i], y)
            for j, y in enumerate(intervals)
            if i != j
        )

        if to_clear:
            intervals.pop(i)
            i -= 1
        i += 1


def find_reverse_inference(file_name: str):
    with open(file_name) as f:
        content = f.read()

    data = parse_str(content)
    _, matrix = data.matrices.popitem()
    _, set_ = data.sets.popitem()

    intervals = [[] for _ in range(matrix.eq_size())]
    solutions = []

    for i in range(matrix.system_size()):
        y = set_.elements[i].val
        get_solutions(matrix, i, y, intervals, solutions)

    # print(solutions)
    # print(intervals)
    possible_results = cartesian_product(copy.deepcopy(intervals))
    # print(possible_results)
    res: Set[Tuple[Interval, ...]] = set()
    # res: List[List[Interval]] = []
    for pos_res in possible_results:
        if is_solution(pos_res, solutions, matrix):
            res.add(tuple(pos_res))

    res_list: List[List[Interval]] = [list(t) for t in res]
    clear_intervals(res_list)

    printout(set_.name + "1", res_list)
