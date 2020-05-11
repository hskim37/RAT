import javafoundations.*;
import java.util.*;
import java.io.*;

/**
 * Investigate.java
 * This class investigates the RATs and uses AdjListsGraph.java.
 *
 * @author yguo2, hkim22
 * @version 11/20/2019
 */
public class Investigate
{
    // instance variables 
    // Compose a graph
    private AdjListsGraph<String> RATAdjGraph; 

    // the key is userID (a String) and the value is a LinkedList of stories
    private Hashtable<String, LinkedList<String>> RAT; 
    private Vector<String> storyCollection;
    /**
     * Constructor for objects of class Investigate
     */
    public Investigate()
    {
        // initialise instance variables
        RATAdjGraph = new AdjListsGraph<String>();
        RAT = new Hashtable<String, LinkedList<String>>();
        storyCollection = new Vector<String>();
    }

    /**
     * Read from a file that contains information of the RATS
     * 
     * @param file_name  the file that contains RAT information
     */
    private void readFile(String file_name){
        try {
            Scanner fileScan = new Scanner (new File(file_name)); 
            String firstLine = fileScan.nextLine(); // the first line is the header
            while (fileScan.hasNext()) {                
                String line = fileScan.nextLine();
                String[] info = line.split("\t");

                String user_id = "U" + info[1];
                String stories = info[4];

                RATAdjGraph.addVertex(user_id); 
                RAT.put(user_id, divideStories(stories));
                //System.out.println(user_id);  // Testing 
            }
            fileScan.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Helper method
     * Divide the stories from a LinkedList add them to the graph as vertices
     * 
     * @param  stories   a string of stories
     * @return  a linked list of all the stories
     */
    private LinkedList<String> divideStories(String stories){
        String[] story = stories.split(",");
        LinkedList<String> result = new LinkedList<String>();
        for (int i = 0; i<story.length; i++){
            result.add(story[i]);
            RATAdjGraph.addVertex(story[i]);
            storyCollection.add(story[i]); // add each individual story to the collection of stories 
        }
        return result;
    }

    /**
     * Add edges to the graph
     */ 
    private void makeEdges(){
        for (String user:RAT.keySet()) {
            for (String story:RAT.get(user)){
                RATAdjGraph.addEdge(user, story); 
            }
        }
    }

    /**
     * Save graph to TGF file
     * 
     * @param tgf_file_name The name of the output file
     */
    private void makeTGF(String tgf_file_name){
        RATAdjGraph.saveToTGF(tgf_file_name);
    }

    /**
     * Find and return the most active RAT object
     * 
     * Answers Research Question #1
     * @return a string of the userID of the most active RAT
     */
    private String findActive() {
        int max = 0;
        String result = "";
        for (String user:RAT.keySet()) {
            if (RAT.get(user).size()>max) {
                max = RAT.get(user).size();
                result = user;
            }
        }
        return result;
    }

    /**
     * Find and return the most popular story
     * 
     * Answers Research Question #2
     * @return a string of the identification number of the most popular story
     */
    private String findPopular() {
        int max = 0;
        String result = "";
        for (String story:storyCollection) {
            int num = RATAdjGraph.getSuccessors(story).size();
            if (num>max){
                max = num;
                result = story;
            }
        }  
        return result;
    }

    /**
     * Runs DFS on the RATSgraph(AdjListGraph) and test if the graph is connected.
     * Answers Research Question #3
     * 
     * @return true if the diameter of the largest connected components is equal to the number of total vertices
     *         false otherwise
     */
    private boolean runDFS(){
        // Pick a random vertex (here we pick 0) as the starting vertex, and run DFS on it
        return (RATAdjGraph.DFS(0).size() == RATAdjGraph.getNumVertices()); 
    }  

    /**
     * Use the BFS method in AdjListsGraph.java to find out how many layers the LCC have
     * 
     * Answers Research Question #4
     * @return  number of layers in the LCC
     */
    private int runBFS(){
        ArrayIterator<String> iter = RATAdjGraph.BFS(0);
        int result = 0;
        boolean isU = false;
        boolean isStory = false;
        for (int i = 0; i<iter.size(); i++) {
            String e = iter.elementAt(i);
            if (e.substring(0,1).equals("U") && !isU) {
                result += 1;
                isU = true;
                isStory = false;
            } // the beginning of the series of users
            else if (!e.substring(0,1).equals("U") && !isStory) {
                result += 1;
                isStory = true;
                isU = false;
            } // the beginning of the series of stories
        }
        return result;
    }

    /**
     * Main driver of the test
     */
    public static void main (String[] args){
        Investigate ratTest = new Investigate();

        ratTest.readFile("All_Russian-Accounts-in-TT-stories.csv.tsv");
        ratTest.makeEdges();
        ratTest.makeTGF("RATgraph.tgf");

        System.out.println(ratTest.findActive());
        System.out.println(ratTest.findPopular());

        System.out.println("The RATgraph is connected: " + ratTest.runDFS()); 
        System.out.println("Number of layers in LCC: " + ratTest.runBFS());
    }
}
