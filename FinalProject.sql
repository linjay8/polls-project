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


 
CREATE TABLE Poll(
	id INT NOT NULL AUTO_INCREMENT,
    count INT,
    isPublic BOOLEAN,
    question VARCHAR(500),
    classCode INT,
    instructorID INT,
    responseListID INT,
    PRIMARY KEY(id)
);

CREATE TABLE Responses(
	responseID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    response VARCHAR(500),
    numVotes INT,
    questionID INT
);

CREATE TABLE UserResponse (
	studentResponseID INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,
    studentID INT NOT NULL,
    questionID INT NOT NULL,
    responseID INT NOT NULL,
    FOREIGN KEY (studentID) REFERENCES UserInfo(userID),
    FOREIGN KEY (questionID) REFERENCES Poll(id),
    FOREIGN KEY (responseID) REFERENCES Responses(responseID)
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

SELECT * FROM UserInfo;
SELECT * FROM Class;
SELECT * FROM ClassMember;
SELECT * FROM Poll;
SELECT * FROM Responses;
SELECT * FROM UserResponse;
SELECT * FROM Chat;



