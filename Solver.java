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
		long start = System.currentTimeMillis( );
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
		// bf.write("p cnf "+(k*vertices)+" "+"5\n");
		int numberVar=0;
		int numberClauses=0;
		String stringToFile="";
		int count=k-1;
		numberVar=k*vertices;
		int check=0;
		// System.out.println("Starting");
		/////--------------------------------------------Getting number of vertices and clauses
		for(int i=0;i<vertices;i++){
			for(int j=i;j<vertices;j++){
				if(isEdge[i][j]==false){
					for(int l=0;l<k;l++){
						numberClauses++;
					}
				}else{
					numberVar+=k;
					numberClauses++;
					for(int l=1;l<=k;l++){
						numberClauses+=2;
					}
				}
			}
		}
		for(int i=0;i<k;i++){
			for(int j=i+1;j<k;j++){
				////printing Z1 Z2 Z3... Zn
				for(int to=0;to<2;to++){
					numberVar+=vertices;
					numberClauses++;
					for(int l=1;l<=vertices;l++){
							numberClauses+=2;
					}
				}
			}
		}
		for(int j=1;j<=vertices;j++){
			numberClauses++;
		}
		bf.write("p cnf "+numberVar+" "+numberClauses+"\n");
		numberVar=k*vertices;
		numberClauses=0;

		//-----------------------------Clauses for the Completeness of graphs  C1---------------------------------
		for(int i=0;i<vertices;i++){
			for(int j=i;j<vertices;j++){
				// if(check%1000==0){
				// System.out.println("check: "+check);
				// }
				// check++;
				if(isEdge[i][j]==false){
					for(int l=0;l<k;l++){
						int firstVar = getID(l,i+1);
						int secondVar = getID(l,j+1);
						firstVar = -1*firstVar;
						secondVar = -1*secondVar;
						bf.write(firstVar+" "+secondVar+" 0"+"\n");
						// stringToFile +=firstVar+" "+secondVar+" 0"+"\n";
						numberClauses++;
					}
				}else{
					count++;
					numberVar+=k;
					String firstLine="";
					for(int l=1;l<=k;l++){
						firstLine += getID(count,l)+" ";
					}
					firstLine+="0";
					// stringToFile+=firstLine+"\n";
					bf.write(firstLine+"\n");
					numberClauses++;
					for(int l=1;l<=k;l++){
						String temp ="";
						temp += (-1*getID(count,l));
						// stringToFile+=temp+" "+getID(l-1,i+1)+" 0\n";
						// stringToFile+=temp+" "+getID(l-1,j+1)+" 0\n";
						bf.write(temp+" "+getID(l-1,j+1)+" 0\n");
						bf.write(temp+" "+getID(l-1,i+1)+" 0\n");
						numberClauses+=2;
					}
				}
			}
		}
		// System.out.println("End of complete");
		//------------------------------End for C1---------------------------------------------------------------
		//-------------------------------Clauses for the Subgraph Conditions C2-------------------------------------
		
		for(int i=0;i<k;i++){
			for(int j=i+1;j<k;j++){
				////printing Z1 Z2 Z3... Zn
				for(int to=0;to<2;to++){
					count++;
					numberVar+=vertices;
					String firstLine="";
					for(int l=1;l<=vertices;l++){
						firstLine += getID(count,l)+" ";
					}
					firstLine+="0";
					// System.out.println(firstLine);
					bf.write(firstLine+"\n");
					// stringToFile+=firstLine+"\n";
					numberClauses++;
					for(int l=1;l<=vertices;l++){
						String temp ="";
						temp+=(-1*getID(count,l));
						if(to==0){
							bf.write(temp+" "+getID(i,l)+" 0\n");
							bf.write(temp+" -"+getID(j,l)+" 0\n");
							// stringToFile+=temp+" "+getID(i,l)+" 0\n";
							// stringToFile+=temp+" -"+getID(j,l)+" 0\n";
							
							numberClauses+=2;
						}else{
							bf.write(temp+" -"+getID(i,l)+" 0\n");
							bf.write(temp+" "+getID(j,l)+" 0\n");
							// stringToFile+=temp+" -"+getID(i,l)+" 0\n";
							// stringToFile+=temp+" "+getID(j,l)+" 0\n";
							numberClauses+=2;
						}
					}
				}
			}
		}
		// System.out.println("Clause part");
		for(int j=1;j<=vertices;j++){
			for(int i=0;i<k;i++){
				// stringToFile+=getID(i,j)+" ";
				bf.write(getID(i,j)+" ");
			}
			bf.write("0\n");
			// stringToFile+="0\n";
			numberClauses++;
		}
		// long now = System.currentTimeMillis( );
		// System.out.println("Time before writing: "+(start-now));
		// bf.write("p cnf "+numberVar+" "+numberClauses+"\n");
		// bf.write(stringToFile);
		// long now1 = System.currentTimeMillis( );
		// System.out.println("Time after writing: "+(start-now1));
		bf.close();
		file.close();
	}
}
