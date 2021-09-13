package restaurant;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


class student{
	String Name;
	int Roll;
	float GPA;
	public student(String s, int r, float g) {
		Name = s;
		Roll = r;
		GPA = g;
	}
}
public class cls{
	public static void main(String args[]) {
	
		Scanner scan = new java.util.Scanner(System.in);
		Map<Integer, student> map = new HashMap<Integer, student>();
		for(int i=0;i<5;i++) {
			String s;
			int x;
			float g;
			System.out.println("Enter number-"+(i+1)+" Student Name: ABCD");
			s = "ABCD";
			System.out.println("Enter number-"+(i+1)+" Student Roll: ");
			x = scan.nextInt();
			System.out.println("Enter number-"+(i+1)+" Student GPA: ");
			g = scan.nextFloat();
			student st = new student(s, x, g);
			map.put(i, st);
		}
		
		for(Map.Entry<Integer, student>entry: map.entrySet()) {
			System.out.println("Roll: "+entry.getValue().Roll);
			System.out.println("Name: "+entry.getValue().Name);
			System.out.println("GPA: "+entry.getValue().GPA);
		}
	}
}