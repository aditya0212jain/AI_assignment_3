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

	static int getI(int b){
		return (b-1)/vertices;
	}

	static int getV(int b){
		if(b%vertices==0){
			return vertices;
		}else{
			return b%vertices;
		}
	}

	static int getID(int i,int v){
		return (i*vertices+v);
	}

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
		bf.write("p cnf "+(k*vertices)+" "+"5\n");
		//-----------------------------Clauses for the Completeness of graphs---------------------------------
		for(int i=0;i<vertices;i++){
			for(int j=i;j<vertices;j++){
				if(isEdge[i][j]==false){
					for(int l=0;l<k;l++){
						int firstVar = getID(l,i+1);
						int secondVar = getID(l,j+1);
						firstVar = -1*firstVar;
						secondVar = -1*secondVar;
						bf.write(firstVar+" "+secondVar+" 0"+"\n");
					}
				}
			}
		}


		bf.close();
		file.close();
	}
}
