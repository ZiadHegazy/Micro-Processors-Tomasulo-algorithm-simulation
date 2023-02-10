package Engine;

public class Register {
	private String tag;
	private double value;
	private String qi;
	
	
	public Register(String tag, int value, String qi) {
		this.tag = tag;
		this.value = value;
		this.qi = qi;
	}

	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getQi() {
		return qi;
	}
	public void setQi(String qi) {
		this.qi = qi;
	}
	public String toString(){
		if(qi.equals(""))
			return "Tag: "+tag+" Value: "+value+" Qi: none ";
		else {
			return "Tag: "+tag+" Value: not ready "+" Qi: "+qi;
		}
	}

}
