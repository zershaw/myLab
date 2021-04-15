package com.cbsys.saleexplore.compos.search.builder;

import com.cbsys.saleexplore.compos.search.record.DiscountLucRecord;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.util.Optional;

/**
 * Lucene index builder base
 */
public abstract class LucIndexBuilder {

    private final static Analyzer analyzer = new StandardAnalyzer();
    final int mergeFactor = 10;
    final int maxMergeDoc = Integer.MAX_VALUE;
    IndexWriter indexWriter;
    IndexWriterConfig indexWriterConfig;

    public LucIndexBuilder() {
        indexWriterConfig = initIndexWriterConfig();
    }

    private IndexWriterConfig initIndexWriterConfig() {
        int maxBufferedDoc = 10;
        indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriterConfig.setMaxBufferedDocs(maxBufferedDoc);
        return indexWriterConfig;
    }

    /**
     * Filed for general searching purpose (by similarity/exact matching)
     *
     * @param name:                        the name of the index field
     * @param value:                       the value to add in the field
     * @param indexOptions:                option whether this field needs to be indexed or not
     * @param storeOption:                 option whether this field should be stored or not
     * @param tokenizedOption:             option whether this field should be tokenized or not
     * @param storeTermVectorOption:       option whether this field store term vector
     * @param storeTermVectorOffsetOption: option whether this field store the term vector offset
     * @return: return index field
     */
    Field buildField(String name,
                     String value,
                     Optional<IndexOptions> indexOptions,
                     Optional<Boolean> storeOption,
                     Optional<Boolean> tokenizedOption,
                     Optional<Boolean> storeTermVectorOption,
                     Optional<Boolean> storeTermVectorOffsetOption) {
        FieldType fieldType = new FieldType();
        fieldType.setOmitNorms(true);

        indexOptions.ifPresent(fieldType::setIndexOptions);
        storeOption.ifPresent(fieldType::setStored);
        tokenizedOption.ifPresent(fieldType::setTokenized);
        storeTermVectorOption.ifPresent(fieldType::setStoreTermVectors);
        storeTermVectorOffsetOption.ifPresent(fieldType::setStoreTermVectorOffsets);

        return new Field(name, value, fieldType);
    }

    /**
     * Field with numerical value for exact matching search
     *
     * @param name:  the name of the field
     * @param value: numerical value to add in the field
     * @return: built-on field to be added into index
     */
    NumericDocValuesField buildNumericField(String name,
                                            long value) {
        return new NumericDocValuesField(name, value);
    }

    /**
     * Field with String value for exact matching search
     *
     * @param name:  the name of the field
     * @param value: string value to add in the field
     * @return: built-on field to be added into index
     */
    StringField buildStringField(String name,
                                 String value) {
        return new StringField(name, value, Field.Store.YES);
    }

    public void buildStrFieldIndex() {
    }

    public void buildFieldIndex() {
    }

    public void contentPrettyPrinter(Directory indexDir) throws IOException {
        IndexReader reader = DirectoryReader.open(indexDir);
        final Fields fields = MultiFields.getFields(reader);

        for (String field : fields) {
            final Terms terms = MultiFields.getTerms(reader, field);
            final TermsEnum it = terms.iterator();
            BytesRef term = it.next();
            while (term != null) {
                System.out.println(term.utf8ToString());
                term = it.next();
            }
        }
    }

    /**
     * delete the entire index
     */
    public void deleteAll() {
        try {
            indexWriter.deleteAll();
            indexWriter.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * delete single document by id
     */
    public void deleteById(long id) {
        try {
            Term term = new Term(DiscountLucRecord.ID, id + "");
            indexWriter.deleteDocuments(term);
            indexWriter.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
