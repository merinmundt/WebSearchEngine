package pa1;

import java.io.IOException;
import java.util.List;

import api.Graph;
import api.TaggedVertex;

/**
 * 
 * @author Merin Mundt
 *
 */

public class Main {

	public static void main(String[] args) throws IOException{
		System.out.println("Hello World");
		
		Crawler crawler = new Crawler("http://web.cs.iastate.edu/~smkautz", 2, 10);
		Graph<String> graph = crawler.crawl();
		
		
		Index webIndex = new Index(graph.vertexDataWithIncomingCounts());
		
		System.out.println("searching");
		List<TaggedVertex<String>> results = webIndex.search("iowa");
		
		for(TaggedVertex<String> result: results) {
			System.out.println(result.getVertexData() + "rank: " + result.getTagValue());
		}
		
		System.out.println("searching with AND");
		List<TaggedVertex<String>> resultsAnd = webIndex.searchWithAnd("iowa", "state");
		
		for(TaggedVertex<String> result: resultsAnd) {
			System.out.println(result.getVertexData() + "rank: " + result.getTagValue());
		}
		
		
		System.out.println("searching wiht OR");
		List<TaggedVertex<String>> resultsOr = webIndex.searchWithOr("iowa", "chapter");
		
		for(TaggedVertex<String> result: results) {
			System.out.println(result.getVertexData() + "rank: " + result.getTagValue());
		}
		
		
		System.out.println("searching with NOT");
		List<TaggedVertex<String>> resultsNot = webIndex.searchAndNot("iowa", "state");
		
		for(TaggedVertex<String> result: resultsNot) {
			System.out.println(result.getVertexData() + "rank: " + result.getTagValue());
		}
		
	}
}
