package com.cbsys.saleexplore.compos.nlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Xiangnan Ren
 * @project discountserver
 */
@Component
public class POSTagger extends POSTaggerContext {

    private final Set<String> retainSet = new HashSet<>(
            Arrays.asList( "NN", "NNP", "NNPS", "NNS")
    );

    public String lemmatizeByStandfordNLPCore(String input) {
        StringBuilder output = new StringBuilder();
        Annotation lemmatized = new Annotation(input);
        super.coreNLP.annotate(lemmatized);

        List<CoreMap> sentences = lemmatized.get(CoreAnnotations.SentencesAnnotation.class);

        for(CoreMap sentence: sentences) {
            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String lemma = token.getString(CoreAnnotations.LemmaAnnotation.class);
                output.append(lemma).append(" ");
            }
        }

        return output.substring(0, output.length() - 1);
    }

    private CoreMap getPOS(String input) {
        Annotation annotation = new Annotation(input);
        coreNLP.annotate(annotation);

        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        CoreMap posTag = null;

        try {
            posTag = sentences.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posTag;
    }

    public List<String> getTaggedWords(String sentence) {
        CoreMap taggedSentence = getPOS(sentence);
        List<String> extractedKeywords = new ArrayList<>();

        for (int i = 0; i < taggedSentence.get(CoreAnnotations.TokensAnnotation.class).size(); i ++) {
            CoreLabel token = taggedSentence.get(CoreAnnotations.TokensAnnotation.class).get(i);
            String word = token.get(CoreAnnotations.TextAnnotation.class);
            String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
//            String lemmatized = lemmatizeByStandfordNLPCore(word.toLowerCase());

            if (!retainSet.contains(pos)) { continue; }
            extractedKeywords.add(word);
        }
        return extractedKeywords;
    }


}
