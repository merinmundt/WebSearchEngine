package pa1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.net.ssl.SSLHandshakeException;

import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import api.Graph;
import api.Util;

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
 * @throws IOException 
   */
  public Graph<String> crawl() throws IOException
  {
	  MyGraph webGraph = new MyGraph();
	  Queue<UrlAndDepth> Q = new LinkedList<UrlAndDepth>();
	  List<String> discovered = new ArrayList<String>(); 
	  int pageCounter = 0;
	  int webhitCounter = 1;
	  String root = _seedUrl;
	  
	  Q.add(new UrlAndDepth(root, 0));
	  discovered.add(root);
	  
	  while(!Q.isEmpty() && pageCounter <= this._maxPages) 
	  {
			UrlAndDepth curUrlAndDepth = Q.peek();
			if(curUrlAndDepth.Depth > _maxDepth){
				break;
			}
			String cur = curUrlAndDepth.Url;
			//checks if we are at 50 consecutive webhits
			if(webhitCounter % 50 == 0){
				waitthree();
			}
			System.out.println("Crawling " + cur);
			Document doc = Jsoup.connect(cur).get();
			webhitCounter++;
			Elements links = doc.select("a[href]");
			for (Element link : links) 
			{
				String v = link.attr("abs:href");
				Document temp = null;
				if(!Util.ignoreLink(cur, v) && !v.endsWith("#")) 
				{
					if(!discovered.contains(v)) {
						try
						{
							//checks if we are at 50 consecutive webhits
							if(webhitCounter % 50 == 0){
								waitthree();
							}
							System.out.println("Checking Link: " + v);
							temp = Jsoup.connect(v).get();
							Q.add(new UrlAndDepth(v, curUrlAndDepth.Depth + 1));
							discovered.add(v);
							pageCounter++;
							webGraph.addEdge(cur, v);
								
							
							webhitCounter++;
						}
						catch (UnsupportedMimeTypeException e)
						{
							System.out.println("--unsupported document type, do nothing");
						} 
						catch (HttpStatusException  e)
						{
							System.out.println("--invalid link, do nothing");
						}
						catch(SSLHandshakeException e) {
							System.out.println("--invalid link, do nothing");
						}
						
						
						
					}
					
				
				}  
			}
			Q.remove();
	  }
	  return webGraph;
  }
  
 
  private void waitthree()
  {
	try 
	{ 
		Thread.sleep(3000); 
	} 
	catch (InterruptedException ignore) 
	{ 

	}

  }

  
}
