package pa1;

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
	  int counter = 0;
	  int counter1 = 0;
	  int counter2 = 0;
	  int counter3 = 0;
	  //Extracting the links
	  Document doc = Jsoup.connect(_seedUrl).get();
	  Elements links = doc.select("a[href]");
	  for (Element link : links) {
		  if(!Util.ignoreLink(_seedUrl, link)) {
			  	counter++;
			  	if(counter > 6) {
			  		break;
			  	}
			  	Document doc1 = Jsoup.connect(link).get();
			  	Elements links1 = doc1.select("a[href]");
			  	fo
			  
		  }
	  }
	  return null;
  }
}
