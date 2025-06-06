package com.gestion.proyectos.frontend.views;

import com.gestion.proyectos.frontend.MainLayout;
import com.gestion.proyectos.model.Evaluacion;
import com.gestion.proyectos.model.Proyecto;
import com.gestion.proyectos.service.EvaluacionService;
import com.gestion.proyectos.service.ProyectoService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

/**
 * Vista para gestionar las evaluaciones de los proyectos.
 * Implementado con Vaadin para Java 2025.
 */
@PageTitle("Evaluaciones")
@Route(value = "evaluaciones", layout = MainLayout.class)
public class EvaluacionView extends VerticalLayout {

    private final EvaluacionService evaluacionService;
    private final ProyectoService proyectoService;
    
    private final Grid<Evaluacion> grid = new Grid<>(Evaluacion.class);
    private final Button addEvaluacionButton = new Button("Nueva Evaluación");
    private final Button reportButton = new Button("Generar Reporte");
    
    private final FormLayout form = new FormLayout();
    private final Binder<Evaluacion> binder = new Binder<>(Evaluacion.class);
    private Evaluacion currentEvaluacion;

    public EvaluacionView(EvaluacionService evaluacionService, ProyectoService proyectoService) {
        this.evaluacionService = evaluacionService;
        this.proyectoService = proyectoService;
        
        addClassName("evaluacion-view");
        setSizeFull();
        
        configureGrid();
        configureForm();
        
        H2 title = new H2("Evaluaciones de Proyectos");
        
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
        grid.addClassName("evaluacion-grid");
        grid.setSizeFull();
        grid.setColumns("id", "titulo", "fechaEvaluacion", "calificacionTotal");
        grid.addColumn(evaluacion -> evaluacion.getProyecto().getNombre()).setHeader("Proyecto");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        
        grid.asSingleSelect().addValueChangeListener(e -> editEvaluacion(e.getValue()));
    }

    private void configureForm() {
        form.addClassName("evaluacion-form");
        
        TextField titulo = new TextField("Título");
        DatePicker fechaEvaluacion = new DatePicker("Fecha de Evaluación");
        NumberField calificacionTotal = new NumberField("Calificación Total");
        TextArea observaciones = new TextArea("Observaciones");
        ComboBox<Proyecto> proyecto = new ComboBox<>("Proyecto");
        proyecto.setItems(proyectoService.findAll());
        proyecto.setItemLabelGenerator(Proyecto::getNombre);
        
        form.add(titulo, fechaEvaluacion, calificacionTotal, observaciones, proyecto);
        
        binder.bindInstanceFields(this);
        
        Button save = new Button("Guardar");
        Button cancel = new Button("Cancelar");
        
        save.addClickListener(e -> saveEvaluacion());
        cancel.addClickListener(e -> closeEditor());
        
        form.add(new HorizontalLayout(save, cancel));
    }

    private HorizontalLayout getToolbar() {
        addEvaluacionButton.addClickListener(e -> addEvaluacion());
        addEvaluacionButton.setIcon(new Icon(VaadinIcon.PLUS));
        
        reportButton.addClickListener(e -> {
            if (currentEvaluacion != null) {
                Notification.show("Generando reporte para: " + currentEvaluacion.getTitulo());
                // Aquí se implementaría la generación del reporte
            } else {
                Notification.show("Seleccione una evaluación para generar el reporte");
            }
        });
        reportButton.setIcon(new Icon(VaadinIcon.FILE_TEXT));
        
        HorizontalLayout toolbar = new HorizontalLayout(addEvaluacionButton, reportButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateList() {
        List<Evaluacion> evaluaciones = evaluacionService.findAll();
        grid.setItems(evaluaciones);
    }
    
    private void addEvaluacion() {
        grid.asSingleSelect().clear();
        editEvaluacion(new Evaluacion());
    }
    
    private void editEvaluacion(Evaluacion evaluacion) {
        if (evaluacion == null) {
            closeEditor();
        } else {
            currentEvaluacion = evaluacion;
            binder.setBean(evaluacion);
            form.setVisible(true);
        }
    }
    
    private void closeEditor() {
        form.setVisible(false);
        currentEvaluacion = null;
    }
    
    private void saveEvaluacion() {
        if (currentEvaluacion != null && binder.writeBeanIfValid(currentEvaluacion)) {
            evaluacionService.save(currentEvaluacion);
            updateList();
            closeEditor();
            Notification.show("Evaluación guardada correctamente");
        }
    }
}
