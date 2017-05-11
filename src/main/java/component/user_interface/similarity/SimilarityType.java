package component.user_interface.similarity;

/**
 * Created by mustafa on 11.05.2017.
 */
public abstract class SimilarityType {
    public abstract double findSimilarity(double vec1[], double vec2[]);

    double dotVector(double[] vec1, double[] vec2) {
        double sum = 0.0d;

        //karşılıklı elemanları çarpıp topla
        for(int i = 0 ; i < vec1.length; i++) {
            if(i < vec2.length)
                sum += vec1[i] * vec2[i];
        }

        return sum;
    }

    //bir vektörün boyutunu hesaplar - öklid bağıntısı ile
    double vectorMagnitude(double[] vec) {
        double sum = 0.0d;

        for(int i = 0; i < vec.length; i++) {
            sum += vec[i] * vec[i];
        }

        return Math.sqrt(sum);
    }
}
