package it.unicam.cs.mpgc.rpg125820;

import it.unicam.cs.mpgc.rpg125820.ui.RpgApplication;
import javafx.application.Application;

/**
 * Punto di ingresso del programma: avvia l'interfaccia grafica.
 */
public final class App {

    private App() {
    }

    public static void main(String[] args) {
        Application.launch(RpgApplication.class, args);
    }
}
