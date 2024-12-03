from dataclasses import dataclass, field
from typing import Dict, List, Optional, Tuple

PRECISION = 1000000


class ParseError(Exception):
    pass


@dataclass
class Element:
    val: int


@dataclass
class Set:
    name: str
    elements: List[Element]

    @staticmethod
    def new(name: str, elements: List[Element]) -> "Set":
        return Set(name=name, elements=elements)


@dataclass
class Matrix:
    inner: List[List[int]]

    def eq_size(self) -> int:
        return len(self.inner[0])

    def system_size(self) -> int:
        return len(self.inner)

    def __getitem__(self, index: int) -> List[int]:
        return self.inner[index]

@dataclass
class ParsedData:
    sets: Dict[str, Set] = field(default_factory=dict)
    matrices: Dict[str, Matrix] = field(default_factory=dict)


def parse_str(input_str: str) -> ParsedData:
    parsed_data = ParsedData()
    matrices_data = []
    in_matrix = False

    for line in input_str.splitlines():
        line = line.strip()
        if not line:
            continue

        if in_matrix:
            in_matrix = process_matrix_line(line, matrices_data)
            continue

        if "=(" in line:
            start_new_matrix(line, matrices_data)
            in_matrix = True
        elif "={" in line:
            parse_and_store_set(line, parsed_data)
        else:
            raise ParseError("Invalid construct provided")

    process_matrices_data(parsed_data, matrices_data)
    return parsed_data


def process_matrix_line(line: str, matrices_data: List[Tuple[str, str]]) -> bool:
    name, content = matrices_data[-1]
    if line.endswith(")"):
        matrices_data[-1] = (name, content + line[: line.find(")")])
        return False
    matrices_data[-1] = (name, content + line + "\n")
    return True


def start_new_matrix(line: str, matrices_data: List[Tuple[str, str]]):
    name, content = map(str.strip, line.split("=", 1))
    paren_pos = content.find("(")
    if paren_pos == -1:
        raise ParseError("Invalid matrix start")
    matrices_data.append((name, content[paren_pos + 1 :] + "\n"))


def parse_and_store_set(line: str, parsed_data: ParsedData):
    result = parse_set(line)
    if result is None:
        raise ParseError("Invalid set")
    name, set_obj = result
    parsed_data.sets[name] = set_obj


def process_matrices_data(
    parsed_data: ParsedData, matrices_data: List[Tuple[str, str]]
):
    for name, data in matrices_data:
        matrix = []
        for line in data.splitlines():
            if not line:
                continue
            row = [
                int(round(float(num) * PRECISION))
                for num in line.split()
                if 0.0 <= float(num) <= 1.0
            ]
            matrix.append(row)
        parsed_data.matrices[name] = Matrix(inner=matrix)


def parse_set(line: str) -> Optional[Tuple[str, Set]]:
    parts = [part.strip() for part in line.split("=")]
    if len(parts) != 2:
        return None
    set_name, pair_str = parts[0], parts[1].lstrip("{").rstrip("}")
    elements = parse_pairs(pair_str)
    if elements is None:
        return None
    return set_name, Set.new(set_name, elements)


def parse_pairs(pair_str: str) -> Optional[List[Element]]:
    elements = []
    for val in pair_str.split(","):
        val = parse_element_value(val.strip())
        if val is None:
            return None
        elements.append(Element(val=val))
    return elements


def parse_element_value(value_str: str) -> Optional[int]:
    try:
        val = float(value_str)
        if 0.0 <= val <= 1.0:
            return int(round(val * PRECISION))
    except ValueError:
        return None
    return None
