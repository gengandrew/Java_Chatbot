// Basic Chatbot utilizing fundamential NLP concepts, Note that
// training is required from a basic corpus

import java.util.*;
import java.io.*;

public class Chatbot{
    private static String filename = "";	//corpus filename
    private static ArrayList<Integer> readCorpus(){
        ArrayList<Integer> corpus = new ArrayList<Integer>();
        try{
            File f = new File(filename);
            Scanner sc = new Scanner(f);
            while(sc.hasNext()){
                if(sc.hasNextInt()){
                    int i = sc.nextInt();
                    corpus.add(i);
                }
                else{
                    sc.next();
                }
            }
        }
        catch(FileNotFoundException ex){
            System.out.println("File Not Found.");
        }
        return corpus;
    }
    
    private static int[] counter2(ArrayList<Integer> corpus) {
    	int[] output = new int[4700];
    	for(int i = 0; i < corpus.size(); i++) {
    		output[corpus.get(i)]++;
    	}
    	return output;
    }
    
    private static int[][] counter(ArrayList<Integer> corpus) {
    	int[][] output = new int[4700][4700];
    	for(int i = 0; i < corpus.size() - 1; i++) {
    		output[corpus.get(i)][corpus.get(i+1)]++;
    	}
    	return output;
    }
    
    private static int[] counter3(ArrayList<Integer> corpus, int h1, int h2){
        int output[] = new int[4700];
    	for(int i = 0; i < corpus.size() - 2; i++) {
        	if(corpus.get(i) == h1 && corpus.get(i+1) == h2) {
        		output[corpus.get(i+2)]++;
        	}
        }
        return output;
    }
    
