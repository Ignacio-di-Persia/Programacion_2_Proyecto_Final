CREATE DATABASE IF NOT EXISTS guarderia_central;
USE guarderia_central;

-- INSERT de datos para pruebas 
INSERT INTO zonas (codigo, ancho, cantidad_vehiculos, profundidad, tipo_vehiculos)
VALUES
('A', 10, 5, 20, 'Autos'),
('B', 8, 3, 15, 'Motos'),
('C', 12, 7, 25, 'Camionetas');

INSERT INTO garage (codigo, ocupado, zona_codigo)
VALUES
('G1', FALSE, 'A'),
('G2', FALSE, 'A'),
('G3', FALSE, 'B'),
('G4', FALSE, 'C'),
('G5', FALSE, 'C');
