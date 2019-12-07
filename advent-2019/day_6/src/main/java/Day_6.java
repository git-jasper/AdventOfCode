import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Day_6 {

	Map<String, Planet> allPlanets = new HashMap<>();
	private int orbits;
	public static void main(String[] args) {
		Day_6 day_6 = new Day_6();
		day_6.calculate();
	}

	private void calculate() {
		List<String> lines = ReadFile.read();
		;
		for (String line : lines) {
			String[] split = line.split("\\)");
			String planetName = split[0];
			String orbitingPlanetName = split[1];
			Planet planet = allPlanets.getOrDefault(planetName, new Planet(planetName));
			Planet orbitedBy =  allPlanets.getOrDefault(orbitingPlanetName, new Planet(orbitingPlanetName));
			planet.addOrbitingPlanet(orbitedBy);
			orbitedBy.notCenter();
			allPlanets.putIfAbsent(planetName, planet);
			allPlanets.putIfAbsent(orbitingPlanetName, orbitedBy);
		}
//		Part 1
//		planets.values().forEach(this::countOrbits);

		List<Planet> you = findOrbitingPlanet(new ArrayList<>(), allPlanets.get("YOU"));
		List<Planet> santa = findOrbitingPlanet(new ArrayList<>(), allPlanets.get("SAN"));
		for (int intersection = 0; intersection < you.size(); intersection++) {
			Planet planet = you.get(intersection);
			if (santa.contains(planet)) {
				int orbitsToSantaFromIntersection = santa.indexOf(planet);
				orbits = orbitsToSantaFromIntersection + intersection;
				break;
			}
		}
		System.out.println(orbits);
	}

	private List<Planet> findOrbitingPlanet(List<Planet> planets, Planet target) {
		allPlanets.values().forEach(planet -> {
			if (planet.surroundedBy.contains(target)) {
				planets.add(planet);
				findOrbitingPlanet(planets, planet);
			}
		});
		return planets;
	}

	private void countOrbits(Planet planet) {
		orbits += planet.surroundedBy.size();
		for (Planet p : planet.surroundedBy) {
			countOrbits(p);
		}
	}

	class Planet {
		String planet;
		List<Planet> surroundedBy = new ArrayList<>();
		boolean center = true;

		public Planet(String planet) {
			this.planet = planet;
		}

		public void addOrbitingPlanet(Planet planet) {
			surroundedBy.add(planet);
		}

		public void notCenter() {
			this.center = false;
		}

		public boolean isCenter(){
			return center;
		}
	}
}
