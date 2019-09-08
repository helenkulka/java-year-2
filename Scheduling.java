/* uses a greedy algorithm to determine the order in which a 
set of assignments should be completed in, depending on its weight and deadline
 */

import java.util.*;

class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
	int deadline;
	
	
	protected Assignment() {
	}
	
	protected Assignment(int number, int weight, int deadline) {
		this.number = number;
		this.weight = weight;
		this.deadline = deadline;
	}
	
	
	
	/**
	 * This method is used to sort to compare assignment objects for sorting. 
	 * The way you implement this method will define which order the assignments appear in when you sort.
	 * Return 1 if a1 should appear after a2
	 * Return -1 if a1 should appear before a2
	 * Return 0 if a1 and a2 are equivalent 
	 */
	@Override
	public int compare(Assignment a1, Assignment a2) {
		
		if (a1.deadline < a2.deadline) {
			return-1;
		}
		else if (a1.deadline > a2.deadline) {
			return 1;
		}
		else if (a1.deadline == a2.deadline) {
			if (a1.weight > a2.weight) {
				return -1;
			}
			else if (a1.weight < a2.weight) {
				return 1;
			}
		}
		
		return 0;
	}
}

public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
	int lastDeadline = 0;
	
	protected HW_Sched(int[] weights, int[] deadlines, int size) {
		for (int i=0; i<size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m =size;
	}
	
	
	/**
	 * 
	 * @return Array where output[i] corresponds to when assignment #i will be completed. output[i] is 0 if assignment #i is never completed.
	 * The homework you complete first will be given an output of 1, the second, 2, etc.
	 */
	public int[] SelectAssignments() {
		//Use the following command to sort your Assignments: 
		//Collections.sort(Assignments, new Assignment());
		//This will re-order your assignments. The resulting order will depend on how the compare function is implemented
		Collections.sort(Assignments, new Assignment());
		
		
		int[] homeworkPlan = new int[Assignments.size()];
		
		int deadlineTracker = 1;
		int order = 1;
		
		
		for(Assignment a: this.Assignments) {
			
			if (a.deadline < deadlineTracker) {
				for (int i=Assignments.indexOf(a)-1; i>=0; i--) {
					if (Assignments.get(i).weight < a.weight) {
						int k = homeworkPlan[Assignments.get(i).number];
						homeworkPlan[Assignments.get(i).number] = 0;
						homeworkPlan[a.number] = k;
						break;
					}
				}
				
			}
			else {
				homeworkPlan[a.number] = order;
				deadlineTracker++;
				order++;
			}

		}
	
		return homeworkPlan;
	}
}
	



