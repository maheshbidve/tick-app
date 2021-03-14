-------------------Solactive Coding Challenge--------------
API documnetation can be found at following url
http://localhost:8081/swagger-ui/index.html

Following are the API can be used by client

1. API to consume tick
Url : http://localhost:8081/v1/tick/consume
Method Type : POST
Following are the input parameters required 
parameter name is input and its type is text e.g. input : TIMESTAMP=1615626899|PRICE=5.24|CLOSE_PRICE=4.5|CURRENCY=EUR|RIC=ric2

2. API to get ticks for a specific RIC
Url : http://localhost:8081/v1/tick/getTicks/{ric name}
MEthod Type : GET
Header Param : x-api-key (Value is specific to each RIC and it will be shared seperately)

Exported files will be created in a folder named as "ExportedFiles"
