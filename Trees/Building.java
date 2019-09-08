/*an assignment using data on instances of the class Building
to decide costs for planning using a tree structure
*/

public class Building {

	OneBuilding data;
	Building older;
	Building same;
	Building younger;

	public Building(OneBuilding data) {
		this.data = data;
		this.older = null;
		this.same = null;
		this.younger = null;
	}

	public String toString() {
		String result = this.data.toString() + "\n";
		if (this.older != null) {
			result += "older than " + this.data.toString() + " :\n";
			result += this.older.toString();
		}
		if (this.same != null) {
			result += "same age as " + this.data.toString() + " :\n";
			result += this.same.toString();
		}
		if (this.younger != null) {
			result += "younger than " + this.data.toString() + " :\n";
			result += this.younger.toString();
		}
		return result;
	}

	public Building addBuilding(OneBuilding b) {
		if (b == null) {
			return this;
		}
		if (this.data.yearOfConstruction > b.yearOfConstruction) {
			if (this.older == null) {
				this.older = new Building(b);
			} else {
				this.older.addBuilding(b);
			}

		}
		if (this.data.yearOfConstruction < b.yearOfConstruction) {
			if (this.younger == null) {
				this.younger = new Building(b);
			} else {
				this.younger.addBuilding(b);
			}

		} 
		if (this.data.yearOfConstruction == b.yearOfConstruction) {
			if (b.height > this.data.height) {
				OneBuilding n = new OneBuilding(this.data.name, this.data.yearOfConstruction, this.data.height, this.data.yearForRepair, this.data.costForRepair);
				this.data = b;
			if (this.same==null) {
				this.same = new Building(n);
			}
			else {
				this.same.addBuilding(n);
			}
			}
			else {
				if (this.same == null) {
					this.same = new Building(b);
				}
				else {
					this.same.addBuilding(b);
				}
			}
		} 
		return this;
	}

	public Building findRoot(Building b) {
		return this;
	}

	public Building addBuildings(Building b) {
		if (b.data != null) {
			this.addBuilding(b.data);
		}
		if (b.older != null) {
			addBuildings(b.older);
		}
		if (b.younger != null) {
			addBuildings(b.younger);
		}
		if (b.same != null) {
			addBuildings(b.same);
		}
		return this;
	}

	public Building removeBuilding(OneBuilding b) {
		if (this.data.equals(b)) {
			Building c = new Building(this.data);
			c.same = this.same;
			c.older = this.older;
			c.younger = this.younger;
		if (this.same != null) {
			if (c.same != null) {
				this.data = c.same.data;
				this.older = c.same.older;
				this.younger = c.same.younger;
				this.same = c.same.same;
			}
			if (c.older != null) {
				this.addBuildings(c.older);
			}
			if (c.younger != null) {
				this.addBuildings(c.younger);
			}
		}
		else {
			if (this.same==null && this.older != null) {
				if (c.older != null) {
					this.data = c.older.data;
					this.older = c.older.older;
					this.younger = c.older.younger;
					this.same = c.older.same;
				}
				if (c.same != null) {
					this.addBuildings(c.same);
				}
				if (c.younger != null) {
					this.addBuildings(c.younger);
				}
			}
			else {
				if (this.same == null && this.older == null) {
					if (c.younger != null) {
						this.data = c.younger.data;
						this.older = c.younger.older;
						this.younger = c.younger.younger;
						this.same = c.younger.same;
					}
					else {
						this.data = null;
					}
				}
			}
		}
		} else {
			if (this.older != null && b.yearOfConstruction < this.data.yearOfConstruction) {
				this.older.removeBuilding(b);
			}
			if (this.same != null && b.yearOfConstruction== this.data.yearOfConstruction) {
				this.same.removeBuilding(b);
			}
			if (this.younger != null && b.yearOfConstruction>this.data.yearOfConstruction) {
				this.younger.removeBuilding(b);
			}
		}
		return this;
	}

	public int oldest() {
		Building current = this;
		int yr = current.data.yearOfConstruction;
		while (current != null) {
			yr = current.data.yearOfConstruction;
			current = current.older;
		}
		return yr;
	}

	public int highest() {
		int h = this.data.height;
		if (this.younger != null) {
			if (this.younger.data.height > this.data.height) {
				h = this.younger.data.height;
				this.younger.highest();
			}
		}
		if (this.older != null) {
			if (this.older.data.height > this.data.height) {
				h = this.older.data.height;
				this.older.highest();
			}
		}
		if (this.same != null) {
			if (this.same.data.height > this.data.height) {
				h = this.older.data.height;
			}
		}
		return h;
	}

	public OneBuilding highestFromYear(int year) {
		if(this.data.yearOfConstruction == year) {
					return this.data;
		}
		if (this.data.yearOfConstruction < year) {
			if (this.younger != null) {
				return this.younger.highestFromYear(year);
				
			}
			else {
				return null;
			}
		}
		if (this.data.yearOfConstruction > year) {
			if (this.older != null) {
				return this.older.highestFromYear(year);
				
			}
			else {
				return null;
			}
		} 
		return this.data;
	}

	public int numberFromYears(int yearMin, int yearMax) {
		if (yearMin > yearMax) {
			return 0;
		}
		else {
			if (this.data == null) {
				return 0;
			}
			else if (this.data.yearOfConstruction <= yearMax && this.data.yearOfConstruction >= yearMin) {
				int y = 0;
				int o = 0;
				int s = 0;
				if (this.younger != null) {
					y = this.younger.numberFromYears(yearMin, yearMax);
				}
				if (this.older != null) {
					o = this.older.numberFromYears(yearMin, yearMax);
				}
				if(this.same != null) {
					s = this.same.numberFromYears(yearMin, yearMax);
				}
				return 1 + y + o + s;
			}
			else {
				int y = 0;
				int o = 0;
				if (this.younger != null) {
					y = this.younger.numberFromYears(yearMin, yearMax);
				}
				if (this.older != null) {
					o = this.older.numberFromYears(yearMin, yearMax);
				}
				return y + o;
			}
		}						
	}

	public int[] costPlanning(int nbYears) {
		int[] costs = new int[nbYears];
		int[] olderA = new int[nbYears];
		int[] youngerA = new int[nbYears];
		int[] sameA = new int[nbYears];
		
	
		if(this.data.yearForRepair - 2018 < nbYears) {
			costs[this.data.yearForRepair - 2018] = this.data.costForRepair;	
		}
		
		if(older != null) {
			olderA = this.older.costPlanning(nbYears);
		}
		if(younger != null) {
			youngerA = this.younger.costPlanning(nbYears);
		}
		if (same != null) {
			sameA= this.same.costPlanning(nbYears);
		}
		
		for(int i=0; i<nbYears; i++) {
			costs[i] = olderA[i] + youngerA[i] + sameA[i]+costs[i];
		}
return costs;
		}
	
}
