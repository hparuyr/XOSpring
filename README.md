# XOSpring

## DB create script

create table GAME
(
ID INT NOT NULL AUTO_INCREMENT,
PLAYER1   VARCHAR(30) not null,
PLAYER2  VARCHAR(30),
STATE  VARCHAR(100),
TURN  VARCHAR(30),
WINNER VARCHAR(30),
LAST_MOVE INT,
primary key (ID)
);

## Maven commands usage

#### To clean project
mvn clean

#### To build project
mvn install

#### To run project on tomcat,(Note tomcat plugin is already configured in pom)
mvn tomcat7:run

#### All together
mvn clean install tomcat7:run
