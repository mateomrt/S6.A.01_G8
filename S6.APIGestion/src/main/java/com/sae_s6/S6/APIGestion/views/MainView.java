package com.sae_s6.S6.APIGestion.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * Vue principale de l'application.
 * Cette vue s'affiche lorsque l'utilisateur navigue vers la racine ('/') de l'application.
 */
@Route
public final class MainView extends Main {

    /**
     * Constructeur de la vue principale.
     * Configure le style et ajoute un message d'accueil.
     */
    MainView() {
        // Ajoute une classe CSS pour le padding
        addClassName(LumoUtility.Padding.MEDIUM);

        // Ajoute un message d'accueil
        add(new Div("Choisissez une option dans le menu à gauche."));
    }

    /**
     * Navigue vers la vue principale.
     * Cette méthode peut être utilisée pour rediriger l'utilisateur vers la vue principale.
     */
    public static void showMainView() {
        UI.getCurrent().navigate(MainView.class);
    }
}