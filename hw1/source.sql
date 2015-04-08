CREATE DATABASE IF NOT EXISTS WebServices;
Use WebServices;

DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS WatchedCompanies;
DROP TABLE IF EXISTS Companies;
DROP TABLE IF EXISTS Following;

  -- FOREIGN KEY (fKey) REFERENCES fTable
  -- ON UPDATE CASCADE
  -- ON DELETE NO ACTION

# Users are the 'accounts in the web service'

CREATE TABLE Users (
User_ID INTEGER NOT NULL AUTO_INCREMENT,
Username VARCHAR(255) NOT NULL,
Password VARCHAR(255) NOT NULL,
UNIQUE (Username),
PRIMARY KEY(User_ID)
);

# A User can watch a company by symbol
# Junction Table

CREATE TABLE WatchedCompanies (
User_ID INTEGER REFERENCES Users,
Symbol VARCHAR(20),
PRIMARY KEY (User_ID, Symbol),
CONSTRAINT watched UNIQUE (User_ID, Symbol)
);

-- CREATE TABLE WatchedCompanies (
-- User_ID INTEGER REFERENCES Users,
-- Symbol VARCHAR(20) REFERENCES Companies,
-- PRIMARY KEY (User_ID, Symbol)
-- -- FOREIGN KEY (User_ID) REFERENCES Users,
-- -- FOREIGN KEY (Symbol) REFERENCES Companies
-- );

-- # Companies how infoemation releated to it

-- CREATE TABLE Companies (
-- Symbol VARCHAR(20) NOT NULL,
-- Company_Name VARCHAR(255),
-- Start_Date VARCHAR(10),
-- End_Date VARCHAR(10),
-- Sector VARCHAR(255),
-- Industry VARCHAR(255),
-- PRIMARY KEY (Symbol)
-- );

# A User can follow other users, and see their watched companies

CREATE TABLE Following (
Follower INTEGER NOT NULL,
Followee INTEGER NOT NULL,
PRIMARY KEY (Follower, Followee),
FOREIGN KEY (Follower) REFERENCES Users (User_ID),
FOREIGN KEY (Followee) REFERENCES Users (User_ID),
CONSTRAINT watched UNIQUE (Follower, Followee)
);

# Default demo Users

INSERT INTO Users (Username, Password) VALUES ('Apache', 'Tomcat');
INSERT INTO Users (Username, Password) VALUES ('User1','One');
INSERT INTO Users (Username, Password) VALUES ('User2','Two');