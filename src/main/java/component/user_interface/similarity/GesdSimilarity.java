package component.user_interface.similarity;

/**
 * Created by ercan on 11.05.2017.
 */
public class GesdSimilarity extends SimilarityType {

    @Override
    public double findSimilarity(double[] vec1, double[] vec2) {
        double c = 1.0;
        double gama = 1.0;

        double dotProduct = dotVector(vec1, vec2);
        dotProduct = 1 + Math.exp(-1 * gama * (dotProduct + c));
        double vectorMag = 1 + vectorMagnitude(subtractVector(vec1, vec2));
        double result = vectorMag * dotProduct;

        return 1 / result;
    }

    private double[] subtractVector(double[] vec1, double[] vec2){
        int length = vec1.length;
        double[] subtract = new double[length];

        for(int i = 0; i < length; i++){
            subtract[i] = vec1[i] - vec2[i];
        }

        return subtract;
    }

}
