package com.cbsys.saleexplore.compos.search.builder;

import com.cbsys.saleexplore.config.AppProperties;
import com.cbsys.saleexplore.compos.search.record.StoreLucRecord;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.LogByteSizeMergePolicy;
import org.apache.lucene.index.LogMergePolicy;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Xiangnan Ren
 * @project discountserver
 */
@Component
public class StoreLucIndexBuilder extends LucIndexBuilder {

    /**
     * Constructor, takes the index directory of the store as input.
     * If the directory doesn't exit. it will be created automatically
     */
    public StoreLucIndexBuilder(AppProperties appProperties) {
        try {
            Directory indexDir = FSDirectory.open(Paths.get(appProperties.getStoreLuceneIndexDir()));
            indexWriter = new IndexWriter(indexDir, super.indexWriterConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LogMergePolicy logMergePolicy = new LogByteSizeMergePolicy();
        logMergePolicy.setMergeFactor(super.mergeFactor);
        logMergePolicy.setMaxMergeDocs(super.maxMergeDoc);
        indexWriter.getConfig().setMergePolicy(logMergePolicy);
    }


    private Document buildDocByField(StoreLucRecord newRecord) {

        newRecord.sanitise();

        Document document = new Document();
        Field idField = buildField(
                StoreLucRecord.ID, newRecord.getId() + "",
                Optional.of(IndexOptions.DOCS),
                Optional.of(true),
                Optional.empty(),
                Optional.empty(),
                Optional.empty());

        Field storeNameField = buildField(
                StoreLucRecord.NAME, newRecord.getStoreName(),
                Optional.of(IndexOptions.DOCS),
                Optional.of(true),
                Optional.empty(),
                Optional.empty(),
                Optional.empty());

        Field infoField = buildField(
                StoreLucRecord.INFO, newRecord.getInfoDescription(),
                Optional.of(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS),
                Optional.of(true),
                Optional.of(true),
                Optional.of(true),
                Optional.of(true));

        Field mallNameField = buildField(
                StoreLucRecord.MALL_NAME, newRecord.getMallName(),
                Optional.of(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS),
                Optional.of(true),
                Optional.of(true),
                Optional.empty(),
                Optional.empty());

        Field categoryField = buildField(
                StoreLucRecord.CATEGORY, newRecord.getCategory(),
                Optional.of(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS),
                Optional.of(true),
                Optional.of(true),
                Optional.of(true),
                Optional.of(true)
        );

        Field brandField = buildField(
                StoreLucRecord.BRAND, newRecord.getBrand(),
                Optional.of(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS),
                Optional.of(true),
                Optional.of(true),
                Optional.of(true),
                Optional.of(true)
        );


        Field cityNameField = buildField(
                StoreLucRecord.CITY_ID, newRecord.getCityId() + "",
                Optional.of(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS),
                Optional.of(true),
                Optional.of(true),
                Optional.of(true),
                Optional.of(true)
        );

        document.add(idField);
        document.add(storeNameField);
        document.add(infoField);
        document.add(mallNameField);
        document.add(categoryField);
        document.add(brandField);
        document.add(cityNameField);

        return document;
    }


    /**
     * insert single stores to the index
     */
    public void insert(StoreLucRecord newRecord) {
        try {
            indexWriter.addDocument(buildDocByField(newRecord));

            indexWriter.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * insert list of store records to the index
     */
    public void insert(List<StoreLucRecord> newRecords) {
        try {
            List<Document> newFields = new ArrayList<>();
            for (StoreLucRecord nr : newRecords) {
                newFields.add(buildDocByField(nr));
            }

            indexWriter.addDocuments(newFields);
            indexWriter.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
