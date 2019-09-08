

public class Shelf {

	protected int height;
	protected int availableLength;
	protected int totalLength;
	protected Box firstBox;
	protected Box lastBox;

	public Shelf(int height, int totalLength) {
		this.height = height;
		this.availableLength = totalLength;
		this.totalLength = totalLength;
		this.firstBox = null;
		this.lastBox = null;
	}

	protected void clear() {
		availableLength = totalLength;
		firstBox = null;
		lastBox = null;
	}

	public String print() {
		String result = "( " + height + " - " + availableLength + " ) : ";
		Box b = firstBox;
		while (b != null) {
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}

	/**
	 * Adds a box on the shelf. Here we assume that the box fits in height and
	 * length on the shelf.
	 * 
	 * @param b
	 */
	public void addBox(Box b) {

		if (this.lastBox == null) {
			lastBox = firstBox = b;
			availableLength = availableLength - lastBox.length;
		} else {
			Box c = b;
			lastBox.next = b;
			b.previous = lastBox;
			lastBox = c;
			availableLength = availableLength - lastBox.length;
		}
	}

	/**
	 * If the box with the identifier is on the shelf, remove the box from the
	 * shelf and return that box. If not, do not do anything to the Shelf and
	 * return null.
	 * 
	 * @param identifier
	 * @return
	 */
	public Box removeBox(String identifier) {
		if (identifier.equals(null)) {
			throw new IllegalArgumentException("No ID entered");
		}
		if (firstBox == null) {
			return null;
		}
		if (firstBox.id.equals(identifier)) {
			Box first = firstBox;
			firstBox = firstBox.next;
			if (firstBox == null) {
				lastBox = null;
			} else {
				firstBox.previous = null;
			}
			first.next = null;
			first.previous = null;
			availableLength = availableLength + first.length;
			return first;
		} else {
			if (firstBox.equals(lastBox)) {
				return null;
			} else {
				Box current = firstBox;
				while (current.next != null && !(current.next.id.equals(identifier))) {
					current = current.next;
				}
				Box temp = current.next;
				if (current.next == null) {
					return null;
				} else {
					current.next = current.next.next;
					if (current.next == null) {
						lastBox = current;
						lastBox.next = null;
					} else {
						current.next.previous = current;

					}
					this.availableLength = this.availableLength + temp.length;
					current.next = null;
					current.previous = null;
					return temp;
				}
			}

		}
	}

}
