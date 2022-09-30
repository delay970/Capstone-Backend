package com.capstone.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.capstone.models.NGram;

@Service
public class FormulaFinderService {

	// This Method is used to find the repetitions in a single document.
	public Map<String, NGram> findRepetitions(String text, int minSize) {

		Map<String, NGram> results = new HashMap<String, NGram>();

		NGramService ngramService = new NGramService();

		List<String> context = ngramService.stringToList(text);

		Map<String, NGram> ngrams1 = ngramService.createNGrams(context, minSize);

		// This removes any ngram if it only occurs one time as it can't be a
		// repetition.
		ngrams1.values().removeIf(ngram -> 1 == ngram.getTotal());

		// This loop creates ngrams for every value of n+1 starting at minsize.
		// It will never run context.size() times as it breaks after it finds the
		// largest repetition.
		for (int i = minSize; i < context.size(); i++) {

			// if ngrams1 is empty than there are no larger repetitions
			if (ngrams1.isEmpty()) {
				break;
			}
			
			Map<String, NGram> ngrams2 = ngramService.createNGrams(context, i + 1);
			ngrams2.values().removeIf(ngram -> 1 == ngram.getTotal());

			Map<String, NGram> temp = ngramService.mergeUp(ngrams1, ngrams2);

			// Assigning ngrams1 to be ngrams2 so it doesn't need to recompute ngrams i
			// each iteration
			ngrams1 = ngrams2;

			if (temp.isEmpty()) {
				continue;

			}

			results.putAll(temp);

		}

		return results;
	}

	// This method works the same as findRepatitions.
	// The main difference is this function looks for common ngrams between two
	// documents while the first function loops for repeated elements in one
	// document.
	public Map<String, NGram> findFormulas(String text1, String text2, int minSize) {
		Map<String, NGram> results = new HashMap<String, NGram>();
		NGramService ngramService = new NGramService();

		List<String> context1 = ngramService.stringToList(text1);
		List<String> context2 = ngramService.stringToList(text2);

		// Get ngrams of docs 1 and 2 of length minsize
		Map<String, NGram> doc1ngrams = ngramService.createNGrams(context1, minSize);
		Map<String, NGram> doc2ngrams = ngramService.createNGrams(context2, minSize);

		Map<String, NGram> ngrams1 = ngramService.findCommonElements(doc1ngrams, doc2ngrams);
		
		// This loop creates ngrams for every value of n+1 starting at minsize.
		// It will only run context1.size() times if the text of doc one is a subtext of
		// doc two.
		for (int i = minSize; i < context1.size(); i++) {
			
			if (ngrams1.isEmpty()) {
				break;
			}
			
			doc1ngrams = ngramService.createNGrams(context1, i + 1);
			doc2ngrams = ngramService.createNGrams(context2, i + 1);

			Map<String, NGram> ngrams2 = ngramService.findCommonElements(doc1ngrams, doc2ngrams);
			Map<String, NGram> temp = ngramService.mergeUp(ngrams1, ngrams2);

			// Assigning ngrams1 to be ngrams2 so it doesn't need to recompute ngrams i
			// each iteration
			ngrams1 = ngrams2;
			
			if (temp.isEmpty()) {
				continue;

			}

			results.putAll(temp);

		}

		return results;
	}

	// This method looks at each result to see if it was one of the rare cases where
	// it missed both a right and a left count.
	// Results must be sorted from largest to smallest in order to work
	public void cleanResults(List<NGram> results) {
		// loop through results starting with largest
		for (int i = 0; i < results.size(); i++) {

			// result we are verifying
			NGram ngram = results.get(i);

			int total = ngram.getTotal();

			// If the count is the same then this ngram is a formula and not a subformula
			if (total == ngram.getUnique()) {
				continue;
			}

			String ngramString = ngram.getNgram();

			// this loop compares the ngram to all ngrams larger than it to see how many
			// times it was a part of those larger ngrams.
			for (int j = 0; j < i; j++) {
				NGram result = results.get(j); // known result

				// this checks if the current result was already eliminated and ignores it
				// if it was.
				if (result.getUnique() == 0) {
					continue;
				}

				String resultString = result.getNgram();

				// counts number of occurences of the ngram in result
				int pos = resultString.indexOf(ngramString);
				int count = 0;
				while (pos != -1) {
					count++;
					pos = resultString.indexOf(ngramString, pos + 1);
				}

				// Takes the total amount the ngram appeared in the document and subtracts
				// how many times it appeared in the result.
				total -= count * result.getUnique();
			}

			// Set the correct total of unique occurrences
			ngram.setUnique(total);

		}
		// remove false positive results
		results.removeIf(ngram -> ngram.getUnique() == 0);
	}

}
