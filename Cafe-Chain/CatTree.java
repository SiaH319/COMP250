import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException; 


public class CatTree implements Iterable<CatInfo>{
	public CatNode root;

	public CatTree(CatInfo c) {
		this.root = new CatNode(c);
	}

	private CatTree(CatNode c) {
		this.root = c;
	}


	public void addCat(CatInfo c)
	{
		this.root = root.addCat(new CatNode(c));
	}

	public void removeCat(CatInfo c)
	{
		this.root = root.removeCat(c);
	}

	public int mostSenior()
	{
		return root.mostSenior();
	}

	public int fluffiest() {
		return root.fluffiest();
	}

	public CatInfo fluffiestFromMonth(int month) {
		return root.fluffiestFromMonth(month);
	}

	public int hiredFromMonths(int monthMin, int monthMax) {
		return root.hiredFromMonths(monthMin, monthMax);
	}

	public int[] costPlanning(int nbMonths) {
		return root.costPlanning(nbMonths);
	}



	public Iterator<CatInfo> iterator()
	{
		return new CatTreeIterator();
	}


	class CatNode {

		CatInfo data;
		CatNode senior;
		CatNode same;
		CatNode junior;

		public CatNode(CatInfo data) {
			this.data = data;
			this.senior = null;
			this.same = null;
			this.junior = null;
		}

		public String toString() {
			String result = this.data.toString() + "\n";
			if (this.senior != null) {
				result += "more senior " + this.data.toString() + " :\n";
				result += this.senior.toString();
			}
			if (this.same != null) {
				result += "same seniority " + this.data.toString() + " :\n";
				result += this.same.toString();
			}
			if (this.junior != null) {
				result += "more junior " + this.data.toString() + " :\n";
				result += this.junior.toString();
			}
			return result;
		}


		/////////////////////////////////// MY CODE GOES HERE  ///////////////////////////////////      
		private void swapData(CatNode c1, CatNode c2) {
			CatInfo tempData = c1.data;
			c1.data = c2.data;
			c2.data = tempData;
		}

		/**
		 * add CatNode c in a given order
		 * @return root CatNode of the current tree
		 */
		public CatNode addCat(CatNode c) {
			if (root == null) root = c; // If the tree is empty, return a new node becomes a root
			else{ // If there is already an existing tree
				if (c.data.monthHired < this.data.monthHired) {
					if (this.senior == null) this.senior = c; // if there is no existing root.senior, just added
					else this.senior.addCat(c);
				}
				else if (c.data.monthHired > this.data.monthHired) {
					if (this.junior == null) this.junior = c; // if there is no existing root.junior, just added
					else this.junior.addCat(c);
				}
				else { //same seniority (c.data.monthHired == root.data.monthHired)
					if (c.data.furThickness > this.data.furThickness) { // if the added CatNode has thicker fur
						swapData(this, c); //// swap data
						if(this.same == null) this.same = c; 
						else this.same.addCat(c);
					}
					else if(c.data.furThickness <this.data.furThickness) {//c.data.furThickness < root.data.furThicknes, root remains the same
						if(this.same == null) this.same = c;
						else this.same.addCat(c);
					}
					else {// same seniority and same thickness
						if (! this.equals(c)){ // but not the same cat
							if (this.same == null) this.same = c;
							else this.same.addCat(c);
						}
					}
				}
			}
			return this;
		}

		/**
		 * CatNode.removeCat(CatInfo)
		 * @return CatNode it was called on
		 */
		public CatNode removeCat(CatInfo c) {
			if (this.data.equals(c)) { // catToRemove found
				// case 1: if c.same != null, 
				if (this.same != null) {
					swapData(this,this.same); //CatInfo object in c.same moves to the root
					this.same = this.same.same; // update c.same reference 
				}

				// case 2: if c.same == null and c.senior != null,
				else if (this.senior != null) { 
					swapData(this, this.senior); //CatInfo object in c.senior moves to the root					
					this.same = this.senior.same; // update c.same reference 
					if (this.junior != null) this.senior.junior.addCat(this.junior);
					this.junior = this.senior.junior; // update c.junior reference 
					this.senior = this.senior.senior; // update c.senior reference 
				}

				// case 3:  if c.same == null and c.senior == null, 
				else if (this.senior == null) {
					if (this.junior != null) {
						swapData(this,this.junior); //CatInfo object in c.junior moves to the root
						this.senior = this.junior.senior;  // update c.senior reference 
						this.same = this.junior.same; // update c.same reference 
						this.junior = this.junior.junior; // update c.junior reference 
					}
					else return null;// there is no senior, junior, same
				}
			} 

			else {  // catToRemove not found yet
				if (c.monthHired < this.data.monthHired ) { // in senior subtree
					if (senior == null) return this;
					else this.senior.removeCat(c);
				}
				else if (c.monthHired > this.data.monthHired) { // in junior subtree
					if (this.junior == null) return this;
					else this.junior.removeCat(c);
				} 
				else if (c.monthHired == this.data.monthHired) { // in same subtree
					if (this.same == null) return this;
					else { 
						if (c.furThickness <= this.data.furThickness) this.same.removeCat(c);
					}
				} 
			}
			return this;
		}

