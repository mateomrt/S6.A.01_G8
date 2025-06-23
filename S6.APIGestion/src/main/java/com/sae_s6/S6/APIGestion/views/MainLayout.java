package com.sae_s6.S6.APIGestion.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;

import static com.vaadin.flow.theme.lumo.LumoUtility.*;

/**
 * Layout principal de l'application.
 * Définit la structure globale de l'interface utilisateur, incluant un menu latéral et un en-tête.
 */
@Layout
public final class MainLayout extends AppLayout {

    /**
     * Constructeur du layout principal.
     * Configure la section principale et ajoute l'en-tête et le menu latéral au drawer.
     */
    MainLayout() {
        setPrimarySection(Section.DRAWER);
        addToDrawer(createHeader(), new Scroller(createSideNav()));
    }

    /**
     * Crée l'en-tête de l'application.
     * Inclut un logo et le nom de l'application.
     *
     * @return Un composant Div représentant l'en-tête.
     */
    private Div createHeader() {
        // TODO Remplacer par le logo et le nom réel de l'application
        var appLogo = VaadinIcon.CUBES.create();
        appLogo.addClassNames(TextColor.PRIMARY, IconSize.LARGE);

        var appName = new Span("Application : S6.A.01_G8");
        appName.addClassNames(FontWeight.SEMIBOLD, FontSize.LARGE);

        var header = new Div(appLogo, appName);
        header.addClassNames(Display.FLEX, Padding.MEDIUM, Gap.MEDIUM, AlignItems.CENTER);
        return header;
    }

    /**
     * Crée le menu latéral de navigation.
     * Récupère les entrées de menu configurées et les ajoute au menu latéral.
     *
     * @return Un composant SideNav représentant le menu latéral.
     */
    private SideNav createSideNav() {
        var nav = new SideNav();
        nav.addClassNames(Margin.Horizontal.MEDIUM);
        MenuConfiguration.getMenuEntries().forEach(entry -> nav.addItem(createSideNavItem(entry)));
        return nav;
    }

    /**
     * Crée un élément de navigation pour le menu latéral.
     * Ajoute une icône si elle est définie dans l'entrée de menu.
     *
     * @param menuEntry Une entrée de menu contenant les informations nécessaires.
     * @return Un composant SideNavItem représentant l'élément de navigation.
     */
    private SideNavItem createSideNavItem(MenuEntry menuEntry) {
        if (menuEntry.icon() != null) {
            return new SideNavItem(menuEntry.title(), menuEntry.path(), new Icon(menuEntry.icon()));
        } else {
            return new SideNavItem(menuEntry.title(), menuEntry.path());
        }
    }

    /*
     * Méthode pour créer un menu utilisateur.
     * TODO Remplacer par des informations et actions utilisateur réelles.
     */
    /* 
    private Component createUserMenu() {
        var avatar = new Avatar("John Smith");
        avatar.addThemeVariants(AvatarVariant.LUMO_XSMALL);
        avatar.addClassNames(Margin.Right.SMALL);
        avatar.setColorIndex(5);

        var userMenu = new MenuBar();
        userMenu.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
        userMenu.addClassNames(Margin.MEDIUM);

        var userMenuItem = userMenu.addItem(avatar);
        userMenuItem.add("John Smith");
        userMenuItem.getSubMenu().addItem("View Profile").setEnabled(false);
        userMenuItem.getSubMenu().addItem("Manage Settings").setEnabled(false);
        userMenuItem.getSubMenu().addItem("Logout").setEnabled(false);

        return userMenu;
    }
    */
}