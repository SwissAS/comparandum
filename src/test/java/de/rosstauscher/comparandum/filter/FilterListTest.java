package de.rosstauscher.comparandum.filter;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.junit.Test;

import de.rosstauscher.comparandum.filter.FilterList;
import de.rosstauscher.comparandum.filter.IFilter;
import de.rosstauscher.comparandum.filter.ScaleFilter;
import de.rosstauscher.comparandum.render.IRenderable;

/*****************************************************************************
 * Some unit tests. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class FilterListTest {

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#size()}.
	 */
	@Test
	public void testSize() {
		FilterList list = new FilterList();
		assertEquals(0, list.size());
		list.add(new ScaleFilter(10, 10));
		assertEquals(1, list.size());
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#isEmpty()}.
	 */
	@Test
	public void testIsEmpty() {
		FilterList list = new FilterList();
		assertTrue(list.isEmpty());
		list.add(new ScaleFilter(10, 10));
		assertFalse(list.isEmpty());
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#contains(java.lang.Object)}.
	 */
	@Test
	public void testContains() {
		FilterList list = new FilterList();
		ScaleFilter f = new ScaleFilter(10, 10);
		list.add(f);
		assertTrue(list.contains(f));
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#iterator()}.
	 */
	@Test
	public void testIterator() {
		FilterList list = new FilterList();
		ScaleFilter f = new ScaleFilter(10, 10);
		list.add(f);
		Iterator<IFilter> iterator = list.iterator();
		assertTrue(iterator.hasNext());
		assertEquals(f, iterator.next());
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#toArray()}.
	 */
	@Test
	public void testToArray() {
		FilterList list = new FilterList();
		list.add(new ScaleFilter(10, 10));
		Object[] actual = list.toArray();
		assertEquals(1, actual.length);
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#toArray(T[])}.
	 */
	@Test
	public void testToArrayTArray() {
		FilterList list = new FilterList();
		list.add(new ScaleFilter(10, 10));
		IFilter[] expected = new IFilter[1];
		IFilter[] actual2 = list.toArray(expected);
		assertArrayEquals(expected, actual2);
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#add(de.rosstauscher.comparandum.filter.IFilter)}.
	 */
	@Test
	public void testAddIFilter() {
		FilterList list = new FilterList();
		list.add(new ScaleFilter(10, 10));
		assertEquals(1, list.size());
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#remove(java.lang.Object)}.
	 */
	@Test
	public void testRemoveObject() {
		FilterList list = new FilterList();
		ScaleFilter f = new ScaleFilter(10, 10);
		list.add(f);
		assertEquals(1, list.size());
		list.remove(f);
		assertEquals(0, list.size());
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#containsAll(java.util.Collection)}.
	 */
	@Test
	public void testContainsAll() {
		FilterList list = new FilterList();
		ScaleFilter f = new ScaleFilter(10, 10);
		ScaleFilter f2 = new ScaleFilter(10, 20);
		list.add(f);
		list.add(f2);
		List<IFilter> l = new ArrayList<IFilter>();
		l.add(f);
		l.add(f2);
		assertTrue(list.containsAll(l));
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#addAll(java.util.Collection)}.
	 */
	@Test
	public void testAddAllCollectionOfQextendsIFilter() {
		List<IFilter> l = new ArrayList<IFilter>();
		l.add(new ScaleFilter(10, 10));
		l.add(new ScaleFilter(10, 20));
		FilterList list = new FilterList();
		list.addAll(l);
		assertEquals(2, list.size());
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#addAll(int, java.util.Collection)}.
	 */
	@Test
	public void testAddAllIntCollectionOfQextendsIFilter() {
		List<IFilter> l = new ArrayList<IFilter>();
		l.add(new ScaleFilter(10, 10));
		l.add(new ScaleFilter(10, 20));
		FilterList list = new FilterList();
		list.addAll(l);
		list.addAll(2, l);
		assertEquals(list.get(0), list.get(2));
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#removeAll(java.util.Collection)}.
	 */
	@Test
	public void testRemoveAll() {
		List<IFilter> l = new ArrayList<IFilter>();
		l.add(new ScaleFilter(10, 10));
		l.add(new ScaleFilter(10, 20));
		FilterList list = new FilterList();
		list.addAll(l);
		assertEquals(2, list.size());
		list.removeAll(l);
		assertEquals(0, list.size());
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#retainAll(java.util.Collection)}.
	 */
	@Test
	public void testRetainAll() {
		List<IFilter> l = new ArrayList<IFilter>();
		l.add(new ScaleFilter(10, 10));
		l.add(new ScaleFilter(10, 20));
		FilterList list = new FilterList();
		list.addAll(l);
		list.add(new ScaleFilter(40, 40));
		assertEquals(3, list.size());
		list.retainAll(l);
		assertEquals(2, list.size());
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#clear()}.
	 */
	@Test
	public void testClear() {
		FilterList list = new FilterList();	
		list.add(new ScaleFilter(10, 10));
		list.add(new ScaleFilter(10, 20));
		list.clear();
		assertTrue(list.isEmpty());
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#get(int)}.
	 */
	@Test
	public void testGet() {
		FilterList list = new FilterList();	
		list.add(new ScaleFilter(10, 10));
		ScaleFilter f = new ScaleFilter(10, 20);
		list.add(f);
		assertEquals(f, list.get(1));
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#set(int, de.rosstauscher.comparandum.filter.IFilter)}.
	 */
	@Test
	public void testSet() {
		FilterList list = new FilterList();	
		list.add(new ScaleFilter(10, 10));
		ScaleFilter f = new ScaleFilter(10, 20);
		list.set(0, f);
		assertEquals(f, list.get(0));
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#add(int, de.rosstauscher.comparandum.filter.IFilter)}.
	 */
	@Test
	public void testAddIntIFilter() {
		FilterList list = new FilterList();	
		list.add(new ScaleFilter(10, 10));
		ScaleFilter f = new ScaleFilter(10, 20);
		list.add(0, f);
		assertEquals(2, list.size());
		assertEquals(f, list.get(0));
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#remove(int)}.
	 */
	@Test
	public void testRemoveInt() {
		FilterList list = new FilterList();	
		list.add(new ScaleFilter(10, 10));
		ScaleFilter f = new ScaleFilter(10, 20);
		list.add(f);
		assertEquals(f, list.remove(1));
		assertEquals(1, list.size());
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#indexOf(java.lang.Object)}.
	 */
	@Test
	public void testIndexOf() {
		FilterList list = new FilterList();	
		list.add(new ScaleFilter(10, 10));
		ScaleFilter f = new ScaleFilter(10, 20);
		list.add(f);
		assertEquals(1, list.indexOf(f));
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#lastIndexOf(java.lang.Object)}.
	 */
	@Test
	public void testLastIndexOf() {
		FilterList list = new FilterList();	
		ScaleFilter f = new ScaleFilter(10, 20);
		list.add(f);
		list.add(f);
		assertEquals(1, list.lastIndexOf(f));
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#listIterator()}.
	 */
	@Test
	public void testListIterator() {
		FilterList list = new FilterList();	
		list.add(new ScaleFilter(10, 20));
		list.add(new ScaleFilter(20, 20));
		ListIterator<IFilter> iter = list.listIterator();
		assertTrue(iter.hasNext());
		iter.next();
		assertTrue(iter.hasNext());
		assertTrue(iter.hasPrevious());
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#listIterator(int)}.
	 */
	@Test
	public void testListIteratorInt() {
		FilterList list = new FilterList();	
		list.add(new ScaleFilter(10, 20));
		list.add(new ScaleFilter(20, 20));
		ListIterator<IFilter> iter = list.listIterator(1);
		assertTrue(iter.hasNext());
		iter.next();
		assertFalse(iter.hasNext());
		assertTrue(iter.hasPrevious());
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#subList(int, int)}.
	 */
	@Test
	public void testSubList() {
		FilterList list = new FilterList();	
		list.add(new ScaleFilter(10, 20));
		ScaleFilter f = new ScaleFilter(20, 20);
		list.add(f);
		List<IFilter> sub = list.subList(1, 2);
		assertEquals(1, sub.size());
		assertEquals(f, sub.get(0));
	}

	/**
	 * Test method for {@link de.rosstauscher.comparandum.filter.FilterList#applyTo(de.rosstauscher.comparandum.render.IRenderable)}.
	 */
	@Test
	public void testApply() {
		FilterList list = new FilterList();	
		list.add(new ScaleFilter(10, 10));
		list.add(new ScaleFilter(20, 20));
		IRenderable actual = list.applyTo(new IRenderable() {
			@Override
			public void paint(Graphics2D g) {
			}
			@Override
			public Dimension getDimension() {
				return new Dimension(5, 5);
			}
		});
		assertEquals(new Dimension(20, 20), actual.getDimension());
	}

}

