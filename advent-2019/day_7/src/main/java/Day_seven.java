import java.util.ArrayList;
import java.util.List;

public class Day_seven {

    private int input;
    List<Integer> outputs = new ArrayList<>();

    public static void main(String[] args) {
        Day_seven day_seven = new Day_seven();
        day_seven.run();
    }

    private void run() {
        List<Integer> original = ReadFile.read();
        int minPhase = 5;
        int maxPhase = 10;
        for(int ampA = minPhase; ampA < maxPhase; ampA++) {
            for(int ampB = minPhase; ampB < maxPhase; ampB++) {
                if (ampB == ampA) {
                    continue;
                }
                for(int ampC = minPhase; ampC < maxPhase; ampC++) {
                    if (ampC == ampA || ampC == ampB) {
                        continue;
                    }
                    for(int ampD = minPhase; ampD < maxPhase; ampD++) {
                        if (ampD == ampA || ampD == ampB || ampD == ampC) {
                            continue;
                        }
                        for(int ampE = minPhase; ampE < maxPhase; ampE++) {
                            if (ampE == ampA || ampE == ampB || ampE == ampC || ampE == ampD) {
                                continue;
                            }
                            Program AMP_A = new Program(new ArrayList<>(original), ampA);
                            Program AMP_B = new Program(new ArrayList<>(original), ampB);
                            Program AMP_C = new Program(new ArrayList<>(original), ampC);
                            Program AMP_D = new Program(new ArrayList<>(original), ampD);
                            Program AMP_E = new Program(new ArrayList<>(original), ampE);

                            input = 0;
                            while(!AMP_A.terminate) {
                                run(AMP_A);
                                run(AMP_B);
                                run(AMP_C);
                                run(AMP_D);
                                run(AMP_E);
                            }
                            outputs.add(input);
                        }
                    }
                }
            }
        }
        int max = outputs.stream().mapToInt(output -> output).max().getAsInt();
        System.out.println(max);
        System.out.println("correct answer = 1336480");
    }

    private void run(Program program) {
        boolean firstLoop = true;
        int startIndex = program.pointer;
        List<Integer> instructions = program.instructions;
        running: {
            for (int i = startIndex; i <= instructions.size();) {
                String instruction = getInstructionCode(instructions, i);
                String mode = instruction.substring(0, 3);
                switch (instruction.substring(3)) {
                    case "01":
                        opCode1(instructions, i, mode);
                        i += 4;
                        break;
                    case "02":
                        opCode2(instructions, i, mode);
                        i += 4;
                        break;
                    case "03":
                        if(i == 0) {
                            opCode3(instructions, i, program.phase);
                        } else if (firstLoop){
                            opCode3(instructions, i, input);
                            firstLoop = false;
                        } else {
                            program.pointer = i;
                            break running;
                        }
                        i += 2;
                        break;
                    case "04":
                        opCode4(instructions, i, mode);
                        i += 2;
                        break;
                    case "05":
                        i = opCode5(instructions, i, mode);
                        break;
                    case "06":
                        i = opCode6(instructions, i, mode);
                        break;
                    case "07":
                        opCode7(instructions, i, mode);
                        i += 4;
                        break;
                    case "08":
                        opCode8(instructions, i, mode);
                        i += 4;
                        break;
                    case "99":
                        program.terminate = true;
                        break running;
                    default:
                        throw new IllegalStateException("Unknown opCode");
                }
            }
        }
    }

    private String getInstructionCode(List<Integer> code, int i) {
        String opcode = "0000" + code.get(i);
        opcode = opcode.substring(opcode.length()-5);
        return opcode;
    }

    private Integer getFirstParameter(List<Integer> code, int i, String mode) {
        return "0".equals(mode.substring(2)) ? code.get(code.get(i+1)) : code.get(i+1);
    }

    private Integer getSecondParameter(List<Integer> code, int i, String mode) {
        return "0".equals(mode.substring(1,2)) ? code.get(code.get(i+2)) : code.get(i+2);
    }

    private void opCode1(List<Integer> code, int i, String mode) {
        int one = getFirstParameter(code, i, mode);
        int two = getSecondParameter(code, i, mode);
        code.set(code.get(i+3), one + two);
    }

    private void opCode2(List<Integer> code, int i, String mode) {
        int one = getFirstParameter(code, i, mode);
        int two = getSecondParameter(code, i, mode);
        code.set(code.get(i+3), one * two);
    }

    private void opCode3(List<Integer> code, int i, int input) {
        code.set(code.get(i+1), input);
    }

    private void opCode4(List<Integer> code, int i, String mode) {
        input = getFirstParameter(code, i, mode);
    }

    private int opCode5(List<Integer> code, int i, String mode) {
        int one = getFirstParameter(code, i, mode);
        int two = getSecondParameter(code, i, mode);
        if (one != 0) {
            return two;
        } else {
            return i + 3;
        }
    }

    private int opCode6(List<Integer> code, int i, String mode) {
        int one = getFirstParameter(code, i, mode);
        int two = getSecondParameter(code, i, mode);
        if (one == 0) {
            return two;
        } else {
            return i + 3;
        }
    }

    private void opCode7(List<Integer> code, int i, String mode) {
        int first = getFirstParameter(code, i, mode);
        int second = getSecondParameter(code, i, mode);
        int third = code.get(i+3);
        if (first < second) {
            code.set(third, 1);
        } else {
            code.set(third, 0);
        }
    }

    private void opCode8(List<Integer> code, int i, String mode) {
        int first = getFirstParameter(code, i, mode);
        int second = getSecondParameter(code, i, mode);
        int third = code.get(i+3);
        if (first == second) {
            code.set(third, 1);
        } else {
            code.set(third, 0);
        }
    }

    class Program {
        List<Integer> instructions;
        int phase;
        int pointer;
        boolean terminate;

        public Program(List<Integer> instructions, int phase) {
            this.instructions = instructions;
            this.phase = phase;
        }
    }
}
