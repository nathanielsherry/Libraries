package plural.workers.executor;

import java.util.ArrayList;
import java.util.List;

import plural.workers.PluralMap;

public class TicketManager {

	private List<Integer>	ticketBlockStart;
	private List<Integer>	ticketBlockSize;
	
	private int				totalBlocks;
	private int				nextBlock = 0;
	
	public TicketManager(int taskSize, int blockSize) {
	
		this.totalBlocks = (int)Math.ceil(taskSize / (double)blockSize);
		
		
		ticketBlockStart = new ArrayList<Integer>(totalBlocks);
		ticketBlockSize = new ArrayList<Integer>(totalBlocks);

		int ticketsAssigned = 0;

		// set the ticket counts
		for (int i = 0; i < totalBlocks - 1; i++) {
		
			ticketBlockStart.add(ticketsAssigned);
			ticketBlockSize.add(blockSize);

			ticketsAssigned += blockSize;
		}
		ticketBlockStart.add(ticketsAssigned);
		ticketBlockSize.add(taskSize - ticketsAssigned);
		
	}
	
	/**
	 * Returns the starting index for the block of work to be done by the {@link PluralMap} for this thread.
	 * @param threadNum the thread number.
	 * @return the starting index for the associated block of work
	 */
	public int getBlockStart(int threadNum)
	{
		return ticketBlockStart.get(threadNum);
	}

	/**
	 * Returns the size of the block of work to be done by the {@link PluralMap} for this thread.
	 * @param threadNum the thread number.
	 * @return the size of the associated block of work
	 */
	public int getBlockSize(int threadNum)
	{
		return ticketBlockSize.get(threadNum);

	}
	
	public synchronized int getTicketBlockIndex()
	{
		if (nextBlock >= totalBlocks) return -1;
		return nextBlock++;
	}
	
}
