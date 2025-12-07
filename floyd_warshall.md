function floydWarshall(graph):
    n = number of vertices
    dist = matrix[n][n]

    # initialize dist with direct edge weights
    for i = 1 to n:
        for j = 1 to n:
            if edge(i, j) exists:
                dist[i][j] = weight(i, j)
            else if i == j:
                dist[i][j] = 0
            else:
                dist[i][j] = ∞

    # main triple loop
    for k = 1 to n:
        for i = 1 to n:
            for j = 1 to n:
                dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])

    return dist
