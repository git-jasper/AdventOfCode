import java.util.ArrayList;
import java.util.List;

public class Day_seven {

    private int input = 0;
    private int firstInput = 0;
    private Integer AMP_A_INDEX = 0;
    private Integer AMP_B_INDEX = 0;
    private Integer AMP_C_INDEX = 0;
    private Integer AMP_D_INDEX = 0;
    private Integer AMP_E_INDEX = 0;
    List<Integer> outputs = new ArrayList<>();
    boolean looping = true;

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
                            List<Integer> AMP_A = new ArrayList<>(original);
                            List<Integer> AMP_B = new ArrayList<>(original);
                            List<Integer> AMP_C = new ArrayList<>(original);
                            List<Integer> AMP_D = new ArrayList<>(original);
                            List<Integer> AMP_E = new ArrayList<>(original);
                            AMP_A_INDEX = 0;
                            AMP_B_INDEX = 0;
                            AMP_C_INDEX = 0;
                            AMP_D_INDEX = 0;
                            AMP_E_INDEX = 0;
                            input = 0;
                            firstInput = ampA;
                            AMP_A_INDEX = runAmp(AMP_A, AMP_A_INDEX);
                            firstInput = ampB;
                            AMP_B_INDEX = runAmp(AMP_B, AMP_B_INDEX);
                            firstInput = ampC;
                            AMP_C_INDEX = runAmp(AMP_C, AMP_C_INDEX);
                            firstInput = ampD;
                            AMP_D_INDEX = runAmp(AMP_D, AMP_D_INDEX);
                            firstInput = ampE;
                            AMP_E_INDEX = runAmp(AMP_E, AMP_E_INDEX);
                            while(looping) {
                                AMP_A_INDEX = runAmp(AMP_A, AMP_A_INDEX);
                                AMP_B_INDEX = runAmp(AMP_B, AMP_B_INDEX);
                                AMP_C_INDEX = runAmp(AMP_C, AMP_C_INDEX);
                                AMP_D_INDEX = runAmp(AMP_D, AMP_D_INDEX);
                                AMP_E_INDEX = runAmp(AMP_E, AMP_E_INDEX);
                            }
                            looping = true;
                            outputs.add(input);
                        }
                    }
                }
            }
        }
        int max = outputs.stream().mapToInt(output -> output).max().getAsInt();
        System.out.println(max);
    }

    private int runAmp(List<Integer> code, Integer amp_index) {
        boolean halt = false;
        boolean firstLoop = true;
        int startIndex = amp_index;
        for (int i = startIndex; i <= code.size();) {
            String instruction = getInstructionCode(code, i);
            String mode = instruction.substring(0, 3);
            switch (instruction.substring(4)) {
                case "1":
                    opCode1(code, i, mode);
                    i += 4;
                    break;
                case "2":
                    opCode2(code, i, mode);
                    i += 4;
                    break;
                case "3":
                    if(i == 0) {
                        opCode3(code, i, firstInput);
                    } else if (firstLoop){
                        opCode3(code, i, input);
                        firstLoop = false;
                    } else {
                        amp_index = i;
                        halt = true;
                    }
                    i += 2;
                    break;
                case "4":
                    opCode4(code, i, mode);
                    i += 2;
                    break;
                case "5":
                    i = opCode5(code, i, mode);
                    break;
                case "6":
                    i = opCode6(code, i, mode);
                    break;
                case "7":
                    opCode7(code, i, mode);
                    i += 4;
                    break;
                case "8":
                    opCode8(code, i, mode);
                    i += 4;
                    break;
                case "9":
                    halt = true;
                    looping = false;
                    break;
                default:
                    throw new IllegalStateException("Unknown opCode");
            }
            if (halt) {
                break;
            }
        }
        return amp_index;
    }

    private String getInstructionCode(List<Integer> code, int i) {
        String opcode = "0000" + String.valueOf(code.get(i));
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
}
