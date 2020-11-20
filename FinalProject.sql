DROP DATABASE IF EXISTS FinalProject;
CREATE DATABASE FinalProject;

USE FinalProject;

CREATE TABLE UserInfo (
	userID VARCHAR(2000) NOT NULL PRIMARY KEY,
    firstname VARCHAR(200) NOT NULL,
    lastname VARCHAR(200) NOT NULL,	
    email VARCHAR(200) NOT NULL UNIQUE,
    accountlevel INT NOT NULL
);
            
CREATE TABLE Class (
	classcode VARCHAR(6) NOT NULL UNIQUE PRIMARY KEY,
    classname VARCHAR(200) NOT NULL UNIQUE,
    instructorID VARCHAR(2000) NOT NULL,
    FOREIGN KEY (instructorID) REFERENCES UserInfo(userID)
);

CREATE TABLE ClassMember (
	studentID VARCHAR(500) NOT NULL,
	classcode VARCHAR(6) NOT NULL,
    FOREIGN KEY (studentID) REFERENCES UserInfo(userID),
    FOREIGN KEY (classcode) REFERENCES Class(classcode)
);


CREATE TABLE Poll (
	questionID INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,
    question VARCHAR(500) NOT NULL,
    instructorID VARCHAR(2000) NOT NULL,
    classcode VARCHAR(6) NOT NULL,
    resultvis INT NOT NULL,
    FOREIGN KEY (instructorID) REFERENCES UserInfo(userID),
    FOREIGN KEY (classcode) REFERENCES Class(classcode)
);

CREATE TABLE Response (
	responseID INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,
    response VARCHAR(500) NOT NULL,
    numvotes INT NOT NULL,
    questionID INT NOT NULL,
    FOREIGN KEY (questionID) REFERENCES Poll(questionID)
);

CREATE TABLE UserResponse (
	studentResponseID INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,
    studentID VARCHAR(2000) NOT NULL,
    questionID INT NOT NULL,
    responseID INT NOT NULL,
    FOREIGN KEY (studentID) REFERENCES UserInfo(userID),
    FOREIGN KEY (questionID) REFERENCES Poll(questionID),
    FOREIGN KEY (responseID) REFERENCES Response(responseID)
);

 CREATE TABLE chat (
	id INT NOT NULL AUTO_INCREMENT,
	sender_id VARCHAR(200) NOT NULL,
	recipient_id VARCHAR(200) NOT NULL,
	chat_id VARCHAR(400) NOT NULL,
	message VARCHAR(2000) NOT NULL,
	time DATETIME NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (sender_id) REFERENCES UserInfo(userID),
	FOREIGN KEY (recipient_id) REFERENCES UserInfo(userID)
  );

SELECT * FROM UserInfo;
SELECT * FROM Class;
SELECT * FROM ClassMember;
SELECT * FROM Poll;
SELECT * FROM Response;
SELECT * FROM UserResponse;
SELECT * FROM chat;


