# Guide de déploiement de l'application Java

Guide pour mettre en place et déployer l'application Java sur un serveur Tomcat 8.5.

---

## Prérequis

- **Java 17** : Assurez-vous que la version JDK 17 est installée.
- **Apache Tomcat 8.5** : Téléchargez et installez cette version depuis [ici](https://apache.root.lu/tomcat/tomcat-8/v8.5.93/bin/).
- **Base de données** : Une base de données PostgreSQL. Les scripts SQL nécessaires sont fournis (voir `src/main/resources/`).
- Le fichier `.war` généré de l'application.

---

## Étapes de déploiement

### 1. Préparer la base de données

1. Importez les scripts SQL fournis :
   - Créez la base de données sous le nom `amis_escalade` avec le port **5432**.
   - Exécutez les scripts disponibles dans `src/main/resources/` pour créer les tables et insérer les données de démonstration.

2. Configurez l'application pour se connecter à la base de données :
   - Modifiez le fichier `application.properties` ou un autre fichier de configuration avec les paramètres de connexion (URL, utilisateur, mot de passe).

---

### 2. Installer Apache Tomcat

1. Téléchargez et installez Apache Tomcat 8.5.
2. Configurez les variables d'environnement `JAVA_HOME` et `CATALINA_HOME`.

   Exemple pour **Linux/Mac** :
   ```bash
   export JAVA_HOME=/chemin/vers/java17
   export CATALINA_HOME=/chemin/vers/tomcat
   export PATH=$PATH:$CATALINA_HOME/bin
   ```

   Exemple pour **Windows** (Variables système) :
   - `JAVA_HOME`: `C:\Program Files\Java\jdk-17`
   - `CATALINA_HOME`: `C:\chemin\vers\tomcat`

### 3. Lancez Tomcat

- Linux/Mac : ./startup.sh
- Windows : startup.bat

---

## Déployer l'application

1. Copiez le fichier .war généré dans le dossier **webapps** de Tomcat puis renommez-le `Escalade.war` :
   - Exemple : C:\Program Files\Tomcat\webapps\Escalade.war.

2. Tomcat extraira et déploiera automatiquement l'application.
3. Accédez à l'application via un navigateur à l'adresse suivante :
   `http://localhost:8080/Escalade/home`
