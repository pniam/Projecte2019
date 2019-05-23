INSERT INTO `rol` (`id`, `nom`) VALUES
(1, 'Administrador'),
(2, 'Establiment'),
(3, 'Degustador');
INSERT INTO `adreca` (`id`, `carrer`, `num`, `localitat`, `cp`, `provincia`, `direccio`) VALUES
(1, 'ptge. La Tossa', 9, 'Igualada', '8700', 'Barcelona', 'ptge. La Tossa, 9, Igualada 08700 Barcelona');
INSERT INTO `usuari` (`id`, `mail`, `password`, `idRol`) VALUES
(1, 'admin@admin.com', 'admin', 1),
(2, 'empresa01', 'empresa01', 2),
(3, 'empresa02', 'empresa02', 2),
(4, 'client01', 'client01', 3),
(5, 'client02', 'client02', 3);
INSERT INTO `client` (`idUsuari`, `nom`) VALUES
(1, 'admin'),
(4, 'client01');
INSERT INTO `negoci` (`idUsuari`, `NifCif`, `nomComercial`, `idAdreca`, `baixa`) VALUES
(2, '47112432A', 'Empresa 01 SL', 1, 0);
INSERT INTO `producte` (`id`, `idNegoci`, `nom`) VALUES
(1, 2, 'vi blanc'),
(2, 2, 'vi negre');
INSERT INTO `cates` (`id`, `idNegoci`, `idProducte`, `dataEvent`) VALUES
(1, 2, 1, '2019-05-01 00:00:00');
INSERT INTO `participacio` (`idUsuari`, `idCata`, `assistencia`, `valoracio`) VALUES
(4, 1, '', NULL);






