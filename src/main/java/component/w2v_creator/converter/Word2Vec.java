package component.w2v_creator.converter;

import admin.W2VCreatorAdmin;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

/**
 * Created by ercan on 26.04.2017.
 */
public class Word2Vec {

    public void run() throws Exception {

        //String filePath = new ClassPathResource().getFile().getAbsolutePath();
        String filePath = W2VCreatorAdmin.filenameMap.get("source_file");

        SentenceIterator iterator = new BasicLineIterator(filePath);
        TokenizerFactory t = new DefaultTokenizerFactory();

        t.setTokenPreProcessor(new CommonPreprocessor());

        org.deeplearning4j.models.word2vec.Word2Vec vec = new org.deeplearning4j.models.word2vec.Word2Vec.Builder()
                .minWordFrequency(W2VCreatorAdmin.w2vParameterMap.get("min_word_freq"))
                .iterations(W2VCreatorAdmin.w2vParameterMap.get("iteration"))
                .layerSize(W2VCreatorAdmin.w2vParameterMap.get("layer_size"))
                .learningRate(0.025)
                .minLearningRate(1e-2)
                .negativeSample(10)
                .seed(42)
                .windowSize(W2VCreatorAdmin.w2vParameterMap.get("window_size"))
                .iterate(iterator)
                .tokenizerFactory(t)
                .build();
        vec.fit();

        WordVectorSerializer.writeWordVectors(vec, W2VCreatorAdmin.filenameMap.get("target_file"));
    }
}
