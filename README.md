README
======

Setup
-----

COGDA requires MySQL, Grails, and Groovy. To set up the development database 
and user run `mysql --user=root -p` to enter the MySQL prompt. Then execute

	mysql> CREATE USER 'dev'@'localhost' IDENTIFIED BY 'dev';
	mysql> GRANT ALL PRIVILEGES ON *.* TO 'dev'@'localhost'
    	->     WITH GRANT OPTION;

For more details about Hibernate and the MySQL connection see `grails-app/conf/DataSource.groovy`.

Now, you should be able to simply run `grails run-app`.