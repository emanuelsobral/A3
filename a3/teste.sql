CREATE DATABASE A3;

USE A3;

CREATE TABLE usuario (
  userID INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(60) NOT NULL,
  email VARCHAR(60) NOT NULL,
  senha VARCHAR(60) NOT NULL,
  altura FLOAT NOT NULL,
  idade INT(11) NOT NULL,
  peso FLOAT NOT NULL,
  frequencia INT(2) NOT NULL,
  genero TINYTEXT NOT NULL,
  PRIMARY KEY (userID)
);

INSERT INTO usuario (nome, email, senha, altura, idade, peso, frequencia, genero) 
VALUES  ('Carlos', 'carlos@email.com', 'senhacarlos', 1.76, 38, 65.7, 4, 'M');
