
Welcome! To start using this application you need to setup MySQL database first.
1. to setup your database please open MySQL Workbech and run following commands: 
CREATE USER 'inventi'@'localhost' IDENTIFIED BY 'inventi';
GRANT ALL PRIVILEGES ON * . * TO 'inventi'@'localhost';
2. After that create new account for into MySQL workbench
3. Database setup is finished
4. Download source code
5. Run the application
6. Open postman and do following things: 
POST: http://localhost:8080/api/account 
with bodies 
{
    "fullName": "Petras Petruliavicius"
}
AND
{
    "fullName": "Danielius Miciulis"
}
7. After that download CSV file with data (attached to email)
8. Comeback to Postmap and do following:
POST: http://localhost:8080/api/transaction/upload
add form-data file and attach csv file you downloaded.
9. Again if you want to get account balance
GET: http://localhost:8080/api/account/balance/DAN18202118
you will get all history balance
also you can specify date from and to with Json body
{
    "dateTo": "2021-05-25",
    "dateFrom": "2021-05-23"
}
10. If you want to export all transaction use:
GET: http://localhost:8080/api/transaction/export
also you can specify date from and to with Json body
{
    "dateTo": "2021-05-25",
    "dateFrom": "2021-05-23"
}
11. If you want to export transaction for one bank account use:
http://localhost:8080/api/transaction/export/DAN18202118
also you can specify date from and to with Json body
{
    "dateTo": "2021-05-25",
    "dateFrom": "2021-05-23"
}
12. Happy Review, sorry could not add test because of lack of time! Hope to see you and talk with you! (Not good with Junit testing)
13. Not really sure about data i used because i have to improvise and do it on my self. There is lot ways to improve the code and do custom validations!
