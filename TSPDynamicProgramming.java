import java.util.*;

public class TSPDynamicProgramming {
    private static final int INF = Integer.MAX_VALUE / 2; // Avoid overflow

    // Solve TSP using DP + Bitmask
    public static int tsp(int[][] dist) {
        int n = dist.length;
        int[][] dp = new int[1 << n][n]; // dp[mask][i] = min cost to reach i with visited mask
        for (int[] row : dp) Arrays.fill(row, INF);

        dp[1][0] = 0; // Start at city 0 with only city 0 visited

        for (int mask = 1; mask < (1 << n); mask++) {
            for (int u = 0; u < n; u++) {
                if ((mask & (1 << u)) != 0) { // If u is visited in mask
                    for (int v = 0; v < n; v++) {
                        if ((mask & (1 << v)) == 0) { // If v is not visited
                            int nextMask = mask | (1 << v);
                            dp[nextMask][v] = Math.min(dp[nextMask][v], dp[mask][u] + dist[u][v]);
                        }
                    }
                }
            }
        }

        // Return to starting city (0)
        int ans = INF;
        for (int u = 1; u < n; u++) {
            ans = Math.min(ans, dp[(1 << n) - 1][u] + dist[u][0]);
        }
        return ans;
    }

    public static void main(String[] args) {
        // Example: 4 cities with distances
        int[][] dist = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };

        int minCost = tsp(dist);
        System.out.println("Minimum TSP cost: " + minCost);
    }
}