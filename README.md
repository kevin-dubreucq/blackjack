# Blackjack (console, Java)

Jeu de Blackjack en ligne de commande, écrit en Java. Un ou plusieurs joueurs (1 à 7) affrontent la banque (« House »). Chaque joueur décide à tour de rôle s'il tire une carte, puis la banque joue selon une règle fixe.

> Version actuelle : `0.1.0`
> Auteurs : Drqke, Kevin

---

## Sommaire

1. [Fonctionnalités](#fonctionnalités)
2. [Prérequis](#prérequis)
3. [Structure du projet](#structure-du-projet)
4. [Compilation et lancement](#compilation-et-lancement)
5. [Règles implémentées](#règles-implémentées)
6. [Déroulement d'une partie](#déroulement-dune-partie)
7. [Architecture et classes](#architecture-et-classes)
8. [Exemple de session](#exemple-de-session)
9. [Limitations et bugs connus](#limitations-et-bugs-connus)
10. [Pistes d'amélioration](#pistes-damélioration)

---

## Fonctionnalités

- Partie de 1 à 7 joueurs contre la banque.
- Saisie des noms des joueurs (nom par défaut `Joueur N` si vide).
- Paquet standard de 52 cartes, mélangé au démarrage.
- Calcul de la valeur de la main (as valant 11 ou 1, voir [limitations](#limitations-et-bugs-connus)).
- Tirage de cartes à la demande pour chaque joueur.
- Banque automatique : tire tant que sa main vaut 16 ou moins.
- Résolution de la manche : victoire, défaite, égalité ou dépassement de 21.

---

## Prérequis

- **JDK 21 ou supérieur.**
  Le code utilise `List.removeLast()` (`Deck.withdraw()`), méthode introduite en Java 21. Avec une version antérieure, la compilation échoue.
- Un terminal (le jeu est entièrement en console).

Vérifier sa version :

```bash
java -version
javac -version
```

---

## Structure du projet

Les classes sont déclarées dans le package `fr.univartois.butinfo.qdev.blackjack.card`. Les fichiers doivent donc être placés dans l'arborescence correspondante :

```
blackjack/
├── README.md
└── src/
    └── fr/univartois/butinfo/qdev/blackjack/card/
        ├── Blackjack.java        # Point d'entrée (main)
        ├── BlackjackGame.java    # Logique d'une manche
        ├── Card.java             # Une carte
        ├── CardOwner.java        # Classe abstraite : main + calcul de valeur
        ├── Deck.java             # Le paquet de cartes
        ├── House.java            # La banque
        ├── Player.java           # Un joueur humain
        ├── Suit.java             # Enum des couleurs
        └── Value.java            # Enum des valeurs
```

`package-info.java` déclare actuellement le package `fr.univartois.butinfo.qdev.blackjack` (sans `.card`), ce qui ne correspond pas au package des autres classes. Voir [limitations](#limitations-et-bugs-connus).

---

## Compilation et lancement

Depuis la racine du projet :

**Linux / macOS**

```bash
mkdir -p out
javac -d out $(find src -name "*.java" ! -name "package-info.java")
java -cp out fr.univartois.butinfo.qdev.blackjack.card.Blackjack
```

**Windows (PowerShell)**

```powershell
mkdir out
$files = Get-ChildItem -Recurse src -Filter *.java | Where-Object { $_.Name -ne "package-info.java" } | ForEach-Object { $_.FullName }
javac -d out $files
java -cp out fr.univartois.butinfo.qdev.blackjack.card.Blackjack
```

Avec un IDE (IntelliJ, Eclipse, VS Code), importer le dossier `src` comme source root et lancer la classe `Blackjack`.

---

## Règles implémentées

| Élément | Règle dans ce projet |
|---|---|
| Paquet | 1 paquet de 52 cartes, mélangé une seule fois |
| Valeur des cartes | 2 à 10 : valeur faciale. Valet, Dame, Roi : 10. As : 11 (ramené à 1 si nécessaire) |
| Distribution | 2 cartes par joueur, puis 2 cartes pour la banque |
| Carte cachée | La première carte de la banque est retournée face cachée jusqu'à la fin des tours des joueurs |
| Tour du joueur | Le joueur tire tant qu'il répond « o » ou « oui » et qu'il n'a pas dépassé 21 |
| Tour de la banque | Tire tant que sa main vaut **16 ou moins**, s'arrête à **17 ou plus** |
| Dépassement | Une main strictement supérieure à 21 est perdante (« bust ») |
| Résolution | Joueur battu par dépassement → perdu. Banque dépasse → tous les joueurs restants gagnent. Sinon comparaison des valeurs : plus haut gagne, égalité = « push » |

**Non implémenté** : mise / argent, blackjack naturel (21 sur les deux premières cartes avec paiement spécial), doublement (« double down »), séparation (« split »), assurance, abandon (« surrender »), plusieurs manches consécutives.

---

## Déroulement d'une partie

1. `Blackjack.main` demande le nombre de joueurs (une saisie invalide ou hors plage est ramenée entre 1 et 7) puis le nom de chaque joueur.
2. `BlackjackGame.play()` est appelée :
   1. Chaque joueur reçoit 2 cartes.
   2. La banque reçoit 2 cartes et retourne sa première carte.
   3. Les mains sont affichées.
   4. Chaque joueur, dans l'ordre, se voit demander s'il veut tirer, jusqu'à ce qu'il refuse ou dépasse 21.
   5. La banque retourne sa première carte et affiche sa main.
   6. La banque tire jusqu'à atteindre 17 ou plus.
   7. Chaque joueur reçoit son résultat : `win()`, `lose()`, `push()` ou `bust()`.
3. Le programme se termine après cette unique manche.

---

## Architecture et classes

```
              CardOwner (abstract)
              ├── hand : List<Card>
              ├── getHandValue(), isBusted(), receive()
              └── isHitting() (abstract)
               ▲                ▲
               │                │
            Player            House
        (saisie console)   (tire si ≤ 16)

   BlackjackGame ──► Deck ──► Card ──► Value, Suit
        │
        ├── House
        └── List<Player>
```

### `Blackjack`
Point d'entrée. Lit le nombre de joueurs et leurs noms via un `Scanner`, enregistre les joueurs dans `BlackjackGame`, puis appelle `game.play()`.

### `BlackjackGame`
Orchestre une manche : création et mélange du paquet, distribution, tours des joueurs, tour de la banque, résolution. Les erreurs de `Deck.withdraw()` (paquet vide) sont encapsulées dans une `RuntimeException`.

| Méthode | Rôle |
|---|---|
| `BlackjackGame()` | Crée un paquet de 52 cartes, le mélange, crée la banque et la liste de joueurs |
| `addPlayer(String name)` | Ajoute un joueur à la partie |
| `play()` | Joue une manche complète |

### `CardOwner` (abstraite)
Représente un détenteur de cartes (joueur ou banque). Gère la main et le calcul de sa valeur.

| Méthode | Rôle |
|---|---|
| `isHitting()` | *Abstraite.* Indique si le détenteur veut tirer une carte |
| `receive(Card)` | Ajoute une carte à la main |
| `getHandValue()` | Calcule la valeur de la main |
| `isBusted()` | `true` si la valeur dépasse 21 |
| `toString()` | Affiche `name : <nom> Hand value : <valeur>` |
| `getCard(int)` | *Protégée.* Accède à une carte de la main |

### `Player`
Joueur humain. `isHitting()` affiche `Voulez-vous tirer une carte ? (o/n)` et lit la réponse sur l'entrée standard. Accepte `o` ou `oui` (insensible à la casse). Fournit aussi les messages de fin de manche : `win()`, `push()`, `lose()`, `bust()`.

### `House`
La banque. `isHitting()` renvoie `true` si la main vaut 16 ou moins. `flipFirstCard()` retourne la première carte de sa main.

### `Deck`
Paquet de cartes. Constructeur privé, instanciation via la fabrique `Deck.create52CardsDeck()`.

| Méthode | Rôle |
|---|---|
| `create52CardsDeck()` | Crée les 52 combinaisons couleur × valeur |
| `shuffle()` | Mélange le paquet |
| `add(Card)` | Ajoute une carte si elle n'est pas déjà présente |
| `withdraw()` | Retire et renvoie la dernière carte ; lève une `Exception` si le paquet est vide |

### `Card`
Carte immuable en valeur/couleur, avec un état face visible / cachée (`flip()`). Créée face cachée par défaut. `toString()` renvoie `**********` si cachée, sinon `<valeur numérique> of <couleur>`.

### `Value` et `Suit`
Énumérations. `Value` associe chaque rang à sa valeur numérique (As = 11, figures = 10). `Suit` contient `CLUBS`, `DIAMONDS`, `HEARTS`, `SPADES`.

---

## Exemple de session

Le format exact dépend de l'implémentation actuelle (voir les limitations : les cartes elles-mêmes ne sont pas affichées, seulement la valeur de la main).

```
=== BIENVENUE AU BLACKJACK ===
Combien de joueurs (1 à 7) ? : 2
Nom du joueur 1 : Alice
Nom du joueur 2 : Bob
name : Alice Hand value : 15
name : Bob Hand value : 19
name : House Hand value : 18
Voulez-vous tirer une carte ? (o/n) : o
name : Alice Hand value : 24
Voulez-vous tirer une carte ? (o/n) : n
name : House Hand value : 18
Vous avez dépassé 21
Perdu
```

---

## Limitations et bugs connus

Voici l'état réel du code, afin de ne pas être surpris.

### Bugs de logique

1. **Calcul de l'as incorrect dans certains cas.** `getHandValue()` ne ramène un as de 11 à 1 que lorsque *la carte en cours de traitement* est un as et que le total dépasse 21. Si l'as vient en premier et qu'un dépassement survient avec une carte suivante, l'as n'est jamais réduit.
   Exemple : As, 5, 10 → le programme calcule `26` (bust) alors que la valeur correcte est `16`.
   Correction recommandée : additionner toutes les cartes en comptant les as pour 11, compter les as, puis retirer 10 tant que le total dépasse 21 et qu'il reste des as à 11.
2. **Les cartes ne sont jamais affichées.** `CardOwner.toString()` n'affiche que le nom et la valeur de la main, pas les cartes. Conséquence : la carte cachée de la banque n'est pas réellement cachée, puisque sa valeur est incluse dans le total affiché dès le début.
3. **`Card.toString()` affiche la valeur numérique**, donc un Roi ou une Dame s'affichent comme `10 of hearts`. Il faudrait utiliser le nom de `Value`.
4. **Pas de gestion du blackjack naturel** : un 21 sur les deux premières cartes est traité comme n'importe quel 21.

### Problèmes techniques

5. **Plusieurs `Scanner` sur `System.in`.** `Blackjack.main` en crée un, et `Player.isHitting()` en crée un *nouveau à chaque appel*. Cela fonctionne en saisie interactive mais est fragile (perte d'entrée tamponnée, entrée redirigée par un fichier ou un pipe). Il vaut mieux créer un seul `Scanner` et le transmettre aux joueurs.
6. **Incohérence de package.** `package-info.java` déclare `fr.univartois.butinfo.qdev.blackjack` alors que toutes les autres classes sont dans `fr.univartois.butinfo.qdev.blackjack.card`. Ce fichier doit être aligné ou supprimé.
7. **Paquet épuisable.** Un seul paquet est utilisé pour toute la manche, sans remélange. Avec beaucoup de joueurs tirant de nombreuses cartes, `Deck.withdraw()` peut lever une exception, transformée en `RuntimeException` qui interrompt le programme.
8. **`Deck.add()` utilise `contains()`** alors que `Card` ne redéfinit ni `equals` ni `hashCode` : la vérification de doublon compare des références et ne protège de rien.
9. **Gestion d'erreurs trop large.** `withdraw()` déclare `throws Exception` et `BlackjackGame` répète des blocs `try/catch (Exception)`. Une exception dédiée (`EmptyDeckException`) ou une exception non vérifiée simplifierait beaucoup le code.
10. **Imports et code redondants** : imports de classes du même package, imports inutilisés (`Collections` dans `CardOwner`, `Scanner` pris en charge à plusieurs endroits), `Card` aux attributs non `final`.

### Limites fonctionnelles

- Une seule manche par exécution.
- Aucune mise ni gestion de jetons.
- Pas de tests unitaires.
- Pas d'outil de build (Maven / Gradle).

---

## Pistes d'amélioration

- Corriger le calcul de l'as (point 1 ci-dessus), c'est le bug le plus impactant sur les résultats.
- Afficher réellement les mains (`Card.toString()` + `CardOwner.toString()`), en masquant la carte cachée de la banque et son poids dans le total.
- Utiliser un unique `Scanner` injecté dans `Player`.
- Ajouter un blackjack naturel, des mises, le doublement et la séparation.
- Permettre de rejouer plusieurs manches, avec remélange quand le paquet est bas.
- Ajouter des tests JUnit, notamment sur `getHandValue()` (cas avec un ou plusieurs as) et sur le comportement de la banque.
- Introduire Maven ou Gradle pour la compilation et l'exécution.
- Compléter la Javadoc (plusieurs méthodes de `Deck` ont des balises `@param` / `@return` vides).

---

## Licence

© 2026 Kevin DUBREUCQ. Tous droits réservés.
