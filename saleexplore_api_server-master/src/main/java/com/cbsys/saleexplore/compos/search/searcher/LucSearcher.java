package com.cbsys.saleexplore.compos.search.searcher;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Xiangnan Ren
 * @project discountserver
 * Lucene index builder base
 */
public abstract class LucSearcher {

    protected IndexSearcher searcher;

    protected FuzzyQuery buildFuzzyQuery(String fieldName,
                                         String text,
                                         int maxEditDist) {
        return new FuzzyQuery(new Term(fieldName, text + "~"), maxEditDist);
    }

    protected String findTopOneIdStr(org.apache.lucene.search.IndexSearcher searcher,
                                     TopDocs topDocs,
                                     String fieldName) throws IOException {
        return searcher.doc(topDocs.scoreDocs[0].doc).get(fieldName);
    }

    protected String[] findTopKId(org.apache.lucene.search.IndexSearcher searcher,
                                  TopDocs topDocs,
                                  String fieldName,
                                  int topk) throws IOException {
        String[] ids = new String[Math.min(topDocs.totalHits, topk)];
        for (int i = 0; i < Math.min(topDocs.totalHits, topk); i++) {
            ids[i] = searcher.doc(topDocs.scoreDocs[i].doc).get(fieldName);
        }
        return ids;
    }


    protected Query buildFuzzyQueryWithFilter(String fieldName,
                                              String text,
                                              String filterFieldName,
                                              String filterText,
                                              int maxEditDist) {
        FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term(fieldName, text + "~"), maxEditDist);
        Query filterQuery = new TermQuery(new Term(filterFieldName, filterText));
        return new BooleanQuery.Builder().
                add(fuzzyQuery, BooleanClause.Occur.MUST).
                add(filterQuery, BooleanClause.Occur.FILTER).
                build();
    }


    protected Set<String> searchIdsWithFilter(String text,
                                              String filterText,
                                              int topK) throws IOException {
        return new HashSet<>();
    }

    /**
     * @text query text which may contains several keywords seperated by space ' '
     */
    public List<Long> searchWithSpecialChar(String text, String filterText, int topK) throws IOException {
        String[] splitWords = text.split(String.valueOf(' '));
        Set<String> finalRes = searchIdsWithFilter(splitWords[0], filterText, topK);

        for (int i = 1; i < splitWords.length; i++) {
            finalRes.retainAll(searchIdsWithFilter(splitWords[i], filterText, topK));
        }

        String[] results = finalRes.toArray(new String[0]);

        // Convert into string
        List<Long> resultIds = new ArrayList<>();
        for(String result : results){
            resultIds.add(Long.valueOf(result));
        }
        return resultIds;
    }


}
