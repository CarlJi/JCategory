package com.jcj.jcategory;

import java.util.Comparator;

public class StringComparator implements Comparator<String>
{
  public int compare(String o1, String o2)
  {
    double s1 = Double.parseDouble(o1);
    double s2 = Double.parseDouble(o2);

    double sx = s1 - s2;

    if(sx == 0)
    {
      return 0;
    }
    else if(sx > 0)
    {
      return 1;
    }
    else
    {
      return -1;
    }
  }

}
