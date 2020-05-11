package javafoundations;
import java.io.*;
import java.lang.Exception;
import java.util.*;

/**
 * AdjListsGraph.java
 * The AdjListsGraph class implements the Graph interface.
 * It represents an AdjListsGraph object, which is a collection of unordered lists used to represent a finite graph.
 *
 * @author yguo2, hkim22
 * @version 11/20/2019
 */
public class AdjListsGraph<T> implements Graph<T>
{
    private Vector<T> vertices;
    private Vector<LinkedList<T>> arcs;

    /**
     * Constructor
     */
    public AdjListsGraph(){
        vertices = new Vector<T>();
        arcs = new Vector<LinkedList<T>>();
    }

    /** 
     * Returns a boolean indicating whether this graph is empty or not.
     * A graph is empty when it contains no vertices and no edges.
     *  
     * @return true if this graph is empty, false otherwise.
     */
    public boolean isEmpty() {
        return (vertices.size() == 0);
    }

    /** 
     * Returns the number of vertices in this graph. 
     * 
     * @return the number of vertices in this graph
     */
    public int getNumVertices() {
        return vertices.size();
    }

    /** 
     * Returns the number of arcs in this graph.
     * An arc between Vertices A and B exists, if a direct connection
     * from A to B exists.
     * 
     * @return the number of arcs in this graph
     *  */
    public int getNumArcs(){
        int num = 0;
        for (int i = 0; i < arcs.size(); i ++) {
            num += arcs.get(i).size();
        }
        return num;
    }

    /** 
     * Returns true if an arc (direct connection) exists 
     * from the first vertex to the second, false otherwise
     * 
     * @return true if an arc exists between the first given vertex (vertex1),
     * and the second one (vertex2),false otherwise
     * 
     *  */
    public boolean isArc (T vertex1, T vertex2){
        if (vertices.contains(vertex1) && vertices.contains(vertex2)) {
            return (arcs.get(vertices.indexOf(vertex1))).contains(vertex2);
        }
        return false;
    }

    /** 
     * Returns true if an edge exists between two given vertices, i.e,
     * an arch exists from the first vertex to the second one, and an arc from
     * the second to the first vertex, false otherwise.
     *  
     * @return true if an edge exists between vertex1 and vertex2, 
     * false otherwise
     * 
     * */
    public boolean isEdge (T vertex1, T vertex2){
        return (isArc(vertex1, vertex2) && isArc(vertex2, vertex1));
    }

    /** 
     * Returns true if the graph is undirected, that is, for every
     * pair of nodes i,j for which there is an arc, the opposite arc
     * is also present in the graph, false otherwise.  
     * 
     * @return true if the graph is undirected, false otherwise
     * */
    public boolean isUndirected(){
        for (int i = 0; i < arcs.size(); i++){
            for (int j = 0; j < arcs.get(i).size(); j++){
                if (!isEdge(vertices.get(i), arcs.get(i).get(j))){
                    return false;
                }                
            }
        }
        return true;
    }

    /** 
     * Adds the given vertex to this graph
     * If the given vertex already exists, the graph does not change
     * 
     * @param The vertex to be added to this graph
     * */
    public void addVertex (T vertex){
        if (!vertices.contains(vertex)){
            vertices.add(vertex);
            arcs.add(new LinkedList<T>());
        }
    }

    /** 
     * Removes the given vertex from this graph.
     * If the given vertex does not exist, the graph does not change.
     * 
     * @param the vertex to be removed from this graph
     *  */
    public void removeVertex (T vertex){
        if (vertices.contains(vertex)){
            int index = vertices.indexOf(vertex); //index of vertex to be removed in vertices
            arcs.remove(index); //remove predecessors of vertex
            for (int i = 0; i<arcs.size(); i++){                
                if (arcs.get(i).contains(vertex)){ //if other vertices have an arc to vertex
                    int j = arcs.get(i).indexOf(vertex);
                    arcs.remove(arcs.get(i).get(j)); 
                }
                vertices.remove(vertex); //removes vertex frm vertices
            }
        }
    }

    /** 
     * Inserts an arc between two given vertices of this graph.
     * if at least one of the vertices does not exist, the graph 
     * is not changed.
     * 
     * @param the origin of the arc to be added to this graph
     * @param the destination of the arc to be added to this graph
     * 
     *  */
    public void addArc (T vertex1, T vertex2) {
        if (vertices.contains(vertex1) && vertices.contains(vertex2)) {
            int index1 = vertices.indexOf(vertex1);
            //int index2 = vertices.indexOf(vertex2);
            arcs.get(index1).add(vertex2);
        }
    }

