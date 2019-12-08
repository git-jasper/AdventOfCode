import java.util.List;

public class Day_five {

    public static void main(String[] args) {
        Day_five day_five = new Day_five();
        day_five.run();
    }
    
    private void run() {
        List<Integer> code = ReadFile.read();
        boolean halt = false;
        int input = 5;
        for (int i = 0; i <= code.size();) {
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
                    opCode3(code, i, input);
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
                    break;
                default:
                    throw new IllegalStateException("Unknown opCode");
            }
            if (halt) {
                break;
            }
        }
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
        int output = getFirstParameter(code, i, mode);
        System.out.println(output);
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
