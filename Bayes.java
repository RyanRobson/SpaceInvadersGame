	package simpleslickgame;
	
	public class Bayes {
		static String size;
		static String distance;
		static int PlayerLives;
		static String PlayerLivesSt;
		
		private static String trainingdata[][] = {
			{"small", "far", "5", "low"},
			{"small", "medium","4", "medium"},
			{"small", "close", "4", "medium"},
			{"small", "close","2", "high"},
			{"small", "far","1", "medium"},		
			{"medium", "close", "2","high"},
			{"medium", "far", "4","low"},
			{"medium", "close","5", "medium"},
			{"medium", "far","5", "low"},
			{"large", "close","3", "high"},
			{"large", "close","1", "high"},
			{"large", "medium", "5", "high"},
			{"large", "medium","1", "high"},
			{"large", "far", "2", "high"}
	};
	
	protected static String testData[] = {
			"","",""
	};
	
	private static double m;
	private static double p;
	static int num_attr = 3;
	static int train_size = 13;
	static int num_category =3;
	static int test_size = 1;
	String threat = "";	
	
	public Bayes() {
		 m = 2.0;
		 p = 0.5;		
	}
	
	public static double CalculateProbability(String test, String category) {
	
	int count[] = new int[num_attr];
	for (int i=0; i<num_attr; i++){
		count[i] = 0;
	}	
	double p_category = 0.0;
	int num_category = 0;
	
	for (int j=0; j<train_size; j++)	
		if (category.equals(trainingdata[j][num_attr]))
		    	num_category ++;
	
	p_category = (double)num_category/(double)train_size;
	
	for (int i=0; i<num_attr; i++)
	{
		for (int j=0; j<train_size; j++){	
			    if ((test.equals(trainingdata[j][i])) && (category.equals(trainingdata[j][num_attr]))){
			    	count[i] ++;
		    }
		}
	
	    p_category *=  ((double)count[i] + m * p)/((double)num_category + m);
	}
	
	return p_category;
		
	}
	public String Threat(){

		testData[0] = NPC.getEnemySize();
		testData[1] = NPC.getEnemyDistance();
		testData[2] = Integer.toString(Player.getPlayerLives());
		
		 double result[] = new double[num_category];
		 String category[] = {"high", "medium","low"};
		 for (int k = 0; k<test_size; k++)
		 {
		   for (int i=0; i<num_category; i++)
			 {
			   result[i] = CalculateProbability(testData[k], category[i]);
			   threat.equals(category[i]);
			 }
		   double max = -1000.0;
		   int max_position = -1;
		   for (int i=0; i<num_category; i++)
			 if (result[i]> max)
			 { 
				 max_position = i;
				 max = result[i];
			 }
		   threat = category[max_position];
		 }
		 return threat;
	}
	
	 public int getNumAttr(){
		 return num_attr;
	 }
	 public int getTrainSize(){
		 return train_size;
	 }
	 public int getNumCategory(){
		 return num_category;
	 }
	 public int getTestSize(){
		 return test_size;
	 }
	 
	 
	 public void setEnemySize(String st){
		 size = st;
	 }
	 public void setDistance(String st){
		 distance = st;
	 }
	
	 public void setPlayerLives(int i){
		 PlayerLives = i;
		 PlayerLivesSt = Integer.toString(PlayerLives);
	 }
	 public String getBayesThreat(){
		 return threat;
	 }
	 
	}
