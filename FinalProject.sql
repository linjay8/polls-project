DROP DATABASE IF EXISTS FinalProject;
CREATE DATABASE FinalProject;

USE FinalProject;

CREATE TABLE UserInfo (
	userID INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    logintoken VARCHAR(2000) NOT NULL,
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


CREATE TABLE Poll (
	questionID INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,
    question VARCHAR(500) NOT NULL,
    instructorID int NOT NULL,
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
    studentID INT NOT NULL,
    questionID INT NOT NULL,
    responseID INT NOT NULL,
    FOREIGN KEY (studentID) REFERENCES UserInfo(userID),
    FOREIGN KEY (questionID) REFERENCES Poll(questionID),
    FOREIGN KEY (responseID) REFERENCES Response(responseID)
);

SELECT * FROM UserInfo;
SELECT * FROM Class;
SELECT * FROM ClassMember;
SELECT * FROM Poll;
SELECT * FROM Response;
SELECT * FROM UserResponse;



