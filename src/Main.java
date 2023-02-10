import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import javafx.scene.control.SplitPane.Divider;

import javax.activation.MailcapCommandMap;
import javax.security.auth.Subject;
import javax.swing.plaf.multi.MultiButtonUI;
import javax.xml.stream.events.StartDocument;

import org.omg.PortableServer.ServantActivator;

import com.sun.media.jfxmedia.events.NewFrameEvent;


public class Main {
	public static Queue<String> instructions=new LinkedList<String>();
	public static ReservationStation[] multiplyStation=new ReservationStation[2];//2
	public static ReservationStation[] additionStation=new ReservationStation[3];//3
	public static LoadStation[] loadBuffer=new LoadStation[3] ; //3
	public static StoreStation[] storeBuffer=new StoreStation[3] ; //3
	public static Register[] registerFile=new Register[32] ; //size 32
	public static ArrayList<Object> executingInstruction=new ArrayList();
	public static ArrayList<String> waitingInstructions=new ArrayList();
	public static ArrayList<Object> finishedAll=new ArrayList<>();
	public static double[] memory=new double[1024];
	public static ArrayList<DataCycle> cyclesData=new ArrayList(); // cycle,multiply,addition,load,store,register,exec,waiting
	public static int cycle=1;
	public static int loadCycles;
	public static int additionCycles;
	public static int subtractionCycles;
	public static int storeCycles;
	public static int mulCycles;
	public static int divCylces;
	public Main(){
		for(int i=0;i<registerFile.length;i++){
			registerFile[i]=new Register("f"+i, i, "");
		}
		for(int i=0;i<memory.length;i++){
			memory[i]=2*i;
		}
		
		start();
	}
	public static void addInstruction(String instruction){
		instructions.add(instruction);
		
	}
	public static boolean isFull(Object[] arr){
		for(int i=0;i<arr.length;i++){
			if(arr[i]==null)
				return false;
		}
		return true;
	}
	public static int firstEmptyPosition(Object[] arr){
		for(int i=0;i<arr.length;i++){
			if(arr[i]==null)
				return i;
		}
		return -1;
	}
	public static void issueInstruction(String instruction){
		String[] arr2=instruction.toLowerCase().split(" ");
		String op=arr2[0];
		String[] arr=arr2[1].split(",");
		String destination="";
		String source1="";
		String source2="";
		int address;
		switch(op.toLowerCase()){
		case "l.d": 
			address=Integer.parseInt(arr[1]);
			destination=arr[0];
			if(!isFull(loadBuffer)){
				int index=firstEmptyPosition(loadBuffer);
				loadBuffer[index]=new LoadStation(1,address,"L"+(index+1),instruction);
				if(waitingForStore(address).equals("")){
					loadBuffer[index].setReady(true);
					loadBuffer[index].setTime(loadCycles);
				}else{
					loadBuffer[index].setQi(waitingForStore(address));
				}
				loadBuffer[index].setIssuedCycle(cycle);
				int destReg=Integer.parseInt(destination.substring(1));
				registerFile[destReg]=new Register("f"+destReg,0,"L"+(index+1));
				
			}else{
				waitingInstructions.add(instruction);
				
			}
			instructions.remove();
			break;
		case "s.d":
			address=Integer.parseInt(arr[1]);
			source1=arr[0];
			int registerIndex=Integer.parseInt(source1.substring(1));
			if(!isFull(storeBuffer)){
				if(waitingForLoad(address)!="" || waitingForStore(address)!=""){
					waitingInstructions.add(instruction);
				}else{
					if(registerFile[registerIndex].getQi()==""){
						int index=firstEmptyPosition(storeBuffer);
						storeBuffer[index]=new StoreStation(1, address, "S"+(index+1), 
								registerFile[registerIndex].getValue(), "",instruction);
						storeBuffer[index].setReady(true);
						storeBuffer[index].setTime(storeCycles);
						storeBuffer[index].setIssuedCycle(cycle);
					}else{
						int index=firstEmptyPosition(storeBuffer);
						storeBuffer[index]=new StoreStation(1, address, "S"+(index+1), 
								0,registerFile[registerIndex].getQi(),instruction );
						storeBuffer[index].setReady(false);
						storeBuffer[index].setIssuedCycle(cycle);
					}
					
				}
				
			}else{
				waitingInstructions.add(instruction);
			}
			instructions.remove();
			break;
		case "add.d":
			destination=arr[0];
			source1=arr[1];
			source2=arr[2];
			if(!isFull(additionStation)){
				
				ReservationStation newStation=new ReservationStation(instruction);
				newStation.setIssuedCycle(cycle);
				int registerIndex1=Integer.parseInt(source1.substring(1));
				int registerIndex2=Integer.parseInt(source2.substring(1));
				if(registerFile[registerIndex1].getQi()==""){
					newStation.setVj(registerFile[registerIndex1].getValue());
					newStation.setQj("");
					
				}else{
					newStation.setQj(registerFile[registerIndex1].getQi());
				}
				if(registerFile[registerIndex2].getQi()==""){
					newStation.setVk(registerFile[registerIndex2].getValue());
					newStation.setQk("");
				}else{
					newStation.setQk(registerFile[registerIndex2].getQi());
				}
				newStation.setBusy(1);
				newStation.setOp("add");
				int index=firstEmptyPosition(additionStation);
				newStation.setTag("A"+(index+1));
				if(newStation.getQj()=="" && newStation.getQk()==""){
					newStation.setReady(true);
					newStation.setTime(additionCycles);
				}
				additionStation[index]=newStation;
				int destReg=Integer.parseInt(destination.substring(1));
				registerFile[destReg]=new Register("f"+destReg, 0, newStation.getTag());
				
			}else{
				waitingInstructions.add(instruction);
			}
			instructions.remove();
			break;
		case "sub.d" :
			destination=arr[0];
			source1=arr[1];
			source2=arr[2];
			if(!isFull(additionStation)){
				
				ReservationStation newStation=new ReservationStation(instruction);
				newStation.setIssuedCycle(cycle);
				int registerIndex1=Integer.parseInt(source1.substring(1));
				int registerIndex2=Integer.parseInt(source2.substring(1));
				if(registerFile[registerIndex1].getQi()==""){
					newStation.setVj(registerFile[registerIndex1].getValue());
				}else{
					newStation.setQj(registerFile[registerIndex1].getQi());
				}
				if(registerFile[registerIndex2].getQi()==""){
					newStation.setVk(registerFile[registerIndex2].getValue());
				}else{
					newStation.setQk(registerFile[registerIndex2].getQi());
				}
				newStation.setBusy(1);
				newStation.setOp("sub");
				int index=firstEmptyPosition(additionStation);
				newStation.setTag("A"+(index+1));
				if(newStation.getQj()=="" && newStation.getQk()==""){
					newStation.setReady(true);
					newStation.setTime(subtractionCycles);
				}
				additionStation[index]=newStation;
				int destReg=Integer.parseInt(destination.substring(1));
				registerFile[destReg]=new Register("f"+destReg, 0, newStation.getTag());
				
			}else{
				waitingInstructions.add(instruction);
			}
			instructions.remove();
			break;
		case "mul.d":
			destination=arr[0];
			source1=arr[1];
			source2=arr[2];
			if(!isFull(multiplyStation)){
				
				ReservationStation newStation=new ReservationStation(instruction);
				newStation.setIssuedCycle(cycle);
				int registerIndex1=Integer.parseInt(source1.substring(1));
				int registerIndex2=Integer.parseInt(source2.substring(1));
				if(registerFile[registerIndex1].getQi()==""){
					newStation.setVj(registerFile[registerIndex1].getValue());
				}else{
					newStation.setQj(registerFile[registerIndex1].getQi());
				}
				if(registerFile[registerIndex2].getQi()==""){
					newStation.setVk(registerFile[registerIndex2].getValue());
				}else{
					newStation.setQk(registerFile[registerIndex2].getQi());
				}
				newStation.setBusy(1);
				newStation.setOp("mul");
				int index=firstEmptyPosition(multiplyStation);
				newStation.setTag("M"+(index+1));
				if(newStation.getQj()=="" && newStation.getQk()==""){
					newStation.setReady(true);
					newStation.setTime(mulCycles);
				}
				multiplyStation[index]=newStation;
				int destReg=Integer.parseInt(destination.substring(1));
				registerFile[destReg]=new Register("f"+destReg, 0, newStation.getTag());
				
			}else{
				waitingInstructions.add(instruction);
			}
			instructions.remove();
			break;
		case "div.d":
			destination=arr[0];
			source1=arr[1];
			source2=arr[2];
			if(!isFull(multiplyStation)){
				
				ReservationStation newStation=new ReservationStation(instruction);
				newStation.setIssuedCycle(cycle);
				int registerIndex1=Integer.parseInt(source1.substring(1));
				int registerIndex2=Integer.parseInt(source2.substring(1));
				if(registerFile[registerIndex1].getQi()==""){
					newStation.setVj(registerFile[registerIndex1].getValue());
				}else{
					newStation.setQj(registerFile[registerIndex1].getQi());
				}
				if(registerFile[registerIndex2].getQi()==""){
					newStation.setVk(registerFile[registerIndex2].getValue());
				}else{
					newStation.setQk(registerFile[registerIndex2].getQi());
				}
				newStation.setBusy(1);
				newStation.setOp("div");
				int index=firstEmptyPosition(multiplyStation);
				newStation.setTag("M"+(index+1));
				if(newStation.getQj()=="" && newStation.getQk()==""){
					newStation.setReady(true);
					newStation.setTime(divCylces);
				}
				multiplyStation[index]=newStation;
				int destReg=Integer.parseInt(destination.substring(1));
				registerFile[destReg]=new Register("f"+destReg, 0, newStation.getTag());
				
			}else{
				waitingInstructions.add(instruction);
			}
			instructions.remove();
			break;
		}	
	}
	public static String waitingForStore(int address){
		for(int i=0;i<storeBuffer.length;i++){
			if(storeBuffer[i]!=null && storeBuffer[i].getAddress()==address){
				return storeBuffer[i].getTag();
			}
		}
		
		return "";
	}
	public static String waitingForLoad(int address){
		for(int i=0;i<loadBuffer.length;i++){
			if(loadBuffer[i]!=null && loadBuffer[i].getAddress()==address){
				return loadBuffer[i].getTag();
			}
		}
		
		return "";
	}
	public static void publish(){
		ArrayList<Object> finishedExecution=new ArrayList<>();
		for(int i=0;i<executingInstruction.size();i++){
			if(executingInstruction.get(i) instanceof ReservationStation){
				ReservationStation inst=(ReservationStation) executingInstruction.get(i);
				if ( ((ReservationStation) executingInstruction.get(i)).getTime()<=0 ){
					finishedExecution.add(inst);
					
				}
			}
			else if(executingInstruction.get(i) instanceof LoadStation){
				LoadStation inst=(LoadStation) executingInstruction.get(i);
				if(inst.getTime()<=0){
					finishedExecution.add(inst);
				}
			}else if(executingInstruction.get(i) instanceof StoreStation){
				StoreStation inst=(StoreStation) executingInstruction.get(i);
				if(inst.getTime()<=0){
					finishedExecution.add(inst);
				}
			}
		}
		if(finishedExecution.size()==1){
			double result=0;
			
			Object inst=finishedExecution.get(0);
			if(inst instanceof ReservationStation){
				String op=((ReservationStation) inst).getOp();
				String tag=((ReservationStation) inst).getTag();
				if(op.equals("add")){
					result=((ReservationStation)inst).getVj()+((ReservationStation)inst).getVk();
				}else if(op.equals("sub")){
					result=((ReservationStation)inst).getVj()-((ReservationStation)inst).getVk();
				}else if(op.equals("mul")){
					result=((ReservationStation)inst).getVj()*((ReservationStation)inst).getVk();
				}else if(op.equals("div")){
					result=((ReservationStation)inst).getVj()/((ReservationStation)inst).getVk();
				}
				updateRegAndStations(tag, result);
				removeFromStation(tag);
				((ReservationStation) inst).setFinishedCycle(cycle-1);
				for(int i=0;i<waitingInstructions.size();i++){
					if(waitingInstructions.get(i).contains("add") || waitingInstructions.get(i).contains("sub") ){
						if(! isFull(additionStation)){
							instructions.add(waitingInstructions.get(i));
							issueInstruction(waitingInstructions.get(i));
							waitingInstructions.remove(waitingInstructions.get(i));
						}
					}else if(waitingInstructions.get(i).contains("mul") || waitingInstructions.get(i).contains("div") ){
						if(! isFull(multiplyStation)){
							instructions.add(waitingInstructions.get(i));
							issueInstruction(waitingInstructions.get(i));
							waitingInstructions.remove(waitingInstructions.get(i));
						}
					}
				}
			}else if(inst instanceof LoadStation){
				String tag=((LoadStation)inst).getTag();
				result=memory[((LoadStation)inst).getAddress()];
				updateRegAndStations(tag, result);
				removeFromStation(tag);
				((LoadStation) inst).setFinishedCycle(cycle-1);
				for(int i=0;i<waitingInstructions.size();i++){
					if(waitingInstructions.get(i).contains("l.d")){
						if(! isFull(loadBuffer)){
							instructions.add(waitingInstructions.get(i));
							issueInstruction(waitingInstructions.get(i));
							waitingInstructions.remove(waitingInstructions.get(i));
						}
					}
					if(waitingInstructions.get(i).contains("s.d") && ((waitingInstructions.get(i).split(" ")[1]).split(","))[1].equals(((LoadStation)inst).getAddress()+"") ){
						if(!isFull(storeBuffer)){
							instructions.add(waitingInstructions.get(i));
							issueInstruction(waitingInstructions.get(i));
							waitingInstructions.remove(waitingInstructions.get(i));
						}
					}
				}
			}else if(inst instanceof StoreStation){
				String tag=((StoreStation) inst).getTag();
				memory[((StoreStation) inst).getAddress()]=((StoreStation) inst).getStoreValue();
				removeFromStation(tag);
				((StoreStation) inst).setFinishedCycle(cycle-1);
				for(int i=0;i<waitingInstructions.size();i++){
					if(waitingInstructions.get(i).contains("s.d")){
						if(! isFull(storeBuffer)){
							instructions.add(waitingInstructions.get(i));
							issueInstruction(waitingInstructions.get(i));
							waitingInstructions.remove(waitingInstructions.get(i));
						}
					}
				}
			}
			
			finishedExecution.remove(inst);
			finishedAll.add(inst);
			executingInstruction.remove(inst);
			
			
		}else if(finishedExecution.size()>1){
			int max=0;
			int index=0;
			for(int i=0;i<finishedExecution.size();i++){
				Object inst=finishedExecution.get(i);
				int current=0;
				if(inst instanceof ReservationStation){
					current=willExecuteAfter(((ReservationStation) inst).getTag());
				}else if(inst instanceof LoadStation){
					current=willExecuteAfter(((LoadStation) inst).getTag());
				}else if(inst instanceof StoreStation){
					current=willExecuteAfter(((StoreStation) inst).getTag());
				}
				if(current>max){
					max=current;
					index=i;
				}
			}
			double result=0;
			Object inst=finishedExecution.get(index);
			if(inst instanceof ReservationStation){
				String op=((ReservationStation) inst).getOp();
				String tag=((ReservationStation) inst).getTag();
				if(op.equals("add")){
					result=((ReservationStation)inst).getVj()+((ReservationStation)inst).getVk();
				}else if(op.equals("sub")){
					result=((ReservationStation)inst).getVj()-((ReservationStation)inst).getVk();
				}else if(op.equals("mul")){
					result=((ReservationStation)inst).getVj()*((ReservationStation)inst).getVk();
				}else if(op.equals("div")){
					result=((ReservationStation)inst).getVj()/((ReservationStation)inst).getVk();
				}
				updateRegAndStations(tag, result);
				removeFromStation(tag);
				((ReservationStation) inst).setFinishedCycle(cycle-1);
				for(int i=0;i<waitingInstructions.size();i++){
					if(waitingInstructions.get(i).contains("add") || waitingInstructions.get(i).contains("sub") ){
						if(! isFull(additionStation)){
							instructions.add(waitingInstructions.get(i));
							issueInstruction(waitingInstructions.get(i));
							waitingInstructions.remove(waitingInstructions.get(i));
						}
					}else if(waitingInstructions.get(i).contains("mul") || waitingInstructions.get(i).contains("div") ){
						if(! isFull(multiplyStation)){
							instructions.add(waitingInstructions.get(i));
							issueInstruction(waitingInstructions.get(i));
							waitingInstructions.remove(waitingInstructions.get(i));
						}
					}
				}
			}else if(inst instanceof LoadStation){
				String tag=((LoadStation)inst).getTag();
				result=memory[((LoadStation)inst).getAddress()];
				updateRegAndStations(tag, result);
				removeFromStation(tag);
				((LoadStation) inst).setFinishedCycle(cycle-1);
				for(int i=0;i<waitingInstructions.size();i++){
					if(waitingInstructions.get(i).contains("l.d")){
						if(! isFull(loadBuffer)){
							instructions.add(waitingInstructions.get(i));
							issueInstruction(waitingInstructions.get(i));
							waitingInstructions.remove(waitingInstructions.get(i));
						}
					}
				}
			}else if(inst instanceof StoreStation){
				String tag=((StoreStation) inst).getTag();
				memory[((StoreStation) inst).getAddress()]=((StoreStation) inst).getStoreValue();
				removeFromStation(tag);
				((StoreStation) inst).setFinishedCycle(cycle-1);
				for(int i=0;i<waitingInstructions.size();i++){
					if(waitingInstructions.get(i).contains("l.d")){
						if(! isFull(storeBuffer)){
							instructions.add(waitingInstructions.get(i));
							issueInstruction(waitingInstructions.get(i));
							waitingInstructions.remove(waitingInstructions.get(i));
						}
					}
				}
			}
			
			finishedExecution.remove(inst);
			executingInstruction.remove(inst);
			finishedAll.add(inst);
			
		}
		
	}
	public static int willExecuteAfter(String tag){
		int count=0;
		for(int i=0;i<additionStation.length;i++){
			if(additionStation[i]!=null && additionStation[i].getQj().equals(tag) && additionStation[i].getQk().equals("")){
				count++;
			}else if(additionStation[i]!=null && additionStation[i].getQj().equals("") && additionStation[i].getQk().equals(tag)){
				count++;
			}
		}
		for(int i=0;i<multiplyStation.length;i++){
			if(multiplyStation[i]!=null && multiplyStation[i].getQj().equals(tag) && multiplyStation[i].getQk().equals("")){
				count++;
			}else if(multiplyStation[i]!=null && multiplyStation[i].getQj().equals("") && multiplyStation[i].getQk().equals(tag)){
				count++;
			}
		}
		for(int i=0;i<storeBuffer.length;i++){
			if(storeBuffer[i]!=null && storeBuffer[i].getQueueValue().equals(tag)){
				count++;
			}
		}
		
		return count;
	}
	public static void removeFromStation(String tag){
		
		if(tag.charAt(0)=='M'){
			multiplyStation[Integer.parseInt(tag.substring(1))-1]=null;
		}else if(tag.charAt(0)=='A'){
			additionStation[Integer.parseInt(tag.substring(1))-1]=null;
		}
		else if(tag.charAt(0)=='L'){	
			
			loadBuffer[Integer.parseInt(tag.substring(1))-1]=null;
			
		}
		else if(tag.charAt(0)=='S'){
			storeBuffer[Integer.parseInt(tag.substring(1))-1]=null;
		}
	}
	public static void updateRegAndStations(String tag,double value){
		for(int i=0;i<registerFile.length;i++){
			if(registerFile[i].getQi().equals(tag)){
				registerFile[i].setQi("");
				registerFile[i].setValue(value);
			}
		}
		for(int i=0;i<loadBuffer.length;i++){
			if(loadBuffer[i] !=null && loadBuffer[i].getQi().equals(tag)){
				loadBuffer[i].setQi("");
				loadBuffer[i].setValue(memory[loadBuffer[i].getAddress()]);
			}
		}
		for(int i=0;i<storeBuffer.length;i++){
			if(storeBuffer[i]!=null && storeBuffer[i].getQueueValue().equals(tag)){
				storeBuffer[i].setQueueValue("");
				storeBuffer[i].setStoreValue(value);
			}
		}
		for(int i=0;i<additionStation.length;i++){
			if(additionStation[i]!=null){
				if(additionStation[i].getQj().equals(tag)){
					additionStation[i].setQj("");
					additionStation[i].setVj(value);
				}
				if(additionStation[i].getQk().equals(tag)){
					additionStation[i].setQk("");
					additionStation[i].setVk(value);
				}
			}
		}
		for(int i=0;i<multiplyStation.length;i++){
			if(multiplyStation[i]!=null){
				if(multiplyStation[i].getQj().equals(tag)){
					multiplyStation[i].setQj("");
					multiplyStation[i].setVj(value);
				}
				if(multiplyStation[i].getQk().equals(tag)){
					multiplyStation[i].setQk("");
					multiplyStation[i].setVk(value);
				}
			}
		}
	}
	public static void setReadyAndExecute(){
		for(int i=0;i<storeBuffer.length;i++){
			if(storeBuffer[i]!=null && storeBuffer[i].getQueueValue()=="" &&
					!executingInstruction.contains(storeBuffer[i])){
				storeBuffer[i].setReady(true);
				storeBuffer[i].setTime(storeCycles);
				storeBuffer[i].setExecutedCycle(cycle+1);
				executingInstruction.add(storeBuffer[i]);
			}
		}
		for(int i=0;i<loadBuffer.length;i++){
			if(loadBuffer[i]!=null && loadBuffer[i].isReady() && !executingInstruction.contains(loadBuffer[i]) ){
				loadBuffer[i].setExecutedCycle(cycle+1);
				executingInstruction.add(loadBuffer[i]);
				
			}
			else if(loadBuffer[i]!=null && waitingForStore(loadBuffer[i].getAddress()).equals("") && !executingInstruction.contains(loadBuffer[i])){
				loadBuffer[i].setReady(true);
				loadBuffer[i].setTime(loadCycles);
				loadBuffer[i].setExecutedCycle(cycle+1);
				executingInstruction.add(loadBuffer[i]);
			}
		}
		for(int i=0;i<additionStation.length;i++){
			if(additionStation[i]!=null && additionStation[i].getQj()=="" && 
					additionStation[i].getQk()=="" && !executingInstruction.contains(additionStation[i])){
				additionStation[i].setReady(true);
				if(additionStation[i].getOp().equals("add")){
					additionStation[i].setTime(additionCycles);
				}else{
					additionStation[i].setTime(subtractionCycles);
				}
				additionStation[i].setExecutedCycle(cycle+1);
				executingInstruction.add(additionStation[i]);
				
			}
		}
		for(int i=0;i<multiplyStation.length;i++){
			if(multiplyStation[i]!=null && multiplyStation[i].getQj()=="" 
					&& multiplyStation[i].getQk()==""
					&& !executingInstruction.contains(multiplyStation[i])){
				multiplyStation[i].setReady(true);
				if(multiplyStation[i].getOp().contains("div"))
					multiplyStation[i].setTime(divCylces);
				else {
					multiplyStation[i].setTime(mulCycles);
				}
				executingInstruction.add(multiplyStation[i]);
			}
		}
	}
	public static void executeInstructions(){
		for(int i=0;i<executingInstruction.size();i++){
			if(executingInstruction.get(i) instanceof ReservationStation){
				ReservationStation current=(ReservationStation)executingInstruction.get(i);
				current.setTime(current.getTime()-1);
				if(current.getOp().contains("add") || current.getOp().contains("sub") ){
					additionStation[Integer.parseInt(current.getTag().substring(1))-1]=current;
				}else if(current.getOp().contains("mul") || current.getOp().contains("div")){
					multiplyStation[Integer.parseInt(current.getTag().substring(1))-1]=current;
				}
			}else if(executingInstruction.get(i) instanceof LoadStation){
				LoadStation current=(LoadStation)executingInstruction.get(i);
				current.setTime(current.getTime()-1);
				loadBuffer[Integer.parseInt(current.getTag().substring(1))-1]=current;
			}else if(executingInstruction.get(i) instanceof StoreStation){
				StoreStation current=(StoreStation)executingInstruction.get(i);
				current.setTime(current.getTime()-1);
				storeBuffer[Integer.parseInt(current.getTag().substring(1))-1]=current;
			}
		}
	}
	//public static Object[] cloneArr(Object[])
	public static boolean isEmptyStation(Object[] arr){
		for(int i=0;i<arr.length;i++){
			if(arr[i]!=null)
				return false;
		}
		return true;
	}
	public void parseCode(ArrayList<String> arr){
		for(int i=0;i<arr.size();i++){
			instructions.add(arr.get(i));
		}
	}
	public static void start(){
		Scanner sc=new Scanner(System.in);
		System.out.println("start entering code line by line and end the code with an empty line");
		String s=sc.nextLine();
		while(!s.equals("")){
			instructions.add(s);
			s=sc.nextLine();
		}
		int x=0;
		System.out.println("enter cylces for load");
		x=Integer.parseInt(sc.nextLine());
		loadCycles=x;
		System.out.println("enter cylces for store");
		x=Integer.parseInt(sc.nextLine());
		storeCycles=x;
		System.out.println("enter cylces for add");
		x=Integer.parseInt(sc.nextLine());
		additionCycles=x;
		System.out.println("enter cylces for sub");
		x=Integer.parseInt(sc.nextLine());
		subtractionCycles=x;
		System.out.println("enter cylces for mul");
		x=Integer.parseInt(sc.nextLine());
		mulCycles=x;
		System.out.println("enter cylces for div");
		x=Integer.parseInt(sc.nextLine());
		divCylces=x;
	
		while(!instructions.isEmpty() ||  !isEmptyStation(additionStation) 
				||  !isEmptyStation(multiplyStation) 
				||  !isEmptyStation(loadBuffer) ||  !isEmptyStation(storeBuffer) || !waitingInstructions.isEmpty() 
				|| !executingInstruction.isEmpty()){
			System.out.println("----------------Cycle: "+cycle+"----------------------------");
			publish();
			executeInstructions();
			System.out.println("Executing Instructions: "+Arrays.toString(executingInstruction.toArray()));
			if(!instructions.isEmpty() && waitingInstructions.isEmpty())
				issueInstruction(instructions.peek());
			setReadyAndExecute();
			System.out.println("Addition Stations: "+Arrays.toString(additionStation));
			System.out.println("Multiply Stations: " +Arrays.toString(multiplyStation));
			System.out.println("Load Statios: "+Arrays.toString(loadBuffer));
			System.out.println("Store Stations: "+Arrays.toString(storeBuffer));
			System.out.println("Register File: "+Arrays.toString(registerFile));
			System.out.println("Memory: "+Arrays.toString(memory));
			if(!waitingInstructions.isEmpty()){
				System.out.println("Waiting Instructions: "+Arrays.toString(waitingInstructions.toArray()));
			}
			
			cyclesData.add(new DataCycle(cycle, multiplyStation.clone(), additionStation.clone(), loadBuffer.clone(), storeBuffer.clone(), registerFile.clone(), executingInstruction, waitingInstructions));
			cycle++;
		}
		System.out.println(Arrays.toString(cyclesData.get(1).getAdditionStation()));
		
	}
	public static void main(String[] args){
		
		Main m=new Main();
		
		
	}
	

}
//mul.d f3,f1,f2
//add.d f5,f3,f4
//add.d f7,f2,f6
//add.d f10,f8,f9
//mul.d f11,f7,f10
//add.d f5,f5,f11


//l.d f6 32
//l.d f2 44
//mul.d f0 f2 f4
//sub.d f8 f2 f6
//div.d f10 f0 f6
//add.d f6 f8 f2
