DROP DATABASE IF EXISTS FinalProject;
CREATE DATABASE FinalProject;

USE FinalProject;

CREATE TABLE UserInfo (
	userID INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    firstname VARCHAR(200) NOT NULL,
    lastname VARCHAR(200) NOT NULL,	
    email VARCHAR(200) NOT NULL,
    accountlevel INT NOT NULL
);
            
CREATE TABLE Class (
	classcode VARCHAR(6) NOT NULL UNIQUE PRIMARY KEY,
    classname VARCHAR(200) NOT NULL,
    instructorID INT NOT NULL,
    FOREIGN KEY (instructorID) REFERENCES UserInfo(userID)
);

CREATE TABLE ClassMember (
	studentID INT NOT NULL,
	classcode VARCHAR(6) NOT NULL,
    FOREIGN KEY (studentID) REFERENCES UserInfo(userID),
    FOREIGN KEY (classcode) REFERENCES Class(classcode)
);

CREATE TABLE Response(
	responseID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    response varchar(500),
    numVotes int,
    questionID int
);

CREATE TABLE Poll(
	questionID int NOT NULL AUTO_INCREMENT,
    count int,
    isPublic boolean,
    question varchar(500),
    classcode VARCHAR(6),
    instructorID varchar(200),
    responseListID int,
    PRIMARY KEY(questionID)
);

CREATE TABLE UserResponse (
	studentResponseID INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,
    studentID INT NOT NULL,
    questionID INT NOT NULL,
    responseID INT NOT NULL,
    FOREIGN KEY (questionID) REFERENCES Poll(questionID),
    FOREIGN KEY (responseID) REFERENCES Response(responseID)
);




 CREATE TABLE Chat (
	id INT NOT NULL AUTO_INCREMENT,
	sender_id INT NOT NULL,
	recipient_id INT NOT NULL,
	chat_id VARCHAR(200) NOT NULL,
	message VARCHAR(2000) NOT NULL,
	time DATETIME NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (sender_id) REFERENCES UserInfo(userID),
	FOREIGN KEY (recipient_id) REFERENCES UserInfo(userID)
  );
  

SELECT * FROM UserResponse;
SELECT * FROM UserInfo;
SELECT * FROM Class;
SELECT * FROM ClassMember;
SELECT * FROM Poll;
SELECT * FROM Response;
SELECT * FROM UserResponse;

