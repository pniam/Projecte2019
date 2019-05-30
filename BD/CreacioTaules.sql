CREATE TABLE `adreca` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `carrer` varchar(50) NOT NULL,
  `num` int(11) NOT NULL,
  `localitat` varchar(50) NOT NULL,
  `cp` varchar(11) NOT NULL,
  `provincia` varchar(50) NOT NULL,
  `direccio` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `rol` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `usuari` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mail` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `idRol` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user_rol` FOREIGN KEY (`idRol`) 
    REFERENCES `rol` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `client` (
  `idUsuari` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  PRIMARY KEY (`idUsuari`),
  CONSTRAINT `fk_client_usuari` FOREIGN KEY (`idUsuari`) 
    REFERENCES `usuari` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `negoci` (
  `idUsuari` int(11) NOT NULL,
  `NifCif` varchar(50) NOT NULL,
  `nomComercial` varchar(50) NOT NULL,
  `idAdreca` int(11) NOT NULL,
  `baixa` int(11) NOT NULL,
  PRIMARY KEY (`idUsuari`),
  CONSTRAINT uc_nif_nomComercial UNIQUE(`NifCif`,`nomComercial`),
  CONSTRAINT uc_adreca UNIQUE(`idAdreca`),
  CONSTRAINT `fk_negoci_adreca` FOREIGN KEY (`idAdreca`) 
    REFERENCES `adreca` (`id`),
  CONSTRAINT `fk_negoci_usuari` FOREIGN KEY (`idUsuari`) 
    REFERENCES `usuari` (`id`) ON DELETE CASCADE
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `producte` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idNegoci` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  PRIMARY KEY (`id`,`idNegoci`),
  CONSTRAINT `fk_producte_negoci` FOREIGN KEY (`idNegoci`) 
    REFERENCES `negoci` (`idUsuari`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `cates` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idNegoci` int(11) NOT NULL,
  `idProducte` int(11) NOT NULL,
  `dataEvent` datetime NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_cata_negoci` FOREIGN KEY (`idNegoci`) 
    REFERENCES `producte` (`idNegoci`) on delete cascade,
  CONSTRAINT `fk_cata_producte` FOREIGN KEY (`idProducte`) 
    REFERENCES `producte` (`id`)    
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `participacio` (
  `idUsuari` int(11) NOT NULL,
  `idCata` int(11) NOT NULL,
  `assistencia` varchar(2) NOT NULL,
  `valoracio` int(11) DEFAULT NULL,
  PRIMARY KEY (`idUsuari`,`idCata`),  
  CONSTRAINT `fk_participacio_cata` FOREIGN KEY (`idCata`) 
    REFERENCES `cates` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_participacio_client` FOREIGN KEY (`idUsuari`) 
    REFERENCES `client` (`idUsuari`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;