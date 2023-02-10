package Engine;

public class LoadStation {
	private int busy=0;
	private int address;
	private String tag="";
	private String qi="";
	private int time=-1;
	private String instruction="";
	private boolean ready=false;
	private double value;
	private int issuedCycle;
	private int executedCycle;
	private int finishedCycle;
	public LoadStation(int busy, int address, String tag,String instruction) {
		super();
		this.busy = busy;
		this.address = address;
		this.tag = tag;
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
	
	public String getQi() {
		return qi;
	}


	public void setQi(String qi) {
		this.qi = qi;
	}


	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
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
	public String toString(){
		
		String s="";
		if(time!=-1){
			s+="Time: "+time;
		}
		s+=" Tag: "+tag;
		s+=" Busy: "+busy;
		s+=" Address: "+address;
		if(!qi.equals("")){
			s+=" Waiting For: "+qi;
		}
		return s;
	}

}
