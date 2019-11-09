package pa1;

import java.util.List;
import java.util.Queue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import api.Graph;

/**
 * Implementation of a basic web crawler that creates a graph of some
 * portion of the world wide web.
 *
 * @author Merin Mundt
 */
public class Crawler
{
	String _seedUrl;
	int _maxDepth;
	int _maxPages;

	
  /**
   * Constructs a Crawler that will start with the given seed url, including
   * only up to maxPages pages at distance up to maxDepth from the seed url.
   * @param seedUrl
   * @param maxDepth
   * @param maxPages
   */
  public Crawler(String seedUrl, int maxDepth, int maxPages)
  {
    _seedUrl = seedUrl;
    _maxDepth = maxDepth;
    _maxPages = maxPages;   
	  
  }
  
  /**
   * Creates a web graph for the portion of the web obtained by a BFS of the 
   * web starting with the seed url for this object, subject to the restrictions
   * implied by maxDepth and maxPages.  
   * @return
   *   an instance of Graph representing this portion of the web
   */
  public Graph<String> crawl()
  {
	  Graph<String> webGraph;
	  Queue<String> Q;
	  List<String> list = new ArrayList<String>(); 
	  int counter = 0;
	  String root = _seedUrl;
	  
	  Q.add(root);
	  list.add(root);
	  
	  while(!Q.isEmpty()) {
		  do {
			  String cur = Q.peek();
			  Document doc = Jsoup.connect(cur).get();
			  Elements links = doc.select("a[href]");
			  for (Element link : links) {
				  if(!Util.ignoreLink(cur, link)) {
					  if(!list.contains(link)) {
						  Q.add(link);
						  list.add(link);
						  counter++
					  }
					  if(counter > _maxPages) {
						  break;
					  }
				  }  
			  }
			  Q.remove();
		  }
	  }
	  return null;
  }
 
}