    /** 
     * Removes the arc between two given vertices of this graph.
     * If one of the two vertices does not exist in the graph,
     * the graph does not change.
     * 
     * @param the origin of the arc to be removed from this graph
     * @param the destination of the arc to be removed from this graph
     * 
     * */
    public void removeArc (T vertex1, T vertex2) {
        if (vertices.contains(vertex1) && vertices.contains(vertex2) && isArc(vertex1, vertex2)) {
            int index1 = vertices.indexOf(vertex1);
            int index2 = vertices.indexOf(vertex2);
            arcs.get(index1).remove(index2);
        }
    }

    /** 
     * Inserts the edge between the two given vertices of this graph,
     * if both vertices exist, else the graph is not changed.
     * 
     * @param the origin of the edge to be added to this graph
     * @param the destination of the edge to be added to this graph
     * 
     *  */
    public void addEdge (T vertex1, T vertex2) {
        addArc(vertex1, vertex2); //add the edge from vertex 1 to vertex 2
        addArc(vertex2, vertex1); //add the edge from vertex 2 to vertex 1
    }

    /** 
     * Removes the edge between the two given vertices of this graph,
     * if both vertices exist, else the graph is not changed.
     * 
     * @param the origin of the edge to be removed from this graph
     * @param the destination of the edge to be removed from this graph
     * 
     */
    public void removeEdge (T vertex1, T vertex2) {
        removeArc(vertex1, vertex2); //remove the edge from vertex 1 to vertex 2
        removeArc(vertex2, vertex1); //remove the edge from vertex 2 to vertex 1
    }

    /** 
     * Return all the vertices, in this graph, adjacent to the given vertex.
     * 
     * @param A vertex in the graph whose successors will be returned.
     * @return LinkedList containing all the vertices x in the graph,
     * for which an arc exists from the given vertex to x (vertex -> x).
     *
     * */
    public LinkedList<T> getSuccessors(T vertex) {
        if (vertices.contains(vertex)){
            return arcs.get(vertices.indexOf(vertex));
        }
        return null;
    }

    /** 
     * Return all the vertices x, in this graph, that precede a given
     * vertex.
     * 
     * @param A vertex in the graph whose predecessors will be returned.
     * @return LinkedList containing all the vertices x in the graph,
     * for which an arc exists from x to the given vertex (x -> vertex).
     **/
    public LinkedList<T> getPredecessors(T vertex) {
        LinkedList<T> temp = new LinkedList<T>();
        if (vertices.contains(vertex)){
            for (int i = 0; i < vertices.size(); i++){
                if (isArc(vertices.get(i), vertex)){
                    temp.add(vertices.get(i));
                }
            }
            return temp;
        }
        return null;
    }

    /** 
     * Returns a string representation of this graph.
     * 
     * @return a string represenation of this graph, containing its vertices and its arcs/edges
     * @override toString
     **/
    public String toString() {
        String result = "Vertices:\n"+vertices+"\nEdges:\n";
        for (int i = 0; i<arcs.size(); i++){
            result += "from "+vertices.get(i)+": "+arcs.get(i)+"\n";        
        }
        return result;
    }

