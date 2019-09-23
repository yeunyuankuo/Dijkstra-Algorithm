CSE 373 Summer
August 4, 2017
@Author: Yeun-Yuan(Jessie) Kuo

1) adjacentVertices: O(|V|) because this method only goes through the edges for 
                     one vertex, and the worst case is when all vertices is linked
                     to one another then the run time would be O(|V|).
                     
   edgeCost: O(|V|) because this method only loop through one vertex's edges, 
             and the worst case is when all vertices is linked to one another 
             then the run time would be O(|V|).
             
   shortestPath: O(|V|log|V| + |E|log|V|) because I in this method I assigned a cost 
                 of infinity and previous node of null to all vertices, and that would 
                 be O(|V|). Then I called dijkstra's method that have a while loop to 
                 check if priority queue isn't empty then deleteMin(), which the deleteMin() 
                 worst case would be O(|V|log|V|). Then for every edge of the current vertex 
                 I would check the shortest path and update that, which is O(|E|log|V|). 
                 So the calling of my dijkstra method would be O(|V|log|V| + |E|log|V|).
                 Then I also have to recreate the shortest path of the vertices in order 
                 to return, which worst case is O(|V|). But then the order is reversed so 
                 I called another method to reverse the path to the correct order, and that 
                 would take O(|V|) as well. So by adding them all up I would have 
                 O(|V|log|V| + |E|log|V| + 3|V|), which is also O(|V|log|V| + |E|log|V|).
                 
2) I ensure that the graph abstraction is protected and make sure that my 
   clients wouldn't be able to modify the data I store in anyways by using 
   private fields, copy-in, copy-out, and the immutable concept. So for
   example, when writing the function "Return the collection of vertices of 
   this graph" I made sure to return a copy of my data instead of the keySet
   for the actual Map I'm working with. This copy-out concept is also applied
   through out other functions like "Return the collection of edges of this graph",
   "Return a collection of vertices adjacent to a given vertex v.", etc., basically,
   any return method that involves giving away any sort of access I made a copy for
   it, because I believe it is necessary to ensure that my clients wouldn't 
   "accidently" modify my data. And also to make sure that my field in Classes
   are private and immutable when needed.

3) I tested my code my calculating and drew out the shortest path graph and table
   for every vertex starting from source (SEA), and check if what I output in 
   my program is the same as value and path in my table. Also, I wrote a shorter 
   file with just 3 nodes and 3 edges (one pointing to another node) to test out.

4) Didn't work with a partner.
 
5) A. Created a metroVertex.txt and metroEdge.txt that is a partial representation 
      of Taipei's metro station map data and the shortest path, in this case, means 
      the price difference between each station. 
      
   B. I also used a priority queue to implement dijkstra's algorithm.
   
   C. N/A