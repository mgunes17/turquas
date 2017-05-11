package component.user_interface.similarity;

/**
 * Created by mustafa on 11.05.2017.
 */
public class CosineSimilarity extends SimilarityType {
    public double findSimilarity(double vec1[], double vec2[]) {
        double numerator = dotVector(vec1, vec2);
        double denominator = vectorMagnitude(vec1) * vectorMagnitude(vec2);

        return numerator / denominator;
    }
}
