## Start the server
ServerApplication.java is the application entry. 

## Dependencies
Use the lib/explore-service as library 
## Database Setup
Please check the application.yml for the data configurations. 
Basically, you better have a mysql database and redis installed in your computer. 
We have a AWS RDS database created for dev purpose.

## HTTPS Config
####TODO
Not it is using the certificate of saleexplore.com. We better generate a new one for 
api.saleexplore.com

#### Schema Creation
Pay attention to the FK-dependency. Such as:

* Country Schema before City

#### Redis Server
In order to make the recommendation system work. You need to 
configure the redis server. 

#### Data Initializing
Run the sql_insert files by the following order
* Country Data
* City
* Category, Brand
* User, Admin_User
* Mall
* Store
* Store_Brand, Store_Image, Store_Category
* Discount
* Ads, PopularSearch
