import java.util.ArrayList;


public class DataCycle {
	private int cycle;
	// cycle,multiply,addition,load,store,register,exec,waiting
	private ReservationStation[] multiplyStation;
	private ReservationStation[] additionStation;
	private LoadStation[] loadBuffer;
	private StoreStation[] storeBuffer;
	private Register[] registerFile;
	private ArrayList<Object> executingInstructions;
	private ArrayList<String> waitingInstructions;
	
	public DataCycle(int cycle, ReservationStation[] multiplyStation,
			ReservationStation[] additionStation, LoadStation[] loadBuffer,
			StoreStation[] storeBuffer, Register[] registerFile,
			ArrayList<Object> executingInstructions,
			ArrayList<String> waitingInstructions) {
		super();
		this.cycle = cycle;
		this.multiplyStation = multiplyStation;
		this.additionStation = additionStation;
		this.loadBuffer = loadBuffer;
		this.storeBuffer = storeBuffer;
		this.registerFile = registerFile;
		this.executingInstructions = executingInstructions;
		this.waitingInstructions = waitingInstructions;
	}
	public int getCycle() {
		return cycle;
	}
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	public ReservationStation[] getMultiplyStation() {
		return multiplyStation;
	}
	public void setMultiplyStation(ReservationStation[] multiplyStation) {
		this.multiplyStation = multiplyStation;
	}
	public ReservationStation[] getAdditionStation() {
		return additionStation;
	}
	public void setAdditionStation(ReservationStation[] additionStation) {
		this.additionStation = additionStation;
	}
	public LoadStation[] getLoadBuffer() {
		return loadBuffer;
	}
	public void setLoadBuffer(LoadStation[] loadBuffer) {
		this.loadBuffer = loadBuffer;
	}
	public StoreStation[] getStoreBuffer() {
		return storeBuffer;
	}
	public void setStoreBuffer(StoreStation[] storeBuffer) {
		this.storeBuffer = storeBuffer;
	}
	public Register[] getRegisterFile() {
		return registerFile;
	}
	public void setRegisterFile(Register[] registerFile) {
		this.registerFile = registerFile;
	}
	public ArrayList<Object> getExecutingInstructions() {
		return executingInstructions;
	}
	public void setExecutingInstructions(ArrayList<Object> executingInstructions) {
		this.executingInstructions = executingInstructions;
	}
	public ArrayList<String> getWaitingInstructions() {
		return waitingInstructions;
	}
	public void setWaitingInstructions(ArrayList<String> waitingInstructions) {
		this.waitingInstructions = waitingInstructions;
	}
	

}
