namespace java de.hda.fbi.ds.ks

struct Operands {
  1: i32 operand_a,
  2: i32 operand_b
}

struct Result {
  1: i32 result
}

service Calc {
  void ping(),
  i32 addTwo(1: i32 num1, 2: i32 num2),
  Result addOne(1: Operands operands)
}
