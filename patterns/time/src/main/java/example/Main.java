package example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import jo4neo.ObjectGraph;
import jo4neo.ObjectGraphFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class Main {
	
	//"Mon, 28 Dec 2009 18:35:25 +0000"	
	public static DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
	
	public static void main(String[] args) throws MalformedURLException, IOException, ParseException {
		
		GraphDatabaseService neo = new EmbeddedGraphDatabase("neo_store");
		ObjectGraph graph = ObjectGraphFactory.instance().get(neo);
		
		populateGraph(graph);
		org.neo4j.graphdb.Transaction t = graph.beginTx();
		try {

			Collection<URI> people =  graph.get(URI.class);
			System.out.println(people.size() + " people have been tweeting bout neo4j lately.");
			for (URI uri : people) {
				System.out.println(uri);
			}
			
			Year year = new Year();
			year = graph.find(year).where(year.value).is(2010).result();
			Month january = year.getMonth(0);
			for (Day day : january.days) {
				int count=0;
				for (Hour h : day.hours)
					count += graph.count(h.events);
				System.out.println("Jan " + day.value + ": " + count + " tweets." );
			}
			
		} finally {
			t.finish();
		}
		graph.close();
		neo.shutdown();
	}

	private static void populateGraph(ObjectGraph graph) throws IOException,
			MalformedURLException, ParseException {
		Collection<Tweet> tweets = getTweets();
		System.out.println("finished reading twitter feed...");
		
		Util util = new Util(graph);
		Calendar cal = Calendar.getInstance();
		Transaction t = graph.beginTx();

		try {
			for (Tweet tweet : tweets) {
				cal.setTime(tweet.time);
				int hour = cal.get(Calendar.HOUR_OF_DAY);
				Day day = util.findDay(cal);
				for (Hour h : day.hours) {
					if (h.value == hour)
						tweet.hour = h;
				}
				graph.persist(tweet);
			}
			t.success();
			
		} finally {
			t.finish();
		}
	}

	private static Collection<Tweet> getTweets() throws IOException,
			MalformedURLException, ParseException {
		URI apicall = URI.create("http://search.twitter.com/search.json?q=neo4j&rpp=100");
		
		InputStreamReader reader = new InputStreamReader( apicall.toURL().openStream());
		BufferedReader br = new BufferedReader(reader);
		
		StringBuilder buffer = new StringBuilder();
		String line;
		while((line=br.readLine()) != null)
			buffer.append(line);

		JSONObject json =  JSONObject.fromObject(buffer.toString());
		JSONArray results =  json.getJSONArray("results");

		Collection<Tweet> tweets = new ArrayList<Tweet>();
		for (int i=0; i<results.size(); i++) {	
			JSONObject result =  (JSONObject)results.get(i);			
			Date d = df.parse(result.get("created_at").toString());
			String content = result.get("text").toString();
			String from = result.get("from_user").toString();
			Tweet tweet = new Tweet();
			tweet.content = content;
			tweet.time = d;
			tweet.from = URI.create("http://twitter.com/" + from);
			tweets.add(tweet);
		}
		return tweets;
	}
}
