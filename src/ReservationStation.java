
public class ReservationStation {
	private int busy=0;
	private int time=-1;
	private String tag="";
	private String op="";
	private double vj;
	private double vk;
	private String qj="";
	private String qk="";
	private String A="";
	private boolean ready=false;
	private String instruction="";
	private int issuedCycle;
	private int executedCycle;
	private int finishedCycle;
	public ReservationStation(String instruction){
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

	public boolean isReady() {
		return ready;
	}

	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
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
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	
	public double getVj() {
		return vj;
	}
	public void setVj(double vj) {
		this.vj = vj;
	}
	public double getVk() {
		return vk;
	}
	public void setVk(double vk) {
		this.vk = vk;
	}
	public String getQj() {
		return qj;
	}
	public void setQj(String qj) {
		this.qj = qj;
	}
	public String getQk() {
		return qk;
	}
	public void setQk(String qk) {
		this.qk = qk;
	}
	public String getA() {
		return A;
	}
	public void setA(String a) {
		A = a;
	}
	public String toString(){
		
		String s="";
		if(time!=-1){
			s+="Time: "+time;
		}
		s+=" Tag: "+tag;
		s+=" Busy: "+busy;
		s+=" OP: "+op;
		if(qj.equals(""))
			s+=" Vj: "+vj;
		else {
			s+=" Vj: not ready";
		}
		if(qk.equals(""))
			s+=" Vk:"+vk;
		else {
			s+=" Vk: not ready";
		}
		if(!qj.equals(""))
			s+=" Qj: "+qj;
		if(!qk.equals(""))
			s+=" Qk: "+qk;
		if(!A.equals(""))
			s+=" A: "+A;
		else {
			s+=" A: none";
		}
		return s;
	}
	
	
}
