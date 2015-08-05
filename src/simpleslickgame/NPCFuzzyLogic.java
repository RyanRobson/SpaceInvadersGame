package simpleslickgame;

import java.util.ArrayList;
import java.util.Iterator;

public class NPCFuzzyLogic {

	  
	  protected ArrayList list = new ArrayList();
	 

	  public void add(FuzzySet set)
	  {
	    list.add(set);		
	  }

	  public void clear()
	  {
	    list.clear();
	  }

	  /**
	   * This method is called to evaluate an input variable against
	   * all fuzzy logic sets that are contained by this processor.
	   * This method will return the membership that is held by
	   * each fuzzy logic set given the input value.
	   */
	  public double []evaluate(double in)
	  {
	    double result[] = new double[list.size()];
	    Iterator itr = list.iterator();
	    int i = 0;
	    while (itr.hasNext()) {
	      FuzzySet set = (FuzzySet)itr.next();
	      result[i++] = set.evaluate(in);
	    }
	    return result;
	  }
	}



