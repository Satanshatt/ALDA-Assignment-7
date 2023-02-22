// Ändra inte på paketet
package alda.graph;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author henrikbe
 * @version JUnit 5
 */
public class UndirectedGraphTest {

    private static final String[] STANDARD_NODES = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };

    private UndirectedGraph<String> graph = new MyUndirectedGraph<>();

    private void add(String... nodes) {
        for (String node : nodes) {
            assertTrue(graph.add(node), "Unable to add node " + node);
        }
    }

    private void connect(String node1, String node2, int cost) {
        assertTrue(graph.connect(node1, node2, cost));
        assertEquals(cost, graph.getCost(node1, node2));
        assertEquals(cost, graph.getCost(node2, node1));
    }

    private void addExampleNodes() {
        add(STANDARD_NODES);
    }

    @Test
    public void testAdd() {
        addExampleNodes();
        assertFalse(graph.add("D"));
        assertFalse(graph.add("J"));
        assertTrue(graph.add("K"));
    }

    @Test
    public void testConnect() {
        addExampleNodes();
        assertFalse(graph.isConnected("A", "Z"));
        assertFalse(graph.connect("A", "Z", 5));
        assertEquals(-1, graph.getCost("A", "Z"));
        assertFalse(graph.connect("X", "B", 5));
        assertEquals(-1, graph.getCost("X", "B"));
        assertEquals(-1, graph.getCost("B", "X"));

        assertFalse(graph.isConnected("A", "G"));
        assertFalse(graph.isConnected("G", "A"));
        assertTrue(graph.connect("A", "G", 5));
        assertTrue(graph.isConnected("A", "G"));
        assertTrue(graph.isConnected("G", "A"));
        assertEquals(5, graph.getCost("A", "G"));
        assertEquals(5, graph.getCost("G", "A"));
        assertTrue(graph.connect("G", "A", 3));
        assertEquals(3, graph.getCost("A", "G"));
        assertEquals(3, graph.getCost("G", "A"));
    }

    @Test
    public void testTooLowWeight() {
        addExampleNodes();
        assertFalse(graph.connect("A", "B", 0));
        assertFalse(graph.connect("C", "D", -1));
    }

    // Nedanstående kod är skriven i ett format för att beskriva grafer som
    // heter dot och kan användas om ni vill ha en bild av den graf som
    // nedanående test använder. Det finns flera program och webbsidor man kan
    // använda för att omvandla koden till en bild, bland annat
    // http://sandbox.kidstrythisathome.com/erdos/

    // Observera dock att vi kommer att köra testfall på andra och betydligt
    // större grafer.

    // @formatter:off
    // graph G {
    // A -- A [label=1]; A -- G [label=3]; G -- B [label=28];
    // B -- F [label=5]; F -- F [label=3]; F -- H [label=1];
    // H -- D [label=1]; H -- I [label=3]; D -- I [label=1];
    // B -- D [label=2]; B -- C [label=3]; C -- D [label=5];
    // E -- C [label=2]; E -- D [label=2]; J -- D [label=5];
    // }
    // @formatter:on

    private void createExampleGraph() {
        addExampleNodes();

        connect("A", "A", 1);
        connect("A", "G", 3);
        connect("G", "B", 28);
        connect("B", "F", 5);
        connect("F", "F", 3);
        connect("F", "H", 1);
        connect("H", "D", 1);
        connect("H", "I", 3);
        connect("D", "I", 1);
        connect("B", "D", 2);
        connect("B", "C", 3);
        connect("C", "D", 5);
        connect("E", "C", 2);
        connect("E", "D", 2);
        connect("J", "D", 5);
    }

    @Test
    public void testUpdateExistingEdgeWithSameWeight() {
        createExampleGraph();
        assertTrue(graph.isConnected("A", "G"));
        assertEquals(3, graph.getCost("A", "G"));
        assertTrue(graph.connect("A", "G", 3));
        assertEquals(3, graph.getCost("A", "G"));
    }

    @Test
    public void testUpdateExistingEdgeWithNewWeight() {
        createExampleGraph();
        assertTrue(graph.isConnected("A", "G"));
        assertEquals(3, graph.getCost("A", "G"));
        assertTrue(graph.connect("A", "G", 5));
        assertEquals(5, graph.getCost("A", "G"));
    }

    @Test
    public void testObjectIdentity() {
        createExampleGraph();
        assertTrue(graph.isConnected(new String("A"), new String("G")));
    }

    private void testPath(String start, String end, List<String> path) {
        assertEquals(start, path.get(0));
        assertEquals(end, path.get(path.size() - 1));

        String previous = start;
        for (int i = 1; i < path.size(); i++) {
            assertTrue(graph.isConnected(previous, path.get(i)));
            previous = path.get(i);
        }

        Set<String> nodesInPath = new HashSet<>(path);
        assertEquals(path.size(), nodesInPath.size());
    }

    private void testDepthFirstSearch(String start, String end, int minimumPathLength) {
        createExampleGraph();
        List<String> path = graph.depthFirstSearch(start, end);

        assertTrue(path.size() >= minimumPathLength);
        assertTrue(path.size() <= graph.getNumberOfNodes());

        testPath(start, end, path);
    }

    @Test
    public void testDepthFirstSearchFromAToJ() {
        testDepthFirstSearch("A", "J", 5);
    }

    @Test
    public void testDepthFirstSearchFromJToA() {
        testDepthFirstSearch("J", "A", 5);
    }

    @Test
    public void testDepthFirstSearchFromFToE() {
        testDepthFirstSearch("F", "E", 3);
    }

    @Test
    public void testDepthFirstSearchToSameNode() {
        for (String node : STANDARD_NODES) {
            graph = new MyUndirectedGraph<>();
            testDepthFirstSearch(node, node, 1);
        }
    }

    private void testBreadthFirstSearch(String start, String end, int expectedathLength) {
        createExampleGraph();
        List<String> path = graph.breadthFirstSearch(start, end);

        assertEquals(expectedathLength, path.size());

        testPath(start, end, path);
    }

    @Test
    public void testBreadthFirstSearchFromAToJ() {
        testBreadthFirstSearch("A", "J", 5);
    }

    @Test
    public void testBreadthFirstSearchFromJToA() {
        testBreadthFirstSearch("J", "A", 5);
    }

    @Test
    public void testBreadthFirstSearchFromFToE() {
        testBreadthFirstSearch("F", "E", 4);
    }

    @Test
    public void testBreadthFirstSearchToSameNode() {
        for (String node : STANDARD_NODES) {
            graph = new MyUndirectedGraph<>();
            testBreadthFirstSearch(node, node, 1);
        }
    }

    @Test
    public void testMinimumSpanningTree() {
        createExampleGraph();
        UndirectedGraph<String> mst = graph.minimumSpanningTree();

        int totalEdges = 0;
        int totalCost = 0;

        for (char node1 = 'A'; node1 <= 'J'; node1++) {
            for (char node2 = node1; node2 <= 'J'; node2++) {
                int cost = mst.getCost("" + node1, "" + node2);
                if (cost > -1) {
                    totalEdges++;
                    totalCost += cost;
                }
            }
        }

        assertEquals(9, totalEdges);
        assertEquals(45, totalCost);
    }

    // Här börjar vi använda andra grafer

    @Test
    public void testMinimumSpanningTreeFromBook() {
        add("V1", "V2", "V3", "V4", "V5", "V6", "V7");
        connect("V1", "V2", 2);
        connect("V1", "V3", 4);
        connect("V1", "V4", 1);
        connect("V2", "V4", 3);
        connect("V2", "V5", 10);
        connect("V3", "V4", 2);
        connect("V3", "V6", 5);
        connect("V4", "V5", 7);
        connect("V4", "V6", 8);
        connect("V4", "V7", 4);
        connect("V5", "V7", 6);
        connect("V6", "V7", 1);

        UndirectedGraph<String> mst = graph.minimumSpanningTree();

        int totalEdges = 0;
        int totalCost = 0;

        for (int node1 = 1; node1 <= 7; node1++) {
            for (int node2 = node1; node2 <= 7; node2++) {
                int cost = mst.getCost("V" + node1, "V" + node2);
                if (cost > -1) {
                    totalEdges++;
                    totalCost += cost;
                }
            }
        }

        assertEquals(6, totalEdges);
        assertEquals(16, totalCost);
    }

}