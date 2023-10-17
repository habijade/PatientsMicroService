
# Poseidon Application

## À Propos
Bienvenue dans Poseidon, une application Web conçue pour dynamiser les transactions sur les marchés financiers. Notre objectif est d'agréger une multitude de sources d'informations pour fournir une solution complète aux utilisateurs.

## Configuration Système
Poseidon repose sur une pile technologique performante pour répondre à vos besoins.

### Backend
- Langage de Programmation : Java 17
- Framework : Spring Boot v3.1.0
- Sécurité : Spring Security

### Frontend
- Technologies : HTML, Thymeleaf
- Framework : Bootstrap v4.1.3

### Base de Données
- Système de Gestion de Bases de Données : MySQL

## Prérequis
- JDK Java 17 installé sur votre système
- Une base de données "poseidon"
- Dépendances logicielles : Thymeleaf, Spring Security, Bootstrap

## Configuration de l'Application
1. Sélectionnez un outil de gestion de bases de données tel que Docker.
2. Créez une base de données avec docker en utilisant la commande : `docker run --name poseidon-db -e POSTGRES_USER=mon_utilisateur -e POSTGRES_PASSWORD=mon_mot_de_passe -e POSTGRES_DB=poseidon -p 5432:5432 -d postgres`
3. Assurez-vous d'utiliser la base de données "Poseidon".
4. Une fois la configuration prête, démarrez l'application en utilisant la commande : `mvn spring-boot:run`.

## Tester Poseidon
Après avoir lancé l'application, accédez à l'URL suivante : `http://localhost:8080` pour commencer à utiliser Poseidon.

## Fonctionnalités
1. Liste d'enchères
2. Analyse de courbe
3. Notation
4. Transactions
5. Règles
6. Gestion des utilisateurs (accès uniquement au Administrateur)