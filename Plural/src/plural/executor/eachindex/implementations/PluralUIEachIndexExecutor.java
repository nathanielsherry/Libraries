package plural.executor.eachindex.implementations;


import fava.signatures.FnEach;
import plural.executor.PluralSet;

public class PluralUIEachIndexExecutor extends PluralEachIndexExecutor{

	public PluralUIEachIndexExecutor(int size, FnEach<Integer> pluralEachIndex) {
		super(size, pluralEachIndex);
	}
	
		
	@Override
	public void executeBlocking()
	{

		super.plural.advanceState();

		super.execute(super.threadCount);
		
		if (plural.pluralSet != null && plural.pluralSet.isAbortRequested()) {
			plural.pluralSet.aborted(); 
		}

		super.plural.advanceState();
				
	
	}


	@Override
	protected void workForExecutor()
	{

		int blockNum;
		
		while (true) {

			int blockStart, blockEnd;
		
			//get the block number to work on
			blockNum = super.ticketManager.getTicketBlockIndex();
					
			//if there are no more block numbers left, exit
			if (blockNum == -1) break;
			
			//calculate start, stop indexes
			blockStart = super.ticketManager.getBlockStart(blockNum);
			blockEnd = blockStart + super.ticketManager.getBlockSize(blockNum);
			
			
			//do work
			for (int i = blockStart; i < blockEnd; i++) {
				super.eachIndex.f(i);
			}
			
			if (plural.pluralSet != null) {

				super.plural.workUnitCompleted(blockEnd - blockStart);
				if (plural.pluralSet.isAbortRequested()) return;
			}

		}


	}
	
	
	@Override
	public int getDesiredBlockSize()
	{
		return Math.max(super.getDataSize() / (calcNumThreads() * 50), 1);
	}
	
}
