import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Elephants {

    private int numberOfElephants;
    private int[] elephantsWeights;
    private int[] primaryArrangement;
    private int[] finalArrangement;
    private boolean[] visited;
    private int minWeight = Integer.MAX_VALUE;


    public void readElephantData() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String strLine = null;
        try {
            strLine = in.readLine();
            StringTokenizer input = new StringTokenizer(strLine);
            this.numberOfElephants = Integer.parseInt(input.nextToken());

            initializeArrayMembers();

            assignValues(in);
        } catch (IOException e) {
            System.out.println("Error reading from standard input: " + e.getMessage());
        }
    }

    private void initializeArrayMembers() {
        this.elephantsWeights = new int[this.numberOfElephants];
        this.primaryArrangement = new int[this.numberOfElephants];
        this.finalArrangement = new int[this.numberOfElephants];
        this.visited = new boolean[this.numberOfElephants];
        Arrays.fill(this.visited, false);
    }

    private void assignValues(BufferedReader inputBuffer) throws IOException {
        int index = 0;
        StringTokenizer input = new StringTokenizer(inputBuffer.readLine());
        while (index < this.numberOfElephants) {
            this.elephantsWeights[index] = Integer.parseInt(input.nextToken());
            this.minWeight = Math.min(this.minWeight, this.elephantsWeights[index]);
            index++;
        }

        index = 0;
        input = new StringTokenizer(inputBuffer.readLine());
        while (index < this.numberOfElephants) {
            this.primaryArrangement[index] = Integer.parseInt(input.nextToken()) - 1;
            index++;
        }

        index = 0;
        input = new StringTokenizer(inputBuffer.readLine());
        while (index < this.numberOfElephants) {
            this.finalArrangement[Integer.parseInt(input.nextToken()) - 1] = this.primaryArrangement[index];
            index++;
        }

    }


    public void findSolution() {
        long result = 0;
        for (int index = 0; index < this.numberOfElephants; index++) {
            if (!this.visited[index]) {
                int minWeightCycle = Integer.MAX_VALUE;
                long subResult = 0;
                int cycleLen = 0;
                int cycleIndex = index;
                do {
                    minWeightCycle = Math.min(minWeightCycle, elephantsWeights[cycleIndex]);
                    subResult += elephantsWeights[cycleIndex];
                    cycleIndex = finalArrangement[cycleIndex];
                    visited[cycleIndex] = true;
                    cycleLen++;
                } while (cycleIndex != index);
                result += Math.min(subResult + (cycleLen - 2) * (long) minWeightCycle, subResult + minWeightCycle + (cycleLen + 1) * (long) (minWeight));
            }
        }
        System.out.println(result);
    }
}