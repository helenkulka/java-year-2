//package assignment2;

public class Warehouse {

	protected Shelf[] storage;
	protected int nbShelves;
	public Box toShip;
	public UrgentBox toShipUrgently;
	static String problem = "problem encountered while performing the operation";
	static String noProblem = "operation was successfully carried out";

	public Warehouse(int n, int[] heights, int[] lengths) {
		this.nbShelves = n;
		this.storage = new Shelf[n];
		for (int i = 0; i < n; i++) {
			this.storage[i] = new Shelf(heights[i], lengths[i]);
		}
		this.toShip = null;
		this.toShipUrgently = null;
	}

	public static void main(String[] args) {
	}

	public String printShipping() {
		Box b = toShip;
		String result = "not urgent : ";
		while (b != null) {
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n" + "should be already gone : ";
		b = toShipUrgently;
		while (b != null) {
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}

	public String print() {
		String result = "";
		for (int i = 0; i < nbShelves; i++) {
			result += i + "-th shelf " + storage[i].print();
		}
		return result;
	}

	public void clear() {
		toShip = null;
		toShipUrgently = null;
		for (int i = 0; i < nbShelves; i++) {
			storage[i].clear();
		}
	}

	/**
	 * initiate the merge sort algorithm
	 */
	public void sort() {
		mergeSort(0, nbShelves - 1);
	}

	/**
	 * performs the induction step of the merge sort algorithm
	 * 
	 * @param start
	 * @param end
	 */
	protected void mergeSort(int start, int end) {

		if (start < end) {
			int m = start + (end - start) / 2;
			mergeSort(start, m);
			mergeSort(m + 1, end);
			merge(start, m, end);
		}
	}

	/**
	 * performs the merge part of the merge sort algorithm
	 * 
	 * @param start
	 * @param mid
	 * @param end
	 */
	protected void merge(int start, int mid, int end) {
		int i, j, k;
		int n1 = mid - start + 1;
		int n2 = end - mid;

		Shelf[] first = new Shelf[n1];
		Shelf[] second = new Shelf[n2];

		for (i = 0; i < n1; i++)
			first[i] = storage[start + i];
		for (j = 0; j < n2; j++)
			second[j] = storage[mid + 1 + j];

		i = 0;
		j = 0;
		k = start;
		while (i < n1 && j < n2) {
			if (first[i].height <= second[j].height) {
				storage[k] = first[i];
				i++;
			} else {
				storage[k] = second[j];
				j++;
			}
			k++;
		}

		while (i < n1) {
			storage[k] = first[i];
			i++;
			k++;
		}

		while (j < n2) {
			storage[k] = second[j];
			j++;
			k++;
		}
	}

	/**
	 * Adds a box is the smallest possible shelf where there is room available.
	 * Here we assume that there is at least one shelf (i.e. nbShelves >0)
	 * 
	 * @param b
	 * @return problem or noProblem
	 */
	public String addBox(Box b) {
		int counter = 0;
		for (int i = 0; i < this.storage.length; i++) {
			if (this.storage[i].availableLength >= b.length && this.storage[i].height >= b.height) {
				this.storage[i].addBox(b);
				return noProblem;
			} else {
				counter++;
			}
			if (counter == this.nbShelves) {
				return problem;
			}
		}
		return "";
	}

	/**
	 * Adds a box to its corresponding shipping list and updates all the fields
	 * 
	 * @param b
	 * @return problem or noProblem
	 */
	public String addToShip(Box b) {
		if (b == null) {
			return problem;
		} else if (b instanceof UrgentBox) {
			UrgentBox c = (UrgentBox) b;

			if (toShipUrgently == null) {
				toShipUrgently = c;
			}

			else {
				UrgentBox copy = toShipUrgently;
				toShipUrgently = c;
				c.next = copy;
				copy.previous = c;
			}
			return noProblem;
		}

		if (!(b instanceof UrgentBox)) {
			System.out.println(b.id);
			if (toShip == null) {
				toShip = b;
			} else {
				Box copy = toShip;
				toShip = b;
				b.next = copy;
				copy.previous = b;
			}
			return noProblem;
		}

		return problem;

	}

	/**
	 * Find a box with the identifier (if it exists) Remove the box from its
	 * corresponding shelf Add it to its corresponding shipping list
	 * 
	 * @param identifier
	 * @return problem or noProblem
	 */
	public String shipBox(String identifier) {
		int counter = 0;
		for (int i = 0; i < storage.length; i++) {
			Box ship = storage[i].removeBox(identifier);
			if (ship == null) {
				counter++;
			} else {
				addToShip(ship);
				return noProblem;
			}
			if (counter == storage.length) {
				return problem;
			}
		}
		return problem;

	}

	/**
	 * if there is a better shelf for the box, moves the box to the optimal
	 * shelf. If there are none, do not do anything
	 * 
	 * @param b
	 * @param position
	 */
	public void moveOneBox(Box b, int position) {
		int k = storage.length - 1;
		for (int i = 0; i < k; i++) {
			if (b != null) {
				if (storage[i].availableLength >= b.length && storage[i].height >= b.height
						&& storage[i].height < storage[position].height) {
					k = i;
					storage[position].removeBox(b.id);
					storage[k].addBox(b);

				}
			}
		}
	}

	/**
	 * reorganize the entire warehouse : start with smaller shelves and first
	 * box on each shelf.
	 */
	public void reorganize() {
		for (int i = 0; i < storage.length; i++) {
			Box current = storage[i].firstBox;
			while (current != null && current.next != null) {
				moveOneBox(current, i);
				current = current.next;
				moveOneBox(current, i);
			}

		}
	}
}