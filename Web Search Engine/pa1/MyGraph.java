import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/** @author Merin Mundt
 * This class is the class that implements the graph interface 
 * Helps with the implementation of Crawler and index class
 */

public class MyGraph implements Graph<String> {
	
	private Map<String, List<String>> _map = new HashMap<String, List<String>>();
	
	public void addVertex(String object) {
		_map.putIfAbsent(object, new ArrayList<String>());
	}
	
	public void addEdge(String object, String adjObject) {
		_map.putIfAbsent(object, new ArrayList<String>());
		if(!_map.get(object).contains(adjObject)){
			_map.get(object).add(adjObject);
		}
		_map.putIfAbsent(adjObject, new ArrayList<String>());
			
	}
	/**
	   * Returns an ArrayList of the actual objects constituting the vertices
	   * of this graph.
	   * @return
	   *   ArrayList of objects in the graph
	   */
	  public ArrayList<String> vertexData(){
		  return new ArrayList<String>(_map.keySet());
		    
	  }
	  
	  /**
	   * Returns an ArrayList that is identical to that returned by vertexData(), except
	   * that each vertex is associated with its incoming edge count.
	   * @return
	   *   ArrayList of objects in the graph, each associated with its incoming edge count
	   */
	  public ArrayList<TaggedVertex<String>> vertexDataWithIncomingCounts(){
		ArrayList<TaggedVertex<String>> taggedList = new ArrayList<TaggedVertex<String>>();
		for(int i = 0; i < this.vertexData().size();i++){
			taggedList.add(new TaggedVertex<String>(this.vertexData().get(i), getIncoming(i).size()));
		} 

		return taggedList;
	  }
	  
	  /**
	   * Returns a list of outgoing edges, that is, a list of indices for neighbors
	   * of the vertex with given index.
	   * This method may throw ArrayIndexOutOfBoundsException if the index 
	   * is invalid.
	   * @param index
	   *   index of the given vertex according to vertexData()
	   * @return
	   *   list of outgoing edges
	   */
	  public List<Integer> getNeighbors(int index){
		  ArrayList<Integer> outgoingEdges = new ArrayList<Integer>();
		  String obj = this.vertexData().get(index);
		for(String url: _map.get(obj)){
			outgoingEdges.add(this.vertexData().indexOf(url));
		}
		return outgoingEdges;
	  }
	  
	  /**
	   * Returns a list of incoming edges, that is, a list of indices for vertices 
	   * having the given vertex as a neighbor.
	   * This method may throw ArrayIndexOutOfBoundsException if the index 
	   * is invalid. 
	   * @param index
	   *   index of the given vertex according to vertexData()
	   * @return
	   *   list of incoming edges
	   */
	  public List<Integer> getIncoming(int index){
		  
		String obj =  this.vertexData().get(index);
		  ArrayList<Integer> incomingEdges = new ArrayList<Integer>();
		  for(Map.Entry<String, List<String>> eset: _map.entrySet()){
			if(eset.getValue().contains(obj)){
				incomingEdges.add(this.vertexData().indexOf(eset.getKey()));
			}
		  }

		  return incomingEdges;
	  }
	
}
