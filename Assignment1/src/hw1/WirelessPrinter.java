/**
 * This class represents a wireless printer
 * @author Alyssa Esquivel
 */

package hw1;

public class WirelessPrinter{
	
	/**
	 * Constant field values:
	 */
	public static final double NEW_CARTRIDGE_INK_LEVEL = 1.0;
	public static final int PAGES_PER_CARTRIDGE = 1000;
	public static final int TRAY_CAPACITY = 500;
	
	/**
	 * The max amount of paper the printer is GIVEN by the user - cannot be more than TRAY_CAPACITY
	 */
	private int paper_capacity = 0;
	
	/**
	 * Keeps track of the pages printed (used)
	 */
	private int printed_count = 0;
	
	/**
	 * The number of pages printed with ink
	 */
	private int printed_withInk = 0;
	
	/**
	 * The amount of paper currently left in the printer available for use
	 */
	private int currentPaperNum = 0;
	
	/**
	 * Current amount of ink left in the printer to use
	 */
	private double current_ink_level = 0.0;
	
	/**
	 * Connection status of the printer
	 */
	private boolean connection_status;
	
	/**
	 * Power status of the printer
	 */
	private boolean power;
	
	/**
	 * Creates a new printer. The printer initially has a cartridge that is half full and no paper.
	 */
	public WirelessPrinter() {
		current_ink_level = NEW_CARTRIDGE_INK_LEVEL / 2;
		paper_capacity = 0;
		power = false;
	}
	
	/**
	 * Create a new printer with a specified amount of ink and paper.
	 * @param ink - initial ink level: must be between 0.0 and 1.0
	 * @param paper - initial paper level: must be between 0 and 500.
	 */
	public WirelessPrinter(double ink, int paper) {
		current_ink_level = Math.min(ink, NEW_CARTRIDGE_INK_LEVEL);
		paper_capacity = Math.min(paper, TRAY_CAPACITY);
		currentPaperNum = paper_capacity;
	}
	
	/**
	 * Turns the printer on and immediately connects it to the network
	 */
	public void turnOn() {
		power = true;
		connection_status = true;
	}
	
	/**
	 * Turns off the printer and disconnects the wirless connection
	 */
	public void turnOff() {
		connection_status = false;
		power = false;
	}
	
	/**
	 * returns the power status of the printer
	 * @return
	 */
	public boolean isOn() {
		return power;
	}
	
	/**
	 * Connects the printer to the network
	 */
	public void connect() {
		connection_status = true;
	}
	
	/**
	 * Disconnects the wirless connection
	 */
	public void disconnect() {
		connection_status = false;
	}
	
	/**
	 * returns the status of the current wireless connection
	 * @return true if connected and false otherwise
	 */
	public boolean isConnected() {
		return connection_status;
	}
	
	/**
	 * returns the total number of pages used (blank pages do NOT count)
	 * @return total pages actually printed
	 */
	public int getTotalPagesPrinted() {
		return printed_count;
	}
	
	/**
	 * returns the total number of pages printed (ink or not)
	 * @return total pages used with ink on them
	 */
	public int getTotalPaperUsed() {
		return printed_withInk;
	}
	
	/**
	 * replace the cartridge - ink level is reset to NEW_CARTRIDGE_INK_LEVEL
	 */
	public void replaceCartridge() {
		current_ink_level = NEW_CARTRIDGE_INK_LEVEL;
	}
	
	/**
	 * loads more paper to the printer - number of pages in the tray cannot surpass its capacity!
	 * @param pages - the number of pages to be added
	 */
	public void loadPaper(int pages) {
		if((pages + currentPaperNum) > paper_capacity) {
			currentPaperNum = Math.min(pages, currentPaperNum);
		}
		currentPaperNum += pages;
	}
	
	/**
	 * returns the current ink level as a double value that is between 0.0 and 1.0
	 * @return ink level
	 */
	public double getInkLevel() {
		return current_ink_level;
	}
	
	/**
	 * returns how much paper, in percentage, is left in the tray
	 * @return paper amount left in percentage
	 */
	public int getPaperLevel() {
		return (int) Math.round(currentPaperNum * 100 / TRAY_CAPACITY);
	}
	
	/**
	 * returns the exact amount of sheets of paper left in the tray
	 * @return number of sheets of paper in the tray
	 */
	public int getPaperLevelExact() {
		return currentPaperNum;
	}
	
	/**
	 * This method simulates printing certain number of pages. If there is not enough 
	 * paper left in the tray, the printing will stop once the paper runs out, and the 
	 * remaining job (if any) is cancelled. If ink runs out (i.e., not enough to print 
	 * one more page) during the printing, the printing will NOT Stop, but only blank 
	 * pages are printed (i.e., the printer continues to consume paper, but not ink, 
	 * until the job has completed).
	 * @param - the requested number of pages to be printed 
	 */
	public void print(int pages) {
		if(!isConnected())
			return;
		
		int blank_pages = 0;
		printed_count += Math.min(pages, paper_capacity);
		
		if(current_ink_level >= 0.0 && current_ink_level <= 1.0) {
			current_ink_level -= (Math.min(currentPaperNum, TRAY_CAPACITY / 10) * .001);
			printed_withInk = printed_count;
		}
		if(current_ink_level <= 0.0 || current_ink_level >= 1.0) {
			blank_pages += pages - currentPaperNum;
			printed_withInk = printed_count + blank_pages;
		}
		currentPaperNum = paper_capacity - printed_count;
	}
}