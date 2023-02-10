package Engine;

public class StoreStation {
	private int busy=0;
	private int address;
	private String tag="";
	private int time=-1;
	private double storeValue;
	private String queueValue="";
	private boolean ready=false;
	private String instruction="";
	private int issuedCycle;
	private int executedCycle;
	private int finishedCycle;
	public StoreStation(int busy, int address, String tag, double storeValue,
			String queueValue,String instruction) {
		super();
		this.busy = busy;
		this.address = address;
		this.tag = tag;
		this.storeValue = storeValue;
		this.queueValue = queueValue;
		this.instruction=instruction;
	}
	public int getIssuedCycle() {
		return issuedCycle;
	}

	public void setIssuedCycle(int issuedCycle) {
		this.issuedCycle = issuedCycle;
	}

	public int getExecutedCycle() {
		return executedCycle;
	}

	public void setExecutedCycle(int executedCycle) {
		this.executedCycle = executedCycle;
	}

	public int getFinishedCycle() {
		return finishedCycle;
	}

	public void setFinishedCycle(int finishedCycle) {
		this.finishedCycle = finishedCycle;
	}

	public String getQueueValue() {
		return queueValue;
	}

	public void setQueueValue(String queueValue) {
		this.queueValue = queueValue;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}
	public int getBusy() {
		return busy;
	}
	public void setBusy(int busy) {
		this.busy = busy;
	}
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public double getStoreValue() {
		return storeValue;
	}
	public void setStoreValue(double storeValue) {
		this.storeValue = storeValue;
	}
	public String toString(){
		
		String s="";
		if(time!=-1)
			s+="Time: "+time;
		s+=" Tag: "+tag;
		s+=" Busy: "+busy;
		s+=" Destination Address: "+address;
		if(queueValue.equals(""))
			s+=" Store Value: "+storeValue +" Qi: none ";
		else {
			s+=" Store Value: not ready Qi: "+queueValue;
		}
		return s;
	}
	

}
