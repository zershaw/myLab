package com.cbsys.saleexplore.compos.nlp;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

/**
 * @author Xiangnan Ren
 * @project discountserver
 */
public abstract class POSTaggerContext {
    StanfordCoreNLP coreNLP;

    POSTaggerContext() {
        Properties properties = new Properties();
        properties.put("annotators", "tokenize, ssplit, pos, lemma");

        coreNLP = new StanfordCoreNLP(properties);
    }

}
