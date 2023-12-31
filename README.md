Contexte du projet

Aftas est un club de sports à Tiznit, situé exactement à Mirleft, il propose un ensemble de services et d'activités : Surf, Tennis, Quad, Pêche sous-marine, Parapente, et autres.

​

Le club organise régulièrement des compétitions de chasse sous-marine dans différentes régions du Maroc, les adhérents du club peuvent participer aux compétitions contre une somme qui doit être payée pour confirmer la participation, le club se charge de la logistique pour réussir la compétition et définir un jury qui se charge de calculer les résultats et afficher le podium.

​

Aflas club souhaite moderniser la gestion de ses compétitions en développant une application web responsive qui répond aux besoins de l'administration du club et du jury.

​

Un adhérent du club est caractérisé par : numéro d’adhésion, nom, prénom, pièce d’identification, nationalité, et date d'adhésion

Une compétition est individuelle et on considère qu’il ne peut y avoir qu’une seule compétition dans la même journée.

​

Les poissons sont caractérisés par leurs noms scientifiques et leurs poids moyen et sont classifiés en fonction de leur niveau de tir(difficulté qu'ils présentent à être chassés).

​

Lorsqu’on enregistre le résultat d’une compétition seul le nombre de poissons qui est comptabilisé(les poisons ne sont pas pesés : on raisonne toujours à partir du poid moyen)

​

À chaque niveau de tir est associé un nombre de points: plus le niveau est élevé plus le nombre de points est important (doit être vérifié à l’insertion des niveaux de tir).

​

Une compétition est caractérisée par un code de type chaîne de caractères (ex: Imsouane: pattern: ims-22-12-23 ), une date, heure de début et heure de fin, nombre de participants, et un lieu.

​

À l’insertion d’une chasse, il faut vérifier qu’elle n’existe pas, sinon on augmente le nombre de poissons chassés.

​

Les adhérents ont la possibilité de s'inscrire à des compétitions dès l'annonce et jusqu'à 24 heures avant le début.

​

L’interface admin doit permettre la recherche d’un adhérent par numéro, nom, ou prénom.

​

Les résultats des chasses sont enregistrés lors de la compétition le même jour, et le podium est affiché Les 3 premiers.

​

L’application doit aussi enregistrer les résultats de tous les participants.

​

Exigences Téchniques:

​

Utilisation de spring boot pour développer l’API.

L’application doit être organisée en couches

La validation de données est une obligation

La gestion des exceptions de manière centralisée(restControllerAdvice)

Il faut gérer la pagination

Ecrire les tests unitaires du service de calcule des résultats des compétitions, avec différents scénarios

L’API doit fournir tous les endpoints nécessaires

Utilisation d’angular pour fournir les interfaces utilisateurs.

​

Exigences fonctionelles:

​

Ajout d’une compétition

Lister les compétitions avec un filtre (en cours, fermé)

Inscription d’un membre dans une compétition(Chercher le membre et l'insérer s’il n’existe pas)

Insérer le résultat de la compétition du jour

Afficher le podium

Respect et vérification des règles mentionnées dans le cahier des charges
