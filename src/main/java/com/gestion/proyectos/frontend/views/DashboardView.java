package com.gestion.proyectos.frontend.views;

import com.gestion.proyectos.frontend.MainLayout;
import com.gestion.proyectos.model.Proyecto;
import com.gestion.proyectos.service.ProyectoService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Vista del dashboard principal que muestra estadísticas y gráficos de los proyectos.
 * Implementado con Vaadin para Java 2025.
 */
@PageTitle("Dashboard")
@Route(value = "", layout = MainLayout.class)
public class DashboardView extends VerticalLayout {

    private final ProyectoService proyectoService;

    public DashboardView(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        
        H2 header = new H2("Dashboard de Gestión de Proyectos");
        header.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.Margin.MEDIUM);
        
        add(header, createDashboardContent());
    }

    private Component createDashboardContent() {
        Board board = new Board();
        board.addRow(
            createProyectosActivosCard(),
            createProyectosPorEstadoChart(),
            createProyectosPorTipoChart()
        );
        return board;
    }

    private Component createProyectosActivosCard() {
        List<Proyecto> proyectos = proyectoService.findAll();
        long proyectosActivos = proyectos.stream()
                .filter(p -> "ACTIVO".equals(p.getEstado()))
                .count();
        
        Span stats = new Span(proyectosActivos + " proyectos activos");
        stats.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.TextColor.SUCCESS);
        
        VerticalLayout card = new VerticalLayout(stats);
        card.addClassNames(LumoUtility.Padding.LARGE, LumoUtility.Border.ALL, 
                LumoUtility.BorderRadius.MEDIUM);
        card.setAlignItems(Alignment.CENTER);
        return card;
    }

    private Component createProyectosPorEstadoChart() {
        List<Proyecto> proyectos = proyectoService.findAll();
        Map<String, Long> proyectosPorEstado = proyectos.stream()
                .collect(Collectors.groupingBy(Proyecto::getEstado, Collectors.counting()));
        
        Chart chart = new Chart(ChartType.PIE);
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Proyectos por Estado");
        
        DataSeries series = new DataSeries();
        proyectosPorEstado.forEach((estado, cantidad) -> 
            series.add(new DataSeriesItem(estado, cantidad))
        );
        conf.addSeries(series);
        
        return chart;
    }

    private Component createProyectosPorTipoChart() {
        List<Proyecto> proyectos = proyectoService.findAll();
        Map<String, Long> proyectosPorTipo = proyectos.stream()
                .collect(Collectors.groupingBy(Proyecto::getTipo, Collectors.counting()));
        
        Chart chart = new Chart(ChartType.COLUMN);
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Proyectos por Tipo");
        
        XAxis xAxis = new XAxis();
        xAxis.setCategories(proyectosPorTipo.keySet().toArray(new String[0]));
        conf.addxAxis(xAxis);
        
        YAxis yAxis = new YAxis();
        yAxis.setTitle("Cantidad");
        conf.addyAxis(yAxis);
        
        ListSeries series = new ListSeries("Proyectos");
        proyectosPorTipo.values().forEach(series::addData);
        conf.addSeries(series);
        
        return chart;
    }
}
