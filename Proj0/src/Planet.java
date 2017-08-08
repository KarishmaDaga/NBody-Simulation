public class Planet {
	double xxPos;
	double yyPos;
	double xxVel;
	double yyVel;
	double mass;
	String imgFileName;
	public static final double G = 6.67E-11; //Newton's gravitational constant
	
	public double xNetForce;
	public double yNetForce;
	public double xxAccel;
	public double yyAccel;
	
	// Two constructors: Original Planet and copyPlanet.
	
	public Planet(double xP, double yP, double xV, double yV, double m, String img) {
		this.xxPos = xP;
		this.yyPos = yP;
		this.xxVel = xV;
		this.yyVel = yV;
		this.mass = m;
		this.imgFileName = img;
	}
	
	public Planet(Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}
	
	
	/* Creates a non static method where the radius between a planet and another planet is calculated. 
	 * 
	 * @param: Planet
	 * @return: double
	 * */
	public double calcDistance(Planet p) {
		// recall: dist_squared = (x2 - x1)^2 + (y2 - y1)^2
		double d_x= (this.xxPos - p.xxPos);
		double d_y = (this.yyPos - p.yyPos);
		
		// r^2 = dx^2 + dy^2
		double radius_squared = (d_x * d_x) + (d_y * d_y);
		double radius = Math.sqrt(radius_squared);
		
		return radius;
	}
	
	/* This method returns the total force acting between two bodies in space. 
	 * It is based on the equation, F = G*m1*m2 / r^2
	 * 
	 * @param: Planet
	 * @return: double
	 * */
	public double calcForceExertedBy(Planet p) {		
		double radius = this.calcDistance(p);
		double radius_squared = radius * radius;
		
		double force = ((G) * (this.mass) * (p.mass)) / radius_squared;
		return force;
	}
	
	/* This returns the x-component of the force exerted between two planets 
	 * 
	 * @param: Planet
	 * @return: double
	 * */
	public double calcForceExertedByX(Planet p) {	
		
		/* f_x is calculated by f_x = F * (d_x / r) */
		double force = this.calcForceExertedBy(p);
		double radius = this.calcDistance(p);
		double d_x= (this.xxPos - p.xxPos);
		double f_x = force * (d_x / radius);
		
		/* To avoid using math.abs, we check if f_x is neg and correct it. */
		if (f_x < 0) {
			f_x = f_x * -1;
		}
		return f_x;
	}
	
	
	/* This returns the y-component of the force exerted between two planets 
	 * 
	 * @param: Planet
	 * @return: double
	 * */
	public double calcForceExertedByY(Planet p) {
		double f_y;
		/* f_y is calculated by f_y = F * (d_y / r) */
		double force = this.calcForceExertedBy(p);
		double radius = this.calcDistance(p);
		double d_y= (this.yyPos - p.yyPos);

		f_y = force * (d_y / radius);
		
		/* To avoid using math.abs, we check if f_y is negative and correct it. */
		if (f_y < 0) {
			f_y = f_y * -1;
		}
		return f_y;
	}
	
	/* These two methods return the net force acting on a Planet in a system of multiple bodies */
	public double calcNetForceExertedByX(Planet[] planets) {
		double netForceX = 0;
		for (Planet p : planets) {
			if (p == this) {
				continue;
			} else {
				netForceX += calcForceExertedByX(p);
			}
		}
		return netForceX;
	}
	public double calcNetForceExertedByY(Planet[] planets) {
		double netForceY = 0;
		for (Planet p : planets) {
			if (p == this) {
				continue;
			} else {
				netForceY += calcForceExertedByY(p);
			}
		}
		return netForceY;
	}
	
	public void update(double dt, double f_x, double f_y) {
		double xxAccel = f_x / this.mass;
		double yyAccel = f_y / this.mass;
		
		// update velocity in x-component
		this.xxVel = this.xxVel + (xxAccel * dt);
		this.yyVel = this.yyVel + (yyAccel * dt);
		
		// compute new position (displacement in space) by (xxPos + velocity_x * time, yyPos + velocity_y * time
		this.xxPos = this.xxPos + this.xxVel * dt;
		this.yyPos = this.yyPos + this.yyVel * dt;
	}
	
	public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos,"./eclipse-workspace/Proj0/images/starfield.jpg");
	}
}

