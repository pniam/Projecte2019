INSERT INTO `rol` (`id`, `nom`) VALUES
(1, 'Administrador'),
(2, 'Degustador'),
(3, 'Establiment');
INSERT INTO `adreca` (`id`, `carrer`, `num`, `localitat`, `cp`, `provincia`, `direccio`) VALUES
(1, 'Passatge de la Tossa', 9, 'Igualada', '08700', 'Barcelona', 'Passatge de la Tossa, 9, 08700 Igualada, Barcelona'),
(2, 'Carrer Sant Martí de Sesgueioles', 1, 'Igualada', '08700', 'Barcelona', 'Carrer Sant Martí de Sesgueioles, 1, 08700 Igualada, Barcelona'),
(3, 'Carrer Lleida', 63, 'Òdena', '08711', 'Barcelona', 'Carrer Lleida, 63, 08711 Òdena, Barcelona'),
(4, 'Carrer del Pont', 47, 'Sant Maure', '08710', 'Barcelona', 'Carrer del Pont, 47, 08710 Sant Maure, Barcelona');
INSERT INTO `usuari` (`id`, `mail`, `password`, `idRol`) VALUES
(1, 'Admin@admin.com', 'e3afed0047b08059d0fada10f400c1e5', 1),
(2, 'Usuari001@usuari.com', 'f903789789ecf6b67ee80d5581c2dbdd', 2),
(3, 'Usuari002@usuari.com', '37e1796ce4d4229eb4514abd8d4c40f0', 2),
(4, 'Usuari003@usuari.com', '73e4c91d5fd94c7c495473cda604a5ba', 2),
(5, 'Usuari004@usuari.com', '082a5fef1aec58600dade1dbe13d6862', 2),
(6, 'Empresa001@empresa.com', '0db3df5418bec028d7fe17dadbc274cf', 3),
(7, 'Empresa002@empresa.com', '2b635afb09381bda81d9066efec35d79', 3),
(8, 'Empresa003@empresa.com', 'fa5bfa2ea2029a32f169257aedd90c14', 3),
(9, 'Empresa004@empresa.com', 'ad7501a9a4115d0c42030ee982e61978', 3);
INSERT INTO `client` (`idUsuari`, `nom`) VALUES
(1, 'Admin'),
(2, 'Pau'),
(3, 'PNIAM'),
(4, 'Closa'),
(5, 'Marina');
INSERT INTO `negoci` (`idUsuari`, `NifCif`, `nomComercial`, `idAdreca`, `baixa`) VALUES
(6, 'Z47112432', 'PNIAM SL', 1, 0),
(7, 'S47112432', 'Paraly SL', 2, 0),
(8, 'P59223521', 'Clos SL', 3, 0),
(9, 'L82192384', 'Guima SL', 4, 0);
INSERT INTO `producte` (`id`, `idNegoci`, `nom`) VALUES
(3, 9, 'Vi Blanc'),
(4, 9, 'Vi Negre'),
(5, 9, 'Vi Rosat'),
(6, 8, 'Formatge Blau'),
(7, 8, 'Pernil Salat'),
(8, 8, 'Formatge de Mel'),
(9, 7, 'Formatge Blau'),
(10, 7, 'Formatge de cabra'),
(11, 6, 'Vi Negre'),
(12, 6, 'Formatge de cabra');
INSERT INTO `cates` (`id`, `idNegoci`, `idProducte`, `dataEvent`) VALUES
(2, 7, 9, '2019-05-16 00:00:00'),
(3, 7, 10, '2019-05-26 00:00:00'),
(4, 6, 12, '2019-05-25 00:00:00'),
(5, 6, 11, '2019-05-21 00:00:00'),
(6, 9, 3, '2019-06-28 00:00:00'),
(7, 9, 4, '2019-06-23 00:00:00');
INSERT INTO `participacio` (`idUsuari`, `idCata`, `assistencia`, `valoracio`) VALUES
(2, 2, 'S', 6),
(2, 3, 'S', 4),
(2, 4, 'S', 2),
(2, 5, 'S', 3),
(3, 2, 'S', 8),
(3, 4, 'N', NULL),
(4, 2, 'S', 3),
(4, 4, 'S', 3),
(4, 5, 'N', NULL),
(5, 2, 'S', 9),
(5, 3, 'S', 6),
(5, 4, 'S', 1),
(5, 5, 'S', 9);






