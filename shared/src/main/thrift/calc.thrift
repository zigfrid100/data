namespace java de.hda.fbi.ds.ks

typedef i32 int

service ShopService {  // defines the service to add two numbers

	    string hello(1:string name),
	    int getPriceByName(1:string name),
	    string buyProduct(1:string name, 2:int value,3:int price),
	    list<string> getInvoices(),
}