    static public void main(String[] args){
        ArrayList<Integer> corpus = readCorpus();
		int flag = Integer.valueOf(args[0]);
		
        if(flag == 100){
			int w = Integer.valueOf(args[1]);
            int count = 0;
            for(int i = 0; i < corpus.size(); i++) {
            	if(corpus.get(i) == w) {
            		count++;
            	}
            }
            System.out.println(count);
            System.out.println(String.format("%.7f",count/(double)corpus.size()));
        }
        else if(flag == 200){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            double r = (double)n1/n2;
            double output = 0;
            double placeHolderOutput = 0.0;
            double output2 = 0;
            double placeHolderOutput2 = 0.0;
            int outIndex = -1;
            int[] myArray = Chatbot.counter2(corpus);
            for(int i = 1; i < 4701; i++) {      	
            	for(int j = 0; j < i; j++) {
            		int count2 = myArray[j];
            		placeHolderOutput2 = count2/(double)corpus.size();
            		if(j < i -1) {
            			int count = count2;
            			placeHolderOutput = count/(double)corpus.size();
            		}
            	}
            	output = output + placeHolderOutput;
            	output2 = placeHolderOutput2 + output2;
            	if(r > output && r <= output2) {
            		outIndex = i - 1;
            		break;
            	} else if(r == 0 && output2 != 0) {
            		outIndex = i - 1;
            		break;
            	}
            }
            System.out.println(outIndex);
            System.out.println(String.format("%.7f",output));
            System.out.println(String.format("%.7f",output2));
        }
        else if(flag == 300){
            int h = Integer.valueOf(args[1]);
            int w = Integer.valueOf(args[2]);
            int count = 0;
            ArrayList<Integer> words_after_h = new ArrayList<Integer>();
            for(int i = 0; i < corpus.size() - 1; i++) {
            	if(corpus.get(i) == h) {
            		if(corpus.get(i+1) == w) {
            			count++;
            		}
            		words_after_h.add(i+1);
            	}
            }
            System.out.println(count);
            System.out.println(words_after_h.size());
            System.out.println(String.format("%.7f",count/(double)words_after_h.size()));
        }
        else if(flag == 400){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            int h = Integer.valueOf(args[3]);        
            double r = (double)n1/n2;
            double output = 0;
            double placeHolderOutput = 0.0;
            double output2 = 0;
            double placeHolderOutput2 = 0.0;
            int outIndex = -1;
            int[] myArray = Chatbot.counter2(corpus);
            int[][] myArray2 = Chatbot.counter(corpus);
            
            for(int i = 1; i < 4701; i++) {      	
            	for(int j = 0; j < i; j++) {
            		int countA2 = myArray[h];
            		int countB2 = myArray2[h][j];
            		if(countA2 == 0) {
            			placeHolderOutput2 = 0;
            		}else {
            			placeHolderOutput2 = countB2/(double)countA2;
            		}
            		if(j < i -1) {
            			int count = countA2;
            			int countB = countB2;
            			if(count == 0) {
            				placeHolderOutput = 0;
            			}else {
            				placeHolderOutput = countB/(double)count;
            			}
            		}
            	}
            	output = output + placeHolderOutput;
            	output2 = placeHolderOutput2 + output2;
            	if(r > output && r <= output2) {
            		outIndex = i - 1;
            		break;
            	}
            	if(r == 0 && output2 != 0) {
            		outIndex = i - 1;
            		break;
            	}
            }
            System.out.println(outIndex);
            System.out.println(String.format("%.7f",output));
            System.out.println(String.format("%.7f",output2));
        }
        else if(flag == 500){
            int h1 = Integer.valueOf(args[1]);
            int h2 = Integer.valueOf(args[2]);
            int w = Integer.valueOf(args[3]);
            int count = 0;
            ArrayList<Integer> words_after_h1h2 = new ArrayList<Integer>();

            for(int i = 0; i < corpus.size() - 2; i++) {
            	if(corpus.get(i) == h1 && corpus.get(i+1) == h2) {
            		if(corpus.get(i+2) == w) {
            			count++;
            		}
            		words_after_h1h2.add(i);
            	}
            }
            
            System.out.println(count);
            System.out.println(words_after_h1h2.size());
            if(words_after_h1h2.size() == 0)
                System.out.println("undefined");
            else
                System.out.println(String.format("%.7f",count/(double)words_after_h1h2.size()));
        }
        else if(flag == 600){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            int h1 = Integer.valueOf(args[3]);
            int h2 = Integer.valueOf(args[4]);            
            double r = (double)n1/n2;
            double output = 0;
            double placeHolderOutput = 0.0;
            double output2 = 0;
            double placeHolderOutput2 = 0.0;
            int outIndex = -1;
            int[][] myArray2 = Chatbot.counter(corpus);
            int[] myArray3 = Chatbot.counter3(corpus, h1, h2);
            for(int i = 1; i < 4701; i++) {      	
            	for(int j = 0; j < i; j++) {
            		int countA2 = myArray2[h1][h2];
            		int countB2 = myArray3[j];
            		if(countA2 == 0) {
            			placeHolderOutput2 = 0;
            		}else {
            			placeHolderOutput2 = countB2/(double)countA2;
            		}
            		if(j < i -1) {
            			int count = countA2;
            			int countB = countB2;
            			if(count == 0) {
            				placeHolderOutput = 0;
            			}else {
            				placeHolderOutput = countB/(double)count;
            			}
            		}
            	}
            	output = output + placeHolderOutput;
            	output2 = placeHolderOutput2 + output2;
            	if(r > output && r <= output2) {
            		outIndex = i - 1;
            		break;
            	}else if(r== 0 && output2 != 0.0) {
            		outIndex = i - 1;
            		break;
            	}
            }
            if(outIndex == -1) {
            	System.out.println("undefined");
            }else {
            	System.out.println(outIndex);
                System.out.println(String.format("%.7f",output));
                System.out.println(String.format("%.7f",output2));
            }
        }
        else if(flag == 700){
            int seed = Integer.valueOf(args[1]);
            int t = Integer.valueOf(args[2]);
            int h1=0,h2=0;

            Random rng = new Random();
            if (seed != -1) rng.setSeed(seed);

            if(t == 0){
                double r = rng.nextDouble();
                double output = 0;
                double placeHolderOutput = 0.0;
                double output2 = 0;
                double placeHolderOutput2 = 0.0;
                int[] myArray = Chatbot.counter2(corpus);
                for(int i = 1; i < 4701; i++) {      	
                	for(int j = 0; j < i; j++) {
                		int count2 = myArray[j];
                		placeHolderOutput2 = count2/(double)corpus.size();
                		if(j < i -1) {
                			int count = count2;
                			placeHolderOutput = count/(double)corpus.size();
                		}
                	}
                	output = output + placeHolderOutput;
                	output2 = placeHolderOutput2 + output2;
                	if(r > output && r <= output2) {
                		h1 = i - 1;
                		break;
                	}
                	else if(r == 0 && output2 != 0.0) {
                		h1 = i - 1;
                		break;
                	}
                }
                
                System.out.println(h1);
                if(h1 == 9 || h1 == 10 || h1 == 12){
                    return;
                }

                r = rng.nextDouble();
                output = 0;
                placeHolderOutput = 0.0;
                output2 = 0;
                placeHolderOutput2 = 0.0;
                int[][] myArray2 = Chatbot.counter(corpus);
                for(int i = 1; i < 4701; i++) {      	
                	for(int j = 0; j < i; j++) {
                		int countA2 = myArray[h1];
                		int countB2 = myArray2[h1][j];
                		if(countA2 == 0) {
                			placeHolderOutput2 = 0;
                		}else {
                			placeHolderOutput2 = countB2/(double)countA2;
                		}
                		if(j < i -1) {
                			int count = countA2;
                			int countB = countB2;
                			if(count == 0) {
                				placeHolderOutput = 0;
                			}else {
                				placeHolderOutput = countB/(double)count;
                			}
                		}
                	}
                	output = output + placeHolderOutput;
                	output2 = placeHolderOutput2 + output2;
                	if(r > output && r <= output2) {
                		h2 = i - 1;
                		break;
                	}
                	else if(r == 0 && output2 != 0) {
                		h2 = i - 1;
                		break;
                	}
                }
                System.out.println(h2);
            }
            else if(t == 1){
                h1 = Integer.valueOf(args[3]);
                double r = rng.nextDouble();
                double output = 0;
                double placeHolderOutput = 0.0;
                double output2 = 0;
                double placeHolderOutput2 = 0.0;
                int[][] myArray2 = Chatbot.counter(corpus);
                int[] myArray = Chatbot.counter2(corpus);
                for(int i = 1; i < 4701; i++) {      	
                	for(int j = 0; j < i; j++) {
                		int countA2 = myArray[h1];
                		int countB2 = myArray2[h1][j];
                		if(countA2 == 0) {
                			placeHolderOutput2 = 0;
                		}else {
                			placeHolderOutput2 = countB2/(double)countA2;
                		}
                		if(j < i -1) {
                			int count = countA2;
                			int countB = countB2;
                			if(count == 0) {
                				placeHolderOutput = 0;
                			}else {
                				placeHolderOutput = countB/(double)count;
                			}
                		}
                	}
                	output = output + placeHolderOutput;
                	output2 = placeHolderOutput2 + output2;
                	if(r > output && r <= output2) {
                		h2 = i - 1;
                		break;
                	}
                	else if(r == 0 && output2 != 0) {
                		h2 = i - 1;
                		break;
                	}
                }
                System.out.println(h2);
            }
            else if(t == 2){
                h1 = Integer.valueOf(args[3]);
                h2 = Integer.valueOf(args[4]);
            }

            while(h2 != 9 && h2 != 10 && h2 != 12){
                double r = rng.nextDouble();
                int w  = 0;
 
                double output = 0;
                double placeHolderOutput = 0.0;
                double output2 = 0;
                double placeHolderOutput2 = 0.0;
                int[][] myArray2 = Chatbot.counter(corpus);
                int[] myArray3 = Chatbot.counter3(corpus, h1, h2);
                for(int i = 1; i < 4701; i++) {      	
                	for(int j = 0; j < i; j++) {
                		int countA2 = myArray2[h1][h2];
                		int countB2 = myArray3[j];
                		if(countA2 == 0) {
                			placeHolderOutput2 = 0;
                		}else {
                    		placeHolderOutput2 = countB2/(double)countA2;
                		}
                		if(j < i -1) {
                			int count = countA2;
                			int countB = countB2;
                			if(count == 0) {
                				placeHolderOutput = 0;
                			}else {
                    			placeHolderOutput = countB/(double)count;	
                			}
                		}
                	}
                	output = output + placeHolderOutput;
                	output2 = placeHolderOutput2 + output2;
                	if(r > output && r <= output2) {
                		w = i - 1;
                		break;
                	}
                	else if(r == 0 && output2 != 0) {
                		w = i - 1;
                		break;
                	}
                }
                
                System.out.println(w);
                h1 = h2;
                h2 = w;
            }
        }

        return;
    }
}
