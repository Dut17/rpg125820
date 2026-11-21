# RPG

Gioco di ruolo a turni in Java, sviluppato per l'esame di Metodologie di Programmazione (Università di Camerino, A.A. 2025/26).

Il giocatore sceglie un eroe tra Guerriero, Mago e Cacciatore e affronta in sequenza i nemici di un'arena, scegliendo a ogni turno tra attacco, abilità speciale (che consuma mana) e pozione di cura. L'eroe guadagna esperienza e sale di livello. La partita può essere salvata e ripresa da un file JSON.

Package base: `it.unicam.cs.mpgc.rpg125820`

## Come eseguire il progetto

Prerequisiti: Java 25 e Gradle (il wrapper è incluso nel repository).

```bash
git clone https://github.com/Dut17/rpg125820.git
cd rpg125820
./gradlew build
./gradlew run
```

Su Windows usare `.\gradlew` al posto di `./gradlew`.

Per eseguire solo i test: `./gradlew test`.

## Come si gioca

- Dalla schermata iniziale si inserisce il nome dell'eroe e si sceglie il ruolo (Guerriero, Mago o Cacciatore).
- Con Nuova partita si entra nell'arena Foresta, che contiene tre nemici da affrontare in ordine: Goblin, Orco e Troll (il boss).
- A ogni turno si sceglie tra Attacco, Abilità speciale (consuma mana) e Pozione (cura l'eroe). I bottoni non disponibili sono disattivati.
- Sconfiggendo i nemici l'eroe guadagna esperienza e sale di livello, fino al livello massimo. A ogni livello recupera vita e mana.
- Il pulsante Salva registra la partita in corso; Continua la riprende dalla schermata iniziale. Il nemico corrente riparte con la vita piena.
- A fine partita, con vittoria o sconfitta, si torna al menu.

Il salvataggio è il file `saves/partita.json`, creato nella cartella da cui si lancia il gioco. La cartella `saves/` è esclusa da Git.

## Struttura

- `model`: personaggi, ruoli, abilità, nemici e arene
- `combat`: interfaccia comune dei combattenti
- `battle`: scontro a turni e partita
- `persistence`: salvataggio e caricamento (interfaccia e implementazione JSON)
- `controller`: collegamento tra interfaccia e logica di gioco
- `ui`: interfaccia grafica JavaFX

La logica di gioco non dipende dall'interfaccia grafica.

## Stato del progetto

Logica di gioco, persistenza, controller e interfaccia grafica JavaFX sono completati. La logica di gioco, la persistenza e il controller sono coperti da test automatici; l'interfaccia grafica è stata provata a mano.

---

## Uso di strumenti di AI

- Utilizzato Perplexity per:
  - Discussione della progettazione (classi, responsabilità, punti di estensione)
  - Spiegazione degli errori di build e dei test, con proposte di correzione
  - Scrittura delle classi di test per verificare ogni classe sviluppata
  - Preparazione dei commit (messaggi e contenuto), poi approvati da me

- Scopo dell'uso dell'AI:
  - Ragionare sulla progettazione e capire gli errori di build e di test
  - Velocizzare la scrittura dei test, mantenendo io il controllo delle scelte e del codice finale

- Svolto da me (Io):
  - Scelta del tipo di gioco, delle regole, del sistema di livelli ed esperienza e dei valori di vita dei nemici
  - Scelta delle tecnologie: JSON per il salvataggio e JavaFX per l'interfaccia
  - Scrittura del codice
  - Esecuzione delle build in locale e verifica dei risultati
  - Revisione e approvazione di ogni commit

- Processo di lavoro:
  - Piccoli passi con molti commit: ogni classe o funzionalità ha un commit e, subito dopo, un commit con i relativi test
  - Build e test eseguiti in locale dopo ogni modifica, per individuare subito gli errori
