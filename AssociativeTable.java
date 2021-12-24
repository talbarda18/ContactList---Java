/**
* The AssociativeTable Class is a Generic Class that implements
* associative sorted table. 
* the key is a Comparable type, the value can be any type.
*
* @author  Tal Barda
* @Date   20-12-2021 
*/

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


public class AssociativeTable <K extends Comparable<K>, E> implements Iterable<K>{
	
	private Map<K,E> table;
	
	// function default constructor
	// generate empty table
	public AssociativeTable() {
		this.table = new TreeMap<K,E>();
	}
	// end of default constructor
	
	
	// AssociativeTable constructor
	// generate table for two arguments
	public <T extends Comparable<K>> AssociativeTable(ArrayList <K> keys, ArrayList <E> values ) throws IllegalArgumentException{
		try{
			if(keys.size() != values.size()) {
				throw new IllegalArgumentException();
			}
			else {
				int i = 0;
				this.table = new TreeMap<K,E>();
				
				for(K key : keys) {
					table.put(key,values.get(i));
					i++;
				}
			}
		}
		catch(IllegalArgumentException illegalArgumentException) {
			System.err.println("Exception in \"AssociationTable\" Argument length:"+ keys.size() +
					" not equals to Argument length:"+ values.size());
			
		}
	}
	// end of constructor

	
	//~~~Iterator Section~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// function KeyIterator
	// returns iterator for the table
	public Iterator<K> KeyIterator() {
		return iterator();
	}
	// end KeyIterator
	
	@Override
	public Iterator<K> iterator() {
		return new TableIterator<K>();
	}
	
	//private class implements iterator
	private class TableIterator<K> implements Iterator<K>{
		private int current = 0;
		private K[] arr;
		
		public TableIterator() {
			arr = (K[])table.keySet().toArray();
		}
		@Override
		public boolean hasNext() {
			return current < arr.length;
		}
		@Override
		public K next() {
			K key = arr[current++];
			return key;
		}
	}
	// end of class TableIterator
	//~~~~~End Of Iterator Section~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// function add
	// add new element to the table
	public void add(K key, E value) {
		table.put(key, value);
	}
	//end of add
	
	// function get
	// return the value that related to the received key  
	public E get(K key) {
		return table.get(key);
	}
	// end of function get 
	
	
	// function contains
	// return true if the table contains the received key
	public boolean contains(K key) {
		return table.containsKey(key);
	}
	// end of contains
	
	
	// function remove
	// return true if element removed from table, else return false
	public boolean remove(K key){
		if(table.remove(key) != null) {
			return true;
		}
		else {
			return false;
		}
	}
	// end of remove
	
	// function size
	// return the number of elements in the table 
	public int size() {
		return table.size();
	}
	// end of size



}
