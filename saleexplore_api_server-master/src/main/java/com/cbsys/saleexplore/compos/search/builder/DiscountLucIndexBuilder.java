package com.cbsys.saleexplore.compos.search.builder;

import com.cbsys.saleexplore.config.AppProperties;
import com.cbsys.saleexplore.compos.search.record.DiscountLucRecord;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DiscountLucIndexBuilder extends LucIndexBuilder {


    /**
     * Constructor, takes the index directory of the discount as input.
     * If the directory doesn't exit. it will be created automatically
     */
    public DiscountLucIndexBuilder(AppProperties appProperties) {
        try {
            Directory indexDir = FSDirectory.open(Paths.get(appProperties.getDiscountLuceneIndexDir()));
            indexWriter = new IndexWriter(indexDir, super.indexWriterConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LogMergePolicy logMergePolicy = new LogByteSizeMergePolicy();
        logMergePolicy.setMergeFactor(super.mergeFactor);
        logMergePolicy.setMaxMergeDocs(super.maxMergeDoc);
        indexWriter.getConfig().setMergePolicy(logMergePolicy);
    }


    private Document buildDocByField(DiscountLucRecord newRecord) {

        // sanitise the record
        newRecord.sanitise();

        Document document = new Document();

        Field idField = buildField(
                DiscountLucRecord.ID, newRecord.getId() + "",
                Optional.of(IndexOptions.DOCS),
                Optional.of(true),
                Optional.empty(),
                Optional.empty(),
                Optional.empty());

        Field titleField = buildField(
                DiscountLucRecord.TITLE, newRecord.getTitle(),
                Optional.of(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS),
                Optional.of(true),
                Optional.of(true),
                Optional.of(true),
                Optional.of(true));

        Field infoField = buildField(
                DiscountLucRecord.INFO, newRecord.getInfoDescription(),
                Optional.of(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS),
                Optional.of(true),
                Optional.of(true),
                Optional.empty(),
                Optional.empty());

        Field mallField = buildField(
                DiscountLucRecord.MALL_NAME, newRecord.getMallName(),
                Optional.of(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS),
                Optional.of(true),
                Optional.of(true),
                Optional.of(true),
                Optional.of(true)
        );

        Field storeNameField = buildField(
                DiscountLucRecord.STORE_NAME, newRecord.getStoreName(),
                Optional.of(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS),
                Optional.of(true),
                Optional.of(true),
                Optional.of(true),
                Optional.of(true)
        );

        Field cityNameField = buildField(
                DiscountLucRecord.CITY_ID, newRecord.getCityId() + "",
                Optional.of(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS),
                Optional.of(true),
                Optional.of(true),
                Optional.of(true),
                Optional.of(true)
        );

        Field categoryField = buildField(
                DiscountLucRecord.CATEGORY, newRecord.getCategory(),
                Optional.of(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS),
                Optional.of(true),
                Optional.of(true),
                Optional.of(true),
                Optional.of(true)
        );

        document.add(idField);
        document.add(titleField);
        document.add(infoField);
        document.add(mallField);
        document.add(storeNameField);
        document.add(cityNameField);
        document.add(categoryField);

        return document;
    }

    /**
     * insert single discount to the index
     */
    public void insert(DiscountLucRecord newRecord) {
        try {
            indexWriter.addDocument(buildDocByField(newRecord));
            indexWriter.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * insert single discount to the index
     */
    public void insert(List<DiscountLucRecord> newRecords) {
        try {
            List<Document> newFields = new ArrayList<>();
            for (DiscountLucRecord nr : newRecords) {
                newFields.add(buildDocByField(nr));
            }

            indexWriter.addDocuments(newFields);
            indexWriter.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

}
