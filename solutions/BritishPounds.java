package com.algorithms;


public class BritishPounds {
	private static int[] denominations={1, 2, 5, 10, 20, 50, 100, 200};
	
	public static int getNumberOfPossibleCombination(int remainingMoney, int numOfCoins) {
		//I don't any thing left to compare the possibilities, So return 1 which includes the current possibility
		if(remainingMoney==0) {
			return 1;
		}
		
		//Return 0 when I still have remainingAmount and left with no coins
		//or when I run into negative balance
		if(remainingMoney<0 || (numOfCoins<=0 && remainingMoney>=1)) {
			return 0;
		}
		
		//Possible moves can be based on 
		//1. current denomination and possibilities with remaining coins
		//2. possibilities with remaining
		return getNumberOfPossibleCombination(remainingMoney, numOfCoins-1) + getNumberOfPossibleCombination(remainingMoney-denominations[numOfCoins-1], numOfCoins);
		
		
	}
	
	/** Above approach uses recursion, and calculates the computed values again and again.
	 * For example : denominations={1,2,5} ; numOfCoins= 3, moneyTooBeCalculated = 4 ; 
	 * Fn(4,3) = Fn(3,3-1) + Fn(4-1,3) = Fn(3,2) +Fn(3,3)
	 * 		Fn(3,2)=Fn(3,1)+Fn(1,2)		& 		Fn(3,3)=Fn(3,2)+Fn(1,3)
	 * Fn(3,2) will be again calculated if we use the above approach
	 * 
	 * 
	 * We can use dynamic programming to avoid this problem, by storing the previously computed values. Let's say amountToBeConverted = 200 (just same as the other test case)
	 * 
	 * We can represent all the possible Fn using 2D matrix
	 * Target table looks as follows
	 * 
	 * 				Fn(0,0)	Fn(0,1)	Fn(0,2)	Fn(0,3)
	 * 				Fn(1,0)	Fn(1,1)	Fn(1,2)	Fn(1,3)
	 * 				Fn(2,0)	Fn(2,1)	Fn(2,2)	Fn(2,3)
	 * Fn(4,3) = 	Fn(3,0)	Fn(3,1)	Fn(3,2)	Fn(3,3)
	 * 				Fn(4,0)	Fn(4,1) Fn(4,2)	Fn(4,3)
	 * 
	 * So, there we have the desired element on the bottom right corner.
	 * 
	 * Fn(0, anything) means, we have many coins, but money to be calculated is 0.. So first case : return 1
	 * Same logic, Fn(amount, coins-1) + Fn(amount-demoniation, coins)
	 * 
	 * But, we can store whenever we calculate one value, and reuse it at the time we use it again.
	 * 
	 * for all i values : until we reach amountToBeConverted
	 * 		j=0 : until we reach the end of denominations
	 * 				target[i][j] = (i-target[i-currentDemoniationValueAt_j][j]  if i-currentDemoniationValueAt_j >= 0) + (target[i][j-1] if j>=1)
	 * 
	 * So, Calculating from the bottom, we will be left with the final answer in the target[amountToBeCalculated][numOfCoins-1]
	 * 
	 * 
	 * 
	 */
	public static int getNumberOfCombinationWORecursion(int remainingMoney, int numOfCoins) {
		int[][] target= new int[remainingMoney+1][numOfCoins];
		
		//No amount, for all dominations
		for(int j=0;j<numOfCoins;j++) {
			target[0][j]=1;
		}
		
		//for all i=0, are already calculated, so start from 1, and continue till remainingMoney + 1
		for(int i=1;i<remainingMoney+1; i++) {
			
			for(int j=0;j<numOfCoins;j++) {
				int firstPossibility = 0;
				if(i-denominations[j] >= 0) {
					firstPossibility = target[i-denominations[j]][j];
				}
				
				int secondPossibility = 0;
				if(j-1 >= 0) {
					secondPossibility = target[i][j-1];
				}
				
				target[i][j] += firstPossibility + secondPossibility;
				
			}
		}
		for(int i=0;i<target.length;i++) {
			for(int j=0;j<target[0].length;j++) {
				System.out.print(target[i][j]+"\t");
			}
			System.out.print("\n");
		}
		return target[remainingMoney][numOfCoins-1];
		
	}
	
	/**
	 * Can we do it better? Yes, It can be still reduced
	 * 
	 * Just by retaining only the last elements of each row in the 2D matrix, since all the other elements are used only for computation.
	 * 
	 * Converting above 2D matrix into a single array, have the array of size numberToBeCalculated +1
	 * 
	 * If the values of matrix are observed, the next change for any row occurs from the denomination[i]
	 * For example: 
	 * As per the problem statement,
	 * first iteration j=0, fixes values from denominationValueAt_1stindex : 2  (Values will not be modified there after)
	 * second iteration j=1, fixes values until denominationValueAt_2ndindex : 5
	 * third iteration j=2, fixes values until denominationValueAt_3rdindex : 10
	 * .
	 * .
	 * last but one, j= numOfCoins-1 , fixes values until denominationValueAt_numOfCoins_index : 200
	 * last iteration, j=numOfCoins, remaining elements will be fixed until amountToBeCalculated : 200 (In this case)
	 * 
	 * And the last element would give answer
	 * 
	 */
	public static int getNumberOfCombinationWMinSpace(int remainingMoney, int numOfCoins) {
		int[] newTarget= new int[remainingMoney+1];
		
		//No amount
		newTarget[0] = 1;
		
		//for all i=0, are already calculated, so start from 1, and continue till remainingMoney + 1
		for(int j=0;j<numOfCoins; j++) {
			
			for(int i=denominations[j];i<remainingMoney+1;i++) {
				
				newTarget[i] += newTarget[i-denominations[j]];
				
			}
			for(int k=0;k<newTarget.length;k++) {
				System.out.print(newTarget[k]+"\t");
				
			}
			System.out.print("\n");
		}
		for(int i=0;i<newTarget.length;i++) {
			System.out.print(newTarget[i]+"\t");
			
		}
		System.out.print("\n");
		return newTarget[remainingMoney];
		
	}
	
	public static void main(String[] args) {
		System.out.println(BritishPounds.getNumberOfPossibleCombination(200, denominations.length));
		System.out.println(BritishPounds.getNumberOfCombinationWORecursion(200, denominations.length));
		System.out.println(BritishPounds.getNumberOfCombinationWMinSpace(200, denominations.length));
	}
}
