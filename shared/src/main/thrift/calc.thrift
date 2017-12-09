namespace java de.hda.fbi.ds.ks

typedef i32 int
/*
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
*/

service ShopService {  // defines the service to add two numbers

	    string hello(1:string name),
	    int getPriceByName(1:string name),
	    string buyProduct(1:string name, 2:int value,3:int price),
	    list<string> getInvoices(),
}