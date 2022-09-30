package com.capstone.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.capstone.models.NGram;

@Service
public class NGramService {

	// This function converts the string that is loaded in from the file to a list
	public List<String> stringToList(String c) {
		List<String> context = new ArrayList<String>();
		String[] temp = c.split(" ");
		context = Arrays.asList(temp);
		return context;
	}

	// This method creates a map of Ngrams
	public Map<String, NGram> createNGrams(List<String> context, int n) {

		// The ngrams are put into a map. This is done to ensure that all of the
		// information associated with the ngrams such as the counters can be quickly
		// accessed with the ngram as the key.
		Map<String, NGram> ngrams = new HashMap<String, NGram>();

		// this loop starts to build the ngrams
		for (int i = 0; i < context.size() - n + 1; i++) {
			StringBuilder temp = new StringBuilder();

			// builds ngram starting at word i
			for (int j = i; j < n + i; j++) {
				temp.append(context.get(j) + " ");
			}

			// remove last space
			temp.deleteCharAt(temp.length() - 1);

			NGram ngram = ngrams.get(temp.toString());

			// Increments count if exists otherwise adds new ngram to map
			if (ngram != null) {
				ngram.increment();
			} else {
				ngram = new NGram(temp.toString(), n, 1, 1);
				ngrams.put(temp.toString(), ngram);
			}

		}

		return ngrams;
	}

	// This method is responsible for removing most of the partial formulas.
	// It fails to remove all partial formulas when a partial formula is the
	// beginning of one formula and the end of different formula.
	// This problem is resolved with the cleanResults function.
	public Map<String, NGram> mergeUp(Map<String, NGram> ngrams1, Map<String, NGram> ngrams2) {

		// ngrams1 = ngrams of size n.
		// ngrams2 = ngrams of size n+1.

		// if ngrams2 is empty there is nothing to compare then nothing will be
		// subtracted from the counts of ngrams1
		if (ngrams2.isEmpty()) {
			return ngrams1;
		}

		// Results set to be returned.
		Map<String, NGram> results = new HashMap<String, NGram>();

		// These are left and right counts for ngrams1 each element stored in a hashmap
		// for fast access.
		// The reason there is two separate counts is because otherwise there were
		// scenarios where the two different ngrams would be apart of the same larger
		// formula and have the same subgram in common.
		// This happens when 2 ngrams overlap and it causes the algorithm to to count
		// the subgram twice.
		// My solution for this was to break the count into two separate counts, one for
		// if the left subgram and one for the right subgram.
		Map<String, Integer> left = new HashMap<String, Integer>();
		Map<String, Integer> right = new HashMap<String, Integer>();

		// Put initial counts inside of left and right hash maps
		for (Map.Entry<String, NGram> entry : ngrams1.entrySet()) {
			left.put(entry.getKey(), entry.getValue().getTotal());
			right.put(entry.getKey(), entry.getValue().getTotal());
		}

		// scan through ngrams2 and use the subgrams of each element to get the counts
		// for ngrams1.
		for (Map.Entry<String, NGram> entry : ngrams2.entrySet()) {

			String key = entry.getKey();
			NGram ngram = entry.getValue();

			// get left and right subgrams from the current entry
			String leftSubgram = key.substring(0, key.lastIndexOf(" "));
			String rightSubgram = key.substring(key.indexOf(" ") + 1);

			// current count for left subgram
			int leftCount = left.get(leftSubgram);

			// Each subgram must appear at least as many times as its supper gram.
			// If a subgram appears the same number of the times as a super gram, then it is
			// not a subformula as all instants of the subgram must be part of a larger
			// formula.
			// This property is shared by each left and right count.
			leftCount -= ngram.getTotal();

			// update count in hash table
			left.replace(leftSubgram, leftCount);

			int rightCount = right.get(rightSubgram);

			rightCount -= ngram.getTotal();
			right.replace(rightSubgram, rightCount);
		}

		// This loop compares the left and right counts for each subgram.
		// It then updates how many times each subformula is present and is not apart of
		// a larger formula.
		for (Map.Entry<String, NGram> entry : ngrams1.entrySet()) {

			String key = entry.getKey();
			NGram ngram = entry.getValue();
			int leftCount = left.get(key);
			int rightCount = right.get(key);

			// If either is less than 0 then something went wrong.
			// This was mainly used for debugging.
			if (leftCount < 0 || rightCount < 0) {
				System.out.println("opps");
				return null;
			}

			// If either count is 0 then all instances of the ngram are part of a larger
			// formulas so we do not add the ngram to the result set
			if (leftCount == 0 || rightCount == 0) {
				continue;
			}

			// This sets the lesser of the two counts as the number of instances that are
			// not apart of a larger formula.
			// The left and right count will not be the same in scenarios where there are
			// there was no left or right subgram for particular ngram.
			// An example where this would occur is at the very beginning or very end of the
			// text.
			// If an ngram is only at the begging of text then it could never be a right
			// subgram.
			// Note there are scenarios where both the left and right count are too large.
			// These are solved in cleanResults
			if (leftCount < rightCount) {
				ngram.setUnique(leftCount);
				results.put(key, ngram);
			} else {
				ngram.setUnique(rightCount);
				results.put(key, ngram);
			}
		}

		return results;
	}

	// this method returns the Ngrams that are the same between two documents for a
	// given n.
	public Map<String, NGram> findCommonElements(Map<String, NGram> ngrams1, Map<String, NGram> ngrams2) {
		Map<String, NGram> results = new HashMap<String, NGram>();

		// Gets common ngrams
		Set<String> keySet = ngrams1.keySet();
		keySet.retainAll(ngrams2.keySet());

		// This loop looks at each one of the common ngrams and puts them into a map.
		// The count for each ngram is the number of times it occurs in both documents.
		for (String key : keySet) {
			NGram ng1 = ngrams1.get(key);
			NGram ng2 = ngrams2.get(key);
			if (ng1.getTotal() < ng2.getTotal()) {
				results.put(key, ng1);
			} else {
				results.put(key, ng2);
			}

		}

		return results;
	}

}
