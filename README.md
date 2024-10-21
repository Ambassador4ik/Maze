# Maze Generator

Simulates maze solving task on rough terrain. Calculatest cost to move between cells based on height difference between the two. 

- Configure your program at [config.toml](config.toml). The comments will guide you through available options.
- Build the program with `mvn clean package` and run the jar.
- Discover your maze at [maze.png](maze.png) or in console. 

Sample output:
```
Solution cost with TANH: 22.693530795077343
Solution length: 67
+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+
| 0   1   1 |                               |                   |               |
+---+---+   +   +---+   +---+---+   +   +---+   +   +---+---+   +---+   +---+   +
|   | 2   1 |       |   |       |   |           |   |       |       |       |   |
+   +   +---+   +   +   +   +   +---+   +---+---+   +---+   +---+   +   +   +---+
|   | 2 |           |   |   |   |           |       |       |       |   |       |
+   +   +---+   +---+   +   +   +   +---+   +   +---+   +   +   +---+   +   +   +
| 3   2 |       |               |   |       |           |   |   |           |   |
+   +---+   +---+---+---+---+   +   +   +---+   +---+   +---+   +---+---+---+   +
| 3 |       |       |       |   |   |   |   |       |       |           |       |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +---+---+   +   +---+
| 3 |   |       |       |   |   |   |   |       |   |   |       |   |   |       |
+   +   +---+---+---+---+   +   +   +   +---+---+   +   +   +   +   +   +---+   +
| 2 |           | 0  -1  -3     |   |           |   |   |   |       |   |       |
+   +---+   +---+   +---+   +   +   +   +---+   +   +   +   +---+   +   +   +---+
| 2   1   0   0  -1 |-2  -3 |       |       |       |   |   |       |   |       |
+---+---+---+---+---+   +---+---+---+   +   +---+---+   +   +---+---+   +---+   +
|   |           |-2  -2         |       |           |   |           |           |
+   +   +   +---+   +---+   +---+   +---+---+---+   +   +---+---+   +---+---+   +
|       |   |    -2 |   |   |       |           |   |           |           |   |
+---+---+   +   +   +   +   +   +---+   +---+   +   +   +---+---+---+---+   +   +
|           |   |-2  -3 |       |       |       |       |       |           |   |
+   +---+---+   +   +   +---+---+   +   +---+---+---+---+   +   +   +   +   +   +
|       |       |   |-2  -2 |       |   |               |   |   |   |       |   |
+---+   +   +---+---+   +   +---+---+   +   +---+---+   +   +   +   +---+---+   +
|       |   |           |-1 | 0   1   1 |   |                       |           |
+   +---+   +   +   +---+   +   +---+   +   +---+---+---+---+---+   +   +---+---+
|       |           |     0 | 1 |     1 |       |           |       |   |       |
+   +   +---+---+---+   +   +   +---+   +---+   +   +---+   +---+---+   +   +   +
|   |   |               | 0 | 1   1 | 1 |   |           |           |   |   |   |
+   +   +   +---+   +   +   +---+   +   +   +---+---+---+---+---+   +   +---+   +
|   |                   | 0   1   1 | 1 | 0  -1  -1 |       |       |     0  -1 |
+   +   +   +   +   +---+---+---+---+   +   +   +   +   +   +   +   +   +   +   +
|   |       |       |               | 1   0 |   |-1     |       |   |   | 0 |-2 |
+   +---+---+   +   +   +---+---+   +---+---+   +   +---+---+   +---+   +   +   +
|   |           |   |           |       |   |   |-1  -2 |   |   |       |-2 |-2 |
+   +   +   +---+---+---+---+   +   +   +   +   +---+   +   +   +   +---+   +   +
|       |       |               |   |       |   |   |-2 |       |       |-1 |-1 |
+---+   +---+   +   +---+---+---+   +---+---+   +   +   +---+---+---+   +   +   +
|           |                   |                   | 0  -1  -1  -1  -1  -1 | 0 |
+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+   +
```

The numbers represent heights of particular nodes. Graphical representation is probably more illustrative.

## P.S. 
- The program will not launch with incorrect or missing config, resulting in runtime exception. This behaviour **is expected**.
- If you find some parts of the code useless, they are probably not. This project is a simplified verion of a greater idea.
- The path is always found from top left to bottom right corner.
