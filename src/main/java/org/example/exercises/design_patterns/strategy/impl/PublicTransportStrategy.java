package org.example.exercises.design_patterns.strategy.impl;

import org.example.exercises.design_patterns.strategy.dijkstra.Dijkstra;
import org.example.exercises.design_patterns.strategy.dijkstra.Graph;
import org.example.exercises.design_patterns.strategy.dijkstra.Node;
import org.example.exercises.design_patterns.strategy.interface_strategy.City;
import org.example.exercises.design_patterns.strategy.interface_strategy.RouteStrategy;
import org.example.exercises.design_patterns.strategy.route.RouteDetails;
import org.example.exercises.design_patterns.strategy.route.StrategyType;

import java.math.BigDecimal;
import java.util.List;


public class PublicTransportStrategy implements RouteStrategy {
    @Override
    public RouteDetails buildRoute(City source, City destination) {
        Graph graph = createGraph(source, destination);
        RouteDetails routeDetails = extractRouteDetails(graph, destination);

        return routeDetails;
    }

    private static RouteDetails extractRouteDetails(Graph graph, City destination) {


        for (Node node : graph.getNodes()) {
            if (node.getName().equals(destination.name())) {
                return setValuesToRouteDetails(node);
            }
        }

        return null;
    }

    private static RouteDetails setValuesToRouteDetails(Node node) {
        RouteDetails routeDetails = new RouteDetails();
        List<Node> shortestPath = node.getShortestPath();
        String path = "";
        if (shortestPath != null) {
            for (Node pathNode : shortestPath) {
                path += pathNode.getName() + " --" + "[" + "]" + "--> ";
            }
            path += node.getName();
        } else {
            System.out.println("No path found.");
        }
        routeDetails.setPath(path);
        routeDetails.setLength(node.getDistance());
        routeDetails.setPrice(BigDecimal.valueOf(node.getDistance() / 2));
        routeDetails.setTime(node.getDistance() / 50);
        return routeDetails;
    }

    @Override
    public Graph createGraph(City source, City destination) {
//odziedziczyc grafu i dodac przystanki
        Graph graph = getGraph();

        Node sourcePoint = null;
        Node destinationPoint = null;
        for (Node node : graph.getNodes()) {
            if (node.getName().equals(source.name())) {
                sourcePoint = node;
            }
            if (node.getName().equals(destination.name())) {
                destinationPoint = node;
            }
        }
        if (destinationPoint == null || sourcePoint == null) {
            throw new RuntimeException("Not possible to get to the destination");
        }
        graph = Dijkstra.calculateShortestPathFromSource(graph, sourcePoint, destinationPoint);
        return graph;
    }

    static Graph getGraph() {

        Node KRAKOW = new Node(City.KRAKOW);
        Node KRAKOW_PUBLIC1_RZESZOW = new Node(City.KRAKOW_PUBLIC1_RZESZOW);

        Graph graph = new Graph();
        KRAKOW.addDestination(City.KRAKOW_PUBLIC1_RZESZOW, 60, StrategyType.PUBLIC_TRANSPORT);
        KRAKOW_PUBLIC1_RZESZOW.addDestination(City.RZESZOW, 50, StrategyType.PUBLIC_TRANSPORT);


        graph.addNode(KRAKOW);
        graph.addNode(KRAKOW_PUBLIC1_RZESZOW);

        return graph;
    }
}


