package edu.gatech.oad.rocket.findmythings.server.util;

import com.google.api.server.spi.response.CollectionResponse;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.cmd.Query;
import edu.gatech.oad.rocket.findmythings.server.db.DatabaseService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Utility class for indexing and searching items conforming to the Searchable
 * interface.
 *
 * User: zw
 * Date: 4/8/13
 * Time: 2:35 AM
 */
public class SearchableHelper {
	private static final Logger log = Logger.getLogger(SearchableHelper.class.getName());

	// Magic numbers!
	public static final int MAXIMUM_NUMBER_OF_WORDS_TO_SEARCH = 10;

	public static final int MAX_NUMBER_OF_WORDS_TO_PUT_IN_INDEX = 200;

	public static <T extends Searchable> Query<T> search(Objectify objectify, Class<T> clazz, String query) {
		Set<String> queryTokens = getSearchTokens(query, MAXIMUM_NUMBER_OF_WORDS_TO_SEARCH);
		return objectify.load().type(clazz).filter("searchTokens in", queryTokens);
	}

	/*public static List<GuestBookEntry> searchGuestBookEntries(String queryString) {

		Objectify ofy = ObjectifyService.begin();

		Query<GuestBookEntry> query = ofy.query(GuestBookEntry.class);

		Set<String> queryTokens = SearchJanitorUtils.getTokensForIndexingOrQuery(queryString, MAXIMUM_NUMBER_OF_WORDS_TO_SEARCH);

		List<String> parametersForSearch = new ArrayList<String>(queryTokens);

		for (String token : parametersForSearch) {
			query.filter("fts", token);
		}

		List<GuestBookEntry> result = null;

		try {
			result = new ArrayList<GuestBookEntry>();
			for (GuestBookEntry guestBookEntry : query) {
				result.add(guestBookEntry);
			}

		} catch (DatastoreTimeoutException e) {
			log.severe(e.getMessage());
			log.severe("datastore timeout at: " + queryString);
		} catch (DatastoreNeedIndexException e) {
			log.severe(e.getMessage());
			log.severe("datastore need index exception at: " + queryString);
		}

		return result;

	}*/

	public static void updateSearchTokens(Searchable item) {
		String sb = item.getSearchableContent();
		Set<String> new_ftsTokens = getSearchTokens(sb, MAX_NUMBER_OF_WORDS_TO_PUT_IN_INDEX);
		Set<String> ftsTokens = item.getSearchTokens();
		ftsTokens.clear();

		for (String token : new_ftsTokens) {
			ftsTokens.add(token);
		}
	}

	/**
	 * Uses Apache Lucene English stemming for indexing similar words.
	 *
	 * @param searchableContext A string describing the object to be indexes
	 * @param maximumNumberOfTokens The limit number of tokens to index
	 * @return A set containing indexed, searchable tokens
	 */
	public static Set<String> getSearchTokens(String searchableContext, int maximumNumberOfTokens) {

		String indexCleanedOfHTMLTags = searchableContext.replaceAll("\\<.*?>"," ");
		Set<String> returnSet = new HashSet<String>();

		try {
			Analyzer analyzer =  new EnglishAnalyzer(Version.LUCENE_42);
			TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(indexCleanedOfHTMLTags));
			OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
			CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

			while (tokenStream.incrementToken()) {
				int startOffset = offsetAttribute.startOffset();
				int endOffset = offsetAttribute.endOffset();
				String term = charTermAttribute.toString();
				returnSet.add(term);
			}
		} catch (IOException e) {
			log.severe(e.getMessage());
		}

		return returnSet;
	}



}
