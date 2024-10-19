import matplotlib.pyplot as plt
import numpy as np
import matplotlib.patches as patches
import re


def read_maze(file_path):
    """
    Reads the maze configuration from a file.

    Each cell in the maze file is expected to have the format:
    height,N,S,E,W

    Parameters:
        file_path (str): Path to the maze file.

    Returns:
        list: A 2D list representing the maze with cell properties.
    """
    maze = []
    with open(file_path, 'r') as file:
        for line_number, line in enumerate(file, start=1):
            # Strip any leading/trailing whitespace and split by whitespace to get cells
            cells = line.strip().split()
            row = []
            for cell in cells:
                # Split each cell by comma and convert to integers
                parts = cell.split(',')
                if len(parts) != 5:
                    raise ValueError(f"Invalid cell format at line {line_number}: {cell}")
                try:
                    height, N, S, E, W = map(int, parts)
                except ValueError:
                    raise ValueError(f"Non-integer value found at line {line_number}: {cell}")
                row.append({
                    'height': height,
                    'N': N,
                    'S': S,
                    'E': E,
                    'W': W
                })
            maze.append(row)
    return maze


def read_solution(file_path):
    """
    Reads the solution path from a file.

    The solution file should have coordinates in the format:
    (x, y)

    Parameters:
        file_path (str): Path to the solution file.

    Returns:
        list: A list of tuples representing the path coordinates.
    """
    solution = []
    coord_pattern = re.compile(r'\(\s*(\d+)\s*,\s*(\d+)\s*\)')
    with open(file_path, 'r') as file:
        for line_number, line in enumerate(file, start=1):
            match = coord_pattern.match(line.strip())
            if not match:
                raise ValueError(f"Invalid coordinate format at line {line_number}: {line.strip()}")
            x, y = map(int, match.groups())
            solution.append((x, y))
    return solution


def visualize_maze(maze, solutions=None, show_solutions=True):
    """
    Visualizes the maze and optionally overlays multiple solution paths.

    Parameters:
        maze (list): 2D list representing the maze with cell properties.
        solutions (list of dict, optional):
            Each dict should have:
                - 'path': List of tuples representing the solution path.
                - 'color': Color for the solution path.
                - 'label': Label for the solution path in the legend.
        show_solutions (bool, optional): Whether to display the solution paths.
    """
    rows = len(maze)
    cols = len(maze[0]) if rows > 0 else 0

    # Create height map
    height_map = np.array([[cell['height'] for cell in row] for row in maze])

    fig, ax = plt.subplots(figsize=(cols, rows))

    # Display the height map using a colormap
    cax = ax.imshow(height_map, cmap='terrain', origin='upper')

    # Create a colorbar
    cbar = fig.colorbar(cax, ax=ax)
    cbar.set_label('Height')

    # Draw walls
    for i in range(rows):
        for j in range(cols):
            cell = maze[i][j]
            x, y = j, i  # Column corresponds to x-axis, row to y-axis

            # North Wall
            if cell['N'] == 1:
                ax.plot([x, x + 1], [y, y], color='black')

            # South Wall
            if cell['S'] == 1:
                ax.plot([x, x + 1], [y + 1, y + 1], color='black')

            # East Wall
            if cell['E'] == 1:
                ax.plot([x + 1, x + 1], [y, y + 1], color='black')

            # West Wall
            if cell['W'] == 1:
                ax.plot([x, x], [y, y + 1], color='black')

    # Plot the solution paths if provided
    if show_solutions and solutions:
        # Define a list of colors for different solutions
        default_colors = ['red', 'blue', 'green', 'orange', 'purple']
        for idx, solution_info in enumerate(solutions):
            path = solution_info.get('path', [])
            color = solution_info.get('color', default_colors[idx % len(default_colors)])
            label = solution_info.get('label', f'Solution {idx + 1}')

            # Extract x and y coordinates
            path_x = [coord[0] + 0.5 for coord in path]
            path_y = [coord[1] + 0.5 for coord in path]

            ax.plot(path_x, path_y, color=color, linewidth=2, marker='o', markersize=5, label=label)

        ax.legend(loc='upper right')

    # Set grid lines
    ax.set_xticks(np.arange(0, cols + 1, 1))
    ax.set_yticks(np.arange(0, rows + 1, 1))
    ax.grid(True, which='both', color='gray', linewidth=0.5, linestyle='-', alpha=0.3)

    # Invert y-axis to have the origin at the top-left
    #sax.invert_yaxis()

    # Remove axis labels
    ax.set_xticks([])
    ax.set_yticks([])

    plt.title('Maze Height Map Visualization with Multiple Solutions')
    plt.show()


def main():
    """
    Main function to execute the maze visualization with multiple solution paths.
    """
    maze_file_path = 'heights_and_maze.txt'  # Replace with your maze file path
    solution_file_paths = ['solution1.txt', 'solution2.txt']  # Replace with your solution file paths

    try:
        maze = read_maze(maze_file_path)

        # Read multiple solutions
        solutions = []
        colors = ['red', 'blue']  # Define distinct colors for each solution
        labels = ['Solution 1', 'Solution 2']  # Define labels for the legend

        for idx, sol_path in enumerate(solution_file_paths):
            solution = read_solution(sol_path)
            solutions.append({
                'path': solution,
                'color': colors[idx % len(colors)],
                'label': labels[idx] if idx < len(labels) else f'Solution {idx + 1}'
            })

        visualize_maze(maze, solutions=solutions, show_solutions=True)
    except FileNotFoundError as fnf_error:
        print(f"File not found: {fnf_error.filename}")
    except ValueError as ve:
        print(f"Value Error: {ve}")
    except Exception as e:
        print(f"An unexpected error occurred: {e}")


if __name__ == "__main__":
    main()
