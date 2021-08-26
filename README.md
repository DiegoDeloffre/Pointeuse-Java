# Pointeuse-Java
Projet de simulation de pointeuse en Java

Dans le cadre de la première année de mon cursus en école d'ingénieur à Polytech Tours, mon groupe et moi avions pour mission de réaliser une pointeuse en Java.

Membres du groupe : DELOFFRE Diego - SENGER Maxime - LOIT Joris - FOURRE Pierre

Les deux programmes sont dans le même projet nommé Pointeuse mais dans deux packages différents.

La pointeuse est située dans le package nommé client.
Les trois classes Pointeuse-VuePointeuse-ControleurPointeuse forment le modèle MVC.
La classe ApplcationPointeuse comporte la méthode main du projet et la classe Sender sert à la transmission des données via socket.

Le server recevant les pointages est situé dans le package nommé server.
Les trois classes Entreprise-VueServer-ControleurServer forment le modèle MVC avec les classes Pointage, DayOfWork et Worker venant compléter l'entreprise.
La classe ApplicationServer comporte la méthode main du projet et les classes Server, SocketServer, ControleurServer servent à la réception des données via socket.

Un troisième package misc est présent pour les classes générales servant dans l'ensemble du projet.

Aucune contrainte sur l'ordre de lancement du programme. Il suffit d'exécuter les raccourcis présents dans le dossier ou directement les exécutables présents dans le sous-dossier src (nous sommes peu familiers avec MacOs et ne sommes pas surs du bon fonctionnement des raccourcis).
Lors de la première exécution des programmes une méthode stub viendra créer une Entreprise complète si les données sérialisées ne sont pas trouvées, puis dès la première fermeture des données sérialisées seront créées et seront chargées à chaque lancement.
Petite précision pour les tests de fonctionnement : il est bien évidemment impossible de pointer le weekend.

On espère que le programme fonctionnera aussi bien que lors de nos tests et nous vous souhaitons bon courage pour toutes les corrections.
