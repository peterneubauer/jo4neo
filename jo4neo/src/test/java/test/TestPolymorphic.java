package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;


public class TestPolymorphic extends BaseTest {


	@Test
	public void basic() {
			BlogPost post = new BlogPost();
			post.setPublishDate(new Date());
			post.setTitle("Nosql: take that Oracle");

			BlogPost post2 = new BlogPost();
			post.setPublishDate(new Date());
			post.setTitle("TopQuadrant creates semantic spider for web");

			HotelReview review = new HotelReview();
			review
					.setReviewContent("Right across from the train station, nice breakfast.");
			review.setReviewDate(new Date());

			Tag semweb = new Tag();
			semweb.setName("semweb");
			semweb.getItems().add(post2);
			post2.getTags().add(semweb);
			semweb.getItems().add(post);
			post.getTags().add(semweb);
			semweb.getItems().add(review);

			graph.persist(semweb);
			Tag tag = new Tag();
			tag = graph.find(tag).where(tag.name).is("semweb").result();

			for (Taggable thing : tag.getItems()) {
				assertTrue(thing instanceof ContentItem);
			}
			assertEquals(3, tag.getItems().size());

			graph.delete(semweb);
	
	}
}
