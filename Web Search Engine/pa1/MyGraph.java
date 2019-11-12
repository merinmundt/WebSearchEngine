import java.util.ArrayList;
import java.util.List;

public class MyGraph<E> extends Graph<E> {
	
	
   /**
   * Returns an ArrayList of the actual objects constituting the vertices
   * of this graph.
   * @return
   *   ArrayList of objects in the graph
   */
	@Override
	public ArrayList<E> vertexData(){
		return this.vertexData();
	    
	}
  
  /**
   * Returns an ArrayList that is identical to that returned by vertexData(), except
   * that each vertex is associated with its incoming edge count.
   * @return
   *   ArrayList of objects in the graph, each associated with its incoming edge count
   */
	@Override
	public ArrayList<TaggedVertex<E>> vertexDataWithIncomingCounts(){
			ArrayList<String> current = new Arraylist<String>();
			ArrayList<E> vData = this.vertexData();
			for(int i = 0; i < vData.size(); i++) {
				int edgeCount = vData[i].getIncoming(i);
				edgeCount.toString();
				vData[i].toString();
				String data = vData[i] + edgeCount;
				current[i] = data;
			}
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
	@Override
	public List<Integer> getNeighbors(int index){
		ArrayList<E> cur = this.vertexData();
		ArrayList<E> neighbors = new ArrayList<E>();
		for(int i = 0; i < cur.size(); i++) {
			
		}
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
  public List<Integer> getIncoming(int index);
	
}
