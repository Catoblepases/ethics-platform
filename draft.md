Avant de commencer à présenter notre projet, nous aimerions vous poser une question. Avez-vous déjà entendu parler d'une dilemme de tramway ?

Le dilemme du tramway est une expérience de pensée éthique.

Il s'agit d'un train en fuite sur les voies. Sur la voie où circule le train, il y a cinq personnes attachées et incapables de bouger.

Le train est sur le point de les écraser.

Vous vous trouvez à côté d'un levier qui modifie la trajectoire du train.

Si vous tirez ce levier, le train se déplacera vers une autre voie. Cependant, sur l'autre voie, il y a aussi un homme attaché.

Vous avez deux choix.

1. ne rien faire et laisser le train écraser les cinq hommes sur son trajet normal
2. abaissez le levier et passez sur l'autre voie pour que le train passe sur l'homme qui se trouve sur l'autre voie.

Cette question éthique peut sembler déconnectée de la réalité, mais elle est en fait pertinente pour de nombreuses questions actuelles, comme la conduite autonome, où l'intelligence artificielle remplace les conducteurs humains dans les décisions et les opérations de conduite. 

Nous devons alors faire face à une nouvelle question éthique semblable au dilemme du tramway : comment les ordinateurs doivent-ils prendre des décisions ? Comment concevoir l'IA de manière à ce que la responsabilité de la prise de décision éthique ne revienne pas au programmeur ?

## intro

C'est le problème que notre projet espère résoudre, mais comme notre titre, plus que le processus de prise de décision éthique, l'objectif de ce projet est de permettre aux personnes n'ayant aucune formation en programmation et aucune connaissance de la programmation par ensembles de réponses d'expérimenter une interface graphique pour concevoir des cadres d'agents autonomes éthiques et construire des probleme éthiques.

Le projet fait partie d'une plate-forme comparative pour le développement de modèles de raisonnement éthique (c'est a dire des mécanismes permettant de déterminer si une décision est considérée comme permissible dans une situation donnée, sur la base de différents principes éthiques).

Dans ce projet, en raison des contraintes de temps, nous nous sommes concentrés sur les problemes éthiques liées au dilemme du tramway, principalement l'interface graphique et l'expérience utilisateur.

Nous présenterons d'abord la structure principale du projet, puis l'inférence éthique utilisée dans le projet, et enfin nous présenterons comment les utilisateurs peuvent construire des problèmes éthiques et les analyser en utilisant des agents autonomes éthiques grâce à ce logiciel.

## structure

然后，这个模型被自动翻译成先前定义的形式主义，并被赋予对不同的现有道德推理机制进行建模的可能性，以表明其提议。

## raisonement ethique

Un modèle d’action A permet à l’agent de
représenter son environnement et les changements qui s’y produisent. Il prend en entrée un ensemble d’actions réalisées.

En fait, cette section concerne la présentation formelle des questions éthiques.

Le modèle causal C retrace la causalité des actions et permet de déduire la responsabilité d'un agent.
Il prend en entrée la trace des événements donnée par le modèle d’action et une spécifcation des événements contenant un ensemble d’événements et de relations de dépendance.

Il est composé d’un moteur causal qui permet la création de l’arbre causal qui caractérise la simulation. 

Il génère une trace causale de chaque simulation qui désigne pour chaque point temporel les relations causales qui s’établissent entre les événements et les ﬂuents.

Un modèle éthique fait une déclaration sur la valeur intrinsèque d'un objectif ou d'un événement et envisage ce que l'agent devrait faire. Il prend en entrée le traçage causal donné par le modèle causal et produit des jugements moraux.

## techique
nous utilisons clingo pour gérer la partie prise de décision éthique et calcul. Nous utilisons le langage "Answer Set Programming" pour décrire le problème logicielle et ainsi utiliser de clingo pour calculer les résultats générés par les choix de l'utilisateur.

Clingo est un système de programmation par ensemble de réponses (ASP) qui prend un programme logique en entrée et calcule son ensemble de réponses, de sorte que si nous écrivons la question correctement dans un langage logique, nous obtiendrons sa réponse.

Pour faciliter la gestion, nous avons divisé le projet en un front-end et un back-end.

Le front-end crée une page web et est responsable de la visualisation et de l'interaction avec l'utilisateur, et le back-end  lit et traite les données.

Le front-end envoie des demandes au back-end lorsqu'il s'agit de modifications et de traitement de la base de données, et le back-end traite les demandes du front-end.
