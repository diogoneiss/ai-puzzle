// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

public class Main {
    public static void main(String[] args) throws Exception {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        int[] sampleInput = { 1, 2, 3, 4, 0, 5, 7, 8, 6};
        Grid base = new Grid(sampleInput);

        var bfs = new BFS(base);

        var solution = bfs.solve();
        System.out.println(solution);
        bfs.showRunInfo();

    }
}