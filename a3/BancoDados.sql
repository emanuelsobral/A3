/*---Criando BD---*/
CREATE DATABASE A3;
USE A3;
/*---Criando tabela de exercicios---*/
CREATE TABLE exercicios (
exercicioID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  exercicio VARCHAR(50) NOT NULL,
  intensidade VARCHAR(10) NOT NULL,
  fatorIntensidade INT NOT NULL,
  MET DECIMAL(4,2) NOT NULL
);

/*CRiando Tabela de Usuário*/
CREATE TABLE usuario (
  userID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(60) NOT NULL,
  email VARCHAR(60) NOT NULL,
  senha VARCHAR(60) NOT NULL,
  altura FLOAT NOT NULL,
  idade INT(11) NOT NULL,
  peso FLOAT NOT NULL,
  frequencia INT(2),
  genero TINYTEXT NOT NULL,
  admin TINYINT(1),
  exercicioID INT,
  CONSTRAINT fk_userexe FOREIGN KEY (exercicioID) REFERENCES exercicios (exercicioID)
);

/*---Populando tabela de exercicios---*/
INSERT INTO exercicios (exercicio, intensidade, fatorIntensidade, MET) VALUES
('Caminhada', 'Fácil', 1, 3.5),
('Corrida', 'Moderado', 3, 7.0),
('Natação', 'Difícil', 5, 10.0),
('Yoga', 'Fácil', 1, 2.5),
('Musculação', 'Moderado', 3, 5.0),
('Boxe', 'Difícil',5,12.0),
('Escalada', 'Difícil', 5 , 11.0),
('HIIT', 'Difícil', 5 , 15.0),
('Crossfit', 'Difícil', 5 , 12.0),
('Pilates', 'Fácil', 1 , 3.0),
('Tai Chi', 'Iniciante', 2 , 3.0),
('Dança Zumba', 'Moderado', 3 , 6.0),
('Basquete', 'Moderado', 3 , 6.5),
('Futebol', 'Avançado', 4 , 7.0),
('Saltar Corda', 'Avançado', 4, 10.0),
('Corida em ladeira', 'Difícil', 5 , 15.0),
('Remo', 'Difícil', 5 , 12.0),
('Ciclismo em terreno acidentado', 'Difícil' , 5 , 14.0),
('HIIT com pesos', 'Difícil', 5 , 16.0);

/*
Intensidades: 1 - Facil, 
              2 - Iniciante, 
              3 - Moderado, 
              4 - Avançado, 
              5 - Dificil
*/

/*---Populando tabela de usuário---*/
INSERT INTO usuario (nome, email, senha, altura, idade, peso, genero, exercicioID) 
VALUES  ('Carlos', 'carlos@email.com', 'senhacarlos', 1.76, 38, 65.7, 'M', 0),
        ('Maria', 'maria@email.com', 'maria123', 1.62, 28, 54.2, 'F', 0),
        ('Roberto', 'roberto@email.com', 'roberto34', 1.72, 43, 72.2, 'M', 0),
        ('Usuario', 'usuario', 'usuario', 1.72, 43, 72.2, 'M', 0);

        
INSERT INTO usuario (nome, email, senha, altura, idade, peso, frequencia, genero, admin) 
VALUES  ('Admin', 'admin', 'admin', 1.72, 43, 72.2, 5, 'M', 1);