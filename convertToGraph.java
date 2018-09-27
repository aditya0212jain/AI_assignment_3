import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class convertToGraph{
	public static int vertices;
	public static int edges;
    public static int k;
    
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
        s.close();
        f.close();
        FileInputStream f2 = new FileInputStream(args[0]+".satoutput");
        Scanner s2 = new Scanner(f2);
        // System.out.println(s2.nextLine());
        String stemp = s2.nextLine();
        if(stemp.equals("SAT")){
        int count=0;
        String finalToFile="";
        String toFile="";
        String toFileTemp="";
        int currentGraph=0;
        int numberOfVertices=0;
        while(count<=(vertices*k)){
            count++;
            if(getI(count)!=currentGraph){
                toFile+="#"+(currentGraph+1)+" "+numberOfVertices+"\n";
                toFile+=toFileTemp+"\n";
                currentGraph=getI(count);
                numberOfVertices=0;
                toFileTemp="";
            }
            int temp = s2.nextInt();
            if(temp>0){
                numberOfVertices++;
                toFileTemp+=getV(temp)+" ";
            }
        }
        FileWriter file = new FileWriter(args[0]+".subgraphs");
        BufferedWriter bf = new BufferedWriter(file);
        bf.write(toFile);
        bf.close();
        file.close();
        }else{
            FileWriter file = new FileWriter(args[0]+".subgraphs");
        BufferedWriter bf = new BufferedWriter(file);
        bf.write("0");
        bf.close();
        file.close();
        }   
    }


}