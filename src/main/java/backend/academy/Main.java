package backend.academy;

import heightmap.HeightMapProviderFactory;
import heightmap.params.PerlinNoiseParams;
import heightmap.providers.HeightMapProvider;
import heightmap.providers.PerlinNoiseHMProvider;
import heightmap.providers.ProviderType;
import heightmap.providers.RandomHMProvider;
import lombok.experimental.UtilityClass;
import maze.Node;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        PerlinNoiseParams perlinNoiseParams = new PerlinNoiseParams(
            10,
            10.0,
            6,
            0.5,
            2.0
        );

        HeightMapProvider rnp = HeightMapProviderFactory.createProvider(
            ProviderType.PERLIN_NOISE,
            perlinNoiseParams
        );

        Node[][] nodes = new Node[20][20];

        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[0].length; j++) {
                nodes[i][j] = new Node(i, j, 0);
            }
        }

        rnp.fillMap(nodes);

        String outputFilePath = "heights.txt"; // Specify your desired file path

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (int i = 0; i < nodes.length; i++) {
                for (int j = 0; j < nodes[0].length; j++) {
                    writer.write(Integer.toString(nodes[i][j].height()));
                    writer.write(" ");
                }
                writer.write("\n");
                // Remove the newline to have all data in a single line
                // If you want to separate rows without newlines, you can use a different delimiter
            }
            // Optionally, you can add a newline at the end of the file
            // writer.newLine();
            System.out.println("Height map successfully written to " + outputFilePath);
        } catch (IOException e) {
            System.err.println("An error occurred while writing the height map to the file:");
            e.printStackTrace();
        }
    }
}
