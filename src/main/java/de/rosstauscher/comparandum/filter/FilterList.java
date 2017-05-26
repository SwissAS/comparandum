package de.rosstauscher.comparandum.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import de.rosstauscher.comparandum.render.IRenderable;

/*****************************************************************************
 * A list of filters that are applied one after the other.
 * This is implementes itself the IFilter interface (Composite design pattern).
 * 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class FilterList implements List<IFilter>, IFilter {

	private final List<IFilter> filters;

	/*************************************************************************
	 * Constructor
	 ************************************************************************/
	
	public FilterList() {
		super();
		this.filters = new ArrayList<IFilter>();
	}
	
	/*************************************************************************
	 * @return the result of the method. the result of the method.
	 * @see java.util.List#size()
	 ************************************************************************/
	@Override
	public int size() {
		return this.filters.size();
	}

	/*************************************************************************
	 * @return the result of the method.
	 * @see java.util.List#isEmpty()
	 ************************************************************************/
	@Override
	public boolean isEmpty() {
		return this.filters.isEmpty();
	}

	/*************************************************************************
	 * @param o the object to check
	 * @return the result of the method.
	 * @see java.util.List#contains(java.lang.Object)
	 ************************************************************************/
	@Override
	public boolean contains(Object o) {
		return this.filters.contains(o);
	}

	/*************************************************************************
	 * @return the result of the method.
	 * @see java.util.List#iterator()
	 ************************************************************************/
	@Override
	public Iterator<IFilter> iterator() {
		return this.filters.iterator();
	}

	/*************************************************************************
	 * @return the result of the method.
	 * @see java.util.List#toArray()
	 ************************************************************************/
	@Override
	public Object[] toArray() {
		return this.filters.toArray();
	}

	/*************************************************************************
	 * @param <T> the type of the array to create.
	 * @param a the array to use as type template.
	 * @return the result of the method.
	 ************************************************************************/
	@Override
	public <T> T[] toArray(T[] a) {
		return this.filters.toArray(a);
	}

	/*************************************************************************
	 * @param e the element to add
	 * @return the result of the method.
	 * @see java.util.List#add(java.lang.Object)
	 ************************************************************************/
	@Override
	public boolean add(IFilter e) {
		return this.filters.add(e);
	}

	/*************************************************************************
	 * @param o the object to remove
	 * @return the result of the method.
	 * @see java.util.List#remove(java.lang.Object)
	 ************************************************************************/
	@Override
	public boolean remove(Object o) {
		return this.filters.remove(o);
	}

	/*************************************************************************
	 * @param c the collection to check
	 * @return the result of the method.
	 * @see java.util.List#containsAll(java.util.Collection)
	 ************************************************************************/
	@Override
	public boolean containsAll(Collection<?> c) {
		return this.filters.containsAll(c);
	}

	/*************************************************************************
	 * @param c the collection to add to this list
	 * @return the result of the method.
	 * @see java.util.List#addAll(java.util.Collection)
	 ************************************************************************/
	@Override
	public boolean addAll(Collection<? extends IFilter> c) {
		return this.filters.addAll(c);
	}

	/*************************************************************************
	 * @param index to insert
	 * @param c the collection to add
	 * @return the result of the method.
	 * @see java.util.List#addAll(int, java.util.Collection)
	 ************************************************************************/
	@Override
	public boolean addAll(int index, Collection<? extends IFilter> c) {
		return this.filters.addAll(index, c);
	}

	/*************************************************************************
	 * @param c the collection who's items are deleted from this list
	 * @return the result of the method.
	 * @see java.util.List#removeAll(java.util.Collection)
	 ************************************************************************/
	@Override
	public boolean removeAll(Collection<?> c) {
		return this.filters.removeAll(c);
	}

	/*************************************************************************
	 * @param c a collection
	 * @return the result of the method.
	 * @see java.util.List#retainAll(java.util.Collection)
	 ************************************************************************/
	@Override
	public boolean retainAll(Collection<?> c) {
		return this.filters.retainAll(c);
	}

	/*************************************************************************
	 * 
	 * @see java.util.List#clear()
	 ************************************************************************/
	@Override
	public void clear() {
		this.filters.clear();
	}

	/*************************************************************************
	 * @param o the object to compare
	 * @return the result of the method.
	 * @see java.util.List#equals(java.lang.Object)
	 ************************************************************************/
	@Override
	public boolean equals(Object o) {
		return this.filters.equals(o);
	}

	/*************************************************************************
	 * @return the result of the method.
	 * @see java.util.List#hashCode()
	 ************************************************************************/
	@Override
	public int hashCode() {
		return this.filters.hashCode();
	}

	/*************************************************************************
	 * @param index to get
	 * @return the result of the method.
	 * @see java.util.List#get(int)
	 ************************************************************************/
	@Override
	public IFilter get(int index) {
		return this.filters.get(index);
	}

	/*************************************************************************
	 * @param index to set
	 * @param element the object to set at this position
	 * @return the result of the method.
	 * @see java.util.List#set(int, java.lang.Object)
	 ************************************************************************/
	@Override
	public IFilter set(int index, IFilter element) {
		return this.filters.set(index, element);
	}

	/*************************************************************************
	 * @param index where the element is inserted
	 * @param element to insert
	 * @see java.util.List#add(int, java.lang.Object)
	 ************************************************************************/
	@Override
	public void add(int index, IFilter element) {
		this.filters.add(index, element);
	}

	/*************************************************************************
	 * @param index of the object that is removed
	 * @return the result of the method.
	 * @see java.util.List#remove(int)
	 ************************************************************************/
	@Override
	public IFilter remove(int index) {
		return this.filters.remove(index);
	}

	/*************************************************************************
	 * @param o the object to search in the list
	 * @return the result of the method.
	 * @see java.util.List#indexOf(java.lang.Object)
	 ************************************************************************/
	@Override
	public int indexOf(Object o) {
		return this.filters.indexOf(o);
	}

	/*************************************************************************
	 * @param o the object to search in the list
	 * @return the result of the method.
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 ************************************************************************/
	@Override
	public int lastIndexOf(Object o) {
		return this.filters.lastIndexOf(o);
	}

	/*************************************************************************
	 * @return the result of the method.
	 * @see java.util.List#listIterator()
	 ************************************************************************/
	@Override
	public ListIterator<IFilter> listIterator() {
		return this.filters.listIterator();
	}

	/*************************************************************************
	 * @param index to start at
	 * @return the result of the method.
	 * @see java.util.List#listIterator(int)
	 ************************************************************************/
	@Override
	public ListIterator<IFilter> listIterator(int index) {
		return this.filters.listIterator(index);
	}

	/*************************************************************************
	 * @param fromIndex the start index of the sublist
	 * @param toIndex the end index
	 * @return the result of the method.
	 * @see java.util.List#subList(int, int)
	 ************************************************************************/
	@Override
	public List<IFilter> subList(int fromIndex, int toIndex) {
		return this.filters.subList(fromIndex, toIndex);
	}

	/*************************************************************************
	 * Filter the given input with all filters in this list, applying one 
	 * after the other.
	 * @see de.rosstauscher.comparandum.filter.IFilter#applyTo(de.rosstauscher.comparandum.render.IRenderable)
	 ************************************************************************/
	@Override
	public IRenderable applyTo(IRenderable input) {
		IRenderable result = input;
		for (IFilter filter : this.filters) {
			result = filter.applyTo(result);
		}
		return result;
	}

}

