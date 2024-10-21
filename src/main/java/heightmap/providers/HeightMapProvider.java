package heightmap.providers;

import maze.Node;

/**
 * HeightMapProvider is an interface that defines a method for filling a
 * 2D array of Nodes with heightmap data.
 */
public interface HeightMapProvider {

    /**
     * Fills the provided 2D array of nodes with heightmap data.
     *
     * @param nodes 2D array of Node objects that represent the heightmap
     */
    void fillMap(Node[][] nodes);
}
