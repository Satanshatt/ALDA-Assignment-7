// Ändra inte på paketet
package alda.graph;

import java.util.List;

public interface UndirectedGraph<T> {

    // ToDo: metod för att räkna den totala vikten

    /**
     * Antalet noder i grafen.
     *
     * @return antalet noder i grafen.
     */
    int getNumberOfNodes();

    /**
     * Antalet bågar i grafen.
     *
     * @return antalet bågar i grafen.
     */
    int getNumberOfEdges();

    /**
     * Lägger till en ny nod i grafen.
     *
     * @param newNode
     *            datat för den nya noden som ska läggas till i grafen.
     * @return false om noden redan finns.
     */
    boolean add(T newNode);

    /**
     * Kopplar samman tvä noder i grafen. Eftersom grafen är oriktad så spelar
     * det ingen roll vilken av noderna som står först. Det är också
     * fullständigt okej att koppla ihop en nod med sig själv. Däremot tillåts
     * inte multigrafer. Om två noder kopplas ihop som redan är ihopkopplade
     * uppdateras bara deras kostnadsfunktion.
     *
     * @param node1
     *            den ena noden.
     * @param node2
     *            den andra noden.
     * @param cost
     *            kostnaden för att ta sig mellan noderna. Denna måste vara >0
     *            för att noderna ska kunna kopplas ihop.
     * @return true om bägge noderna finns i grafen och kan kopplas ihop.
     */
    boolean connect(T node1, T node2, int cost);

    /**
     * Berättar om två noder är sammanbundan av en båge eller inte.
     *
     * @param node1
     *            den ena noden.
     * @param node2
     *            den andra noden.
     * @return om noderna är sammanbundna eller inte.
     */
    boolean isConnected(T node1, T node2);

    /**
     * Returnerar kostnaden för att ta sig mellan två noder.
     *
     * @param node1
     *            den ena noden.
     * @param node2
     *            den andra noden.
     * @return kostnaden för att ta sig mellan noderna eller -1 om noderna inte
     *         är kopplade.
     */
    int getCost(T node1, T node2);

    /**
     * Gär en djupet-först-sökning efter en väg mellan två noder.
     *
     * Observera att denna metod inte använder sig av viktinformationen.
     *
     * @param start
     *            startnoden.
     * @param end
     *            slutnoden.
     * @return en lista över alla noder på vägen mellan start- och slutnoden. Om
     *         ingen väg finns är listan tom.
     */
    List<T> depthFirstSearch(T start, T end);

    /**
     * Gär en bredden-först-sökning efter en väg mellan två noder.
     *
     * Observera att denna metod inte använder sig av viktinformationen. Ni ska
     * alltså inte implementera Dijkstra eller A*.
     *
     * @param start
     *            startnoden.
     * @param end
     *            slutnoden.
     * @return en lista över alla noder på vägen mellan start- och slutnoden. Om
     *         ingen väg finns är listan tom.
     */
    List<T> breadthFirstSearch(T start, T end);

    /**
     * Returnerar en ny graf som utgär ett minimalt spännande träd till grafen.
     * Ni kan förutsätta att alla noder ingär i samma graf.
     *
     * @return en graf som representerar ett minimalt spånnande träd.
     */
    UndirectedGraph<T> minimumSpanningTree();
}