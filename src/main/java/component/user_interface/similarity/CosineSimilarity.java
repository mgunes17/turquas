package component.user_interface.similarity;

/**
 * Created by mustafa on 11.05.2017.
 */
public class CosineSimilarity implements SimilarityType {
    public double findSimilarity(double vec1[], double vec2[]) {
        double numerator = dotVector(vec1, vec2);
        double denominator = vectorMagnitude(vec1) * vectorMagnitude(vec2);

        return numerator / denominator;
    }

    //2 vektörün noktasal çarpımı
    private double dotVector(double[] vec1, double[] vec2) {
        double sum = 0.0d;

        //karşılıklı elemanları çarpıp topla
        for(int i = 0 ; i < vec1.length; i++) {
            if(i < vec2.length)
                sum += vec1[i] * vec2[i];
        }

        return sum;
    }

    //bir vektörün boyutunu hesaplar - öklid bağıntısı ile
    private double vectorMagnitude(double[] vec) {
        double sum = 0.0d;

        for(int i = 0; i < vec.length; i++) {
            sum += vec[i] * vec[i];
        }

        return Math.sqrt(sum);
    }
}
