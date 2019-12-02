import java.util.List;

public class Day_one {

    public static void main(String[] args) {
        Day_one day_one = new Day_one();
        day_one.calculateFuel();
    }

    private void calculateFuel() {
        List<String> modules = ReadFile.read();
        int fuelCost = modules.stream()
                .mapToInt(this::fuelCost)
                .sum();
        System.out.println("fuel: " + fuelCost);
    }

    private int fuelCost(String massString) {
        int mass = Integer.parseInt(massString);
        int fuelCost = 0;
        while (true) {
            int requiredFuel = Math.floorDiv(mass, 3) - 2;
            if (requiredFuel <= 0) {
                break;
            }
            mass = requiredFuel;
            fuelCost += requiredFuel;
        }
        return fuelCost;
    }
}
