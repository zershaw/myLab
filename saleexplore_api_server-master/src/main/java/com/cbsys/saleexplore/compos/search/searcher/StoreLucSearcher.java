package com.cbsys.saleexplore.compos.search.searcher;

import com.cbsys.saleexplore.config.AppProperties;
import com.cbsys.saleexplore.compos.search.record.StoreLucRecord;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Xiangnan Ren
 * @project discountserver
 * Store keyword searcher based on lucene
 */
@Component
public class StoreLucSearcher extends LucSearcher {

    /**
     * Constructor, takes the index directory of the store as input.
     */
    public StoreLucSearcher(AppProperties appProperties) throws Exception {
        try {
            Path indexPath = Paths.get(appProperties.getStoreLuceneIndexDir());
            Directory indexDir = FSDirectory.open(indexPath);
            searcher = new IndexSearcher(DirectoryReader.open(indexDir));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("IndexSearcher initialization failed");
        }
    }

    /**
     * override the searchIds of base. the key function of taking a kwQuery and return a list of Ids in string
     */
    @Override
    protected Set<String> searchIdsWithFilter(String text,
                                              String filterText,
                                              int topK) throws IOException {
        String normalizedText = text.toLowerCase();
        String normalizedFilterText = filterText.toLowerCase();

        int maxEditDist = 1;
        List<String[]> res = new ArrayList<>();

        Query[] queries = new Query[]{
                buildFuzzyQueryWithFilter(StoreLucRecord.NAME, normalizedText, StoreLucRecord.CITY_ID, normalizedFilterText, maxEditDist),
                buildFuzzyQueryWithFilter(StoreLucRecord.INFO, normalizedText, StoreLucRecord.CITY_ID, normalizedFilterText, maxEditDist),
                buildFuzzyQueryWithFilter(StoreLucRecord.MALL_NAME, normalizedText, StoreLucRecord.CITY_ID, normalizedFilterText, maxEditDist),
                buildFuzzyQueryWithFilter(StoreLucRecord.CATEGORY, normalizedText, StoreLucRecord.CITY_ID, normalizedFilterText, 2),
                buildFuzzyQueryWithFilter(StoreLucRecord.BRAND, normalizedText, StoreLucRecord.CITY_ID, normalizedFilterText, maxEditDist)
        };

        for (Query fuzzyQuery : queries) {
            String[] subres = findTopKId(
                    searcher,
                    searcher.search(fuzzyQuery, topK),
                    StoreLucRecord.ID,
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
