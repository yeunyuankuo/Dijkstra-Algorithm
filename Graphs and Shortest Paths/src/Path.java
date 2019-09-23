
import java.util.List;

public class Path {
    // We use public fields fields here since this very simple class is
    // used only for returning multiple results from shortestPath.
    public List<Vertex> vertices;
    public int cost;
    
    public Path(List<Vertex> vertices, int cost) {
		this.vertices = vertices;
		this.cost = cost;
    }
}