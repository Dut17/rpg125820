# RPG

Gioco di ruolo a turni in Java, sviluppato per l'esame di Metodologie di Programmazione (Universita di Camerino, A.A. 2025/26).

Il giocatore sceglie un eroe tra Guerriero, Mago e Cacciatore e affronta in sequenza i nemici di un'arena, scegliendo a ogni turno tra attacco, abilita speciale (che consuma mana) e pozione di cura. L'eroe guadagna esperienza e sale di livello. La partita puo essere salvata e ripresa da un file JSON.

Package base: `it.unicam.cs.mpgc.rpg125820`

## Come eseguire il progetto

Prerequisiti: Java 25 e Gradle (il wrapper e incluso nel repository).

```bash
git clone https://github.com/Dut17/rpg125820.git
cd rpg125820
./gradlew build
./gradlew run
```

Su Windows usare `.\gradlew` al posto di `./gradlew`.

## Struttura

- `model`: personaggi, ruoli, abilita, nemici e arene
- `combat`: interfaccia comune dei combattenti
- `battle`: scontro a turni e partita
- `persistence`: salvataggio e caricamento (interfaccia e implementazione JSON)
- `controller`: collegamento tra interfaccia e logica di gioco
- `ui`: interfaccia grafica JavaFX

La logica di gioco non dipende dall'interfaccia grafica.

## Stato del progetto

Logica di gioco, persistenza e controller sono completati e coperti da test. L'interfaccia grafica JavaFX e in sviluppo.

## Uso di strumenti di AI

E stato utilizzato un assistente AI (Perplexity), con accesso al repository tramite il connettore GitHub.

Attivita svolte dall'assistente:

- discussione della progettazione (classi, responsabilita, punti di estensione)
- scrittura della maggior parte del codice sorgente e dei test JUnit
- spiegazione degli errori di build e dei test
- creazione dei commit, ciascuno dopo la mia conferma esplicita

Attivita svolte da me:

- scelta delle funzionalita e delle regole di gioco (ruoli, livelli, valori dei nemici, formato di salvataggio)
- approvazione di ogni modifica prima del commit
- esecuzione di build e test in locale e segnalazione degli errori
- revisione del codice per comprenderlo

Il livello di intervento diretto sul codice e quindi limitato: la maggior parte del codice e dei test e stata scritta dall'assistente.

La dichiarazione dettagliata e nella Wiki del repository.
