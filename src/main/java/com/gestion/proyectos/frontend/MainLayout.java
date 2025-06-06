package com.gestion.proyectos.frontend;

import com.gestion.proyectos.frontend.views.ActaConstitucionView;
import com.gestion.proyectos.frontend.views.DashboardView;
import com.gestion.proyectos.frontend.views.EvaluacionView;
import com.gestion.proyectos.frontend.views.ProyectosView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * Layout principal de la aplicación que contiene la barra de navegación y el menú lateral.
 * Implementado con Vaadin para Java 2025.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        HorizontalLayout header = new HorizontalLayout(toggle, viewTitle);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(viewTitle);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(true, header);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Sistema de Gestión de Proyectos");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        SideNav nav = new SideNav();
        nav.addItem(new SideNavItem("Dashboard", DashboardView.class));
        nav.addItem(new SideNavItem("Proyectos", ProyectosView.class));
        nav.addItem(new SideNavItem("Actas de Constitución", ActaConstitucionView.class));
        nav.addItem(new SideNavItem("Evaluaciones", EvaluacionView.class));

        VerticalLayout drawerContent = new VerticalLayout(header, nav);
        drawerContent.setSizeFull();
        drawerContent.setPadding(false);
        drawerContent.setSpacing(false);
        drawerContent.getThemeList().add("spacing-s");
        drawerContent.setAlignItems(FlexComponent.Alignment.STRETCH);

        addToDrawer(drawerContent);
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
