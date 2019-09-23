import java.util.Comparator;

/**
 * Comparator method for my path in MyGraph to compare the cost of two vertex.
 * @author jessiekuo
 */
public class CompareState implements Comparator<Path> {
    @Override
    public int compare(Path x, Path y) {
    	return Integer.compare(x.cost, y.cost);
    }
}