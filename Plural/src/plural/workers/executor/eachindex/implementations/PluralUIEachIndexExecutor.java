package plural.workers.executor.eachindex.implementations;


import plural.workers.PluralEachIndex;
import plural.workers.PluralSet;

public class PluralUIEachIndexExecutor extends PluralEachIndexExecutor{

	PluralSet<?>	pluralSet;
	
	public PluralUIEachIndexExecutor(int size, PluralEachIndex pluralEachIndex, PluralSet<?> pluralSet) {
		super(size, pluralEachIndex);
		this.pluralSet = pluralSet;
	}
	
	
	@Override
	public void executeBlocking()
	{

		super.pluralEachIndex.advanceState();

		super.execute(super.threadCount);
		
		if (pluralSet != null && pluralSet.isAbortRequested()) {
			pluralSet.aborted(); 
		}

		super.pluralEachIndex.advanceState();
				
	
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
				super.pluralEachIndex.f(i);
			}
			
			if (pluralSet != null) {

				pluralEachIndex.workUnitCompleted(blockEnd - blockStart);
				if (pluralSet.isAbortRequested()) return;
			}

		}


	}
	
	
	@Override
	public int getDesiredBlockSize()
	{
		return Math.max(super.getDataSize() / (calcNumThreads() * 50), 1);
	}
	
}
