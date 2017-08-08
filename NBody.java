public class NBody {

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		
		double radius = readRadius(filename);
		Planet[] planets = readPlanets(filename);
		
		// Drawing the background
		StdDraw.setScale(-radius, radius);
		StdDraw.clear();
		
		for (Planet p : planets) {
			p.draw();
		}
		
		//Animation
		
		double time = 0;
		while (time != T) {
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];
		
			for (int i = 0; i < planets.length; i++) {
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
			}
			for (int i = 0; i < planets.length; i++) {
				planets[i].update(dt, xForces[i], yForces[i]);
			}
		}
		StdDraw.picture(0, 0, "./images/starfield.jpg");
		
		 StdOut.printf("%d\n", planets.length);
	        StdOut.printf("%.2e\n", radius);
	        for (int i = 0; i < planets.length; i++) {
	            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
	                planets[i].xxPos, planets[i].yyPos, planets[i].xxVel, planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
	        }
	}

	public static double readRadius(String planetsTxtPath) {
		// read the file containing information about the system
		double correct_radius = 2.50e+11;
		In in = new In(planetsTxtPath);
		double radius = in.readDouble();
		
		if (radius != correct_radius) {
			System.out.println("The radius of the universe is incorrect. Please submit a file with the correct value.");
		}
	return radius;
	}
	
	public static Planet[] readPlanets(String planetsTxtPath) {
		//return array of planets
		In in = new In(planetsTxtPath);
        int N = in.readInt();
        Planet[] planets = new Planet[N];
        int i = 0;      
        // ensures we read only relevant lines
        while(i != N) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            planets[i] = new Planet(xP, yP, xV, yV, m, img);
            i += 1;
        }
        return planets;
		}
	}

