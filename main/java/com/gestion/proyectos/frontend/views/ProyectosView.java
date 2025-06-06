package com.gestion.proyectos.frontend.views;

import com.gestion.proyectos.frontend.MainLayout;
import com.gestion.proyectos.model.Proyecto;
import com.gestion.proyectos.service.ProyectoService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

/**
 * Vista de gestión de proyectos que permite listar, buscar y acceder a los detalles de los proyectos.
 * Implementado con Vaadin para Java 2025.
 */
@PageTitle("Proyectos")
@Route(value = "proyectos", layout = MainLayout.class)
public class ProyectosView extends VerticalLayout {

    private final ProyectoService proyectoService;
    private final Grid<Proyecto> grid = new Grid<>(Proyecto.class);
    private final TextField filterText = new TextField();
    private final Button addProyectoButton = new Button("Nuevo Proyecto");

    public ProyectosView(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
        addClassName("proyectos-view");
        setSizeFull();
        configureGrid();
        
        H2 title = new H2("Gestión de Proyectos");
        
        HorizontalLayout toolbar = getToolbar();
        
        add(title, toolbar, grid);
        updateList();
    }

    private void configureGrid() {
        grid.addClassName("proyectos-grid");
        grid.setSizeFull();
        grid.setColumns("id", "nombre", "descripcion", "fechaInicio", "fechaFin", "estado", "tipo");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        
        grid.addComponentColumn(proyecto -> {
            Button viewButton = new Button(new Icon(VaadinIcon.EYE), e -> {
                Notification.show("Ver detalles del proyecto: " + proyecto.getNombre());
                // Aquí se implementaría la navegación a la vista de detalles
            });
            
            Button editButton = new Button(new Icon(VaadinIcon.EDIT), e -> {
                Notification.show("Editar proyecto: " + proyecto.getNombre());
                // Aquí se implementaría la navegación a la vista de edición
            });
            
            Button deleteButton = new Button(new Icon(VaadinIcon.TRASH), e -> {
                Notification.show("Eliminar proyecto: " + proyecto.getNombre());
                // Aquí se implementaría la confirmación y eliminación
            });
            
            return new HorizontalLayout(viewButton, editButton, deleteButton);
        }).setHeader("Acciones");
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Buscar por nombre...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        addProyectoButton.addClickListener(e -> {
            Notification.show("Crear nuevo proyecto");
            // Aquí se implementaría la navegación a la vista de creación
        });
        addProyectoButton.setIcon(new Icon(VaadinIcon.PLUS));

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addProyectoButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateList() {
        List<Proyecto> proyectos;
        if (filterText.getValue().isEmpty()) {
            proyectos = proyectoService.findAll();
        } else {
            proyectos = proyectoService.findByNombreContaining(filterText.getValue());
        }
        grid.setItems(proyectos);
    }
}
