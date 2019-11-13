package pa1;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Scanner;

import java.util.Collections;

import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import api.TaggedVertex;
import api.Util;

/**
 * Implementation of an inverted index for a web graph.
 * 
 * @author Merin Mundt
 */
public class Index
{
  //Map to hold the urls and their indegrees
  private Map<String, Integer> _urlMap = new HashMap<String, Integer>();
  /**
   * Constructs an index from the given list of urls.  The
   * tag value for each url is the indegree of the corresponding
   * node in the graph to be indexed.
   * @param urls
   *   information about graph to be indexed
 * @throws IOException 
   */
  public Index(List<TaggedVertex<String>> urls) throws IOException
  {
    for(TaggedVertex<String> url: urls){
      _urlMap.put(url.getVertexData(), url.getTagValue());
    }
    this.makeIndex();
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
  
  /**
   * Creates the index.
   */

/**
 * data structure to hold each url and its list of words with occurrences
 */
  private Map<String, Map<String, Integer>> _indexMap = new HashMap<String, Map<String, Integer>>();
  public void makeIndex() throws IOException
  {
    int webhits = 0;
    for(Entry<String, Integer> urlEntry: _urlMap.entrySet()){
      Map<String, Integer> wordMap = new HashMap<String, Integer>();
      //search the site add to the Map, url is the key, and a Map of words plus its # occurences
      if(webhits % 50 == 0){
        waitthree();
      }
      String body = Jsoup.connect(urlEntry.getKey()).get().body().text();
      webhits++;
      Scanner sc = new Scanner(body);
      while(sc.hasNext()){
        String word = sc.next();
        word = Util.stripPunctuation(word);
        if(!Util.isStopWord(word) && word.length() > 0){
          System.out.println("adding word " + word);
          wordMap.putIfAbsent(word, 0);
          wordMap.put(word, wordMap.get(word) + 1);
        }
      }
      sc.close();
      _indexMap.put(urlEntry.getKey(), wordMap);
    }
  }
  
  /**
   * Searches the index for pages containing keyword w.  Returns a list
   * of urls ordered by ranking (largest to smallest).  The tag 
   * value associated with each url is its ranking.  
   * The ranking for a given page is the number of occurrences
   * of the keyword multiplied by the indegree of its url in
   * the associated graph.  No pages with rank zero are included.
   * @param w
   *   keyword to search for
   * @return
   *   ranked list of urls
   */
  public List<TaggedVertex<String>> search(String w)
  {
    //temp list to sort later.  UrlAndRanking implement Comparable for sorting.
    ArrayList<UrlAndRanking> list = new ArrayList<UrlAndRanking>();
    for(Entry<String, Map<String, Integer>> entry : _indexMap.entrySet()){
      if(entry.getValue().containsKey(w)){
        //ranking equals url indegree plus number of occurences
        int indegree = _urlMap.get(entry.getKey());
        int numOccurences = entry.getValue().get(w);
        int ranking = indegree * numOccurences;
        if(ranking > 0){
          list.add(new UrlAndRanking(entry.getKey(), ranking));
        }
      }
    }

    Collections.sort(list);
    ArrayList<TaggedVertex<String>> finallist = new ArrayList<TaggedVertex<String>>();
    for(int i = list.size() - 1;i >= 0;i--){
      finallist.add(new TaggedVertex<String>(list.get(i).Url, list.get(i).Ranking));
    }
    
    return finallist;
  }


  /**
   * Searches the index for pages containing both of the keywords w1
   * and w2.  Returns a list of qualifying
   * urls ordered by ranking (largest to smallest).  The tag 
   * value associated with each url is its ranking.  
   * The ranking for a given page is the number of occurrences
   * of w1 plus number of occurrences of w2, all multiplied by the 
   * indegree of its url in the associated graph.
   * No pages with rank zero are included.
   * @param w1
   *   first keyword to search for
   * @param w2
   *   second keyword to search for
   * @return
   *   ranked list of urls
   */
  public List<TaggedVertex<String>> searchWithAnd(String w1, String w2)
  {
    //temp list to sort later.  UrlAndRanking implement Comparable for sorting.
    ArrayList<UrlAndRanking> list = new ArrayList<UrlAndRanking>();
    for(Entry<String, Map<String, Integer>> entry : _indexMap.entrySet()){
      if(entry.getValue().containsKey(w1) && entry.getValue().containsKey(w2)){
        //ranking equals url indegree plus number of occurences
        int indegree = _urlMap.get(entry.getKey());
        int numOccurences1 = entry.getValue().get(w1);
        int numOccurences2 = entry.getValue().get(w2);
        int ranking = indegree * (numOccurences1 + numOccurences2);
        if(ranking > 0){
          list.add(new UrlAndRanking(entry.getKey(), ranking));
        }
      }
    }

    Collections.sort(list);
    ArrayList<TaggedVertex<String>> finallist = new ArrayList<TaggedVertex<String>>();
    for(int i = list.size() - 1;i >= 0;i--){
      finallist.add(new TaggedVertex<String>(list.get(i).Url, list.get(i).Ranking));
    }
    
    return finallist;
  }
  
  /**
   * Searches the index for pages containing at least one of the keywords w1
   * and w2.  Returns a list of qualifying
   * urls ordered by ranking (largest to smallest).  The tag 
   * value associated with each url is its ranking.  
   * The ranking for a given page is the number of occurrences
   * of w1 plus number of occurrences of w2, all multiplied by the 
   * indegree of its url in the associated graph.
   * No pages with rank zero are included.
   * @param w1
   *   first keyword to search for
   * @param w2
   *   second keyword to search for
   * @return
   *   ranked list of urls
   */
  public List<TaggedVertex<String>> searchWithOr(String w1, String w2)
  {
    //temp list to sort later.  UrlAndRanking implement Comparable for sorting.
    ArrayList<UrlAndRanking> list = new ArrayList<UrlAndRanking>();
    for(Entry<String, Map<String, Integer>> entry : _indexMap.entrySet()){
      if(entry.getValue().containsKey(w1) || entry.getValue().containsKey(w2)){
        //ranking equals url indegree plus number of occurences
        int indegree = _urlMap.get(entry.getKey());
        int numOccurences1 = entry.getValue().containsKey(w1) ? entry.getValue().get(w1): 0;
        int numOccurences2 = entry.getValue().containsKey(w2) ? entry.getValue().get(w2): 0;
        int ranking = indegree * (numOccurences1 + numOccurences2);
        if(ranking > 0){
          list.add(new UrlAndRanking(entry.getKey(), ranking));
        }
      }
    }

    Collections.sort(list);
    ArrayList<TaggedVertex<String>> finallist = new ArrayList<TaggedVertex<String>>();
    for(int i = list.size() - 1;i >= 0;i--){
      finallist.add(new TaggedVertex<String>(list.get(i).Url, list.get(i).Ranking));
    }
    
    return finallist;
  }
  
  /**
   * Searches the index for pages containing keyword w1
   * but NOT w2.  Returns a list of qualifying urls
   * ordered by ranking (largest to smallest).  The tag 
   * value associated with each url is its ranking.  
   * The ranking for a given page is the number of occurrences
   * of w1, multiplied by the 
   * indegree of its url in the associated graph.
   * No pages with rank zero are included.
   * @param w1
   *   first keyword to search for
   * @param w2
   *   second keyword to search for
   * @return
   *   ranked list of urls
   */
  public List<TaggedVertex<String>> searchAndNot(String w1, String w2)
  {
    //temp list to sort later.  UrlAndRanking implement Comparable for sorting.
    ArrayList<UrlAndRanking> list = new ArrayList<UrlAndRanking>();
    for(Entry<String, Map<String, Integer>> entry : _indexMap.entrySet()){
      if(entry.getValue().containsKey(w1) && !entry.getValue().containsKey(w2)){
        //ranking equals url indegree plus number of occurences
        int indegree = _urlMap.get(entry.getKey());
        int numOccurences = entry.getValue().get(w1);
        int ranking = indegree * numOccurences;
        if(ranking > 0){
          list.add(new UrlAndRanking(entry.getKey(), ranking));
        }
      }
    }

    Collections.sort(list);
    ArrayList<TaggedVertex<String>> finallist = new ArrayList<TaggedVertex<String>>();
    for(int i = list.size() - 1;i >= 0;i--){
      finallist.add(new TaggedVertex<String>(list.get(i).Url, list.get(i).Ranking));
    }
    
    return finallist;
  }
}