package com.jcj.jcategories;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class NullIterator<T> implements Iterable<T>, Iterator<T>
{
  public Iterator<T> iterator()
  {
    return this;
  }

  public boolean hasNext()
  {
    return false;
  }

  public T next()
  {
    throw new NoSuchElementException();
  }

  public void remove()
  {
  }

}
