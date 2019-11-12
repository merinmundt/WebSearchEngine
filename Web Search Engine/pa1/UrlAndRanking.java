/**
 * 
 * @author Merin Mundt
 *This class implents the ranking of the pages
 */
public class UrlAndRanking implements Comparable<UrlAndRanking>{
	public String Url;
	public int Ranking;
	public UrlAndRanking(String url, int ranking) {
		Url = url;
		Ranking = ranking;
    }
    
    @Override
    public int compareTo(UrlAndRanking comparestu) {
        return this.Ranking - comparestu.Ranking;
    }
}
