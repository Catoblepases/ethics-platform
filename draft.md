Notre projet porte sur la Génération de problèmes éthiques parconstruction graphique, et nous avons commencé ce projet sous la supervision de M. Gauvain Bourgne.

Nous commencerons par une introduction au dilemme du tramway  suivie d'une présentation de la structure principale du projet, comment nous avons mis en œuvre cette plateforme éthique puis du raisonnement éthique utilisé dans le projet, et enfin de la manière dont les utilisateurs peuvent construire des questions éthiques et les analyser à l'aide d'agents autonomes éthiques via le logiciel.

## Prep

Avez-vous entendu parler du dilemme du tramway ?

Le dilemme du tramway est une expérience de pensée éthique.

Il s'agit d'un train en fuite sur les voies. Sur la voie où circule le train, il y a cinq personnes attachées et incapables de bouger.

Le train est sur le point de les écraser.

Vous vous trouvez à côté d'un levier qui modifie la trajectoire du train.

Si vous tirez ce levier, le train se déplacera vers une autre voie. Cependant, sur l'autre voie, il y a aussi un homme attaché.

Vous avez deux choix.

1. ne rien faire et laisser le train écraser les cinq hommes
2. abaissez le levier et passez sur l'autre voie pour que le train passe sur l'homme qui se trouve sur l'autre voie.

Cette dilemme peut sembler déconnectée de la réalité, mais elle est en fait pertinente pour de nombreuses questions actuelles, comme la conduite autonome, où l'intelligence artificielle remplace les conducteurs humains dans les décisions et les opérations de conduite. 

Nous devons alors faire face à une nouvelle question éthique semblable au dilemme du tramway : comment les ordinateurs doivent-ils prendre des décisions ? Comment concevoir l'IA de manière à ce que la responsabilité de la prise de décision éthique ne revienne pas au programmeur ?

## intro

C'est le problème que notre projet espère résoudre, mais comme notre titre, plus que le processus de prise de décision éthique, l'objectif de ce projet est de permettre aux personnes n'ayant aucune formation en programmation d'expérimenter une interface graphique pour concevoir des cadres d'agents autonomes éthiques et construire des probleme éthiques.

### besoins

Le projet fait partie d'une plate-forme comparative pour le développement de modèles de raisonnement éthique (c'est a dire des mécanismes permettant de déterminer si une décision est considérée comme permissible dans une situation donnée, sur la base de différents principes éthiques).

Dans ce projet, en raison des contraintes de temps, nous nous sommes concentrés sur les problemes éthiques liées au dilemme du tramway, principalement l'interface graphique et l'expérience utilisateur.

## raisonement ethique

Un modèle d’action A permet à l’agent de représenter son environnement et les changements qui s’y produisent. Il prend en entrée un ensemble d’actions réalisées.

En fait, cette partie concerne la présentation formelle des probleme éthiques.

Le modèle causal C retrace la causalité des actions et permet de déduire la responsabilité d'un agent. Il est composé d’un moteur causal qui permet la création de l’arbre causal qui caractérise la simulation. 

Il génère une trace causale de chaque simulation qui désigne pour chaque point temporel les relations causales qui s’établissent entre les événements et les ﬂuents.

Un modèle éthique fait une déclaration sur la valeur intrinsèque d'un objectif ou d'un événement et envisage ce que l'agent devrait faire. Il prend en entrée le traçage causal donné par le modèle causal et produit des jugements moraux.

## technique

### ASP et Clingo

nous utilisons clingo pour gérer la partie prise de décision éthique et calcul. Nous utilisons le langage "Answer Set Programming" pour décrire le problème logicielle et ainsi utiliser de clingo pour calculer les résultats générés par les choix de l'utilisateur.

Clingo est un système de programmation par ensemble de réponses (ASP) qui prend un programme logique en entrée et calcule son ensemble de réponses, de sorte que si nous écrivons la question correctement dans un langage logique, nous obtiendrons sa réponse.

Pour faciliter la gestion, nous avons divisé le projet en un front-end et un back-end.

Le front-end crée une page web et est responsable de la visualisation et de l'interaction avec l'utilisateur, et le back-end  lit et traite les données.

Le front-end envoie des demandes au back-end lorsqu'il s'agit de modifications et de traitement de la base de données, et le back-end traite les demandes du front-end.

### structure

Les utilisateurs peuvent construire des problemes éthiques grâce à l'interface graphique du site web.

Ce modèle est ensuite automatiquement traduit dans le formalisme précédemment défini et envoyé à clingo, qui calcule le modèle causal et le raisonnement ethique, le back-end le lit, et la sortie de clingo est envoyée au front-end, où l'utilisateur peut voir le tableau des jugements éthiques et l'arbre causal.

## GUIDE DE L'UTILISATEUR

Dans cette section, nous décrirons comment cette interface présente des dilemme logiques et analyse chaque evenement par l'arbres causals et de jugement ethique.

Nous avons créé une vidéo pour présenter notre interface.

Dans la vidéo, nous avons mentionné l'arbre des causes, où chaque rectangle représente un événement différent.Gris pour les situations, bleu pour les événements volontaire，et rose pour les evenements automatique.En arête, vert signifie causer et rouge signifie empêcher l’événement ou situation de
se produire. Prenons cet exemple. Si nous choisissons de pousser la personne du pont, elle tombera sur les rails et le train la tuera, mais il empêchera aussi la mort des cinq personnes qui suivent.

Dans le tableau final des résultats, nous verrons de nombreuses conclusions éthiques différentes à l'analyse des résultats, comme celle-ci, où nous pouvons voir que l'acte de pousser une personne d'un pont serait accepté par la proscription d'actions purement préjudiciables.

## Conclusion
Dans ce projet, nous avons realise une partie de la plateforme de comparaison éthique, en créant une interface graphique pour les probleme éthiques liées au problème du train.

Auparavant, nous ne savions que très peu de choses sur ce type de problème de logique informatique, mais grâce à ce projet, nous avons appris quelques concepts de base de l'éthique informatique et nous avons eu un premier aperçu du langage des ensembles de réponses, en plus de nous familiariser avec la conception et le développement d'applications web et le langage.

Nous espérons que le fait de permettre une représentation graphique des chaînes de causalité pour modéliser tout type de problème permettra d'élever l'abstraction d'un cran.