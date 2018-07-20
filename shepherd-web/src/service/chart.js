import dagreD3 from "dagre-d3";
import {event, select, zoom, zoomIdentity} from "d3";

export default function Chart(){
    function addNodes(graph, data){
        data.forEach(function(node){
            var nodeProps = {
                class: node.class
            };
            graph.setNode(node.name, nodeProps);
            if(node.connections && node.connections.length){
                node.connections.forEach(function(connection){
                    graph.setEdge(node.name, connection.nodeName, {label: connection.edgeName});
                });
            }
        });
        return graph;
    }

    function getTreeData(treeData){
        var g = new dagreD3.graphlib.Graph().setGraph({});
        g = addNodes(g, treeData);
        return g;
    }

    function createChart(svgSelector, treeData){
        //var g = getTreeData(require('./treedata.json'));
        var g = getTreeData(treeData);

        var svg = select("svg"),
            inner = svg.select("g");

        // Set up zoom support
        var zoomed = zoom().on("zoom", function() {
            inner.attr("transform", event.transform);
        });
        svg.call(zoomed);
        // Run the renderer. This is what draws the final graph.

        new dagreD3.render()(inner, g);
        // Center the graph
        var initialScale = 0.75;
        svg.call(zoomed.transform, zoomIdentity.translate((svg.attr("width") - g.graph().width * initialScale) / 2, 20).scale(initialScale));

        // Center the graph
        var xCenterOffset = (svg.attr("width") - g.graph().width) / 2;
        inner.attr("transform", "translate(" + xCenterOffset + ", 20)");
    }

    return {
        createChart: createChart
    }
}