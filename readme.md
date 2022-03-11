

setup 

copy config.properties.example  to config.properties

install mysql 
sudo apt update

sudo apt install mysql-server
sudo mysql_secure_installation
sudo mysql
    CREATE USER 'app'@'localhost' IDENTIFIED BY 'crab';
    GRANT CREATE, ALTER, DROP, INSERT, UPDATE, DELETE, SELECT, REFERENCES, RELOAD on *.* TO 'app'@'localhost' WITH GRANT OPTION;
    FLUSH PRIVILEGES;
    exit


for haystack
pip install farm-haystack
    