package com.gestion.proyectos.frontend.views;

import com.gestion.proyectos.frontend.MainLayout;
import com.gestion.proyectos.model.ActaConstitucion;
import com.gestion.proyectos.model.Proyecto;
import com.gestion.proyectos.service.ActaConstitucionService;
import com.gestion.proyectos.service.ProyectoService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

/**
 * Vista para gestionar las actas de constitución de los proyectos.
 * Implementado con Vaadin para Java 2025.
 */
@PageTitle("Actas de Constitución")
@Route(value = "actas", layout = MainLayout.class)
public class ActaConstitucionView extends VerticalLayout {

    private final ActaConstitucionService actaConstitucionService;
    private final ProyectoService proyectoService;
    
    private final Grid<ActaConstitucion> grid = new Grid<>(ActaConstitucion.class);
    private final Button addActaButton = new Button("Nueva Acta");
    private final Button downloadButton = new Button("Descargar Acta");
    
    private final FormLayout form = new FormLayout();
    private final Binder<ActaConstitucion> binder = new Binder<>(ActaConstitucion.class);
    private ActaConstitucion currentActa;

    public ActaConstitucionView(ActaConstitucionService actaConstitucionService, ProyectoService proyectoService) {
        this.actaConstitucionService = actaConstitucionService;
        this.proyectoService = proyectoService;
        
        addClassName("acta-constitucion-view");
        setSizeFull();
        
        configureGrid();
        configureForm();
        
        H2 title = new H2("Actas de Constitución");
        
        HorizontalLayout toolbar = getToolbar();
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.setSizeFull();
        
        add(title, toolbar, content);
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassName("acta-grid");
        grid.setSizeFull();
        grid.setColumns("id", "titulo", "fechaCreacion");
        grid.addColumn(acta -> acta.getProyecto().getNombre()).setHeader("Proyecto");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        
        grid.asSingleSelect().addValueChangeListener(e -> editActa(e.getValue()));
    }

    private void configureForm() {
        form.addClassName("acta-form");
        
        TextField titulo = new TextField("Título");
        TextArea proposito = new TextArea("Propósito");
        TextArea objetivos = new TextArea("Objetivos");
        TextArea alcance = new TextArea("Alcance");
        ComboBox<Proyecto> proyecto = new ComboBox<>("Proyecto");
        proyecto.setItems(proyectoService.findAll());
        proyecto.setItemLabelGenerator(Proyecto::getNombre);
        
        form.add(titulo, proposito, objetivos, alcance, proyecto);
        
        binder.bindInstanceFields(this);
        
        Button save = new Button("Guardar");
        Button cancel = new Button("Cancelar");
        
        save.addClickListener(e -> saveActa());
        cancel.addClickListener(e -> closeEditor());
        
        form.add(new HorizontalLayout(save, cancel));
    }

    private HorizontalLayout getToolbar() {
        addActaButton.addClickListener(e -> addActa());
        addActaButton.setIcon(new Icon(VaadinIcon.PLUS));
        
        downloadButton.addClickListener(e -> {
            if (currentActa != null) {
                Notification.show("Descargando acta: " + currentActa.getTitulo());
                // Aquí se implementaría la descarga del acta
            } else {
                Notification.show("Seleccione un acta para descargar");
            }
        });
        downloadButton.setIcon(new Icon(VaadinIcon.DOWNLOAD));
        
        HorizontalLayout toolbar = new HorizontalLayout(addActaButton, downloadButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateList() {
        List<ActaConstitucion> actas = actaConstitucionService.findAll();
        grid.setItems(actas);
    }
    
    private void addActa() {
        grid.asSingleSelect().clear();
        editActa(new ActaConstitucion());
    }
    
    private void editActa(ActaConstitucion acta) {
        if (acta == null) {
            closeEditor();
        } else {
            currentActa = acta;
            binder.setBean(acta);
            form.setVisible(true);
        }
    }
    
    private void closeEditor() {
        form.setVisible(false);
        currentActa = null;
    }
    
    private void saveActa() {
        if (currentActa != null && binder.writeBeanIfValid(currentActa)) {
            actaConstitucionService.save(currentActa);
            updateList();
            closeEditor();
            Notification.show("Acta guardada correctamente");
        }
    }
}
