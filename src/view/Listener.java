package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.plaf.multi.MultiButtonUI;

import Engine.DataCycle;
import Engine.LoadStation;
import Engine.Register;
import Engine.ReservationStation;
import Engine.StoreStation;
import view.Graph;
public class Listener implements ActionListener, MouseListener{
	Model m ;
	String Code = "";
	int CurrentCycle = 1;
	Listener (Model m){
		this.m = m ;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
//		String s = "";
//		if(arg0.getComponent() instanceof JButton)
//			 s =((JButton)arg0.getComponent()).getActionCommand();
//		if(s.equals("Next")){
//			CurrentCycle+=1;
//			s="";
//		}
//		if(s.equals("Prev")){
//			CurrentCycle-=1;
//			s="";
//		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String s = arg0.getActionCommand();
		if (m.CodePage) {
			if (s.equals("Run") ) {
				
				Code=m.g.CodeArea.getText();
				String[] CodeList = Code.split("\\R");
				ArrayList<String> CodeArray = new ArrayList<String>();
				for (String string : CodeList) {
					CodeArray.add(string);
				}
				m.main.parseCode(CodeArray);
				
				// ==== load
					if(m.g.LoadCycles.getText().length()!=0 && !m.g.LoadCycles.getText().equals(" ")){
						try{					
							m.main.loadCycles = Integer.parseInt(m.g.LoadCycles.getText());
						}catch(Exception e){
							JOptionPane.showMessageDialog(m,"Load Cycles should be a number","Alert",JOptionPane.WARNING_MESSAGE);					
							}
						}
					else
						m.main.loadCycles = 2;  
					
				
				// ===== store
					if(m.g.StoreCycles.getText().length()!=0&& !m.g.StoreCycles.getText().equals(" ")){
						try{					
							m.main.storeCycles = Integer.parseInt(m.g.StoreCycles.getText());
						}catch(Exception e){
							JOptionPane.showMessageDialog(m,"Store Cycles should be a number","Alert",JOptionPane.WARNING_MESSAGE);					
						}
					}
					else
						m.main.storeCycles = 2;  
					
				// ===== add
					if(m.g.AddCycles.getText().length()!=0&& !m.g.AddCycles.getText().equals(" ")){
						try{					
							m.main.additionCycles = Integer.parseInt(m.g.AddCycles.getText());
						}catch(Exception e){
							JOptionPane.showMessageDialog(m,"add Cycles should be a number","Alert",JOptionPane.WARNING_MESSAGE);					
						}
					}
					else
						m.main.additionCycles = 2;  
					
				// ===== mul
					if(m.g.MulCycles.getText().length()!=0 && !m.g.MulCycles.getText().equals(" ")){
						
						try{					
							m.main.mulCycles = Integer.parseInt(m.g.MulCycles.getText());
							}catch(Exception e){
							JOptionPane.showMessageDialog(m,"mul Cycles should be a number","Alert",JOptionPane.WARNING_MESSAGE);					
						}
					}
					else
						m.main.mulCycles = 2;  
					
				// ===== sub
					if(m.g.SubCycles.getText().length()!=0  && !m.g.SubCycles.getText().equals(" "))
						{
						try{					
					m.main.subtractionCycles = Integer.parseInt(m.g.SubCycles.getText());
						}catch(Exception e){
							JOptionPane.showMessageDialog(m,"Sub Cycles should be a number","Alert",JOptionPane.WARNING_MESSAGE);					
						}
						}
					else
						m.main.subtractionCycles = 2;  
					
				// ===== div
					if(m.g.DivCycles.getText().length()!=0 && !m.g.DivCycles.getText().equals(" ")){
				try{					
					m.main.divCylces = Integer.parseInt(m.g.DivCycles.getText());
				}catch(Exception e){
					JOptionPane.showMessageDialog(m,"Div Cycles should be a number","Alert",JOptionPane.WARNING_MESSAGE);					
				}
				}
					else
						m.main.divCylces = 2;  
					
	
					
				m.main.start();
				m.CodePage=false;
				m.CyclePage=true;
				
				
			}
			m.g.removeAll();
			m.g.repaint();
			m.repaint();
		}else if(m.CyclePage){
			if(s.equals("Next")){
				CurrentCycle+=1;
				s="";
			}
			if(s.equals("Prev")){
				CurrentCycle-=1;
				s="";
			}
			if(CurrentCycle==1){
				m.prev.setEnabled(false);
			}else{
				m.prev.setEnabled(true);
			}
			if(CurrentCycle==m.main.cycle-1){				
				m.next.setEnabled(false);
			}else{
				m.next.setEnabled(true);
			}
			

			
			DataCycle DataCycle = m.main.cyclesData.get(CurrentCycle-1);
			ReservationStation[] AddStation = DataCycle.getAdditionStation();
			
			for (int i = 0; i < AddStation.length; i++) {
				
					if(AddStation[i]!=null){
					
					m.g.AddButtons[i][0].setText(AddStation[i].getTime()+"");
					m.g.AddButtons[i][1].setText(AddStation[i].getTag()+"");
					m.g.AddButtons[i][2].setText(AddStation[i].getBusy()+"");
					m.g.AddButtons[i][3].setText(AddStation[i].getOp()+"");
					m.g.AddButtons[i][4].setText(AddStation[i].getVj()+"");
					m.g.AddButtons[i][5].setText(AddStation[i].getVk()+"");
					m.g.AddButtons[i][6].setText(AddStation[i].getQj()+"");
					m.g.AddButtons[i][7].setText(AddStation[i].getQk()+"");
					m.g.AddButtons[i][8].setText(AddStation[i].getA()+"");
					}else {
						m.g.AddButtons[i][0].setText("");
						m.g.AddButtons[i][1].setText("");
						m.g.AddButtons[i][2].setText("");
						m.g.AddButtons[i][3].setText("");
						m.g.AddButtons[i][4].setText("");
						m.g.AddButtons[i][5].setText("");
						m.g.AddButtons[i][6].setText("");
						m.g.AddButtons[i][7].setText("");
						m.g.AddButtons[i][8].setText("");
					}
			}
			//================== mul Station ============== 
			
			ReservationStation[] MulStation = DataCycle.getMultiplyStation();
			
			for (int i = 0; i < MulStation.length; i++) {
				
					if(MulStation[i]!=null){
					
					m.g.MulButtons[i][0].setText(MulStation[i].getTime()+"");
					m.g.MulButtons[i][1].setText(MulStation[i].getTag()+"");
					m.g.MulButtons[i][2].setText(MulStation[i].getBusy()+"");
					m.g.MulButtons[i][3].setText(MulStation[i].getOp()+"");
					m.g.MulButtons[i][4].setText(MulStation[i].getVj()+"");
					m.g.MulButtons[i][5].setText(MulStation[i].getVk()+"");
					m.g.MulButtons[i][6].setText(MulStation[i].getQj()+"");
					m.g.MulButtons[i][7].setText(MulStation[i].getQk()+"");
					m.g.MulButtons[i][8].setText(MulStation[i].getA()+"");
					}else {
						m.g.MulButtons[i][0].setText("");
						m.g.MulButtons[i][1].setText("");
						m.g.MulButtons[i][2].setText("");
						m.g.MulButtons[i][3].setText("");
						m.g.MulButtons[i][4].setText("");
						m.g.MulButtons[i][5].setText("");
						m.g.MulButtons[i][6].setText("");
						m.g.MulButtons[i][7].setText("");
						m.g.MulButtons[i][8].setText("");
					}
			}
//================== mul Station ============== 
			
			LoadStation[] LoadStation = DataCycle.getLoadBuffer();
			
			for (int i = 0; i < LoadStation.length; i++) {
				
					if(LoadStation[i]!=null){
					
					m.g.LoadButtons[i][0].setText(LoadStation[i].getTag()+"");
					m.g.LoadButtons[i][1].setText(LoadStation[i].getBusy()+"");
					m.g.LoadButtons[i][2].setText(LoadStation[i].getAddress()+"");
				
					}else{
						m.g.LoadButtons[i][0].setText("");
						m.g.LoadButtons[i][1].setText("");
						m.g.LoadButtons[i][2].setText("");
					
					}
			}

			StoreStation[] StoreStation = DataCycle.getStoreBuffer();
			
			for (int i = 0; i < StoreStation.length; i++) {
				
					if(StoreStation[i]!=null){
					
					m.g.StoreButtons[i][0].setText(StoreStation[i].getTag()+"");
					m.g.StoreButtons[i][1].setText(StoreStation[i].getBusy()+"");
					m.g.StoreButtons[i][2].setText(StoreStation[i].getAddress()+"");
					m.g.StoreButtons[i][3].setText(StoreStation[i].getStoreValue()+"");
					m.g.StoreButtons[i][4].setText(StoreStation[i].getQueueValue()+"");
				
					}else{
						m.g.StoreButtons[i][0].setText("");
						m.g.StoreButtons[i][1].setText("");
						m.g.StoreButtons[i][2].setText("");
						m.g.StoreButtons[i][3].setText("");
						m.g.StoreButtons[i][4].setText("");
					
					}
			}
			
			ArrayList<String[]> RegStation = DataCycle.getRegisterFile();
			int k=0;
			for (k = 0; k < RegStation.size(); k++) {
				
					if(RegStation.get(k)!=null){
					
					m.g.RegButtons[k][0].setText(RegStation.get(k)[0]);
					m.g.RegButtons[k][1].setText(RegStation.get(k)[1]);
					m.g.RegButtons[k][2].setText(RegStation.get(k)[2]);
				
					}else{
						m.g.RegButtons[k][0].setText("");
						m.g.RegButtons[k][1].setText("");
						m.g.RegButtons[k][2].setText("");

					}
			}
			for(;k<m.g.RegButtons.length;k++){
				m.g.RegButtons[k][0].setText("");
				m.g.RegButtons[k][1].setText("");
				m.g.RegButtons[k][2].setText("");
			}
			ArrayList<String[]> ExecStation = DataCycle.getExecutingInstructions();
			int i=0;
			for (i = 0; i < ExecStation.size(); i++) {
				m.g.ExecButtons[i][0].setText("Time :"+ExecStation.get(i)[0]+"_"+ExecStation.get(i)[1]);
					
			}
			for(;i<m.g.ExecButtons.length;i++){
				m.g.ExecButtons[i][0].setText("");
			}

		}
		
		m.g.removeAll();
		m.g.repaint();
		m.repaint();
	}

}