		/**
		 * @return the smallest integer of the monthHired of the tree
		 */
		public int mostSenior() {
			if (root == null) // if no tree exist
				return 0; 
			else if (this.senior == null)
				return this.data.monthHired;
			else //(this.senior != null)
				return this.senior.mostSenior();
		}

		/**
		 * c.fluffiest() should be the fur thickness of the CatNode with greatest fur thickness 
		 * in the tree with root c.
		 * @return the biggest integer of the furThickness of the tree 
		 */
		public int fluffiest() { 
			int fluffiestSenior = 0;
			int fluffiestJunior = 0;
			int fluffiest = 0;
			if (root == null) return 0;
			if (this.senior!=null) fluffiestSenior = this.senior.fluffiest();
			if (this.junior!=null) fluffiestJunior = this.junior.fluffiest();
			// no need to check this.same since the node with thicker fur is located on the top
			fluffiest = Math.max(Math.max(fluffiestSenior, fluffiestJunior), this.data.furThickness);
			return fluffiest;
		}

		/**
		 * c.hiredrFromMonths(monthMin, monthMax) should be the number of cats hired from month
		 * monthMin to monthMax (including those two months), that is in the tree with root c. 
		 * If monthMin > monthMax, @return 0.
		 * @return the integer of the number of cats hired from monthMin to month Max.
		 */
		public int hiredFromMonths(int monthMin, int monthMax) {
			int numCat = 0;
			if (monthMin > monthMax) return 0;
			else{ // valid input: monthMin <= monthMax
				if (this.data.monthHired >= monthMin && this.data.monthHired <= monthMax) numCat++; // count this node if valid
				if (this.senior != null)numCat += this.senior.hiredFromMonths(monthMin, monthMax); // check all senior subtree
				if (this.same != null) numCat += this.same.hiredFromMonths(monthMin, monthMax); // check all same subtree
				if (this.junior != null) numCat += this.junior.hiredFromMonths(monthMin, monthMax); // check all junior subtree
				return numCat;
			}
		}

		/***
		 * @return null if no such cat is found
		 * @return the CatInfo linked to the cat with thickest fur, hired in the given input month month, 
		 * in the tree with root c.
		 */
		public CatInfo fluffiestFromMonth(int month) {
			try{
				if (month == this.data.monthHired) 
					return this.data; // no this to check same subtree since it has the thickest fur
				else if (month < this.data.monthHired) 
					return this.senior.fluffiestFromMonth(month); // check senior subtree
				else  //(month > this.data.monthHired)
					return this.junior.fluffiestFromMonth(month); // check junior subtree
			}
			catch(Exception e){ // no such cat is found
				return null; //return null; 
			} 
		}

		/**
		 * @return the array of @param nbMonths size with total expectedGroomingCost corresponding to the month of index-243
		 */
		public int[] costPlanning(int nbMonths) {
			int[] costPlan = new int[nbMonths]; // create an array of size nbMonths
			if (nbMonths != 0) { // if nbMonths is not 0
				CatTreeIterator catIterator = new CatTreeIterator(); // create an iterator
				while (catIterator.hasNext()) { // as long as catIterator hasNext catInfo
					CatInfo currCat = catIterator.next(); // update currCat
					if (currCat.nextGroomingAppointment - 243 < nbMonths && currCat.nextGroomingAppointment >= 243)  
						costPlan[currCat.nextGroomingAppointment - 243] += currCat.expectedGroomingCost;
				}
			} 
			return costPlan;
		}
	}

	/**
	 *  @return a CatTreeIterator object which can be used to iterate through all the cats in the tree.
	 */
	private class CatTreeIterator implements Iterator<CatInfo> {
		ArrayList<CatInfo> array = new ArrayList<CatInfo>();
		int currCat; // track index

		public CatTreeIterator() {
			currCat = 0;
			inOrderTraversal(root); 
		}

		private void inOrderTraversal(CatNode root) { //access the cats from most senior to most junior.
			if (root.senior != null) inOrderTraversal(root.senior); // traverse senior subtree
			if (root.same != null) inOrderTraversal(root.same); // traverse same subtree; cats with thicker fur should be access later.
			array.add(root.data);
			if (root.junior != null) inOrderTraversal(root.junior); //traverse junior subtree
		}

		public CatInfo next(){
			if (currCat >= array.size()) throw new NoSuchElementException("no such cat exist.");
			CatInfo tmpCat = array.get(currCat);
			currCat ++;
			return tmpCat;
		}

		public boolean hasNext() { // check has next catInfo
			return (currCat + 1 <= array.size());
		} 
	}
}

