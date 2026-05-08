🏦 Digital Banking Application

Une application complète d'E-Banking (Système de gestion de comptes bancaires) développée avec une architecture moderne, sécurisée par JWT et entièrement conteneurisée.

📋 Table des matières

Architecture

Fonctionnalités

Prérequis

Installation & Démarrage (Docker)

Développement Local

Auteur

🏗️ Architecture

Ce projet utilise une structure Monorepo divisée en micro-services logiques, orchestrée par Docker Compose :

Frontend : Application SPA (Single Page Application) développée en Angular 13.

Backend : API RESTful développée en Java 17 avec Spring Boot.

Base de données : MySQL 8 persistée via des volumes Docker.

Sécurité : Implémentation robuste avec Spring Security et OAuth2 (JWT) + Guards Angular.

✨ Fonctionnalités

Espace Administrateur

Gestion des clients (Création, Lecture, Recherche, Suppression).

Gestion des comptes bancaires (Courant et Épargne).

Consultation de l'historique de tous les comptes.

Espace Utilisateur (Client)

Authentification sécurisée.

Consultation de ses propres comptes bancaires.

Visualisation de l'historique des opérations (Débits, Crédits).

(À venir : Virements de compte à compte).

⚙️ Prérequis

Pour exécuter l'application en environnement de production simulé, vous avez uniquement besoin de :

Docker

Docker Compose

🚀 Installation & Démarrage (Docker)

Cloner le dépôt :

git clone [https://github.com/Andynator3/digital-banking-monorepo.git](https://github.com/Andynator3/digital-banking-monorepo.git)
cd digital-banking-monorepo


Configuration de l'environnement :
Créez un fichier .env à la racine du projet (un fichier .env.example est fourni) et configurez vos variables :

DB_NAME=digital_banking_db
DB_USER=dbuser
DB_PASSWORD=dbpassword
DB_ROOT_PASSWORD=rootpassword
DB_HOST_PORT=3307
BACKEND_PORT=8090
FRONTEND_PORT=8085
JWT_SECRET=votre_cle_secrete_tres_longue_et_complexe


Lancement de l'infrastructure :

docker-compose up -d --build


Accès à l'application :

Frontend : http://localhost:8085

Backend API : http://localhost:8090

Base de données : Accessible sur localhost:3307

💻 Développement Local

Pour les développeurs souhaitant modifier le code en temps réel (Hot-Reload) sans utiliser Docker pour la compilation :

Lancer uniquement la base de données via Docker : docker-compose up -d mysqldb

Lancer le backend via votre IDE (IntelliJ/Eclipse) sur le port 8090.

Lancer le frontend via le CLI Angular : ng serve (Accessible sur localhost:4200).

👨‍💻 Auteur

Andy JEANTY/Andynator - Développeur Full-Stack & DevOps