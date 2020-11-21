-- DROP DATABASE IF EXISTS UserInformation;

-- CREATE DATABASE UserInformation;
-- USE UserInformation;

-- Correct way to add instructor and response list to table?
CREATE TABLE Poll(
	id int NOT NULL AUTO_INCREMENT,
    count int,
    isPublic boolean,
    question varchar(500),
    instructorID int,
    responseListID int,
    PRIMARY KEY(id)
);

CREATE TABLE Responses(
	responseID int,
    response varchar(500),
    numVotes int,
    questionID int
);

Select * from Responses;

