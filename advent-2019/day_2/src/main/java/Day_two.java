import java.util.ArrayList;
import java.util.List;

public class Day_two {

    public static void main(String[] args) {
        Day_two day_two = new Day_two();
        day_two.run();

    }
    
    private void run() {
        List<Integer> original = ReadFile.read();
        boolean compute = true;
        while (compute) {
            for (int noun = 0; noun <= 99; noun++) {
                for (int verb = 0; verb <= 99; verb++) {
                    List<Integer> code = new ArrayList<>(original);
                    code.set(1, noun);
                    code.set(2, verb);
                    boolean halt = false;
                    for (int i = 0; i <= code.size(); i += 4) {
                        switch (code.get(i)) {
                            case 1:
                                opCode1(code, i);
                                break;
                            case 2:
                                opCode2(code, i);
                                break;
                            case 99:
                                halt = true;
                                break;
                            default:
                                throw new IllegalStateException("Unknown opCode");
                        }
                        if (halt) {
                            if (code.get(0) == 19690720) {
                                System.out.println("noun: " +noun);
                                System.out.println("verb: " +verb);
                                compute = false;
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    private void opCode1(List<Integer> code, int i) {
        int one = code.get(code.get(i+1));
        int two = code.get(code.get(i+2));
        code.set(code.get(i+3), one + two);
    }

    private void opCode2(List<Integer> code, int i) {
        int one = code.get(code.get(i+1));
        int two = code.get(code.get(i+2));
        code.set(code.get(i+3), one * two);
    }
}
