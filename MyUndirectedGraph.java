package alda.graph;

import java.util.List;
import java.util.LinkedList;
import java.util.Hashtable;

public class MyUndirectedGraph<T> implements UndirectedGraph<T>{

    private int nodeCounter = 0;
    private int edgeCounter = 0;
    private Hashtable<T, LinkedList<Edge<T>>> hashtable = new Hashtable<>();

    public MyUndirectedGraph() {
    }

    private static class Edge<E> {
        private E nodeOne;
        public E nodeTwo;
        private int weight;

        public Edge(E nodeOne, E nodeTwo, int weight) {
            this.weight = weight;
            this.nodeOne = nodeOne;
            this.nodeTwo = nodeTwo;
        }
    }

    @Override
    public int getNumberOfNodes() {
        return nodeCounter;
    }

    @Override
    public int getNumberOfEdges() {
        return edgeCounter;
    }

    @Override
    public boolean add(T newNode) {
        if(newNode == null) { //if newNode is null
            return false;     //return false
        }
        if(hashtable.containsKey(newNode)) { //if hashtable contains the key
            return false;                 //return false. IF duplicates are not allowed
        }
        hashtable.put(newNode, new LinkedList()); //else we add a new node and a new linked list to the hashtable
        nodeCounter++;
        return true;
    }

    @Override
    public boolean connect(T node1, T node2, int cost) {

       if(node1 == null || node2 == null){
           return false;
       }
       if(cost <= 0) {
           return false;
       }
       if(!hashtable.containsKey(node1) || !hashtable.containsKey(node2)){
           return false;
       }
       if(isConnected(node1, node2)) {
            changeCost(node1, node2, cost);
       }
        hashtable.get(node1).add(new Edge(node1, node2, cost));
        hashtable.get(node2).add(new Edge(node2, node1, cost));
        edgeCounter++;
        return true;
    }

    @Override
    public boolean isConnected(T node1, T node2) {
        if(node1 == null || node2 == null) {
            return false;
        }
        if(getEdge(node1, node2) == null){
            return false;
        }
        return true;
    }


    public Edge getEdge(T node1, T node2){

        for(Edge<T> edge : hashtable.get(node1)){
            if (node2.equals(edge.nodeTwo)){ // if we find a connection
                return edge;
            }
        }
        return null;
    }

    @Override
    public int getCost(T node1, T node2) {
        if(node1 == null || node2 == null) {
            throw new NullPointerException("Node parameter can not be null");
        }
        if(!hashtable.containsKey(node1) || !hashtable.containsKey(node2)){
            return -1;
        }
        Edge edge = getEdge(node1, node2);
        if(edge == null) {
            return -1;
        }

        return edge.weight;
    }

    public boolean changeCost(T node1, T node2, int newCost) {
        Edge connectionOne = getEdge(node1, node2);
        connectionOne.weight = newCost;
        Edge connectionTwo = getEdge(node2, node1);
        connectionTwo.weight = newCost;

        return true;
    }

    @Override
    public List<T> depthFirstSearch(T start, T end) {
        LinkedList stack = new LinkedList<>();

        return stack;
    }

    @Override
    public List<T> breadthFirstSearch(T start, T end) {
        return null;
    }

    @Override
    public UndirectedGraph<T> minimumSpanningTree() {
        return null;
    }
}
