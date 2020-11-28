# ENSF607/608 ToolShop Project
ENSF607 and ENSF608 Joint Project, ToolShop Project  

**Author: Karen Yunying Zhang, Scott Tianhan Jiang**

### Database Conceptual and Logical Design
##### ER Diagram
[Lucid Chart Editable Sharing Link](https://lucid.app/invitations/accept/d0bdbd76-d8f8-4cc0-9238-4e9bcc2f756f)  
##### Relational Model Mapping
[Lucid Chart Editable Sharing Link](https://lucid.app/invitations/accept/4c08bc4f-2114-4ae7-98b6-0bed1c0e1945)
##### Database Description
[GoogleDoc](https://docs.google.com/document/d/180pSjMFg5sbJ-rtn0WJmAR0fkeGppYdac0jhApfK9g0/edit)
### Package and Class Design
##### UML Package Class Diagram (The high-level Diagram)
UML_1 High Level Package Diagram ToolShop MVC.mdj
##### UML Class Diagram, Model Package
UML_2 Low Level Class Diagram Toolshop Model.mdj


### Implementation
##### Change Note  
1. Let ModelController class implement runnable. Reason: when ServerController started running multiple thread, multiple sets of ModelController+DBController+Model has to be constructed. 
2. Deleted Shop class, Model class aggregates Inventory, SupplierList, and CustomerList classes. Reason: Model act as the class who directly interacts with ModelController has to have all methods related to all operations in Item, Supplier, Customer class. This will render Shop class serving no purpose but as a shell class that allows all calls passing through it.
3. Changed the Item class variable Supplier theSupplier into int supplierId, because two object with two way association relationship is not able to be serialized to JSON. [Reference](https://stackoverflow.com/questions/27926794/jackson-and-serialization)

##### Design Note
1. To minimize interaction with DB, in Model constructor, it reads all supplier, item and client lists from DB and store info in local. This is under the assumption that database size is small. If database size increases dramatically, this has to be changed into a query-on-demand manner.
2. Upon the initiation of each client application, all content in MySQL table item, electrical_item, supplier, international_supplier, customer, and part of daily_order(WHERE odate = LocalDate.now()) will be loaded to memory. 
3. After initialization, all changes will be updated to MySQL tables in realtime. Those changes include: update item quantity, add new item, delete item, create new order, create new orderline, add new customer, delete customer.

### Instructions on How to Run this Program
1. Include SQL jar file into classpath.
2. At server side: [Include JACKSON jar file into classpath.](https://www.youtube.com/watch?v=J2RBO_9wjYg)
3. At client side: [Include official JSON jar file into classpath.](https://github.com/stleary/JSON-java)
4. Both server and client side project are built on JDK-14 (Java Runtime version 58.0). Please set up JDK to this version or up, otherwise project build will fail.

### AWS Deployment Note
1. Before server program deployment, AWS plugin for Eclipse should be installed with the following guide.[Link](https://aws.amazon.com/eclipse/)