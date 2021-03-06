package simpleslickgame;

public class FuzzySet {

	  protected double min;
	  protected double max;
	  protected double mid;
	  protected double midValue;

	  public FuzzySet(double min,double max,double midValue)
	  {
	    this(min,max,min+((max-min)/2),midValue);
	  }

	  public FuzzySet(double min,double max,double mid,double midValue)
	  {
	    this.min = min;
	    this.max = max;
	    this.mid = mid;
	    this.midValue = midValue;
	  }

	  public double evaluate(double in)
	  {
	    if ( in<=min || in>=max )
	      return 0;
	    else if (in==mid)
	      return midValue;
	    else if (in<mid) {
	      double step = midValue / (mid-min);
	      double d = in-min;
	      return d*step;
	    } else {
	      double step = midValue / (max-mid);
	      double d = max-in;
	      return d*step;
	    }
	  }

	  public boolean isMember(double in)
	  {
	    return evaluate(in)!=0;
	  }

	  public double getMin()
	  {
	    return min;
	  }

	  public double getMax()
	  {
	    return max;
	  }

	  public double getMid()
	  {
	    return mid;
	  }

	  public double getMidValue()
	  {
	    return midValue;
	  }
	}