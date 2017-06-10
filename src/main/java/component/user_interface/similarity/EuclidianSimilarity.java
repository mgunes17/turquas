package component.user_interface.similarity;

/**
 * Created by ercan on 05.06.2017.
 */
public class EuclidianSimilarity extends SimilarityType {
    @Override
    public double findSimilarity(double[] vec1, double[] vec2) {
        double[] subtract = subtractVector(vec1, vec2);
        double magnitude = vectorMagnitude(subtract);

        return 1.0 / (1.0 + magnitude);
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
