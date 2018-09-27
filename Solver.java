import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Solver {
	public static int vertices;
	public static int edges;
	public static int k;
	public static boolean[][] isEdge;
	public static boolean[][] inSub;
	public static void main(String[] args) throws IOException{
		FileInputStream f = new FileInputStream(args[0]+".graph");
		Scanner s = new Scanner(f);
		vertices = s.nextInt();
		edges = s.nextInt();
		k = s.nextInt();
		isEdge = new boolean[vertices][vertices];
		for(int i=0;i<vertices;i++){
			for(int j=0;j<vertices;j++){
				isEdge[i][j]=false;
			}
		}
		for(int i=0;i<vertices;i++){
			isEdge[i][i]=true;
		}
		for(int i=0;i<edges;i++){
			int t1=s.nextInt();
			int t2=s.nextInt();
			isEdge[t1-1][t2-1]=true;
			isEdge[t2-1][t1-1]=true;
		}
		s.close();
		FileWriter file = new FileWriter(args[0]+".satinput");
		BufferedWriter bf = new BufferedWriter(file);
		bf.write("p cnf "+(k*vertices)+" "+"5");
		bf.close();
		file.close();
	}
}
