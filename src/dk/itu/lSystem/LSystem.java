package dk.itu.lSystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import dk.itu.lSystem.util.Canvas;

/**
 * The LSystem class is for implementing your own L-system that can expand
 * n number of times the axiom of the system. And visualize this expansion 
 * utilizing a turtle. 
 * 
 * @author Anders Hartzen
 * 
 * @implemented Alberto Alvarez
 *
 */
public class LSystem {
	
	/**
	 * The Axiom (i.e. start state) for the L-system.
	 */
	public String axiom;
	
	/**
	 * Result of applying production rules during generations
	 */
	public String result;
	
	/**
	 * The production rules for the L-system.
	 */
	public Map<String,String> productionRule = new HashMap<String,String>();
		
	/**
	 *X start position for the turtle. Used for visualization.  
	 */
	public double startx = 100;
	
	/**
	 *Y start position for the turtle. Used for visualization.  
	 */
	public double starty = 100;

	/**
	 *Start angle for the turtle. Used for visualization.  
	 */
	public double startAngle = 0;
	
	/**
	 *The turn angle for the turtle to use when a turn symbol is processed. Used for visualization. 
	 *PI/6 is 30 degrees in radians. 
	 */
	public double turnAngle = Math.PI/6;
	
	/**
	 * Length of each step the turtle takes, when a move forward symbol is processed. Used for visualization.
	 */
	public double length = 5;
	
	Stack<State> TurtleStack = new Stack<State>();
	
	/**
	 * The expand method is used to expand the axiom of the L-system n number of times.
	 * @param depth The number of times to expand the axiom.
	 */
	public void expand(double depth)
	{
		this.result = axiom;
		//this.result = productionRule.get(axiom);
		//Your code here :-)
		for(double i = 0; i < depth; i++)
		{
			String[] final_product = result.split("");
			result = "";
			for(String f : final_product)
			{
				if(f.equals("F"))
				{
					result += productionRule.get(f);
				}
				else
				{
					result += f;
				}
			}
		}
	}
	
	public void MovementAndRotationVertical(double step, double radian_angle)
	{
		startx += step * Math.sin(radian_angle);
		starty += step * Math.cos(radian_angle);
	}
	
	public void MovementAndRotationHorizontal(double step, double radian_angle)
	{
		startx += step * Math.cos(radian_angle);
		starty += step * Math.sin(radian_angle);
	}
	
	/**
	 * After expansion we need the turtle to process the expansion and move around on screen to draw our plant. 
	 * @param C The canvas to draw turtle movement on.
	 */
	public void interpret(Canvas C)
	{
		
		try {
			Thread.sleep(50);
		State state = new State(startx, starty, startAngle, length, turnAngle);
		
		//Your code here :-)
		String[] final_product = result.split("");
		
		for(String f : final_product)
		{

			if(f.equals("F"))
			{
				MovementAndRotationVertical(state.getLength(), state.getAngle());
				state.draw(C, startx, starty);
			}
			else if(f.equals("+"))
			{
				startAngle += turnAngle;
				//Rotate();
			}
			else if(f.equals("-"))
			{
				startAngle -= turnAngle;
				//Rotate();
			}
			else if(f.equals("["))
			{
				TurtleStack.push(state);
			}
			else if(f.equals("]"))
			{
				State s = TurtleStack.pop();
				startx = s.getX();
				starty = s.getY();
				startAngle = s.getAngle();
				turnAngle = s.getTurningAngle();
				length = s.getLength();
			}
			
			state = new State(startx, starty, startAngle, length, turnAngle);
		}

			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//C.drawLine(100, 100, 150, 100);
	}
}
