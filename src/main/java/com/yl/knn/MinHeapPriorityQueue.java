package com.yl.knn;

import java.util.*;

/**
 * 小顶堆求topN
 */
public class MinHeapPriorityQueue<T extends Comparable<T>> {
	private PriorityQueue<T> queue;
	private int maxSize;
	
	/**
	 * @param maxSize
	 */
	public MinHeapPriorityQueue(int maxSize) {
		this(maxSize, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				return o1.compareTo(o2);
			}
		});
	}
	
	public MinHeapPriorityQueue(int maxSize, Comparator<T> comparator) {
		this.maxSize = maxSize;
		this.queue = new PriorityQueue<>(maxSize, comparator);
	}
	
	public synchronized void insert(T t) {
		if (queue.size() < maxSize) {
			queue.add(t);
		} else {
			T tmp = queue.peek();
			if (t.compareTo(tmp) > 0) {
				queue.poll();
				queue.add(t);
			}
		}
	}
	
	public synchronized List<T> sortList() {
		List<T> list = new LinkedList<>(queue);
		Collections.sort(list, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				return o2.compareTo(o1);
			}
		});
		return list;
	}

	public synchronized List<T> getList(){
		List<T> list = new LinkedList<>(queue);
		return list;
	}

	public static double format(double d, int n) {
		double p = Math.pow(10, n);
		return Math.round(d * p) / p;
	}



	
	public static void main(String[] args) {
		MinHeapPriorityQueue<Double> queue = new MinHeapPriorityQueue<>(3);
		Random r = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < 20; i++) {
			double rd = format(r.nextDouble(), 3);
			queue.insert(rd);
			buf.append(rd);
			if (i != 19)
				buf.append(", ");
		}
		System.out.println("buff: " + buf.toString());
		System.out.println("list: " + queue.sortList());
	}
}