    /** 
     * Writes this graph into a file in the TGF format.
     * 
     * @param the name of the file where this graph will be written in the TGF format.
     **/
    public void saveToTGF(String tgf_file_name) {
        try {
            PrintWriter writer = new PrintWriter(new File(tgf_file_name));
            for (int i = 0; i < vertices.size(); i ++) {
                writer.println((i+1) + " " + vertices.get(i));
            }
            writer.println("#");
            for (int i = 0; i < arcs.size(); i++) {
                for (int j = 0; j < arcs.get(i).size(); j++) {
                    // +1 because the indices start from 1
                    writer.println((i+1) + " " + (vertices.indexOf(arcs.get(i).get(j))+1));
                }
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Runs a breadth-first traversal (BFS)
     * 
     * @param v index of the starting vertex  
     * @return the iterator containing the order traversed using BFS
     */
    public ArrayIterator<T> BFS(int v) 
    { 
        int currentVertex;
        // Create a queue 
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
        ArrayIterator<T> iter = new ArrayIterator<T>();

        // If the starting vertex is invalid, returns null
        if (!(v<vertices.size() || v<0)){
            return null;
        }

        // Mark all the vertices as not visited(false by default) 
        boolean visited[] = new boolean[vertices.size()];         

        traversalQueue.enqueue(v); // add the starting vertex to the queue
        // Mark the starting vertex as visited
        visited[v] = true;

        // Procedure
        while (!traversalQueue.isEmpty())
        {
            currentVertex = traversalQueue.dequeue();
            iter.add(vertices.elementAt(currentVertex));
            for (int vertexIndex = 0; vertexIndex < vertices.size(); vertexIndex++)
                if (isEdge(vertices.elementAt(currentVertex),vertices.elementAt(vertexIndex)) &&
                !visited[vertexIndex])
                {
                    traversalQueue.enqueue(vertexIndex);
                    visited[vertexIndex] = true;
                }
        } 
        return iter;
    }

    /**
     * Runs a depth-first traversal (DFS)
     * 
     * @param v index of the starting vertex 
     * @return the iterator containing the order traversed using DFS
     */
    public ArrayIterator<T> DFS(int v)
    {
        int currentVertex;
        LinkedStack<Integer> traversalStack = new LinkedStack<Integer>();
        ArrayIterator<T> iter = new ArrayIterator<T>();
        boolean[] visited = new boolean[vertices.size()];
        boolean found;
        // If the starting vertex is invalid, returns null
        if (!(v<vertices.size() || v<0)){
            return null;
        }
        traversalStack.push(v);
        iter.add (vertices.elementAt(v));
        visited[v] = true;

        while (!traversalStack.isEmpty())
        {
            currentVertex = traversalStack.peek();
            found = false;
            for (int i = 0; i < vertices.size() && !found; i++)
                if (isEdge(vertices.elementAt(currentVertex),vertices.elementAt(i)) && !visited [i]) {
                    traversalStack.push(i);
                    iter.add(vertices.elementAt(i));
                    visited[i] = true;
                    found = true;
                }
            if (!found && !traversalStack.isEmpty())
                traversalStack.pop();
        }
        return iter;
    }

    /**
     * Main driver of the test
     */
    public static void main (String[] args){
        // Testing on a cycle (C5)
        AdjListsGraph<String> g1 = new AdjListsGraph();

        g1.addVertex("A");
        g1.addVertex("B");
        g1.addVertex("C");
        g1.addVertex("D");
        g1.addVertex("E");
        g1.addEdge("A","B");
        g1.addEdge("B","C");
        g1.addEdge("C","D");
        g1.addEdge("D","E");
        g1.addEdge("E","A");

        // Make a tgf file for C5 
        g1.saveToTGF("Cycle.tgf");

        // System.out.println(g);
        // System.out.println(g.getSuccessors("A"));
        // System.out.println(g.getSuccessors("C"));
        // System.out.println(g.getPredecessors("B"));

        //System.out.println(g.BFS(2));
        //System.out.println(g.DFS(2));

        // g.saveToTGF("test.tgf");
        // g.saveToTGF("test2.tgf");

        // Testing on a tree 
        AdjListsGraph<String> g2 = new AdjListsGraph();
        g2.addVertex("A");
        g2.addVertex("B");
        g2.addVertex("C");
        g2.addVertex("D");
        g2.addVertex("E");
        g2.addVertex("F");
        g2.addVertex("G");
        g2.addEdge("A","B");
        g2.addEdge("A","C");
        g2.addEdge("B","D");
        g2.addEdge("B","E");
        g2.addEdge("C","F");        
        g2.addEdge("C","G");

        g2.saveToTGF("Tree.tgf");

        // Testing on a path P4
        AdjListsGraph<String> g3 = new AdjListsGraph();

        g3.addVertex("A");
        g3.addVertex("B");
        g3.addVertex("C");
        g3.addVertex("D");
        g3.addEdge("A","B");
        g3.addEdge("B","C");
        g3.addEdge("C","D");

        g3.saveToTGF("Path.tgf");

        // Testing on a disconnected graph
        AdjListsGraph<String> g4 = new AdjListsGraph();

        g4.addVertex("A");
        g4.addVertex("B");
        g4.addVertex("C");
        g4.addVertex("D");
        g4.addEdge("A","B");
        g4.addEdge("B","C");
        g4.addEdge("C","A");  

        g4.saveToTGF("Disconnected.tgf");       

        // Testing on a bipartite graph K2,3 
        AdjListsGraph<String> g5 = new AdjListsGraph();

        g5.addVertex("A");
        g5.addVertex("B");
        g5.addVertex("C");
        g5.addVertex("D");
        g5.addVertex("E");
        g5.addEdge("A","C");
        g5.addEdge("A","D");
        g5.addEdge("A","E");  
        g5.addEdge("B","D");
        g5.addEdge("B","C");
        g5.addEdge("B","E");  

        g5.saveToTGF("Bipartite.tgf");         

        // Testing on a single vertex
        AdjListsGraph<String> g6 = new AdjListsGraph();
        g6.addVertex("A");

        g6.saveToTGF("Single.tgf");

        // Testing on a complete graph K
        AdjListsGraph<String> g7 = new AdjListsGraph();

        g7.addVertex("A");
        g7.addVertex("B");
        g7.addVertex("C");
        g7.addVertex("D");
        g7.addVertex("E");
        g7.addEdge("A","B");
        g7.addEdge("A","C");
        g7.addEdge("A","D");
        g7.addEdge("A","E");  
        g7.addEdge("B","D");
        g7.addEdge("B","C");
        g7.addEdge("B","E");          
        g7.addEdge("C","D");
        g7.addEdge("C","E");          
        g7.addEdge("D","E");        

        g7.saveToTGF("Complete.tgf");


        //System.out.println(g2.runDFS());
    }
}
