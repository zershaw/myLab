package com.cbsys.saleexplore.compos.search.searcher;

import com.cbsys.saleexplore.config.AppProperties;
import com.cbsys.saleexplore.compos.search.record.DiscountLucRecord;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Component
public class DiscountLucSearcher extends LucSearcher {

    /**
     * Constructor, takes the index directory of the discount as input.
     */
    public DiscountLucSearcher(AppProperties appProperties) throws Exception {
        try {
            Path indexPath = Paths.get(appProperties.getDiscountLuceneIndexDir());
            Directory indexDir = FSDirectory.open(indexPath);
            searcher = new IndexSearcher(DirectoryReader.open(indexDir));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("IndexSearcher initialization failed");
        }
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

    @Override
    protected Set<String> searchIdsWithFilter(String text,
                                              String filterText,
                                              int topK) throws IOException {

        String normalizedText = text.toLowerCase();
        String normalizedFilterText = filterText.toLowerCase();
        int maxEditDist = 1;
        List<String[]> res = new ArrayList<>();
        Query[] queries = new Query[]{
                buildFuzzyQueryWithFilter(
                        DiscountLucRecord.TITLE, normalizedText, DiscountLucRecord.CITY_ID, normalizedFilterText, maxEditDist),
                buildFuzzyQueryWithFilter(
                        DiscountLucRecord.INFO, normalizedText, DiscountLucRecord.CITY_ID, normalizedFilterText, maxEditDist),
                buildFuzzyQueryWithFilter(
                        DiscountLucRecord.MALL_NAME, normalizedText, DiscountLucRecord.CITY_ID, normalizedFilterText, maxEditDist),
                buildFuzzyQueryWithFilter(
                        DiscountLucRecord.STORE_NAME, normalizedText, DiscountLucRecord.CITY_ID, normalizedFilterText, maxEditDist),
                buildFuzzyQueryWithFilter(
                        DiscountLucRecord.CATEGORY, normalizedText, DiscountLucRecord.CITY_ID, normalizedFilterText, 2)
        };
        for (Query fuzzyQuery : queries) {
            String[] subres = findTopKId(
                    searcher,
                    searcher.search(fuzzyQuery, topK),
                    DiscountLucRecord.ID,
                    topK);
            res.add(subres);
        }
        Set<String> mergedSearchRes = new LinkedHashSet<>();
        for (String[] re : res) {
            mergedSearchRes.addAll(Arrays.asList(re));
        }
        return mergedSearchRes;
    }

}
